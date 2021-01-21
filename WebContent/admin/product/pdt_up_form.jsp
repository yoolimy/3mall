<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");

ArrayList<CataBigInfo> cataBigList = (ArrayList<CataBigInfo>)request.getAttribute("cataBigList");
ArrayList<CataSmallInfo> cataSmallList = (ArrayList<CataSmallInfo>)request.getAttribute("cataSmallList");
PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");

int cpage = Integer.parseInt(request.getParameter("cpage"));
int psize = Integer.parseInt(request.getParameter("psize"));

//검색조건 및 정렬조건 쿼리스트링을 받음
String id, isview, schtype, keyword, bcata, scata, rent;
id		= request.getParameter("id");		isview	= request.getParameter("isview");
schtype = request.getParameter("schtype");	keyword = request.getParameter("keyword");
bcata	= request.getParameter("bcata");	scata	= request.getParameter("scata");
rent 	= request.getParameter("rent");	

String args = "?cpage=" + cpage + "&psize=" + psize;
if (isview != null && !isview.equals(""))	args += "&isview=" + isview;
if (bcata != null && !bcata.equals(""))		args += "&bcata=" + bcata;
if (scata != null && !scata.equals(""))		args += "&scata=" + scata;
if (rent != null && !rent.equals(""))		args += "&rent=" + rent;
if (keyword != null && !keyword.equals(""))
	args += "&schtype=" + schtype + "&keyword=" + keyword;

int csIdx = pdtInfo.getCs_idx();	// 소분류 idx
String cbIdx = (csIdx + "").substring(0, 1);	// 대분류 idx(소분류의 앞 두자리 숫자값)
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#pdtUpForm th, #pdtUpForm td { border-bottom:1px solid black; height:30px; }
</style>
<script>
<%
String scName = null;
int bc = 0, sc = 0;
for (int i = 0, j = 1 ; i < cataSmallList.size() ; i++, j++) {
	if (bc != cataSmallList.get(i).getCb_idx()) {
		j = 1;
%>
var arr<%=cataSmallList.get(i).getCb_idx()%> = new Array();
arr<%=cataSmallList.get(i).getCb_idx()%>[0] = new Option("", "소분류 선택");
<%
	}
	bc = cataSmallList.get(i).getCb_idx();
	sc = cataSmallList.get(i).getCs_idx();
	scName = cataSmallList.get(i).getCs_name();
%>
arr<%=bc%>[<%=j%>] = new Option("<%=sc%>", "<%=scName%>");
<%
}
%>

function setCategory(obj, target) {
	var x = obj.value;
	for (var m = target.options.length - 1 ; m > 0 ; m--) {
		target.options[m] = null;
	}
	if (x != "") {
		var selectedArray = eval("arr" + x);
		for (var i = 0 ; i < selectedArray.length ; i++) {
			target.options[i] = new Option(selectedArray[i].value, selectedArray[i].text);
		}
		target.options[0].selected = true;
	}
}

var imgCheck = true;
function imagecheck(image) {
	img = image.slice(image.indexOf(".")+1).toLowerCase();

	if (img!="jpg" && img!="png" && img!="gif" &&img!="bmp") {
		alert("이미지파일만 등록 가능합니다");	
		imgCheck = false;
	} else {
		imgCheck = true;
	}
}
function chkData(frm) {
	var bCata = frmPdt.bCata.value;			var sCata = frmPdt.sCata.value;
	var name = frmPdt.name.value;			var price = frmPdt.price.value;
	var mainimg = frmPdt.mainimg.value;	


	if (bCata == "")	{
		 alert("대분류를 선택하세요");		frmPdt.bCata.focus();	 return false;
	}
	if (sCata == "")	{
		 alert("소분류를 선택하세요");		frmPdt.sCata.focus();	 return false;
	}
	if (name == "")	{
		 alert("상품명을 입력하세요.");		frmPdt.name.focus();	 return false;
	}
	if (price == "")	{
		alert("가격을 입력하세요.");			frmPdt.price.focus();	 return false;
	}
	
	if (mainimg == "")	{
		alert("메인이미지는 반드시 등록해야 합니다.");		 return false;
	}

	if (!imgCheck) {
		alert("확장자를 확인하세요.");	frmPdt.mainimg.value="";
		frmPdt.img1.value="";	frmPdt.img2.value="";	return false;
	}

	return true;
}

function onlyNum(obj) {
		if (isNaN(obj.value)) {
			obj.value = "";
	}
}
</script>
</head>
<body>
<h2>상품 수정 폼</h2>
<form name="frmPdt" action="pdt_up_proc.pdta" method="post" enctype="multipart/form-data" onsubmit="return chkData(this);">
<input type="hidden" name="args" value="<%=args %>" />
<input type="hidden" name="id" value="<%=pdtInfo.getPl_id() %>" />
<input type="hidden" name="oldmainimg" value="<%=pdtInfo.getPl_mainimg() %>" />
<input type="hidden" name="oldImg1" value="<%=(pdtInfo.getPl_img1() == null) ? "" : pdtInfo.getPl_img1() %>" />
<input type="hidden" name="oldImg2" value="<%=(pdtInfo.getPl_img2() == null) ? "" : pdtInfo.getPl_img2() %>" />
<table width="600" cellpadding="5" >
<tr>
<th width="25%">분류</th>
<td width="*">
	<select name="bCata" onchange="setCategory(this, this.form.sCata);">
		<option value="">대분류 선택</option>
<% for (int i = 0 ; i < cataBigList.size() ; i++) { %>
		<option value="<%=cataBigList.get(i).getCb_idx()%>" 
		<% if ((cataBigList.get(i).getCb_idx() + "").equals(cbIdx)) { %>
		selected="selected"<% } %>><%=cataBigList.get(i).getCb_name()%></option>
<% } %>
	</select>
	<select name="sCata">
		<option value="">소분류 선택</option>
<%
for (int i = 0 ; i < cataSmallList.size() ; i++) {
	if (cbIdx.equals((cataSmallList.get(i).getCs_idx() + "").substring(0, 1))) {
%>
		<option value="<%=cataSmallList.get(i).getCs_idx()%>" 
		<% if (csIdx == cataSmallList.get(i).getCs_idx()) { %>selected="selected"<% } %>>
		<%=cataSmallList.get(i).getCs_name()%></option>
<%
	}
}
%>
	</select>
</td>
</tr>
<tr>
<th>상품 상태</th>
<td>
	<input type="radio" name="view" value="y" <% if (pdtInfo.getPl_view().equals("y")) { %>checked="checked"<% } %>/>상품 게시
	<input type="radio" name="view" value="n" <% if (pdtInfo.getPl_view().equals("n")) { %>checked="checked"<% } %> />상품 미게시
</td>
</tr>
<tr>
<th>상품명</th><td><input type="text" name="name" value="<%=pdtInfo.getPl_name() %>"/></td>
</tr>
<tr>
<th>가격</th><td><input type="text" name="price" value="<%=pdtInfo.getPl_price() %>" onkeyup="onlyNum(this);"/>원 </td>| 
</tr>
<tr>
<th>상품상세정보</th><td><input type="text" name="detail" value="<%=pdtInfo.getPl_detail() %>"  placeholder="Ex) 가슴둘레 115cm,바지길이 112cm"/></td>
</tr>
<tr>
<th>상품상세설명</th><td><input type="text" name="deInfo" value="<%=pdtInfo.getPl_deInfo() %>" /></td>
</tr>
<tr>
<th>상품메인사진</th><td><input type="file" name="mainimg" onchange="imagecheck(this.value);"/><br />
<%=(pdtInfo.getPl_mainimg() == null) ? "이미지 없음" : pdtInfo.getPl_mainimg() %></td>
</tr>
<tr>
<th>상품 상세사진1</th><td><input type="file" name="img1" onchange="imagecheck(this.value);"/><br />
<%=(pdtInfo.getPl_img1() == null) ? "이미지 없음" : pdtInfo.getPl_img1() %></td>
</tr>
<tr>
<th>상품 상세사진2</th><td><input type="file" name="img2" onchange="imagecheck(this.value);"/><br />
<%=(pdtInfo.getPl_img2() == null) ? "이미지 없음" : pdtInfo.getPl_img2() %><br />
최대 5MB 이하의 (JPG, JPEG, GIF, PNG) 파일만 가능합니다.</td>
</tr>
<tr><td colspan="4" align="right">
	<input type="submit" value="수정" />
</td></tr>
</table>
</form>
</body>
</html>




