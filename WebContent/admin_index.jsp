<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.box { 
	border:1px solid black; position:relative; top:100px; left:300px; 
	width:300px; padding-left:20px; padding-bottom:10px; margin:10px;
}
</style>
</head>
<body>
<form name="aLogin" action="admLogin" method="post">
<div class="box">
	<h3>Admin Login Page</h3>
	<table cellpadding="5">
	<tr><th>아이디</th><td><input type="text" name="aid" value="admin"></td></tr>
	<tr><th>비밀번호</th><td><input type="password" name="pwd" value="1234"></tr>
	<tr><td colspan="2" align="center">
		<input type="submit" value="로그인" />&nbsp;&nbsp;
		<input type="reset" value="다시 입력" />
	</td></tr>
	</table>
</div>
</form>
</body>
</html>