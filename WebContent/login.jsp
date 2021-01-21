<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>    
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
if (loginMember == null) {
%>
<a href="login_form.jsp">로그인</a><br />
<a href="join_form.jsp">회원가입</a>
<%
} else {
%>
<%=loginMember.getMl_id() + "(" + loginMember.getMl_name() + ")" %>님 환영합니다.<br />
<a href="logout">로그아웃</a>

<%
}
%>
<hr />
<a href="member_list.amem">회원관리페이지</a>
<a href="mypage_view.mem">마이페이지</a>
</body>
</html>