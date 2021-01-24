<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>contents</title>
<script src="./js/Chart.min.js"></script>
<script src="./js/utils.js"></script>
<style>
canvas {
	-moz-user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
}
.chart1 {
	position:absolute;	left:50px;	top:200px;	border:1px solid black;
}
</style>
</head>
<body>
<h2>&nbsp;Admin page</h2>
&nbsp;&nbsp;This page is only for administrators.

<div class="chart1" style="width:35%;">
	<canvas id="canvas"></canvas>
</div>
<br>
<br>
<script>
var config = {
	type: 'line',
	data: {
		labels: [1, 2, 3, 4, 5, 6], 
		datasets: [{
			label: '', 
			backgroundColor: window.chartColors.red,
			borderColor: window.chartColors.red,
			data: [2, 3, 3, 6, 5, 3], 
			fill: false,
		}, {
			label: '', 
			fill: true,
			backgroundColor: window.chartColors.gray,
			borderColor: window.chartColors.orange,
			data: [1, 2, 1, 4, 3, 1], 
		}]
	},
	options: {
		responsive: true,
		title: { display: false, text: '' }, 
		tooltips: { mode: 'index', intersect: false }, 
		hover: { mode: 'nearest', intersect: false }, 
		scales: { 
			xAxes: [{ display:false, scaleLabel:{ display:false, labelString:'' } }],
			yAxes: [{ display:false, scaleLabel:{ display:false, labelString:'' } }]
		}
	}
};

window.onload = function() {
	var ctx = document.getElementById('canvas').getContext('2d');
	window.myLine = new Chart(ctx, config);
};
</script>
</body>
</html>