<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<CartInfo> cartList = (ArrayList<CartInfo>)request.getAttribute("cartList");

int cpage = 1, psize = 12;
if (request.getParameter("cpage") != null)
	cpage = Integer.parseInt(request.getParameter("cpage"));
if (request.getParameter("psize") != null)
	psize = Integer.parseInt(request.getParameter("psize"));



//검색조건 및 정렬조건 쿼리스트링을 받음
String id, isview, schtype, keyword, bcata, scata, stre, pop, rentDate;
id		= request.getParameter("id");		isview	= request.getParameter("isview");
schtype = request.getParameter("schtype");	keyword = request.getParameter("keyword");
bcata	= request.getParameter("bcata");	scata	= request.getParameter("scata");
stre	= request.getParameter("stre");		pop		= request.getParameter("pop");
rentDate= request.getParameter("rentDate");
if (rentDate == null) rentDate = "1";

String args = "?cpage=" + cpage + "&psize=" + psize;
if (isview != null && !isview.equals(""))	args += "&isview=" + isview;
if (bcata != null && !bcata.equals(""))		args += "&bcata=" + bcata;
if (scata != null && !scata.equals(""))		args += "&scata=" + scata;
if (stre != null && !stre.equals(""))		args += "&stre=" + stre;
if (keyword != null && !keyword.equals(""))
	args += "&schtype=" + schtype + "&keyword=" + keyword;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#cartTable th { border-bottom:3px black double; }
#cartTable td { border-bottom:1px black solid; }
</style>
<script src="jquery-3.5.1.js"></script>
<script>
function getSelectChk() {	// 사용자가 선택한 체크박스들의 value를 추출하는 함수
	var arrChk = document.frmCart.chk;
	// 문서내의 frmCart폼안에 있는컨트롤들 중 chk라는 이름을 가진 컨트롤들을 배열로 받아옴
	// 단 chk라는 이름을 가진 컨트롤이 하나일 경우에는 배열이 만들어 지지 않음
	var idx = "";
	for (var i = 0 ; i < arrChk.length ; i++) {
		if (arrChk[i].checked) {	// i인덱스의 체크박사가 선택된 상태라면
			idx += "," + arrChk[i].value;	// 선택된 체크박스의 value(cl_idx값)를 idx변수에 누적
		}
	}
	if (idx != "")	{
		if (idx.indexOf(",,") > -1) idx = idx.substring(2);	// 전체 구매시
		else						idx = idx.substring(1);	// 선택한 상품 구매시
	}
	
	return idx;
}

function notCool(idx) {
	var isConfirm = false;
	if (idx == 0) {	// 선택한 상품(들) 삭제시
		var arrChk = document.frmCart.chk;
		idx = getSelectChk();	// 선택한 상품들의 idx들을 받아 옴
		if (idx != "") {	// 삭제할 상품을 선택했으면
			isConfirm = confirm("선택한 상품(들)을 장바구니에서 삭제하시겠습니까?");
		} else {	// 삭제할 상품을 선택하지 않았을 경우
			alert("삭제할 상품을 하나 이상 선택하세요.");
		}
	} else {	// 특정 상품 삭제시
		isConfirm = confirm("해당 상품을 장바구니에서 삭제하시겠습니까?");
	}

	if (isConfirm) {
		$.ajax({
			type : "POST", 
			url : "./cart_del.ord", 
			data : { "idx" : idx }, 
			success : function(chkRst) {
				if(chkRst == 0)		alert("선택한 상품 삭제에 실패했습니다.\n다시 시도해 주십시오.");
				else				location.reload();
			}
		});
	}
}

function chkBuy() {
// 선택한 상품(들)을 구매하는 함수
	var arrChk = document.frmCart.chk;
	var idx = getSelectChk();	// 선택한 상품들의 idx들을 받아 옴
	if (idx != "") {	// 구매할 상품을 선택했으면
		document.frmCart.idxs.value = idx;
<%
if (loginMember == null) {	// 로그인을 하지 않은 상태일 경우
	session.setAttribute("url", "order_form.ord");
%>
		document.frmCart.action = "login_form.jsp";
		// 비회원인 경우 먼저 로그인을 유도한 후 결제창으로 이동함
<% } %>
		document.frmCart.submit();
	} else {	// 구매할 상품을 선택하지 않았을 경우
		alert("구매할 상품을 하나 이상 선택하세요.");
	}
}

function chkAll(all) {
	var arrChk = document.frmCart.chk;
	// 폼(frmCart) 안에 chk라는 이름의 컨트롤이 여러 개 있으므로 배열로 변환하여 받아 옴
	for (var i = 0 ; i < arrChk.length ; i++) {
		arrChk[i].checked = all.checked;
	}
}

function allBuy() {
	var all = document.frmCart.all;	// 전체 선택용 체크박스
	all.checked = true;	// 전체 선택용 체크박스를 인위적으로 체크하게 만듬
	chkAll(all);		// 전체 선택용 체크박스를 이용하여 전체를 선택하게 만듬
	chkBuy();			// 선택된 상품을 구매하는 함수 호출
}

</script>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="main">
<form name="frmCart" action="order_form.ord" method="post">
<input type="hidden" name="chk" value="" />
<input type="hidden" name="idxs" value="" />
<input type="hidden" name="kind" value="cart" />
<table width="700" cellpadding="5" cellspacing="0" id="cartTable" align="center">
<tr>
<th width="5%"><input type="checkbox" checked="checked" name="all" onclick="chkAll(this);" /></th>
<th width="30%">상품</th><th width="*%">대여일</th><th width="13%">가격</th><th width="10%">삭제</th>
</tr>
<%
if (cartList != null && cartList.size() > 0) {	// 장바구니에 데이터가 들어 있으면
	int total = 0;	// 총 구매가격을 저장할 변수
	for (int i = 0 ; i < cartList.size() ; i++) {
		String lnk = "<a href='pdt_view.pdt" + args + "&id=" + cartList.get(i).getPl_id() + "'>";
%>
<tr align="center">
<td><input type="checkbox" name="chk" value="<%=cartList.get(i).getCl_idx()%>" checked="checked" /></td>
<td align="left">
	<%=lnk%><img src="./product/pdt_img/<%=cartList.get(i).getPl_mainimg() %>" width="50" height="50" align="absmiddle" />
	<%=cartList.get(i).getPl_name() %></a>
</td>

<td>
<%=cartList.get(i).getCl_sdate() %> ~ <%=cartList.get(i).getCl_edate() %> (총 <%=cartList.get(i).getCl_rdate() %> 일) 
</td>
<td><%=Integer.parseInt(cartList.get(i).getPrice()) * Integer.parseInt(cartList.get(i).getCl_rdate()) %> 원</td>
<td><input type="button" value="삭제" onclick="notCool(<%=cartList.get(i).getCl_idx()%>);" /></td>
</tr>
<%
		total += Integer.parseInt(cartList.get(i).getPrice()) * Integer.parseInt(cartList.get(i).getCl_rdate()) ;
	}
%>
</table>
<table width="700" cellpadding="15" cellspacing="0" align="center">
<tr>
<td width="*">
	<input type="button" value="선택한 상품 구매" onclick="chkBuy();" />
	<input type="button" value="선택한 상품 삭제" onclick="notCool(0);" /> 
</td>
<td width="250" align="right">총 구매가격 : <span id="total"><%=total %></span> 원</td>
</tr>
<tr><td colspan="2" align="center">
	<input type="button" value="전체 구매" onclick="allBuy()" />
	<input type="button" value="계속 쇼핑" onclick="location.href='pdt_list.pdt<%=args %>';" />
</td></tr>
<%
} else {	// 장바구니에 데이터가 없으면
%>
<tr><td colspan="6" align="center">장바구니가 비었습니다.</td></tr>
<tr><td colspan="6" align="center">
	<input type="button" value="계속 쇼핑" onclick="location.href='pdt_list.pdt<%=args %>';" />
</td></tr>
<%
}
%>
</table>
</form>
</div>
</body>
</html>
