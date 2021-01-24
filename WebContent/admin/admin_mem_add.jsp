<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>어드민 추가 폼</h2>
<form name="frmAdminAdd" action="admin_mem_add.amem" method="post">
아이디:<input type="text" name="al_id" /><br />
이름:<input type="text" name="al_name" /><br />
전화번호:<input type="text" name="al_phone" /><br />
이메일:<input type="text" name="al_email" /><br />
부서:<input type="text" name="al_team" /><br />
직급:<input type="text" name="al_position" /><br />
메모:<input type="text" name="al_memo" /><br />
<input type="submit" value="등록" />
<input type="reset" value="다시 입력" />
</form>
</body>
</html>