package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class MemberViewSvc {
	public MemberInfo getMemberInfo(String id) {
		MemberInfo memberInfo = null;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		
		memberInfo = memberDao.getMemberInfo(id);
		
		close(conn);
		return memberInfo;
	}
	public MemberAddrInfo getMemberAddrInfo(String id, String basicYN) {
		MemberAddrInfo memberAddrInfo = null;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		
		memberAddrInfo = memberDao.getMemberAddrInfo(id, basicYN);
		
		close(conn);
		return memberAddrInfo;
	}
}
