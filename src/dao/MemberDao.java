package dao;

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

	public int joinInsert(MemberInfo memberInfo) {
	// 회원가입을 위한 메소드
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "insert into t_member_list(ml_id, ml_pwd, ml_name, ml_gender, ml_birth, ml_phone, ml_email) values (?, ?, ?, ?, ? ,?, ?)";
				if (rs.next()) {
					
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memberInfo.getMl_id());
				pstmt.setString(2, memberInfo.getMl_pwd());
				pstmt.setString(3, memberInfo.getMl_name());
				pstmt.setString(4, memberInfo.getMl_gender());
				pstmt.setString(5, memberInfo.getMl_birth());
				pstmt.setString(6, memberInfo.getMl_phone());
				pstmt.setString(7, memberInfo.getMl_email());
				
				}
			result = pstmt.executeUpdate();	// 새로운 회원 등록
			
		} catch(Exception e) {
			System.out.println("JoinInsert() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	
			close(pstmt);
		}	
		return result;
	}
	
	public MemberInfo getMypageView(String id) {
	// 지정된 id의 정보들을 보여줄 메소드
		MemberInfo memberInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			stmt = conn.createStatement();
			sql = "select * from t_member_list where ml_id = '" + id + "' ";
			System.out.println(sql);
			
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
			}
			
		} catch(Exception e) {
			System.out.println("getMypageView() 오류");
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
			sql = " select ML.ml_id, ML.ml_pwd, ML.ml_name, ML.ml_gender, ML.ml_birth, ML.ml_phone, ML.ml_email, ML.ml_agremail, Ma.ma_idx, Ma.ma_zip, Ma.ma_addr1, Ma.ma_addr2, Ma.ma_basic " + 
					"from t_member_list ML, t_member_addr MA  where ML.ml_id = MA.ml_id and ML.ml_id = '" + id
					+ "'  and MA.ma_basic = '" + basicYN.toLowerCase() + "'";
			
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				memberAddrInfo = new MemberAddrInfo();
				memberAddrInfo.setMl_id(rs.getString("ml_id"));
				memberAddrInfo.setMa_idx(rs.getInt("ma_idx"));
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
	public int getMypageUpdate(MemberInfo mypageInfo) {
	// 회원 정보 수정 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		
		try {
			String sql = "update t_member_list set  ml_pwd = '" + mypageInfo.getMl_pwd() + "', " +  
					" ml_name = '" + mypageInfo.getMl_name() + "', " + 
					" ml_phone = '" + mypageInfo.getMl_phone() + "', " +
					" ml_email = '" + mypageInfo.getMl_email() + "', " + 
					" ml_agremail = '" + mypageInfo.getMl_agremail() + "', " + 
					" ml_birth = '" + mypageInfo.getMl_birth() + "', " + 
					" ml_gender = '" + mypageInfo.getMl_gender() + "' " + 
					" where ml_id = '" + mypageInfo.getMl_id() + "' "; 
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("getMypageUpdate() 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return result;
	}
	public int getMypageAddrUpdate(MemberAddrInfo memberAddrInfo) {
		// 회원 주소 정보 수정 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		
		try {
			String sql = "update t_member_addr set ma_zip = '" + memberAddrInfo.getMa_zip() + "', " + 
					" ma_addr1 = '" + memberAddrInfo.getMa_addr1() + "', " +  
					" ma_addr2 = '" + memberAddrInfo.getMa_addr2() + "', " +  
					" ma_basic = '" + memberAddrInfo.getMa_basic() + "' where ma_idx = '" + memberAddrInfo.getMa_idx() + "' ";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		System.out.println(sql);	
		} catch(Exception e) {
			System.out.println("getMypageUpdate() 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return result;
	}
	public int getMypageInsert(String id, MemberAddrInfo memberAddrList) {
	// 회원 주소 정보 등록처리를 위한 메소드
		PreparedStatement pstmt = null;
		ResultSet rs = null;		// 등록할 게시글의 번호를 얻기 위한 ResultSet
		int idx = 1, result = 0;	// 새로운 글번호와 쿼리 실행 결과 개수를 저장할 변수
		String sql = null;
		
		try {
			sql = "select max(ma_idx) + 1 from t_member_addr";	// count 사용 안됨(중간중간 지워진게 있을 수 있음)
			pstmt = conn.prepareStatement(sql);	// prepareStatement 만들면서 쿼리를 실행
			rs = pstmt.executeQuery();
			if (rs.next()) {
				idx = rs.getInt(1); // resertSet안에 들어있는 컬럼들의 번호들 중 1번
				// 등록할 게시글의 새로운 글번호 생성
			}
			sql = "insert into t_member_addr (ma_idx, ml_id, ma_zip, ma_addr1, ma_addr2, ma_basic) " + 
			" values (?, ?, ?, ?, ?, ?)";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, id);
			pstmt.setString(3, memberAddrList.getMa_zip());
			pstmt.setString(4, memberAddrList.getMa_addr1());
			pstmt.setString(5, memberAddrList.getMa_addr2());
			pstmt.setString(6, memberAddrList.getMa_basic());
			
			
			result = pstmt.executeUpdate();
			// 새로운 게시글 등록
			
		} catch(Exception e) {
			System.out.println("getMypageInsert() 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}
}
