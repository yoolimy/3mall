package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrdFormSvc {
	public ArrayList<CartInfo> getOrdFrmPdtList(String kind, String where) {
		ArrayList<CartInfo> pdtList = null;
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);
		pdtList = ordDao.getOrdFrmPdtList(kind, where);
		close(conn);

		return pdtList;
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
