<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
ReviewInfo article = (ReviewInfo)request.getAttribute("article");
if (article == null) {
// 저장된 게시물이 없으면
	out.println("<script>");
	out.println("alert('잘못된 경로로 들어오셨습니다.');");
	out.println("history.back();");
	out.println("</script>");
}

String uid = null;
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
if (loginMember != null) 	uid = loginMember.getMl_id();
// 현재 로그인한 상태이면 로그인 아이디를 uid에 저장

int idx = Integer.parseInt(request.getParameter("idx"));
int cpage = Integer.parseInt(request.getParameter("cpage"));
String schtype = request.getParameter("schtype");
String keyword = request.getParameter("keyword");
String args = "?cpage=" + cpage;
if (schtype != null &&  keyword != null && !keyword.equals("")) {
	args += "&schtype=" + schtype + "&keyword=" + keyword;
	// 검새겅가 있으면 검색어를 쿼리스트링으로 가져감
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#top td, #top th { border-bottom:1px black solid; }
#top { background:#D7AC87; }
</style>
</head>
<body>
<table width="700" align="center" cellpadding="5" cellspacing="0" id="top">
<tr>
<th width="30%">[카테고리 명] [상품명]</th>
<th width="*">[<%=article.getRl_title() %>]</th>
<th width="20%">[<%=uid %>]</th>
<th width="20%">[<%=article.getRl_date().substring(2,10).replace('.', '-') %>]</th>
</tr>
</table>
<table width="700" height="300" align="center">
<tr>
<td width="40%" align="center"><img src="1.jpg"/></td>
<td width="*"><%=article.getRl_content() %></td>
</tr>
<tr>
<td><input type="button" value="목록" onclick="location.href='brd_list.review<%=args %>';" /></td>
<%
boolean isPms = false;	// 수정 및 삭제 권한이 있는지 여부를 저장할 변수
String link1 = null, link2 = null;
// 각각 수정과 삭제시 이동할 url을 저장할 변수로 회원과 비회원의 경우 url이 달라짐

if (uid != null && uid.equals(article.getMl_id())) {
	isPms = true;
	link1 = "location.href='brd_form.review" + args + "&wtype=up&idx=" + idx + 
	"&ismember=" + article.getRl_ismember() + "';";
	// 로그인한 아이디로 검사했으므로 바로 수정폼으로 이동
	link2 = "notCool(" + idx + ");";
	// 삭제는 물어본후 실행하도록 자바스크립트 함수를 연결
	
}

if (isPms) {	// 수정 및 삭제 권한이 있거나 비회원 글이면
	if (article.getRl_ismember().equals("y")) {
	// 회원이 등록한 글일 경우(현재 글이 로그인한 회원의 글이면)
%>
<script>

function notCool(idx) {
	if (confirm("정말 삭제하시겠습니까?")) {
		location.href="brd_proc.review?wtype=del&idx="  + idx + "&ismember=<%=article.getRl_ismember()%>";
	}
}

</script>
<%
	}
%>
	<td><input type="button" value="수정" onclick="<%=link1 %>" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<td><input type="button" value="삭제" onclick="<%=link2 %>" />
<% } %>
</tr>
</table>
</body>
</html>