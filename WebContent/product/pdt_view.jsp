<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");

request.setCharacterEncoding("utf-8");
int cpage = Integer.parseInt(request.getParameter("cpage"));
int psize = Integer.parseInt(request.getParameter("psize"));

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


PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");
	
Calendar sdate = Calendar.getInstance();	// 시작일
int year, month, cYear, cMonth, cDay;
cYear = sdate.get(Calendar.YEAR);			// 올해 연도
cMonth = sdate.get(Calendar.MONTH) + 1;		// 현재 월
cDay = sdate.get(Calendar.DATE);			// 현재 일
if (request.getParameter("y") == null) {	// 받아온 연도와 월이 없으면
	year = sdate.get(Calendar.YEAR);		// 올해 연도
	month = sdate.get(Calendar.MONTH) + 1;	// 현재 월
} else {
	year = Integer.parseInt(request.getParameter("y"));
	month = Integer.parseInt(request.getParameter("m"));
}
int price = pdtInfo.getPl_price() * Integer.parseInt(rentDate);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#thImg img { margin:10px; }
#cal { 
width:350px; height:300px; background:#ecedeb; overflow:auto; position:absolute; 
top:360px; left:500px; border:2px dotted;
}
#buttons { border:0px; }

</style>
<script>
function showImg(imgName) {
	var bigImg = document.getElementById("bigImg");
	bigImg.src = "/3mall/product/pdt_img/" + imgName;
}

function changeDate(cal) {
	document.frmSchDate.submit();
	var obj = document.getElementById(cal);
	obj.style.display = "block";
}

function setToday() {
	document.frmSchDate.y.value = "<%=cYear%>";
	document.frmSchDate.m.value = "<%=cMonth%>";
	document.frmSchDate.submit();
}
function showSchedule(cal) {
	var obj = document.getElementById(cal);
	obj.style.display = "block";
}

function hideSchedule(cal) {
	var obj = document.getElementById(cal);
	obj.style.display = "none";
}

function sbmtDate(date) { 
	var plusDate = parseInt(document.frmPdt.rentDate.value);
	var dt = new Date(date);
	document.frmPdt.sdate.value = date;
	dt.setDate(dt.getDate() + plusDate);
	var newDate = dt.toLocaleDateString();
	var arrNewDate = newDate.split(". ");
	if (parseInt(arrNewDate[1]) < 10) 	arrNewDate[1] = "0" + arrNewDate[1];
	if (parseInt(arrNewDate[2]) < 10) 	arrNewDate[2] = "0" + arrNewDate[2];
	document.frmPdt.edate.value = (arrNewDate[0] + "-" + arrNewDate[1] + "-" + arrNewDate[2]).substring(0,10); 
	document.getElementById('cal').style.display = "none";
}

function howMuch() {
	document.frmPdt.submit();
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
		<img src="/3mall/product/pdt_img/<%=pdtInfo.getPl_mainimg() %>" width="400" height="480" id="bigImg" />
	</td></tr>
	<tr><td align="center" valign="middle" id="thImg">
		<img src="/3mall/product/pdt_img/<%=pdtInfo.getPl_mainimg() %>" width="100" height="120" onclick="showImg('<%=pdtInfo.getPl_mainimg() %>');" />
<% if (pdtInfo.getPl_img1() != null && !pdtInfo.getPl_img1().equals("")) { %>
		<img src="/3mall/product/pdt_img/<%=pdtInfo.getPl_img1() %>" width="100" height="120" onclick="showImg('<%=pdtInfo.getPl_img1() %>');" /><% } %>
<% if (pdtInfo.getPl_img2() != null && !pdtInfo.getPl_img2().equals("")) { %>
		<img src="/3mall/product/pdt_img/<%=pdtInfo.getPl_img2() %>" width="100" height="120" onclick="showImg('<%=pdtInfo.getPl_img2() %>');" /><% } %>
	</td></tr>
	</table>
</td>
<td width="*" valign="top">
	<table width="100%" cellpadding="8">
	<tr>
	<td width="100">분류</td>
	<td width="*"><%=pdtInfo.getCb_name() + " - " + pdtInfo.getCs_name() %></td>
	</tr>
	<tr><td>상품명</td><td><%=pdtInfo.getPl_name() %></td></tr>
	<tr><td>가격</td><td><%=pdtInfo.getPl_price() %>(1일 기준)</td></tr>
	<tr><td>상세정보</td><td><%=pdtInfo.getPl_detail() != null? pdtInfo.getPl_detail().replace(",", "<br /> ") : "" %>
	<form name="frmPdt" action="pdt_view.pdt<%=args %>&id=<%=id %>" method="post">
	<input type="hidden" name="id" value="<%=id %>" />
	<input type="hidden" name="args" value="<%=args %>" />
	<input type="hidden" name="price" value="" />
	</td></tr>
	<tr><td>대여 일수</td>
	<td>
		<input type="radio" name="rentDate" value="1" <%if (rentDate.equals("1")) { %>checked="checked" <%} %> onclick="howMuch()"/> 1일 &nbsp;&nbsp;&nbsp;
		<input type="radio" name="rentDate" value="3" <%if (rentDate.equals("3")) { %>checked="checked" <%} %> onclick="howMuch()"/> 3일 &nbsp;&nbsp;&nbsp;
		<input type="radio" name="rentDate" value="7" <%if (rentDate.equals("7")) { %>checked="checked" <%} %> onclick="howMuch()" /> 7일 &nbsp;&nbsp;&nbsp;
	</td>
	</tr>
	<tr><td>날짜 선택</td><td>	
	<input type="text" name="sdate" id="sdate" class="date" onclick="showSchedule('cal')"/> 부터&nbsp;
	<input type="text" name="edate" id="edate" class="date" /> 까지
	</td></tr>
	<tr><td colspan="2" align="right">
	<%=price %> 원
 	<tr><td colspan="2" align="center">
	<input type="button" value="장바구니에 담기" onclick="goCart();" />
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="바로 구매하기" onclick="goDirect();" />
</td></tr>
	</form>
	</table>
</td>
</tr>
<tr><td colspan="2" align="center"><hr width="100%" /></td></tr>
<tr><td colspan="2" align="center">
	<img src="/3mall/product/pdt_img/<%=pdtInfo.getPl_img1() %>" width="780" /><br /><br />
	<img src="/3mall/product/pdt_img/<%=pdtInfo.getPl_img2() %>" width="780" /><br /><br />
	<%=pdtInfo.getPl_deInfo() != null ? pdtInfo.getPl_deInfo() : "" %>
</td></tr>
<tr><td colspan="2" align="center"><hr width="100%" /></td></tr>
<tr><td colspan="2" align="center">
	<input type="button" value="목록" onclick="location.href='pdt_list.pdt<%=args %>';" />
</td></tr>
</table>
<br /><br />
<div id="cal" <%
if (pop != null && pop.equals("y")) { %>
style="display:block"
<%} else { %>
	style="display:none"
<%} %>
>
<a href="javascript:hideSchedule('cal')">
<img src='images/close.png' width='7%' align="right"></a><br /><br />
<%
sdate.set(year, month - 1, 1);
//입력받은 년도와 월을 이용하여 시작일을 지정(월은 -1을 해야 함)
int startWeekDay = 0, endDay = 0;
Calendar edate = Calendar.getInstance();	// 종료일
edate.set(year, month, 1);		// 시작일의 다음달 1일
edate.add(Calendar.DATE, -1);	// 다음달 1일에서 하루를 뺀 날짜(시작월의 말일)
startWeekDay = sdate.get(Calendar.DAY_OF_WEEK); // 시작일의 요일번호이자 1일의 시작 위치
endDay = edate.get(Calendar.DATE); // 말일
%>
<form name="frmSchDate" action="pdt_view.pdt<%=args %>&id=<%=id %>&pop=y" method="post">

<table width="350">
<tr><td align="center">
	<select name="y" onchange="changeDate(cal);">
<% for (int i = 1988 ; i <= (cYear + 1) ; i++) { %>
		<option value="<%=i %>" 
			<% if (year == i) { %>selected="selected"<% } %>><%=i %></option>
<% } %>
	</select> 년&nbsp;&nbsp;
	<select name="m" onchange="changeDate(cal);">
<% for (int i = 1 ; i <= 12 ; i++) { %>
		<option value="<%=i %>" 
			<% if (month == i) { %>selected="selected"<% } %>><%=i %></option>
<% } %>
	</select> 월&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="오늘 날짜" onclick="setToday();" />
</td></tr>
</table>
</form>
<table width="350" cellpadding="5" id="sch">
<tr>
<th width="50">일</th><th width="50">월</th><th width="50">화</th>
<th width="50">수</th><th width="50">목</th><th width="50">금</th>
<th width="50">토</th>
</tr>
<%
if (startWeekDay != 1) {
	out.println("<tr>");
	for (int i = 1 ; i < startWeekDay ; i++){
		out.println("<td>&nbsp;</td>");
	}
}

String disabled = "";
String txtClass = null, txtID = null, sql = null;
String strMonth = month < 10 ? "0" + month : "" + month;
for (int i = 1, n = startWeekDay ; i <= endDay ; i++, n++ ){
	// i: 날짜의 일(day)부분을 담당할 변수
	// n: 요일번호(1~7) 담당으로 일주일이 지나면 다음 줄로 내리기 위한 용도

	if (n % 7 == 1) {
	// 일요일이면 새로운 줄을 열어주고, 글자색을 빨간색으로 변경
		out.println("<tr>");
	}

	String day = i + "";
	String mon = month + "";
	
	String[] srents = pdtInfo.getPl_srent().split("-");
	String[] erents = pdtInfo.getPl_erent().split("-");
	LocalDate srent = LocalDate.of(Integer.parseInt(srents[0]), Integer.parseInt(srents[1]), Integer.parseInt(srents[2]));
	LocalDate erent = LocalDate.of(Integer.parseInt(erents[0]), Integer.parseInt(erents[1]), Integer.parseInt(erents[2]));
	
	if (i < 10 )	day = "0" + Integer.toString(i); 
	if (month < 10 ) mon = "0" + Integer.toString(month);
	LocalDate today = LocalDate.of(year, month, i);
	if (srent.minusDays(Integer.parseInt(rentDate)).equals(today))	disabled = "disabled='disabled'";
	if (erent.equals(today))	disabled = "";
	out.print("<td valign='center'>" + 
	"<input type='button' onclick='sbmtDate(\""+ year + "-" + mon + "-" + day + "\")' value='"+ i + "' id='buttons' " + disabled + " /></td>");

	if (n % 7 == 0)	out.println("</tr>");
	// 일주일이 지났으므로 다음 줄로 내림
	
	if (i == endDay && n % 7 != 0) {
	// 마지막 일을 출력했으나 토요일이 아니어서 "</tr>"을 출력하지 않은 상태이면
		for (int k = n % 7 ; k < 7 ; k++) {
			out.println("<td>&nbsp;</td>");
		}
		out.println("</tr>");
	}
}
%>
</table>
</div>
</body>
</html>