<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
AdminMemberInfo adminMember = (AdminMemberInfo)session.getAttribute("adminMember");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
/* div { border:1px solid black; } */
body { 
	background-color:rgb(222, 222, 222); margin:0; padding:0; 
}
ul { margin:5px; padding:1px; }
li { list-style:none; }
.wrapper { position:relative; width:1200px; margin:0px; padding:0px; }
.main { 
	margin:5px auto; border-top:1px solid black; border-bottom:1px solid black; 
	width:1198px; height:720px; 
}
.main-menu { 
	position:relative; width:195px; float:left; height:720px; 
	border-right:1px solid black;
	background-color: rgb(59, 59, 55); 
	color:azure;
}
.main-content { position:relative; width:995px; float:left; height:720px; }
a { text-decoration:none; }
a:link { color:azure; }
a:visited { color:azure; }
a:hover { text-decoration:underline; }

.mc1 {
	position:relative; padding:18px;
	top:30px; left:10px; width:380px; height:200px;
	background-color:rgba(73, 156, 189, 0.836); color:azure;
}
.help {
	position:relative;
	left:340px; top:50px;
	text-decoration:underline;
}
.mc2 { position:absolute;
	left:435px; top:68px; width:114px; height:114px;
	background-color:rgb(241, 241, 241); color:rgb(160, 160, 160); font-size:13px;
	text-align:center;
}
.mc3 { position:absolute; 
	left:435px; top:190px; width:114px; height:114px;
	background-color:rgb(241, 241, 241); color:rgb(160, 160, 160); font-size:13px;
	text-align:center;
}
.mc4 { position:absolute;
	left:558px; top:68px; width:114px; height:114px;
	background-color:rgb(241, 241, 241); color:rgb(160, 160, 160); font-size:13px;
	text-align:center;
}
</style>
</head>
<!--  <frameset rows="10%, *" frameborder="1" border="1">
	<frame src ="./header.html" name="frame-header">
	<frameset cols="200px, *" frameborder="1">
		<frame src ="./menubar.jsp" name="frame-menubar">
		<frame src ="./main-content.jsp" name="frame-main">
	</frameset>
</frameset>
-->
<body>
	<div class="wrapper">
		<div class="header">
		<div style="width:100%;font-size:30px;line-height:48px;background-color:gray;color:white;margin-bottom:10px">&nbsp;&nbsp;Naedam Admin Page</div>
		</div>
		<div class="main">
			<div class="main-menu">
				<h3>Member</h3>
				<ul>
					<li><a href="../member_list.amem">회원 리스트</a></li>
					<li><a href="../admin_mem_list.amem">관리자 리스트</a></li><br />
				</ul>
				<h3>Product</h3>
				<ul>
					<li><a href="../pdt_list.pdta">상품 관리</a></li><br />
				</ul>
				<h3>Statistics</h3>
				<ul>
					<li>회원 가입 추이</li>
					<li>월별 판매량</li>
					<li><a href="../sales_chart.chart">월별 매출액</a></li><br />
				</ul>
			</div>
			<div class="main-content">
				&nbsp;안녕하세요,
				<strong>[<%=adminMember.getAl_team() %>] <%=adminMember.getAl_name() %> <%=adminMember.getAl_position() %></strong>님<hr />
				<div class="mc1">
					<h3>Welcome to Admin Page</h3>
					This is your Application dashboard, where you can easily access the most important for administrator.<br />
					<br />
					<div class="help"><a href="#">help</a></div>
				</div>
				<div class="mc2" style="cursor:pointer;" onclick="location.href='../member_list.amem';">
					<br /><br /><br /><br />Member List
				</div>
				<div class="mc3" style="cursor:pointer;">
					<br /><br /><br /><br />Admin List
				</div>
				<div class="mc4" style="cursor:pointer;" onclick="location.href='../pdt_list.pdta';">
					<br /><br /><br /><br />Product List
				</div>
			</div>
		</div>
		<div class="footer">
			<h4>&nbsp;ⓒ Naedam</h4>
		</div>
	</div>
</body>
</html>