<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
ArrayList<CataBigInfo> cataBigList = (ArrayList<CataBigInfo>)request.getAttribute("cataBigList");
ArrayList<CataSmallInfo> cataSmallList = (ArrayList<CataSmallInfo>)request.getAttribute("cataSmallList");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");

String keyword, bcata, scata, ord;

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
.pdtBox3 a:link, .pdtBox4 a:link {
  text-decoration:none; font-weight:bold; color:#543900;
 }
.pdtBox3 a:visited, .pdtBox4 a:visited {
  text-decoration:none; font-weight:bold; color:#543900;
 }
.pdtBox3 a:hover, .pdtBox4 a:hover {
  text-decoration:underline; font-weight:bold; color:#C07F5A;
 }
div { border-style:none; }
td { font-size:11; }
.pr { width:50px; }
.pdtBox3 { width:200px; height:400px; }
.pdtBox4 { width:180px; height:330px; }
</style>
<script>
		
function cngPsize(num) {
	document.frmSch.psize.value = num;
	document.frmSch.submit();
}

function ordSubmit(obj) {
	obj.submit();
}

</script>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="main">
<form name="frmSch" action="" method="get">
<input type="hidden" name="bcata" value="<%=bcata %>">
<input type="hidden" name="scata" value="<%=scata %>">
<input type="hidden" name="psize" value="<%=psize %>">
<table width="800" cellpadding="5" align="center">
<tr>
<td align="right">
	<img src="images/v6-1.png" width="20px" onclick="cngPsize('6')" align="absmiddle"/>&nbsp;
	<img src="images/v12-1.png" width="20px" onclick="cngPsize('12')" align="absmiddle"/>
	
&nbsp;&nbsp;
	<select name="ord" onchange="ordSubmit(this.form)">
		<option value="" <% if (ord.equals("")) { %>selected="selected"<% } %>>상품 정렬</option>
		<option value="namea" <% if (ord.equals("namea")) { %>selected="selected"<% } %>>상품명</option>
		<option value="pricea" <% if (ord.equals("pricea")) { %>selected="selected"<% } %>>낮은 가격순</option>
		<option value="priced" <% if (ord.equals("priced")) { %>selected="selected"<% } %>>높은 가격순</option>
		<option value="dated" <% if (ord.equals("dated")) { %>selected="selected"<% } %>>신상품</option>
		<option value="salecntd" <% if (ord.equals("salecntd")) { %>selected="selected"<% } %>>인기상품</option>
	</select>
</td>
</tr>
</table>
</form>
<br /><br />
<table width="1000" cellpadding="5" align="center">
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
		<%=lnk %><img src="./product/pdt_img/<%=pdtList.get(i).getPl_mainimg() %>" width="<%=max == 3 ? 250 : 200 %>" height="<%=max == 3 ? 330 : 270 %>" /></a><br />
		<%=lnk + pdtList.get(i).getPl_name() %></a><%=soldout %><br /><%=price %> 원 (1일)
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
<table width="800" cellpadding="5" align="center">
<tr>
<td width="*" align="center">
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
</div>
</body>
</html>