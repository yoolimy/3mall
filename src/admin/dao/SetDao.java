package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class SetDao {
	private static SetDao setDao;
	private Connection conn;

	private SetDao() {}
	
	public static SetDao getInstance() {
		if (setDao == null) {
			setDao = new SetDao();
		}
		return setDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public int settingForm(SettingInfo set) {
	// 시스템 설정 등록 처리를 위한 메소드
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			sql = "insert into t_admin_etc (ae_main1, ae_main2, ae_main3, ae_popup, ae_system) values (?, ?, ?, ?, ?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, set.getAe_main1());
			pstmt.setString(2, set.getAe_main2());
			pstmt.setString(3, set.getAe_main3());
			pstmt.setString(4, set.getAe_popup());
			pstmt.setString(5, set.getAe_system());
			result = pstmt.executeUpdate();

		} catch(Exception e) {
			System.out.println("pdtInsert() 오류");
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

}
