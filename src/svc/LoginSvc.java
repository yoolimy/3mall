package svc;

import static db.JdbcUtil.*;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import vo.*;

public class LoginSvc {
// 로그인에 대한 처리작업을 실행하는 클래스(DB처리 예외)
	public MemberInfo getLoginMember(HttpServletResponse response, String uid, String pwd ) throws ServletException, IOException {
		LoginDao loginDao = LoginDao.getInstance();
		Connection conn = getConnection();
		loginDao.setConnection(conn);
		MemberInfo loginMember = loginDao.getLoginMember(response, uid, pwd);
		close(conn);
		return loginMember;
	}
}
