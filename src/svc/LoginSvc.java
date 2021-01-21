package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import dao.*;
import vo.*;

public class LoginSvc {
// 로그인에 대한 처리작업을 실행하는 클래스(DB처리 예외)
	public MemberInfo getLoginMember(String uid, String pwd ) {
		LoginDao loginDao = LoginDao.getInstance();
		Connection conn = getConnection();
		loginDao.setConnection(conn);
		MemberInfo loginMember = loginDao.getLoginMember(uid, pwd);
		close(conn);
		return loginMember;
	}
}
