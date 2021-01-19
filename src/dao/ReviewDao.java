package dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class ReviewDao {
	private static ReviewDao reviewDao;
	private Connection conn;

	private ReviewDao() {}
	// �⺻�����ڷ� �ܺο��� �Ժη� �������� ���ϰ� private���� ����
	
	public static ReviewDao getInstance() {
		if (reviewDao == null) {	// ���� ������ �ν��Ͻ��� ������
			reviewDao = new ReviewDao();	// ���Ӱ� �ν��Ͻ��� ����
		}
		return reviewDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public int getArticleCount(String where) {
	// �˻��� �Խñ��� �� ������ �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		
		try {
			sql = "select count(*) from t_review_list " + 
				" where rl_status = 'a' " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	result = rs.getInt(1);
			// �˻��� �Խù��� ������ result�� ����
		} catch(Exception e) {
			System.out.println("getArticleCount() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}
	public ArrayList<ReviewInfo> getArticleList(String where, int cpage, int limit) {
	// �˻��� �Խñ� ����� ArrayList���·� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<ReviewInfo> reviewList = new ArrayList<ReviewInfo>();
		// �˻� ����� ������ ArrayList��ü
		ReviewInfo reviewInfo = null;
		// reviewList�� ���� ���ڵ带 ������ �ν��Ͻ�
		int snum = (cpage - 1) * limit;
		// ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ

		try {
			sql = "select cb.cb_name, p.pl_name, r.* from t_review_list r, t_product_list p, t_cata_small cs, " +
				" t_cata_big cb where r.pl_id = p.pl_id and p.cs_idx = cs.cs_idx and cs.cb_idx = cb.cb_idx " +
				" and r.rl_status = 'a' " + where + " order by r.rl_idx desc limit " + snum + ", " + limit;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
			// rs�� ����� ��� �� ���·� �����ϱ� ���� if�� ������� �ʰ� �ٷ� while���
				reviewInfo = new ReviewInfo();
				// �ϳ��� ���ڵ�(�Խñ�)�� ������ �ν��Ͻ� ����

				reviewInfo.setCatabig(rs.getString("cb_name"));
				reviewInfo.setPlname(rs.getString("pl_name"));
				reviewInfo.setRl_idx(rs.getInt("rl_idx"));
				reviewInfo.setPl_id(rs.getString("pl_id"));
				reviewInfo.setMl_id(rs.getString("ml_id"));
				reviewInfo.setRl_title(rs.getString("rl_title"));
				reviewInfo.setRl_content(rs.getString("rl_content"));
				reviewInfo.setRl_img(rs.getString("rl_img"));
				reviewInfo.setRl_rate(rs.getInt("rl_rate"));
				reviewInfo.setRl_reply(rs.getInt("rl_reply"));
				reviewInfo.setRl_rept(rs.getString("rl_rept"));
				reviewInfo.setRl_ip(rs.getString("rl_ip"));
				reviewInfo.setRl_date(rs.getString("rl_date"));
				reviewInfo.setRl_status(rs.getString("rl_status"));
				// �޾ƿ� ���ڵ��� reviewInfo �ν��Ͻ��� ������� ������ ����

				reviewList.add(reviewInfo);
				// ������ FreeInfo�� �ν��Ͻ��� �ϳ��� ����
			}
		} catch(Exception e) {
			System.out.println("getArticleList() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return reviewList;
	}
}
