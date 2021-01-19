package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MypageViewSvc {
	public MemberInfo getMypageView(String id) {
		MemberInfo memberInfo = null;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		
		memberInfo = memberDao.getMypageView(id);
		
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
