<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>    
<%

MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
request.setCharacterEncoding("utf-8");


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
h1 { text-align:center; color:#383226;}
th { color:#383226;font-size:20px; }
table td .buttonBox { background-color:#efede9; font-weight:bold; font-size:20px; color:#383226; border:0; weight:40px; height:60px; margin:15px 15px; cursor:pointer; }
table td .buttonBox:hover { background-color:#383226; color:#efede9; font-weight:bold; cursor:pointer; }
table td .inputBox {  border-bottom:1px solid #383226; border-right:0; border-left:0; border-top:0; }
</style>
<script>
function chkData(chk) {
	var uid = chk.uid.value;
	var pwd = chk.pwd.value;
	
	if (uid == "") {
		alert("아이디를 입력해주세요.");
		chk.uid.focus();
		return false;
	}
	if (pwd == "") {
		alert("비밀번호를 입력해주세요.");
		chk.pwd.focus();
		return false;
	}

}

</script>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="main">
	<h1>Login</h1>
	<form name="frmLogin" action="login" method="post" onsubmit="return chkData(this);">	<!-- action="login" : 클래스호출(자바파일) -->
		<table cellpadding="5" align="center">
			<tr>
				<th>ID</th>
				<td><input type="text" name="uid" class="inputBox" /></td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type="password" name="pwd" class="inputBox" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="로그인" class="buttonBox" />
					<input type="button" value="회원가입" class="buttonBox" onclick="location.href='join_form.jsp';" />
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>