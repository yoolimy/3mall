package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MypageProcSvc {
	public boolean getMypageUpdate(MemberInfo mypageInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		int result = memberDao.getMypageUpdate(mypageInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return isSuccess;
	}
	public boolean getMypageAddrUpdate(MemberAddrInfo memberAddrInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		int result = memberDao.getMypageAddrUpdate(memberAddrInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return isSuccess;
	}
	public boolean getMypageAddrInsert(String id, MemberAddrInfo memberAddrList) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		int result = memberDao.getMypageInsert(id, memberAddrList);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return isSuccess;
	}
	public boolean getMypageAddrDelete(String id, MemberAddrInfo memberAddrList) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		int result = memberDao.getMypageAddrDelete(id, memberAddrList);
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
