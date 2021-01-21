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
body { margin:0; }

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
.best-new { float:left; padding:15px;}
.pdtBox { float:left; padding:5px; }
</style>
<script src="./jquery-3.5.1.js"></script>
<script src="./jquery-ui-1.10.3.custom.min.js"></script>
<script>
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
                <%=loginMember.getMl_id() %>(<%=loginMember.getMl_name() %>)님 환영합니다.<br />

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
                <a href="pdt_list.pdt?bcata=3">장신구</a>
                <a href="review_list.review">리뷰 게시판</a>
                <hr />
                <a href="member_list.amem">회원 목록</a>
            </div>
        </div>
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
				<img width="100" src="images/best.png" /><br /><br />
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
				<img width="100" src="images/new.png" /><br /><br />
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
        </div>
        <div class="footer">
    
        </div>
    </div>
</body>
</html>