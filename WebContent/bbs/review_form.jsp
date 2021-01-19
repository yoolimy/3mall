<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="java.net.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
//로그인 되어 있을 경우 로그인 정보(현재 로그인 한 회원의 정보)를 받아옴

request.setCharacterEncoding("utf-8");
String wtype = request.getParameter("wtype");
String args = "";


String msg = "수정", writer ="", title = "", content = "", ismember = "";
int idx = 0;
if (wtype.equals("in")) {
	msg = "등록";
} else if (wtype.equals("up")) {	// 게시글 수정일 경우
	ReviewInfo article = (ReviewInfo)request.getAttribute("article");
	// 수정할 게시글의 데이터가 저장된 article 인스턴스를 request에서 받아 생성
	idx = article.getRl_idx();
	writer = article.getMl_id();
	title = article.getRl_title();
	content = article.getRl_content();
	ismember = article.getRl_ismember();	// 게시글의 회원/비회원 여부
	
	String schtype = request.getParameter("schtype");
	String keyword = request.getParameter("keyword");
	if (keyword != null && !keyword.equals("")) {
		args += "&schtype=" + schtype + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
	}
}
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
th { background:#D7AC87;}
</style>
</head>
<body>
<h2>리뷰작성</h2>
<form name="frmReview" action="brd_proc.review" method="post">
<input type="hidden" name="idx" value="<%=idx %>" />
<input type="hidden" name="wtype" value="<%=wtype %>" />
<input type="hidden" name="ismember" value="<%=ismember %>" />
<table cellpadding="5">
<tr>
<th>아이디</th><td><%=loginMember.getMlid() %></td>
</tr>
<tr>
<th>카테고리</th><td>[ㅇㅇ한복]</td>
</tr>
<tr>
<th>상품명</th><td>[ㅇㅇ한복]</td>
</tr>
<tr>
<th>제목</th><td><input type="text" name="title" size="58" <%=title %>/></td>
</tr>
<tr>
<th>문의내용</th>
<td><textarea name="content" rows="10" cols="60"><%=content %></textarea></td>
</tr>
<tr>
<th>첨부파일</th><td><input type="file" name="img"  /></td>
</tr>
<tr>
<td colspan="2">최대 1.5MB 이하의 (JPG.JPEG,GIF,PNG) 파일만 가능합니다.</td>
</tr>
<tr>
<td colspan="2" align="right"><input type="submit" value="완료"/></td>
</tr>
</table>
</form>
</body>
</html>