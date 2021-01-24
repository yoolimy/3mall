<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="java.net.*" %>
<%

request.setCharacterEncoding("utf-8");
String wtype = request.getParameter("wtype");

String msg = "수정", main1 = "", main2 = "", main3 = "", rantSystem = "", popup = "";
if (wtype.equals("in")) {
	msg = "등록";
} else if (wtype.equals("up")) {	// 게시글 수정일 경우
	SettingInfo article = (SettingInfo)request.getAttribute("article");
	// 수정할 게시글의 데이터가 저장된 article 인스턴스를 request에서 받아 생성
	main1 = article.getAe_main1();
	main2 = article.getAe_main2();
	main3 = article.getAe_main3();
	rantSystem = article.getAe_system();
	popup = article.getAe_popup();
	
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form>
<input type="hidden" name="wtype" value="<%=wtype %>" />
	<div class="main">
		<table border="1">
		<!-- 관리자 로그인 관련 정보 필요 LoginMember == null -->
			<% if (wtype.equals("in")) { 	// 글 등록 상황이면  %>	
				<tr>
					<th>메인페이지</th>
					<td>
						<input type="file" name="main1" /><br />
						<input type="file" name="main2" /><br />
						<input type="file" name="main3" />
					</td>
					<td><input type="button" onclick="location.href='settingForm.set';" value="<%=msg %>" /></td>
				</tr>
				<tr>
					<th>대여시스템</th>
					<td><input type="file" name="rantSystem" /></td>
					<td><input type="button" onclick="location.href='settingForm.set';" value="<%=msg %>" /></td>
				</tr>
				<tr>
					<th>팝업이미지</th>
					<td><input type="file" name="popup" /></td>
					<td><input type="button" onclick="location.href='settingForm.set';" value="<%=msg %>" /></td>
				</tr>
			<% } else { %>
			
			<% }  %>	
		</table>
	</div>
</form>
</body>
</html>