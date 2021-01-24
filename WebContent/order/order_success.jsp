<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
OrderInfo ord = (OrderInfo)request.getAttribute("ord");
ArrayList<OrderDetailInfo> detailList = ord.getDetailList();

String payment = "카드결제";
if (ord.getOl_payment().equals("b"))	payment = "계좌이체";
else if (ord.getOl_payment().equals("c"))	payment = "무통장 입금";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.box { border:1px solid black; width:600px; padding:10px; margin-bottom:10px; }
</style>
</head>
<body>
<h2>주문 내역</h2>
<h3>주문한 상품 내역</h3>
<div class="box">
<%
for (int i = 0 ; i < detailList.size() ; i++) {
	OrderDetailInfo detail = detailList.get(i);
	String opt = detail.getOd_opt();
	if(opt == null || opt.equals(""))	opt = "옵션없음";
%>
<img src="/mvcMall/product/pdt_img/<%=detail.getPl_img() %>" width="50" height="50" align="absmiddle" style="margin:2px 0;" />
<%
	out.print(detail.getPl_name()  + " / " + detail.getOd_opt() + " / ");
	out.print(detail.getOd_cnt() + "ea / " + detail.getOd_price() + "<br />");
}%>
</div>
<h3>구매자 정보</h3>
<div class="box">
	이름 : <%=ord.getOl_bname() %><br />
	전화 : <%=ord.getOl_bphone() %><br />
	메일 : <%=ord.getOl_bmail() %><br />
</div>
<h3>배송지 정보</h3>
<div class="box">
	이름 : <%=ord.getOl_rname() %><br />
	전화 : <%=ord.getOl_rphone() %><br />
	우편번호 : <%=ord.getOl_rzip() %><br />
	주소 : <%=ord.getOl_raddr1() + "&nbsp;&nbsp;" + ord.getOl_raddr2() %>
</div>
<h3>결제 정보</h3>
<div class="box">
	주문시 사용 포인트 : <%=ord.getOl_usepnt() %>point<br />
	주문 적립 포인트 : <%=ord.getOl_savepnt() %>point<br />
	결제 수단 : <%=payment %><br />
	총 결제액 : <%=ord.getOl_pay() %>원
</div>
<div style="width:600px; margin:20px; text-align:center;">
	<input type="button" value="계속 쇼핑" onclick="location.href='index.jsp';" />&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="마이 페이지" onclick="location.href='mypage.mem';" />
</div>
</body>
</html>