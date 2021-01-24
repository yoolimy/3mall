package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class MemberDao {
	private static MemberDao memberDao;
	private Connection conn;

	private MemberDao() {}
	
	public static MemberDao getInstance() {
		if (memberDao == null) {
			memberDao = new MemberDao();
		}
		return memberDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public int getMemberCount(String where) {
	// 조건을 받아와 조건에 맞는 회원들의 총합을 리턴하는 메소드
			int rcnt = 0;
			Statement stmt = null;
			ResultSet rs = null;
			String sql = null;
			try {
				sql = "select count(*) from t_member_list " + where;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					rcnt = rs.getInt(1);
				}
				
			} catch(Exception e) {
				System.out.println("getMemberCount() 오류");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}

			return rcnt;
	}
		
	public ArrayList<MemberInfo> getMemberList(String where, String orderby, int cpage, int psize) {
	// 검색조건과 정렬조건을 받아와 조건에 맞는 상품들을 정렬하여 그 목록을 ArrayList<MemberInfo>형으로 리턴하는 메소드
		ArrayList<MemberInfo> memList = new ArrayList<MemberInfo>();
		// 상품 목록을 저장할 ArrayList객체로 MemberInfo형 인스턴스만 저장함
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		MemberInfo memInfo = null; 			// 하나의 회원정보를 저장한 후 memList에 저장될 인스턴스
		int snum = (cpage - 1) * psize;		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			sql = "select * from t_member_list " + where + orderby + " limit " + snum + ", " + psize;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				memInfo = new MemberInfo();
				memInfo.setMl_id(rs.getString("ml_id"));
				memInfo.setMl_pwd(rs.getString("ml_pwd"));
				memInfo.setMl_name(rs.getString("ml_name"));
				memInfo.setMl_gender(rs.getString("ml_gender"));
				memInfo.setMl_birth(rs.getString("ml_birth"));
				memInfo.setMl_phone(rs.getString("ml_phone"));
				memInfo.setMl_email(rs.getString("ml_email"));
				memInfo.setMl_agremail(rs.getString("ml_agremail"));
				memInfo.setMl_date(rs.getString("ml_date"));
				memInfo.setMl_last(rs.getString("ml_last"));
				memInfo.setMl_status(rs.getString("ml_status"));
				memInfo.setMl_memo(rs.getString("ml_memo"));
				
				memList.add(memInfo);
			}
			
		} catch(Exception e) {
			System.out.println("getMemberList() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return memList;
	}	
	public MemberInfo getMemberInfo(String id) {
	// 지정된 id에 해당하는 하나의 회원 정보를 MemberInfo형 인스턴스로 리턴하는 메소드
		MemberInfo memberInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			stmt = conn.createStatement();
			sql = "select * from t_member_list where ml_id = '" + id + "' ";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				memberInfo = new MemberInfo();
				memberInfo.setMl_id(rs.getString("ml_id"));
				memberInfo.setMl_pwd(rs.getString("ml_pwd"));
				memberInfo.setMl_name(rs.getString("ml_name"));
				memberInfo.setMl_gender(rs.getString("ml_gender"));
				memberInfo.setMl_birth(rs.getString("ml_birth"));
				memberInfo.setMl_phone(rs.getString("ml_phone"));
				memberInfo.setMl_email(rs.getString("ml_email"));
				memberInfo.setMl_agremail(rs.getString("ml_agremail"));
				memberInfo.setMl_date(rs.getString("ml_date"));
				memberInfo.setMl_last(rs.getString("ml_last"));
				memberInfo.setMl_status(rs.getString("ml_status"));
				memberInfo.setMl_memo(rs.getString("ml_memo"));
			}
			
		} catch(Exception e) {
			System.out.println("getMemberInfo() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return memberInfo;
	}
	public MemberAddrInfo getMemberAddrInfo(String id, String basicYN) {
	// 지정된 id에 해당하는 하나의 회원 주소를 MemberAddrInfo형 인스턴스로 리턴하는 메소드
		MemberAddrInfo memberAddrInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			stmt = conn.createStatement();
			sql = " select ML.*, Ma.ma_zip, Ma.ma_addr1, Ma.ma_addr2, Ma.ma_basic " + 
					"from t_member_list ML, t_member_addr MA  where ML.ml_id = MA.ml_id and ML.ml_id = '" + id
					+ "'  and MA.ma_basic = '" + basicYN.toLowerCase() + "'";
//			if (memberAddrInfo.getMa_basic().equals("y")) {
//				where += " and MA.ma_basic = 'y' ";
//			} 
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				memberAddrInfo = new MemberAddrInfo();
				memberAddrInfo.setMl_id(rs.getString("ml_id"));
				memberAddrInfo.setMa_zip(rs.getString("ma_zip"));
				memberAddrInfo.setMa_addr1(rs.getString("ma_addr1"));
				memberAddrInfo.setMa_addr2(rs.getString("ma_addr2"));
				memberAddrInfo.setMa_basic(rs.getString("ma_basic"));
			}
			
		} catch(Exception e) {
			System.out.println("getMemberAddrInfo() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return memberAddrInfo;
	}
	public int MemberUpdate(MemberInfo memUpInfo) {
	// 회원 수정 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		String sql = null;
		
		try {
			sql = "update t_member_list set ml_status = '" + memUpInfo.getMl_status() + "', ml_memo = '" + memUpInfo.getMl_memo() + 
					"' where ml_id = '" + memUpInfo.getMl_id() + "'";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("MemberUpdate() 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return result;
	}
}
