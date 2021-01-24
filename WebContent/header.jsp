<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.time.*" %>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
html { font-size:16px; line-height:1.5; }
body { margin:0; color:#000; padding-top:30px; }
.page-header h1, h2, h3, p, ul { margin:0; }
.page-header ul { padding-right:100px; }
.page-header ul li { list-style-type:none; }
.page-header a { color:inherit; text-decoration:none; }
.page-header img { vertical-align:middle; margin-left:100px; }
.main { margin:0 auto; max-width:75em; padding:8em 0; }
.page-header {
	background:#efede9; position:absolute; width:100%; min-width:1200px;
	-webkit-box-shadow:0 1px 1px rgba(0, 0, 0, 0.25);
			-box-shadow:0 1px 1px rgba(0, 0, 0, 0.25);
			z-index:1;
}
.sticky { position:fixed; top:0; }
/* 메뉴바가 스크롤에 의해 화면에서 사라질 때 사용할 스타일 */
.page-header .inner { margin:auto; width:960px; }
.no-boxshadow .page-header { border-bottom:1px solid #f2f2f2; }
.site-logo { float:left; margin-left:-20px; }
.primary-nav { 
	float:right; line-height:80px;
	letter-spacing:1px; text-transform:uppercase;
}
.primary-nav li { float:left; }
.primary-nav a { display:block; padding:0 1.36em; }
.primary-nav a:hover { background:#cccac4; }

</style>
<script src="jquery-3.5.1.js"></script>
<script src="./jquery-ui-1.10.3.custom.min.js"></script>
<script>
$(document).ready(function() {
	$(".page-header").each(function() {
	// 해당 선택자에 대해 무한루프를 돌면서 작업함
		var $window = $(window),	// 창 객체를 변수에 담음
		$header = $(this),			// .page-header 객체를 변수에 담음
		headerOffsetTop = $header.offset().top;
		// .page-header 객체의 최상위 좌표값을 변수에 담음

		$window.on('scroll', function() {
		// 창 객체(window)에 scroll 이벤트가 발생하면
			if ($window.scrollTop() > headerOffsetTop) {
			// 스크롤바의 최상위 위치가 .page-header 객체의 최상위 위치보다 크면(아래에 있으면)
			// $window.scrollTop() : 스크롤바의 최상위 위치
				$header.addClass('sticky');
				// $header에 sticky라는 클래스를 추가
			} else {
				$header.removeClass('sticky');
				// $header에서 sticky라는 클래스를 제거
			}
		});

		$window.trigger('scroll');
		// window객체에 scroll이벤트를 발생시킴
	});
});
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<link rel="stylesheet" href="style.css" />
 <div class="header-login">
    <%
    if (loginMember == null) {
    %>
    <a href="login_form.jsp"  style="text-decoration:none; font-size:0.8em;">로그인</a>
    <%
    } else { %>
    <%=loginMember.getMl_id() %>(<%=loginMember.getMl_name() %>)님 환영합니다. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="logout"  style="text-decoration:none; font-size:0.8em;" >로그아웃</a>
	<a href="mypage_view.mem">마이페이지</a>
    <%
    }
    %>
    </div>
<header class = "page-header">
    <div class="inner">
 	  	<h1 class="header-logo" align="center"><a href="main.jsp"><img src="images/main-logo.jpg" width="223" /></a></h1>
    </div>
   
    <nav class="primary-nav">
    	<ul>
         <li><a href="pdt_list.pdt?bcata=1"><b>여성한복</b></a></li>
         <li><a href="pdt_list.pdt?bcata=2"><b>남성한복</b></a></li>
         <li><a href="pdt_list.pdt?bcata=3"><b>장신구</b></a></li>
         <li><a href="review_list.review"><b>리뷰 게시판</b></a></li>
         <li><a href="member_list.amem"><b>1:1 문의</b></a></li>
         <li><a href="member_list.amem"><b>대여 시스템</b></a></li>
     </ul>
    </nav>
</header>
</body>
</html>