<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
div img { margin-left:90px; }

</style>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="main">
	<img src="images/rantSystemInfo.jpg" width="1000px" height="500px"/>
</div>
</body>
</html>