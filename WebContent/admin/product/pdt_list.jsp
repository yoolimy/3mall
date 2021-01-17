<!-- 167 줄 선택삭제 부분, 상품검색이 checked가 안되는 부분 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
ArrayList<CataBigInfo> cataBigList = (ArrayList<CataBigInfo>)request.getAttribute("cataBigList");
ArrayList<CataSmallInfo> cataSmallList = (ArrayList<CataSmallInfo>)request.getAttribute("cataSmallList");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");

String schtype, keyword, bcata, scata, stock, rent;
schtype =	pageInfo.getSchtype();	// 검색조건(상품코드, 상품명)
keyword =	pageInfo.getKeyword();	// 검색어
bcata =		pageInfo.getBcata();	// 대분류
scata =		pageInfo.getScata();	// 소분류
stock =		pageInfo.getStock();	// 재고량
rent  = 	pageInfo.getRent();		// 대여량


String args = "", schArgs = "";
if (bcata != null)		schArgs += "&bcata=" + bcata;		else	bcata = "";
if (scata != null)		schArgs += "&scata=" + scata;		else	scata = "";
if (rent != null)		schArgs += "&rent=" + rent;			else	rent = "";
if (keyword != null && !keyword.equals("")) {
	schArgs += "&schtype=" + schtype + "&keyword=" + keyword;
} else {
	schtype = "";	keyword = "";
}


int cpage	= pageInfo.getCpage();	// 현재 페이지 번호
int pcnt	= pageInfo.getPcnt();	// 전체 페이지 수
int psize	= pageInfo.getPsize();	// 페이지 크기
int bsize	= pageInfo.getBsize();	// 블록 페이지 개수
int spage	= pageInfo.getSpage();	// 블록 시작 페이지 번호
int epage	= pageInfo.getEpage();	// 블록 종료 페이지 번호
int rcnt	= pageInfo.getRcnt();	// 검색된 게시물 개수
schArgs = "&psize=" + psize + schArgs;
args = "&cpage=" + cpage + schArgs;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
table { border:1px; }
td { font-size:11; }
.date { width:80px; }
.pr { width:50px; }
.pdtBox3 { width:266px; height:250px; border:1px solid black; }
.pdtBox4 { width:195px; height:200px; border:1px solid black; }
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
	bc = cataSmallList.get(i).getCb_idx();	// 대분류 idx를 bc에 저장
	sc = cataSmallList.get(i).getCs_idx();	// 소분류 idx를 sc에 저장
	scName = cataSmallList.get(i).getCs_name();	// 대분류명을 scName에 저장
%>
arr<%=bc%>[<%=j%>] = new Option("<%=sc%>", "<%=scName%>");
<%
}
%>

function setCategory(obj, target) {
	var x = obj.value;	// 대분류에서 선택한 값을 x에 담음

	for (var m = target.options.length - 1 ; m > 0 ; m--) {
		target.options[m] = null;
	}

	if (x != "") {
		var selectedArray = eval("arr" + x);	// 보여줄 배열 지정
		for (var i = 0 ; i < selectedArray.length ; i++) {
			target.options[i] = new Option(selectedArray[i].value, selectedArray[i].text);
		}
		target.options[0].selected = true;
	}
}

function chkAll(all) {
	var arrChk = document.frmList.chk;
	// 폼(frmCart) 안에 chk라는 이름의 컨트롤이 여러 개 있으므로 배열로 변환하여 받아 옴
	for (var i = 0; i < arrChk.length ; i++) {
		arrChk[i].checked = all.checked;
	} 
}

</script>
</head>
<body>
<h2>관리자페이지 - 상품관리 - 상품 목록 화면</h2>
<form name="frmSch" action="" method="get">
<table width="700" cellpadding="5">
<tr>
<th width="15%">상품분류</th>
<td width="35%">
	<select name="bcata" onchange="setCategory(this, this.form.scata);">
		<option value="" <% if (bcata.equals("")) { %>selected="selected"<% } %>>대분류 선택</option>
<% for (int i = 0 ; i < cataBigList.size() ; i++) { %>
		<option value="<%=cataBigList.get(i).getCb_idx()%>" 
		<% if (bcata.equals(cataBigList.get(i).getCb_idx() + "")) { %>selected="selected"<% } %>>
		<%=cataBigList.get(i).getCb_name()%></option>
<% } %>
	</select>&nbsp;
	<select name="scata">
		<option value="" <% if (scata.equals("")) { %>selected="selected"<% } %>>소분류 선택</option>
<%
if (!bcata.equals("")) {	// 대분류를 이용하여 검색한 상태이면(소분류도 보여줘야 함)
	for (int i = 0 ; i < cataSmallList.size() ; i++) {
		if (bcata.equals((cataSmallList.get(i).getCs_idx() + "").substring(0, 1))) {
		// 현재 선택된 대분류에 속한 소분류들만 보여줌
%>
	<option value="<%=cataSmallList.get(i).getCs_idx()%>" 
	<% if (scata.equals(cataSmallList.get(i).getCs_idx() + "")) { %>selected="selected"<% } %>>
	<%=cataSmallList.get(i).getCs_name()%></option>
<%
		}
	}
}
%>
	</select>
</td>
</tr>
<tr>
<th width="15%">재고상태</th>
<td>
	<input type="radio" name="rent" value="" <% if (rent.equals("")) { %>checked="checked"<% } %> />전체
	&nbsp;<input type="radio" name="rent" value="0" <% if (rent.equals("0")) { %>checked="checked"<% } %> />대여가능
	&nbsp;<input type="radio" name="rent" value="1" <% if (rent.equals("1")) { %>checked="checked"<% } %> />대여불가
</td>
</tr>
<tr>
<th>검색</th>
<td colspan="3">
	<select name="schtype">
		<option value="name" <% if (schtype.equals("name")) { %>selected="selected"<% } %>>상품명</option>
		<option value="id" <% if (schtype.equals("id")) { %>selected="selected"<% } %>>상품코드</option>		
	</select>
	<input type="text" name="keyword" value="<%=keyword %>"/>
</td>
</tr>
<tr><td colspan="2" align="center">
	<input type="submit" value="상품 검색" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="조건 초기화" />
</td></tr>
</table>

</form>
<br /><br />
<form name="frmList" action="pdt_form.ord" method="post">
<table width="900" cellpadding="5">
<tr><th colspan="6" align="left">총 상품 수 [<%=rcnt %>]</th>
<td colspan="2" align="right">
	<input type="button" value="상품 등록" onclick="location.href='pdt_in_form.pdta';" />
	<input type="button" value="선택 삭제" onclick="notCool();" />
	 <!-- 체크박스가져가는방식 질문(pdtList.get(i).getCl_idx())를 쓸수없어서 ...-->
</td></tr>
<tr><th>
<%
if (pdtList != null && rcnt > 0) {	// 검색결과가 있으면
%>
<tr>
<th width="7%"><input type="checkbox" checked="checked" onclick="chkAll(this);"/></th>
<th width="10%">상품분류</th>
<th width="10%">상품코드</th>
<th width="10%">상품사진</th>
<th width="15%">상품명</th>
<th width="10%">가격</th>
<th width="*%">재고<br />[총 수량 / 대여중 수량]</th>
<th width="10%">상품상태</th>
</tr>
<%
int seq = rcnt - (10 * (cpage - 1));	// 현재 페이지에서의 시작번호
String lnk = "";
	for (int i = 0 ; i < pdtList.size() && i < psize ; i++) {
	lnk = "<a href='pdt_view.pdta?id=" + pdtList.get(i).getPl_id() + args + "'>";
%>
<tr align="center">
<td><input type="checkbox" name="chk" value="<%=pdtList.get(i).getPl_id() %>" checked="checked" /></td>
<td><%=pdtList.get(i).getCb_name() + "<br />( " + pdtList.get(i).getCs_name() + " )"%></td>
<td><%=pdtList.get(i).getPl_id() %>
<td><img src="/3mall/product/pdt_img/<%=pdtList.get(i).getPl_img1() %>"  height="70" /></td>
<td><%=lnk + pdtList.get(i).getPl_name()%></a></td>
<td><%=pdtList.get(i).getPl_price()%></td>
<td><%=pdtList.get(i).getPl_rent() == 1 ? "대여중" : "재고있음" %></td>
<td><%=pdtList.get(i).getPl_view().equals("y") ? "공개" : "비공개" %></td>
</tr>
<%
	}
} else {
	out.println("<tr><td align='center'>검색결과가 없습니다.</td></tr>");
}
%>
</table>
</form>
<br />

<table width="900" cellpadding="5" >
<tr>
<td width="*" align="center">
<%
if (rcnt > 0) {	// 검색결과 상품들이 있을 경우에만 페이징을 함
	if (cpage == 1) {
		out.println("[<<]&nbsp;&nbsp;[<]&nbsp;&nbsp;");
	} else {
		out.print("<a href='pdt_list.pdta?cpage=1" + schArgs + "'>");
		out.println("[<<]</a>&nbsp;&nbsp;");
		out.print("<a href='pdt_list.pdta?cpage=" + (cpage - 1) + schArgs + "'>");
		out.println("[<]</a>&nbsp;&nbsp;");
	}

	for (int i = 1, j = spage ; i <= bsize && j <= pcnt ; i++, j++) {
		if (cpage == j) {
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='pdt_list.pdta?cpage=" + j + schArgs + "'>");
			out.println(j + "</a>&nbsp;");
		}
	}

	if (cpage == pcnt) {
		out.println("&nbsp;&nbsp;[>]&nbsp;&nbsp;[>>]");
	} else {
		out.print("&nbsp;&nbsp;<a href='pdt_list.pdta?cpage=" + (cpage + 1) + schArgs + "'>");
		out.println("[>]</a>");
		out.print("&nbsp;&nbsp;<a href='pdt_list.pdta?cpage=" + pcnt + schArgs + "'>");
		out.println("[>>]</a>");
	}
}
%>
</td>
</tr>
</table>
</body>
</html>
