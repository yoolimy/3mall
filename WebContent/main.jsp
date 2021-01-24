<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.time.*" %>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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

.slideshow { 
	background:#000; height:425px; min-width:960px; 
	overflow:hidden; position:relative;
}
.slideshow-slides { height:100%; position:absolute; width:100%; }
.slideshow-slides .slide {
	height:100%; width:100%; overflow:hidden; position:absolute;
}
.slideshow-slides .slide img {
	left:50%; margin-left:-800px; position:absolute;
}
.slideshow-nav a, .slideshow-indicator a {
	background:rgba(0, 0, 0, 0); overflow:hidden;
}
.slideshow-nav a:before, .slideshow-indicator a:before {
	content:url("./images/sprites.png");
	display:inline-block; font-size:0; line-height:0;
}
.slideshow-nav a {
	position:absolute; top:50%; left:50%; width:72px;
	height:72px; margin-top:-36px;
}
.slideshow-nav a.prev { margin-left:-480px; }
.slideshow-nav a.prev:before { margin-top:-20px; }
.slideshow-nav a.next { margin-left:408px; }
.slideshow-nav a.next:before { margin:-20px 0 0 -80px; }
.slideshow-nav a.disabled { display:none; }

.slideshow-indicator {
	bottom:30px; height:16px; left:0; position:absolute;
	right:0; text-align:center;
}
.slideshow-indicator a {
	display:inline-block; width:16px; height:16px; margin:0 3px;
}
.slideshow-indicator a.active { cursor:default; }
.slideshow-indicator a:before { margin-left:-110px; }
.slideshow-indicator a.active:before { margin-left:-130px; }
.best-new { float:left; padding:15px; }
.pdtBox { float:left; padding:5px; }
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
$(document).ready(function() {
	$(".slideshow").each(function() {
		var $container = $(this),
			$slideGroup = $container.find(".slideshow-slides"),
			$slides = $slideGroup.find(".slide"),
			$nav = $container.find(".slideshow-nav"),
			$indicator = $container.find(".slideshow-indicator"),
			slideCount = $slides.length,
			indicatorHTML = "",
			currentIndex = 0,
			duration = 500,
			easing = "easeInOutExpo",
			interval = 5000,
			timer;

		$slides.each(function(i) {
			$(this).css({left:100 * i + "%"});
			indicatorHTML += "<a href='#'>" + (i + 1) + "</a>";
		});
		$indicator.html(indicatorHTML);

		function goToSlide(idx) {
			$slideGroup.animate({left:-100 * idx + "%"}, duration, easing);
			currentIndex = idx;
			updateNav();
		}

		function updateNav() {
			var $navPrev = $nav.find(".prev");
			var $navNext = $nav.find(".next");

			if (currentIndex == 0) {
				$navPrev.addClass("disabled");
			} else {
				$navPrev.removeClass("disabled");
			}
			if (currentIndex == slideCount - 1) {
				$navNext.addClass("disabled");
			} else {
				$navNext.removeClass("disabled");
			}

			$indicator.find("a").removeClass("active").eq(currentIndex).addClass("active");
		}

		function startTimer() {
			timer = setInterval(function() {
				var nextIndex = (currentIndex + 1) % slideCount;
				goToSlide(nextIndex);
			}, interval);
		}

		function stopTimer() {
			clearInterval(timer);
		}

		$nav.on("click", "a", function(event) {
			event.preventDefault();
			if ($(this).hasClass("prev")) {
				goToSlide(currentIndex - 1);
			} else {
				goToSlide(currentIndex + 1);
			}
		});

		$indicator.on("click", "a", function(event) {
			event.preventDefault();
			if (!$(this).hasClass("active")) {
				goToSlide($(this).index());
			} 
		});

		$container.on({
			mouseenter:stopTimer,
			mouseleave:startTimer
		});

		goToSlide(currentIndex);

		startTimer();
	});
});

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="style.css" />
<title>Insert title here</title>
</head>
<body>
 <div class="header-login">
    <%
    if (loginMember == null) {
    %>
    <a href="login_form.jsp"  style="text-decoration:none; font-size:0.8em;">로그인</a>
    <%
    } else { %>
    <%=loginMember.getMl_id() %>(<%=loginMember.getMl_name() %>)님 환영합니다. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="logout"  style="text-decoration:none; font-size:0.8em;" >로그아웃</a>
	<a href="mypage_view.mem" style="text-decoration:none; font-size:0.8em;">마이페이지</a>
	<a href="cart_list.ord" style="text-decoration:none; font-size:0.8em;">장바구니</a>
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
         <li><a href="#"><b>1:1 문의</b></a></li>
         <li><a href="rantSystem.jsp"><b>대여 시스템</b></a></li>
     </ul>
    </nav>
</header>
<div class="main">
	<div class="slideshow">
		<div class="slideshow-slides">
			<a href="#" class="slide" id="slide-1"><img width="1400" height="425" src="images/bigmainimg.png" /></a>
			<a href="#" class="slide" id="slide-2"><img width="1400" height="425" src="images/bigmainimg.png" /></a>
			<a href="#" class="slide" id="slide-3"><img width="1400" height="425" src="images/bigmainimg.png" /></a>
		</div>
		<div class="slideshow-nav">
				<a href="#" class="prev">Prev</a>
				<a href="#" class="next">Next</a>
		</div>
		<div class="slideshow-indicator"></div>
	</div>
	<hr />
	<div>
		<div class="best-new">
		<img width="60" src="images/best.jpg" /><br />
			<div class="pdtBox">
				<a href ="#"><img src="product/pdt_img/w1-1.JPG" width="180" height="180" /></a>
			</div>
			<div class="pdtBox">
				<a href ="#"><img src="product/pdt_img/w2-1.JPG" width="180" height="180" /></a>
			</div>
			<div class="pdtBox">
				<a href ="#"><img src="product/pdt_img/w3-1.JPG" width="180" height="180" /></a>
			</div>
		</div>
		<div class="best-new">
		<img width="60" src="images/new.jpg" /><br />
			<div class="pdtBox">
				<a href ="#"><img src="product/pdt_img/w4-1.JPG" width="180" height="180" /></a>
			</div>
			<div class="pdtBox">
				<a href ="#"><img src="product/pdt_img/w5-1.JPG" width="180" height="180" /></a>
			</div>
			<div class="pdtBox">
				<a href ="#"><img src="product/pdt_img/w6-1.JPG" width="180" height="180" /></a>
			</div>
		</div>		
	</div>
    <div class="footer">
    <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
    </div>
</div>
</body>
</html>