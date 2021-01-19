package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class MemberUpProcSvc {
	public boolean MemberUpdate(MemberInfo memUpInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		int result = memberDao.MemberUpdate(memUpInfo);
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
