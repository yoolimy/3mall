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
<h2>Login</h2>
<form name="frmLogin" action="login" method="post" onsubmit="return chkData(this);">	<!-- action="login" : 클래스호출(자바파일) -->
	<table cellpadding="5">
		<tr>
			<th>ID</th>
			<td><input type="text" name="uid" /></td>
		</tr>
		<tr>
			<th>PW</th>
			<td><input type="password" name="pwd" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="로그인" />
				<input type="button" value="회원가입" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>