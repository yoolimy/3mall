package svc;

import static db.JdbcUtil.*;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import vo.*;

public class LoginSvc {
// �α��ο� ���� ó���۾��� �����ϴ� Ŭ����(DBó�� ����)
	public MemberInfo getLoginMember(HttpServletResponse response, String uid, String pwd ) throws ServletException, IOException {
		LoginDao loginDao = LoginDao.getInstance();
		Connection conn = getConnection();
		loginDao.setConnection(conn);
		MemberInfo loginMember = loginDao.getLoginMember(response, uid, pwd);
		close(conn);
		return loginMember;
	}
}
