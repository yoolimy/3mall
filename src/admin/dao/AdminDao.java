package admin.dao;

import static db.JdbcUtil.*;
import java.sql.*;
import java.util.ArrayList;

import vo.*;

public class AdminDao {
	private static AdminDao adminDao;
	private Connection conn;

	private AdminDao() {}

	public static AdminDao getInstance() {
		if (adminDao == null) {
			adminDao = new AdminDao();
		}
		return adminDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public AdminMemberInfo getAdminMember(String aid, String pwd) {
		AdminMemberInfo adminMember = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			// 쿼리를 운반할 Statement생성
			String sql = "select * from t_admin_list " + 
				" where al_status = 'a' and al_id = '" + aid + 
				"' and al_pwd = '" + pwd + "'";
			rs = stmt.executeQuery(sql);
			// 쿼리 실행 결과를 ResultSet에 담음
			if (rs.next()) {	// 로그인 성공시
				adminMember = new AdminMemberInfo();
				// 로그인한 회원정보를 저장할 loginMember인스턴스 생성

				adminMember.setAl_id(aid);
				adminMember.setAl_pwd(pwd);
				adminMember.setAl_name(rs.getString("al_name"));
				adminMember.setAl_phone(rs.getString("al_phone"));
				adminMember.setAl_email(rs.getString("al_email"));
				adminMember.setAl_date(rs.getString("al_date"));
				adminMember.setAl_memo(rs.getString("al_memo"));
				adminMember.setAl_team(rs.getString("al_team"));
				adminMember.setAl_position(rs.getString("al_position"));
				adminMember.setAl_status("a");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close(rs);		close(stmt);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		return adminMember;
	}
	
	public ArrayList<AdminMemberInfo> getAdminMemList() {
		ArrayList<AdminMemberInfo> adminMemList = new ArrayList<AdminMemberInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		AdminMemberInfo adminMemInfo = null;
		
		try {
			sql = "select * from t_admin_list order by al_idx";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				adminMemInfo = new AdminMemberInfo();
				adminMemInfo.setAl_idx(rs.getInt("al_idx"));
				adminMemInfo.setAl_id(rs.getString("al_id"));
				adminMemInfo.setAl_pwd(rs.getString("al_pwd"));
				adminMemInfo.setAl_name(rs.getString("al_name"));
				adminMemInfo.setAl_phone(rs.getString("al_phone"));
				adminMemInfo.setAl_email(rs.getString("al_email"));
				adminMemInfo.setAl_date(rs.getString("al_date"));
				adminMemInfo.setAl_memo(rs.getString("al_memo"));
				adminMemInfo.setAl_team(rs.getString("al_team"));
				adminMemInfo.setAl_position(rs.getString("al_position"));
				adminMemInfo.setAl_status(rs.getString("al_status"));
				
				adminMemList.add(adminMemInfo);
			}
			
		} catch(Exception e) {
			System.out.println("getAdminMemList() 오류" + e);	//	e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return adminMemList;
	}	
	
	public int getAdminMemCnt() {
		int adminCnt = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from t_admin_list";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				adminCnt = rs.getInt(1);
			}
		} catch(Exception e) {
			System.out.println("getAdminMemCnt() 오류" + e);//	e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return adminCnt;		
	}
}
