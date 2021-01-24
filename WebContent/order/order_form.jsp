<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
String kind = request.getParameter("kind");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
MemberAddrInfo memberAddrInfoFirst = (MemberAddrInfo)request.getAttribute("memberAddrInfoFirst");
MemberAddrInfo memberAddrInfoSecond = (MemberAddrInfo)request.getAttribute("memberAddrInfoSecond");
ArrayList<CartInfo> pdtList = (ArrayList<CartInfo>)request.getAttribute("pdtList");

String name = "", p1 = "", p2 = "", p3 = "", zip = "", addr1 = "", addr2 = "";
String zip2 = "", addr12 = "", addr22 = "";
if (loginMember != null) {	// 로그인 한 회원일 경우
	name = loginMember.getMl_name();
	String[] arrPhone = loginMember.getMl_phone().split("-");
	p1 = arrPhone[0];	p2 = arrPhone[1];	p3 = arrPhone[2];
	zip = memberAddrInfoFirst.getMa_zip();
	addr1 = memberAddrInfoFirst.getMa_addr1();
	addr2 = memberAddrInfoFirst.getMa_addr2();
	
	zip2 = memberAddrInfoSecond.getMa_zip();
	addr12 = memberAddrInfoSecond.getMa_addr1();
	addr22 = memberAddrInfoSecond.getMa_addr2();
} else {
	System.out.println("<script>");
	System.out.println("alert('로그인 후 사용할 수 있습니다.');");
	System.out.println("history.back();");
	System.out.println("</script>");
	System.out.close();
}
String img = "&nbsp;<img src=\"images/asterisk.png\" width=\"5\" height=\"5\" align=\"absmiddle\" />";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.box { border:1px solid black; width:600px; padding:10px; margin-bottom:10px; }
</style>
<script src="javascript/mypageScript.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function sameChk(chk) {
	var frm = document.frmOrder;
	if (chk == "n") {	// 구매자 정보와 다를 경우
		frm.rname.value = "";	frm.rp1.value = "010";	frm.rp2.value = "";
		frm.rp3.value = "";		frm.rzip.value = "";	frm.raddr1.value = "";
		frm.raddr2.value = "";
	} else if (chk == "y") {	// 기본배송지일 경우
		frm.rname.value = "<%=name%>";		frm.rp1.value = "<%=p1%>";
		frm.rp2.value = "<%=p2%>";			frm.rp3.value = "<%=p3%>";
		frm.rzip.value = "<%=zip%>";		frm.raddr1.value = "<%=addr1%>";
		frm.raddr2.value = "<%=addr2%>";
	} else {	// 배송지2
		frm.rname.value = "<%=name%>";		frm.rp1.value = "<%=p1%>";
		frm.rp2.value = "<%=p2%>";			frm.rp3.value = "<%=p3%>";
		frm.rzip.value = "<%=zip2%>";		frm.raddr1.value = "<%=addr12%>";
		frm.raddr2.value = "<%=addr22%>";
	}
}

//우편번호 찾기
function execDaumPostcode(num) {
 new daum.Postcode({
     oncomplete: function(data) {
         // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

         // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
         // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
         var roadAddr = data.roadAddress; // 도로명 주소 변수 1
         var extraRoadAddr = ''; // 참고 항목 변수

         // 법정동명이 있을 경우 추가한다. (법정리는 제외)
         // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
         if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
             extraRoadAddr += data.bname;
         }
         // 건물명이 있고, 공동주택일 경우 추가한다.
         if(data.buildingName !== '' && data.apartment === 'Y'){
            extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
         }
         // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
         if(extraRoadAddr !== ''){
             extraRoadAddr = ' (' + extraRoadAddr + ')';
         }

         // 우편번호와 주소 정보를 해당 필드에 넣는다.
         document.getElementById('postcode' + num).value = data.zonecode;
         document.getElementById("roadAddress" + num).value = roadAddr;

         var guideTextBox = document.getElementById("guide");
         // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
         if(data.autoRoadAddress) {
             var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
             guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
             guideTextBox.style.display = 'block';

         } else if(data.autoJibunAddress) {
             var expJibunAddr = data.autoJibunAddress;
             guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
             guideTextBox.style.display = 'block';
         } else {
             guideTextBox.innerHTML = '';
             guideTextBox.style.display = 'none';
         }
     }
 }).open();
}
</script>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="main">
<table width="700" cellpadding="5" cellspacing="0" align="center">
<tr><td><h3>결제페이지</h3></td></tr></table>
<form name="frmOrder" action="order_proc.ord" method="post">
<input type="hidden" name="kind" value="<%=kind %>" /><!-- 바로구매인지 장바구니 구매인지 여부 -->
<table width="700" cellpadding="5" cellspacing="0" align="center">
<tr><th>상품정보</th><th>상품금액</th></tr>
<%
String clIdxs = "";	// 카트인덱스번호를 저장할 변수로 바로 구매시에는 값이 없음
int total = 0;	// 총 구매가격을 저장할 변수
int savePnt = 0;	// 현 구매로 인한 적립 포인트를 저장할 변수
if (pdtList != null && pdtList.size() > 0) {	// 구매할 상품이 있으면
	for (int i = 0 ; i < pdtList.size() ; i++) {
		CartInfo crt = pdtList.get(i);

		if (kind.equals("cart"))	clIdxs += "," + crt.getCl_idx();
		// 장바구니 구매인 경우 카트 인덱스를 저장함
		
%>
	<tr><td><img src="./product/pdt_img/<%=crt.getPl_mainimg() %>" width="50" height="50" align="absmiddle" style="margin:2px 0;" />
	<%=crt.getPl_name()  + " / " + crt.getCl_sdate() + " ~ " + crt.getCl_edate() + " ( " + crt.getCl_rdate() + "일 ) / " %></td><td>
	<%=crt.getPl_price() + " 원" %></td></tr>
<%
		total = total + crt.getPl_price();
	}
	if (clIdxs.indexOf(',') > -1) clIdxs = clIdxs.substring(1);
	// clIdxs에 쉼표가(데이터가) 있으면 맨 앞으 쉼표는 제거한 후 사용
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
</table><br />
<input type="hidden" name="clIdxs" value="<%=clIdxs %>" />
<table width="700" cellpadding="5" cellspacing="0" align="center">
<tr><td>배송지 선택<%=img %></td>
<td>
<input type="radio" name="isSame" value="y" onclick="sameChk('y');" />기본 배송지 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="radio" name="isSame" value="y2" onclick="sameChk('y2');" />배송지 2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="radio" name="isSame" value="n" checked="checked" onclick="sameChk('n');" />신규 배송지 
</td></tr>
<tr><td>수령인<%=img %></td><td><input type="text" name="rname" /></td></tr>
<tr><td>연락처1<%=img %> </td><td>
		<select name="rp1">
			<option value="010">010</option>
			<option value="011">011</option>
			<option value="016">016</option>
			<option value="019">019</option>
		</select> -
		<input type="text" name="rp2" /> -
		<input type="text" name="rp3" /></td></tr>
<tr><td>배송지 주소<%=img %></td><td> <input type="text" name="rzip" placeholder="우편번호" readonly="readonly" id="postcode1"/>
&nbsp;&nbsp;<input type="button" onclick="execDaumPostcode('1')" value="우편번호 찾기" /><br />
	<input type="text" name="raddr1" id="roadAddress1"/>
	<span id="guide" style="color:#999;display:none;font-size:5px;"></span>
	<input type="text" name="raddr2" id="detailAddress1"/></td></tr>
<tr><td>요청사항</td><td><input type="text" name="wants" /></td></tr>
</table><br />
<table width="700" cellpadding="5" cellspacing="0" align="center">
<tr><td><b>결제 수단</b><%=img %></td>
<td>
	<input type="radio" name="payment" value="a" /> 카드결제&nbsp;&nbsp;&nbsp;
	<input type="radio" name="payment" value="b" /> 계좌이체&nbsp;&nbsp;&nbsp;
	<input type="radio" name="payment" value="c" /> 무통장입금
</td></tr>
</table><br />
<table width="700" cellpadding="5" cellspacing="0" align="center">
<tr><td><b>수령 방법</b><%=img %></td>
<td>
	<input type="radio" name="receive" value="a" /> 직접&nbsp;&nbsp;&nbsp;
	<input type="radio" name="receive" value="b" checked="checked"/> 배송&nbsp;&nbsp;&nbsp;
</td>
<td><b>회수 방법</b><%=img %></td>
<td>
	<input type="radio" name="return" value="a" /> 직접&nbsp;&nbsp;&nbsp;
	<input type="radio" name="return" value="b" checked="checked"/> 배송&nbsp;&nbsp;&nbsp;
</td></tr>
</table><br />
<table width="700" cellpadding="5" cellspacing="0" align="center">
<tr><td width="15%"><b>결제 금액</b></td>
<td align="left">
	<b><h2><%=total %></b>원</h2>
</td>
</tr>
</table>
<table width="700" cellpadding="5" cellspacing="0" align="center">
<tr><td align="center">
	<input type="submit" value="상품 구매" />&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="취소" onclick="history.back();" />
</td></tr>
</table>
<input type="hidden" name="total" value="<%=total %>" />

</form>
</div>
</body>
</html>
