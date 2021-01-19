package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class MemberListSvc {
	public int getMemberCount(String where) {
	// 검색된 상품의 전체 개수를 리턴하는 메소드
		int rcnt = 0; // 전체 레코드 개수를 저장할 변수
		
		// DB연결
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		rcnt = memberDao.getMemberCount(where);
		close(conn);
		
		return rcnt;
		
	}
	public ArrayList<MemberInfo> getMemberList(String where, String orderby, int cpage, int psize) {
	// 게시글 목록을 ArrayList로 리턴하며, 반드시 MemberInfo형 인스턴스만 저장되어야 함 /  getMemberList : 메소드 이름
	// 매개변수 : 조건, 정렬, 현재 페이지 번호, 가져올 레코드 개수	
		ArrayList<MemberInfo> memList = new ArrayList<MemberInfo>();
		// 게시물 목록을 담을 ArrayList 선언
		
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		memList = memberDao.getMemberList(where, orderby, cpage, psize);
		close(conn);
		
		
		return memList;
	}
}
