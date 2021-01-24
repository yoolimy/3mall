package admin.svc;

import static db.JdbcUtil.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminSvc {
	public AdminMemberInfo getAdminMember(String aid, String pwd) {
			AdminDao adminDao = AdminDao.getInstance();
			Connection conn = getConnection();
			adminDao.setConnection(conn);
			AdminMemberInfo adminMember = adminDao.getAdminMember(aid, pwd);
			close(conn);

			return adminMember;
		}
}
