<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
int cpage = Integer.parseInt(request.getParameter("cpage"));
int psize = Integer.parseInt(request.getParameter("psize"));

// 검색조건 및 정렬조건 쿼리스트링을 받음
String id, isview, schtype, keyword, bcata, scata, stre;
id		= request.getParameter("id");		isview	= request.getParameter("isview");
schtype = request.getParameter("schtype");	keyword = request.getParameter("keyword");
bcata	= request.getParameter("bcata");	scata	= request.getParameter("scata");
stre	= request.getParameter("stre");

String args = "?cpage=" + cpage + "&psize=" + psize;
if (isview != null && !isview.equals(""))	args += "&isview=" + isview;
if (bcata != null && !bcata.equals(""))		args += "&bcata=" + bcata;
if (scata != null && !scata.equals(""))		args += "&scata=" + scata;
if (stre != null && !stre.equals(""))		args += "&stre=" + stre;
if (keyword != null && !keyword.equals(""))
	args += "&schtype=" + schtype + "&keyword=" + keyword;


PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");



%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#thImg img { margin:10px; }
</style>
<script>
function showImg(imgName) {
	var bigImg = document.getElementById("bigImg");
	bigImg.src = "./product/pdt_img/" + imgName;
}
</script>
</head>
<body>
<h2>상품 상세보기 화면</h2>
<table width="800" cellpadding="5">
<tr>
<td width="40%" align="center" valign="middle">
	<table width="100%">
	<tr><td align="center" valign="middle">
		<img src="product/pdt_img/<%=pdtInfo.getPl_mainimg() %>" width="400" height="480" id="bigImg" />
	</td></tr>
	<tr><td align="center" valign="middle" id="thImg">
		<img src="product/pdt_img/<%=pdtInfo.getPl_mainimg() %>" width="100" height="120" onclick="showImg('<%=pdtInfo.getPl_mainimg() %>');" />
<% if (pdtInfo.getPl_img1() != null && !pdtInfo.getPl_img1().equals("")) { %>
		<img src="product/pdt_img/<%=pdtInfo.getPl_img1() %>" width="100" height="120"  onclick="showImg('<%=pdtInfo.getPl_img1() %>');" /><% } %>
<% if (pdtInfo.getPl_img2() != null && !pdtInfo.getPl_img2().equals("")) { %>
		<img src="product/pdt_img/<%=pdtInfo.getPl_img2() %>" width="100" height="120" onclick="showImg('<%=pdtInfo.getPl_img2() %>');" /><% } %>
	</td></tr>
	</table>
</td>
<td width="*"><!-- 상품명, 가격, 대여중, 판매량, 등록일, 등록자 -->
	<table width="100%" cellpadding="8">
	<tr>
	<td width="100">분류</td>
	<td width="*"><%=pdtInfo.getCb_name() + " - " + pdtInfo.getCs_name() %></td>
	</tr>
	<tr><td>상품명</td><td><%=pdtInfo.getPl_name() %></td></tr>
	<tr><td>가격</td><td><%=pdtInfo.getPl_price() %>(1일 기준)</td></tr>
	<tr><td>상세정보</td><td><%=pdtInfo.getPl_detail() != null ? pdtInfo.getPl_detail().replace(",", "<br /> ") : "" %>
	<tr><td>대여중</td><td><%=pdtInfo.getPl_rent() == 1 ? "대여중" : "재고있음" %></td></tr>
	<tr><td>판매량</td><td><%=pdtInfo.getPl_salecnt() %></td></tr>
	<tr><td>등록일</td><td><%=pdtInfo.getPl_date() %></td></tr>
	<tr><td>등록자</td><td><%=pdtInfo.getAl_idx() %></td></tr>
	</table>
</td>
</tr>
<tr><td colspan="2" align="center"><hr width="100%" /></td></tr>
<tr><td colspan="2" align="center">
	<img src="product/pdt_img/<%=pdtInfo.getPl_img1() %>" width="780" /><br /><br />
	<img src="product/pdt_img/<%=pdtInfo.getPl_img2() %>" width="780" /><br /><br />
	<%=pdtInfo.getPl_deInfo() != null ? pdtInfo.getPl_deInfo() : "" %>
</td></tr>
<tr><td colspan="2" align="center"><hr width="100%" /></td></tr>
<tr><td colspan="2" align="center">
	<input type="button" value="목록" onclick="location.href='pdt_list.pdta<%=args %>';" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="수정" onclick="location.href='pdt_up_form.pdta<%=args %>&id=<%=id %>';" />
<% if (pdtInfo.getPl_salecnt() == 0) {	// 판매된 적이 없을 경우에만 삭제 가능 %>
<script>
function notCool(id) {
	if (confirm("정말 삭제하시겠습니까?\n삭제하면 복구가 불가능 합니다.")) {
		location.href="pdt_del_proc.pdta?id=" + id;
	}
}
</script>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="삭제" onclick="notCool('<%=id %>');" />
<% } %>
</td></tr>
</table>
<br /><br />
</body>
</html>
