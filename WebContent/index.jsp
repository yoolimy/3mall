<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.time.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="style.css" />
<title>Insert title here</title>

</head>
<body>
    <div class="wrapper">
        <div class="header">
        	<div class="header-logo">
        	   	<img src="images/hanbok.jpg" />
        	</div>
            <div class="header-login">
                <%
                if (loginMember == null) {
                %>
                <a href="login_form.jsp">로그인</a>
                <%
                } else { %>
<<<<<<< HEAD
                <%=loginMember.getMlid() %>(<%=loginMember.getMlname() %>)님 환영합니다.<br />
               	<!--  현재 <%=loginMember.getMlpoint() %>p 보유중<br /> -->
=======
                <%=loginMember.getMl_id() %>(<%=loginMember.getMl_name() %>)님 환영합니다.<br /> 
>>>>>>> ddf3e0213694770ec9b1637a8cbc833680d459a8
                <a href="logout">로그아웃</a>
                <%
                }
                %>
            </div>
            <div class="header-menu">
                <a href="pdt_list.pdta">상품목록-백엔드</a>
                <hr />
                <a href="pdt_list.pdt">상품목록-프론트</a>
                <hr />
                <a href="pdt_list.pdt?bcata=1">여성한복</a>
                <a href="pdt_list.pdt?bcata=2">남성한복</a>
<<<<<<< HEAD
                <a href="pdt_list.pdt?bcata=3">장신구</a>
                <a href="review_list.review">리뷰 게시판</a>
=======
                <a href="pdt_list.pdt?bcata=3">장신구</a><br />
                <hr />
                <a href="member_list.amem">회원 목록</a>
>>>>>>> ddf3e0213694770ec9b1637a8cbc833680d459a8
            </div>
        </div>
        <div class="main">

        </div>
        <div class="footer">
    
        </div>
    </div>
</body>
</html>