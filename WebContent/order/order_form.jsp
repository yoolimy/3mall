<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
String kind = request.getParameter("kind");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<CartInfo> pdtList = (ArrayList<CartInfo>)request.getAttribute("pdtList");

String name = "", p1 = "", p2 = "", p3 = "", e1 = "", e2 = "", zip = "", addr1 = "", addr2 = "";
if (loginMember != null) {	// 로그인 한 회원일 경우
	name = loginMember.getMlname();
	String[] arrPhone = loginMember.getMlphone().split("-");
	p1 = arrPhone[0];	p2 = arrPhone[1];	p3 = arrPhone[2];
	String[] arrEmail = loginMember.getMlemail().split("@");
	e1 = arrEmail[0];	e2 = arrEmail[1];
	zip = loginMember.getMazip();
	addr1 = loginMember.getMaaddr1();
	addr2 = loginMember.getMaaddr2();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.box { border:1px solid black; width:600px; padding:10px; margin-bottom:10px; }
</style>
<script>
function sameChk(chk) {
	var frm = document.frmOrder;
	if (chk == "n") {	// 구매자 정보와 다를 경우
		frm.rname.value = "";	frm.rp1.value = "010";	frm.rp2.value = "";
		frm.rp3.value = "";		frm.rzip.value = "";	frm.raddr1.value = "";
		frm.raddr2.value = "";
	} else {	// 구매자 정보와 같을 경우
<%
if (loginMember != null) {	// 회원일 경우
%>
		frm.rname.value = "<%=name%>";		frm.rp1.value = "<%=p1%>";
		frm.rp2.value = "<%=p2%>";			frm.rp3.value = "<%=p3%>";
		frm.rzip.value = "<%=zip%>";		frm.raddr1.value = "<%=addr1%>";
		frm.raddr2.value = "<%=addr2%>";
<%
} else {	// 비회원일 경우
%>
		frm.rname.value = frm.bname.value;	frm.rp1.value = frm.bp1.value;
		frm.rp2.value = frm.bp2.value;		frm.rp3.value = frm.bp3.value;
<%
}
%>
	}
}
</script>
</head>
<body>
<h2>상품 주문서 작성</h2>
<form name="frmOrder" action="order_proc.ord" method="post">
<input type="hidden" name="kind" value="<%=kind %>" /><!-- 바로구매인지 장바구니 구매인지 여부 -->
<h3>구매할 상품</h3>
<div class="box">
<%
String clIdxs = "";	// 카트인덱스번호를 저장할 변수로 바로 구매시에는 값이 없음
int total = 0;	// 총 구매가격을 저장할 변수
int savePnt = 0;	// 현 구매로 인한 적립 포인트를 저장할 변수
if (pdtList != null && pdtList.size() > 0) {	// 구매할 상품이 있으면
	for (int i = 0 ; i < pdtList.size() ; i++) {
		CartInfo crt = pdtList.get(i);

		if (kind.equals("cart"))	clIdxs += "," + crt.getCl_idx();
		// 장바구니 구매인 경우 카트 인덱스를 저장함
		
		String option = "해당 상품은 옵션이 없음";
		String opts = crt.getPl_opt();	// 상품이 가지는 옵션
		String opt = crt.getCl_opt();	// 사용자가 선택한 옵션
		if (opts != null && !opts.equals("")) {		// 해당 상품에 옵션이 있으면
			String[] arrOpt = opts.split(":");		// 옵션의 종류를 배열로 생성
			String[] arrChoose = opt.split(",");	// 선택한 옵션값을 배열로 생성
			option = "";
			for (int j = 0 ; j < arrOpt.length ; j++) {
				String[] arrTmp = arrOpt[j].split(",");
				option += " / " + arrTmp[0] + " : " + arrChoose[j];
			}
			option = option.substring(3);
		}
%>
	<img src="/mvcMall/product/pdt_img/<%=crt.getPl_img1() %>" width="50" height="50" align="absmiddle" style="margin:2px 0;" />
	<%=crt.getPl_name()  + " / " + option + " / " + crt.getCl_cnt() + "ea / " + crt.getPrice() %><br />
<%
		total += crt.getPrice();
	}
	if (clIdxs.indexOf(',') > -1) clIdxs = clIdxs.substring(1);
	// clIdxs에 쉼표가(데이터가) 있으면 맨 앞으 쉼표는 제거한 후 사용
	savePnt = total / 100;
} else {	// 구매할 상품이 없으면
%>
<script>
alert("잘못된 경로로 들어오셨습니다.");
history.back();
</script>
<%
	out.close();
}
%>
<input type="hidden" name="clIdxs" value="<%=clIdxs %>" />
</div>
<h3>구매자 정보</h3>
<div class="box">
<% if (loginMember != null) {	// 로그인한 회원일 경우 %>
	이름 : <%=name %><br />
	전화번호 : <%=p1 + "-" + p2 + "-" + p3 %><br />
	이메일 : <%=e1 + "@" + e2 %>
	<input type="hidden" name="ismember" value="y" />
	<input type="hidden" name="bname" value="<%=name %>" />
	<input type="hidden" name="bp1" value="<%=p1 %>" />
	<input type="hidden" name="bp2" value="<%=p2 %>" />
	<input type="hidden" name="bp3" value="<%=p3 %>" />
	<input type="hidden" name="be1" value="<%=e1 %>" />
	<input type="hidden" name="be2" value="<%=e2 %>" />
<% } else {		// 비회원구매일 경우 %>
<input type="hidden" name="ismember" value="n" />
	이름 : <input type="text" name="bname" /><br />
	전화번호 : 
		<select name="bp1">
			<option value="010">010</option>
			<option value="011">011</option>
			<option value="016">016</option>
			<option value="019">019</option>
		</select> -
		<input type="text" name="bp2" /> -
		<input type="text" name="bp3" /><br />
	이메일 : 
		<input type="text" name="be1" /> @
		<select name="be2">
			<option value="">도메인 선택</option>
			<option value="naver.com">네이버</option>
			<option value="nate.com">네이트</option>
			<option value="gmail.com">지메일</option>
			<option value="testMall.com">테스트 몰</option>
		</select>
<% } %>
</div>
<h3>배송지 정보</h3>
구매자 정보와 <input type="radio" name="isSame" value="y" onclick="sameChk('y');" />같습니다. 
<input type="radio" name="isSame" value="n" checked="checked" onclick="sameChk('n');" />같지 않습니다. 
<div class="box">
	이름 : <input type="text" name="rname" /><br />
	전화번호 : 
		<select name="rp1">
			<option value="010">010</option>
			<option value="011">011</option>
			<option value="016">016</option>
			<option value="019">019</option>
		</select> -
		<input type="text" name="rp2" /> -
		<input type="text" name="rp3" /><br />
	우편번호 : <input type="text" name="rzip" /><br />
	주소 : <input type="text" name="raddr1" /> <input type="text" name="raddr2" />
</div>
<% if (loginMember != null) {	// 로그인 한 회원일 경우 %>
<script>
function onlyNumber(obj) {
	if (isNaN(obj.value)) {
		obj.value = "";
	}
}
function calPoint(pnt) {
	if (pnt.value != "") {
		if (pnt.value > <%=total %>) {
			alert("결제금액 이상으로 사용할 수 없습니다.");
			pnt.value = "0";
		} else if (pnt.value > <%=loginMember.getMlpoint() %>) {
			alert("가지고 계신 포인트가 부족합니다.");
			pnt.value = "0";
		} else if (pnt.value % 1000 > 0) {
			alert("포인트는 1000단위로 사용 가능합니다");
			pnt.value = "0";
		}
	} else pnt.value = "0"; 
}
</script>
<h3>포인트 사용</h3>
<div class="box">
<% if (loginMember.getMlpoint() >= 1000) { %>
	 보유 포인트 <%=loginMember.getMlpoint() %> 중 
	 <input type="text" name="usePnt" value="0" style="text-align:right; width:50px;"
	  onkeyup="onlyNumber(this);" onfocus="this.value='';" onblur="calPoint(this);" /> 포인트를 사용합니다.
<% } else { %>
	현재 보유 포인트가 <%=loginMember.getMlpoint() %>이므로 사용할 수 없습니다.
<% } %>
</div>
<% } %>
<h3>결제 정보</h3>
<div class="box">
	<input type="radio" name="payment" value="a" /> 카드결제<br />
	<input type="radio" name="payment" value="b" /> 계좌이체<br />
	<input type="radio" name="payment" value="c" /> 무통장입금
	<p style="text-align:right;">총 결제 금액 : <%=total %>원</p>
</div>
<div style="width:600px; margin:20px; text-align:center;">
	<input type="submit" value="상품 구매" />&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="취소" onclick="history.back();" />
</div>
<input type="hidden" name="total" value="<%=total %>" />
<input type="hidden" name="savePnt" value="<%=total %>" />
</form>
</body>
</html>
