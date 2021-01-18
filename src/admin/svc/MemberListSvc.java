package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class MemberListSvc {
	public int getMemberCount(String where) {
	// �˻��� ��ǰ�� ��ü ������ �����ϴ� �޼ҵ�
		int rcnt = 0; // ��ü ���ڵ� ������ ������ ����
		
		// DB����
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		rcnt = memberDao.getMemberCount(where);
		close(conn);
		
		return rcnt;
		
	}
	public ArrayList<MemberInfo> getMemberList(String where, String orderby, int cpage, int psize) {
	// �Խñ� ����� ArrayList�� �����ϸ�, �ݵ�� MemberInfo�� �ν��Ͻ��� ����Ǿ�� �� /  getMemberList : �޼ҵ� �̸�
	// �Ű����� : ����, ����, ���� ������ ��ȣ, ������ ���ڵ� ����	
		ArrayList<MemberInfo> memList = new ArrayList<MemberInfo>();
		// �Խù� ����� ���� ArrayList ����
		
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		memList = memberDao.getMemberList(where, orderby, cpage, psize);
		close(conn);
		
		
		return memList;
	}
}
