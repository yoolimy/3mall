package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class JoinProcSvc {
	public boolean JoinInsert(MemberInfo memberInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		int result = memberDao.JoinInsert(memberInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return isSuccess;
	}
	public boolean JoinAddrInsert(String id, MemberAddrInfo memberAddrInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		int result = memberDao.JoinAddrInsert(id, memberAddrInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return isSuccess;
	}
}
