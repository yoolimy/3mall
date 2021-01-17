<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
ArrayList<CataBigInfo> cataBigList = (ArrayList<CataBigInfo>)request.getAttribute("cataBigList");
ArrayList<CataSmallInfo> cataSmallList = (ArrayList<CataSmallInfo>)request.getAttribute("cataSmallList");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");

String keyword, bcata, scata, brand, sprice, eprice, ord;

bcata =		pageInfo.getBcata();	// 대분류
scata =		pageInfo.getScata();	// 소분류
ord =		pageInfo.getOrd();		// 정렬조건

String args = "", schArgs = "";
if (bcata != null)		schArgs += "&bcata=" + bcata;		else	bcata = "";
if (scata != null)		schArgs += "&scata=" + scata;		else	scata = "";
if (ord != null)		schArgs += "&ord=" + ord;			else	ord = "";

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
td { font-size:11; }
.pr { width:50px; }
.pdtBox3 { width:266px; height:250px; border:1px solid black; }
.pdtBox4 { width:195px; height:200px; border:1px solid black; }
</style>
<script>
function cngPsize(num) {
	document.frmSch.psize.value = num;
	document.frmSch.submit();
}
</script>
</head>
<body>
<h2>상품 목록 화면</h2>
<form name="frmSch" action="" method="get">
<table width="800" cellpadding="5">
<tr>
<td align="right">
	<img src="/3mall/images/v6-1.png" width="20px" onclick="cngPsize('6')" align="absmiddle"/>&nbsp;
	<img src="/3mall/images/v12-1.png" width="20px" onclick="cngPsize('12')" align="absmiddle"/>
	<input type="hidden" name="psize" value="6">
&nbsp;&nbsp;
	<select name="ord">
		<option value="" <% if (ord.equals("")) { %>selected="selected"<% } %>>상품 정렬</option>
		<option value="namea" <% if (ord.equals("namea")) { %>selected="selected"<% } %>>상품명</option>
		<option value="pricea" <% if (ord.equals("pricea")) { %>selected="selected"<% } %>>낮은 가격순</option>
		<option value="priced" <% if (ord.equals("priced")) { %>selected="selected"<% } %>>높은 가격순</option>
		<option value="dated" <% if (ord.equals("dated")) { %>selected="selected"<% } %>>신상품</option>
		<option value="salecntd" <% if (ord.equals("salecntd")) { %>selected="selected"<% } %>>인기상품</option>
	</select><!-- 체크박스선택시 바로 서브밋 어떻게...??? -->
</td>
</tr>
<tr><td colspan="4" align="center">
	<input type="submit" value="상품 검색" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="조건 초기화" />
</td></tr>
</table>
</form>
<br /><br />
<table width="800" cellpadding="5">
<%
int max = 3;	// 한 행에서 보여줄 상품의 최대 개수
if (psize == 12) max = 4;

if (pdtList != null && rcnt > 0) {	// 검색결과가 있으면
	String lnk = "", price = "", soldout = "";
	for (int i = 0 ; i < pdtList.size() && i < psize ; i++) {
		lnk = "<a href='pdt_view.pdt?id=" + pdtList.get(i).getPl_id() + args + "'>";
		if (i % max == 0)	out.println("<tr align=\"center\">");
		price = pdtList.get(i).getPl_price() + "";

%>
<td>
	<div class="pdtBox<%=max%>">
		<%=lnk %><img src="/3mall/product/pdt_img/<%=pdtList.get(i).getPl_mainimg() %>" width="<%=max == 3 ? 250 : 180 %>" height="<%=max == 3 ? 180 : 150 %>" /></a><br />
		<%=lnk + pdtList.get(i).getPl_name() %></a><%=soldout %><br />판매가 : <%=price %> (1일)
	</div>
</td>
<%
		if (i % max == max - 1)	out.println("</tr>");
	}
} else {
out.println("<tr><td align='center'>검색결과가 없습니다.</td></tr>");
}
%>
</table>
<br />
<table width="800" cellpadding="5">
<tr><td align="center">
<tr>
<td width="*">
<%
if (rcnt > 0) {	// 검색결과 상품들이 있을 경우에만 페이징을 함
	if (cpage == 1) {
		out.println("[<<]&nbsp;&nbsp;[<]&nbsp;&nbsp;");
	} else {
		out.print("<a href='pdt_list.pdt?cpage=1" + schArgs + "'>");
		out.println("[<<]</a>&nbsp;&nbsp;");
		out.print("<a href='pdt_list.pdt?cpage=" + (cpage - 1) + schArgs + "'>");
		out.println("[<]</a>&nbsp;&nbsp;");
	}

	for (int i = 1, j = spage ; i <= bsize && j <= pcnt ; i++, j++) {
		if (cpage == j) {
			out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='pdt_list.pdt?cpage=" + j + schArgs + "'>");
			out.println(j + "</a>&nbsp;");
		}
	}

	if (cpage == pcnt) {
		out.println("&nbsp;&nbsp;[>]&nbsp;&nbsp;[>>]");
	} else {
		out.print("&nbsp;&nbsp;<a href='pdt_list.pdt?cpage=" + (cpage + 1) + schArgs + "'>");
		out.println("[>]</a>");
		out.print("&nbsp;&nbsp;<a href='pdt_list.pdt?cpage=" + pcnt + schArgs + "'>");
		out.println("[>>]</a>");
	}
}
%>
</tr>
</table>
</body>
</html>