package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminMemListSvc {
	public ArrayList<AdminMemberInfo> getAdminMemList() {
		ArrayList<AdminMemberInfo> adminMemList = new ArrayList<AdminMemberInfo>();
	
		Connection conn = getConnection();
		AdminDao adminDao = AdminDao.getInstance();
		adminDao.setConnection(conn);
		adminMemList = adminDao.getAdminMemList();
		close(conn);
		
		return adminMemList;
	}

	public int getAdminMemCnt() {
		int adminCnt = 0;
	
		Connection conn = getConnection();
		AdminDao adminDao = AdminDao.getInstance();
		adminDao.setConnection(conn);
		adminCnt = adminDao.getAdminMemCnt();
		close(conn);
		
		return adminCnt;
	}
}
