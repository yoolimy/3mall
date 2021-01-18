<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%

MemberInfo memberInfo = (MemberInfo)request.getAttribute("memberInfo");
MemberAddrInfo memberAddrInfoFirst = (MemberAddrInfo)request.getAttribute("memberAddrInfoFirst");
MemberAddrInfo memberAddrInfoSecond = (MemberAddrInfo)request.getAttribute("memberAddrInfoSecond");
MemberPageInfo memberPageInfo = (MemberPageInfo)request.getAttribute("memberPageInfo");



request.setCharacterEncoding("utf-8");

int cpage = Integer.parseInt(request.getParameter("cpage"));
int psize = Integer.parseInt(request.getParameter("psize"));

// 검색 조건 및 정렬조건 쿼리스트링을 받음
String id, schtype, keyword, gender, ord;

id = request.getParameter("id");
gender = request.getParameter("gender");	// 게시여부(y, n)
schtype = request.getParameter("schtype");	// 검색조건으로 상품아이디(id)와 상품명(name)
keyword = request.getParameter("keyword");	// 검색어
ord = request.getParameter("ord");			// 정렬조건

String args = "?cpage=" + cpage + "&psize=" + psize;

if (gender != null && !gender.equals("")) {
	args += "&gender='" + gender;
} 

if (keyword != null && !keyword.equals("")) {
	args += "&schtype=" + schtype + "&keyword=" + keyword;
}
if (ord != null && !ord.equals("")) {
	args += "&ord=" + ord;
}


//관리자 메모
String memo = "";
if (memberInfo.getMl_memo() != null && !memberInfo.getMl_memo().equals("")) {
	memo = memberInfo.getMl_memo();
} else {
	memo = "메모 없음";
}

//메일 수신 동의
String agrEmail = "";
if (memberInfo.getMl_agremail().equals("y")) {
	agrEmail = "동의";
} else {
	agrEmail = "비동의";
}


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>회원 상세보기 화면</h2>
<form name="frmMemberView" action="member_up_proc.amem" method="post" >
<input type="hidden" name="id" value="<%=memberInfo.getMl_id() %>" />
<input type="hidden" name="args" value="<%=args %>" />
<table width="900" cellpadding="5" border="1">
	<tr>
		<th width="20%">아이디</th>
		<td><%=id %></td>
		<th width="20%">성별</th>
		<td><%=memberInfo.getMl_gender() %></td>
	</tr>
	<tr>
		<th>이름</th>
		<td><%=memberInfo.getMl_name() %></td>
		<th>회원상태</th>
		<td>
			<select name="memberStatus">
				<option value="a" <%if (memberInfo.getMl_status().equals("a")) { %>selected="selected"<%} %>>회원</option>
				<option value="b" <%if (memberInfo.getMl_status().equals("b")) { %>selected="selected"<%} %>>휴면</option>
				<option value="c" <%if (memberInfo.getMl_status().equals("c")) { %>selected="selected"<%} %>>탈퇴</option>
			</select>
		</td>
	</tr>
	<tr>
		<th>전화번호</th>
		<td><%=memberInfo.getMl_phone() %></td>
		<th>생년월일</th>
		<td><%=memberInfo.getMl_birth().substring(0, 4)%>년 <%=memberInfo.getMl_birth().substring(4, 6)%>월 <%=memberInfo.getMl_birth().substring(6, 8)%>일</td>
	</tr>
	<tr>
		<th>이메일</th>
		<td><%=memberInfo.getMl_email() %></td>
		<th>수신여부</th>
		<td><%=agrEmail %></td>
	</tr>
	<tr>
		<th>가입일</th>
		<td><%=memberInfo.getMl_date().substring(0, 10).replace("-", ".") %></td>
		<th>최종 로그인</th>
		<td><%=memberInfo.getMl_last().replace("-", ".") %></td>
	</tr>
<%
	if (memberAddrInfoFirst != null && "y".equals(memberAddrInfoFirst.getMa_basic())) {
%>
	<tr>
		<th>기본 배송지</th>
		<td colspan="4">[<%=memberAddrInfoFirst.getMa_zip() %>]&nbsp;<%=memberAddrInfoFirst.getMa_addr1() %>&nbsp;<%=memberAddrInfoFirst.getMa_addr2() %></td>
	</tr>
<% } else { %>
	<tr>
		<th>기본 배송지</th>
		<td colspan="4">추가된 주소가 없습니다.</td>
	</tr>
<% } %>
<%
	if (memberAddrInfoSecond != null && "n".equals(memberAddrInfoSecond.getMa_basic())) {	
%>
	<tr>
		<th>주소 2</th>
		<td colspan="4">[<%=memberAddrInfoSecond.getMa_zip() %>]&nbsp;<%=memberAddrInfoSecond.getMa_addr1() %>&nbsp;<%=memberAddrInfoSecond.getMa_addr2() %></td>
	</tr>
<% } else {%>
	<tr>
		<th>주소 2</th>
		<td colspan="4">추가된 주소가 없습니다.</td>
	</tr>
<% } %>
	<tr>
		<th>관리자 메모</th>
		<td colspan="4">
			<input type="text" name="memo" value="<%=memo %>" />
		</td>
	</tr>
	<tr align="center">
		<td colspan="5">
			<input type="submit" value="수정" />
			<input type="button" value="목록" onclick="location.href='member_list.amem<%=args %>';" />
		</td>
	</tr>
</table>
</form>
</body>
</html>