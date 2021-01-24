package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class SalesDao {
	private static SalesDao salesDao;
	private Connection conn;

	private SalesDao() {}
	
	public static SalesDao getInstance() {
		if (salesDao == null) {
			salesDao = new SalesDao();
		}
		return salesDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<SalesInfo> getSalesList() {
	// DB에서 소분류 목록을 받아 리턴하는 메소드
		ArrayList<SalesInfo> salesList = new ArrayList<SalesInfo>();
		SalesInfo salesInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select substr(ol_id, 3, 4) month, sum(ol_pay) sum " +
				" from t_order_list group by substr(ol_id, 3, 4) order by substr(ol_id, 3, 4);";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				salesInfo = new SalesInfo();

				salesInfo.setEach_month(rs.getString(1));
				salesInfo.setSum_sales(rs.getInt(2));

				salesList.add(salesInfo);
			}
		} catch(Exception e) {
			System.out.println("getSalesList() 오류" + e);	//	e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return salesList;
	}
}
