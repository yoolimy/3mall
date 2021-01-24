<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
ArrayList<SalesInfo> salesList = (ArrayList<SalesInfo>)request.getAttribute("salesList");
String month = "", sum = "";
int totalSum = 0;
for (int i = 0 ; i < salesList.size() ; i++) {
	month += "," + salesList.get(i).getEach_month();
	sum += "," + salesList.get(i).getSum_sales();
	totalSum += salesList.get(i).getSum_sales();
}
month = month.substring(1);
sum = sum.substring(1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>월별 매출 및 순이익 그래프</title>
<script src="./admin/js/Chart.min.js"></script>
<script src="./admin/js/utils.js"></script>
<style>
.wrapper {
	position:absolute;	width:1000px;
}
.tab {
	width:900px; height:100px;
	position:relative;	margin:20px auto;
}
.graph {
	position:relative;	margin:0px auto;
}
canvas{
	-moz-user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
}
.btn { position:absolute; top:26px; left:420px; }
</style>
</head>
<body>
<form>
<input type="hidden" id="month" value="<%=month %>" />
<input type="hidden" id="sum" value="<%=sum %>" />
<h2>관리자 페이지 - 2021 상반기 매출액</h2>
<input type="button" class="btn" value="목록으로" onclick="history.back();" />
<div class="wrapper">
	<div class="tab">
		<table align="center" border="1" cellpadding="0" cellspacing="0" width="850px" height="60px">
		<tr align="center">
			<th width="80px" align="left">
				<select>
					<option value="2021">2021년</option>
					<option value="2020">2020년</option>
				</select>
			</th>
		<% for (int i = 0 ; i < salesList.size() ; i++) { %>
			<th><%=salesList.get(i).getEach_month() %></th>
		<% } %>
			<th>합계</th>
		</tr>
		<tr align="center">
			<td align="left">매출액</td>
		<% for (int i = 0 ; i < salesList.size() ; i++) { %>
			<td><%=salesList.get(i).getSum_sales() %></td>
		<% } %>
			<td><%=totalSum %></td>
		</tr>
		</table>
	</div>
	<div class="graph" style="width:85%;">
		<canvas id="canvas"></canvas>
	</div>
	<br />
	<script>
	var a = document.getElementById('month');
	var aa = a.value.split(',');
	var b = document.getElementById('sum');
	var bb = b.value.split(',');
	var config = {
		type: 'line',
		data: {
			labels: [aa[0], aa[1], aa[2], aa[3], aa[4], aa[5]], 
			datasets: [{
				label: '2021년', 
				backgroundColor: window.chartColors.gray,
				borderColor: window.chartColors.red,
				data: [bb[0], bb[1], bb[2], bb[3], bb[4], bb[5]], 
				fill: true,
			}, {
				label: '2020년', 
				fill: false,
				backgroundColor: window.chartColors.gray,
				borderColor: window.chartColors.orange,
				data: [27000, 18000, 31000, 60000, 54000, 32000], 
			}]
		},
		options: {
			responsive: true,
			title: { display: true, text: '상반기 매출액' }, 
			tooltips: { mode: 'index', intersect: false }, 
			hover: { mode: 'nearest', intersect: true }, 
			scales: { 
				xAxes: [{ display:true, scaleLabel:{ display:true, labelString:'월별' } }],
				yAxes: [{ display:true, scaleLabel:{ display:true, labelString:'매출액' } }]
			}
		}
	};
	
	window.onload = function() {
		var ctx = document.getElementById('canvas').getContext('2d');
		window.myLine = new Chart(ctx, config);
	};
	</script>
</div>
</form>
</body>
</html>
