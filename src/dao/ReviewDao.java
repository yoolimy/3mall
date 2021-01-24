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
	
	
	public ReviewInfo getArticle(int idx) {
		// ������ �Խù��� �����͸� Review�� �ν��Ͻ��� �����ϴ� �޼ҵ�
			ReviewInfo article = null;
			Statement stmt = null;
			ResultSet rs = null;
			String sql = null;
			
			try {
				sql = " select cb.cb_name, p.pl_name, r.* " + 
						" from t_review_list r, t_product_list p, t_cata_small cs, t_cata_big cb " + 
						" where r.pl_id = p.pl_id and p.cs_idx = cs.cs_idx and cs.cb_idx = cb.cb_idx " + 
						" and r.rl_status = 'a' " + 
						" order by r.rl_idx desc";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
				// rs�� �����Ͱ� ������(�ش� �Խù��� ������)
					article = new ReviewInfo();
					// ������ �ν��Ͻ�(�����͸� ������ �ν��Ͻ�) ����
					article.setCatabig(rs.getString("cb_name"));
					article.setPlname(rs.getString("pl_name"));
					article.setRl_idx(rs.getInt("rl_idx"));
					article.setPl_id(rs.getString("pl_id"));
					article.setMl_id(rs.getString("ml_id"));
					article.setRl_title(rs.getString("rl_title"));
					article.setRl_content(rs.getString("rl_content"));
					article.setRl_img(rs.getString("rl_img"));
					article.setRl_ip(rs.getString("rl_ip"));
					article.setRl_date(rs.getString("rl_date"));
					article.setRl_status(rs.getString("rl_status"));
					// �޾ƿ� ���ڵ��� article �ν��Ͻ��� ��� ������ ���� ����
				}
			} catch(Exception e) {
				System.out.println("getArticle() ����");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}

			return article;
		}
	
	public int reviewInsert(ReviewInfo reviewInfo) {
	// �Խñ� ��� �޼ҵ�
		PreparedStatement pstmt = null;
		ResultSet rs = null;		// ����� �Խñ��� ��ȣ�� ��� ���� ResultSet
		int idx = 1, result = 0;	// ���ο� �۹�ȣ�� ���� ���� ��� ������ ������ ����
		String sql = null;

		try {
			sql = "select max(Rl_idx) + 1 from t_review_list";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())	idx = rs.getInt(1);
			// ����� �Խñ��� ���ο� �۹�ȣ ����

			sql = "insert into t_review_list (rl_idx, pl_id, ml_id, " + 
				"rl_title, rl_content, rl_img) values (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.setString(2, reviewInfo.getPl_id());
			pstmt.setString(3, reviewInfo.getMl_id());
			pstmt.setString(4, reviewInfo.getRl_title());
			pstmt.setString(5, reviewInfo.getRl_content());
			pstmt.setString(6, reviewInfo.getRl_img());

			result = pstmt.executeUpdate();
			// ���ο� �Խñ� ���

		} catch(Exception e) {
			System.out.println("reviewInsert() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(pstmt);
		}

		return result;
	}
	
	
	public int reviewUpdate(ReviewInfo reviewInfo) {
	// �Խñ� ������ ���� �޼ҵ�
		PreparedStatement pstmt = null;
		String sql = null;
		int result = 0;		// ���� ���� ��� ������ ������ ����

		try {
			sql = "update t_review_list set rl_title = ?, rl_content = ? where rl_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reviewInfo.getRl_title());
			pstmt.setString(2, reviewInfo.getRl_content());
			pstmt.setInt(3, reviewInfo.getRl_idx());
			result = pstmt.executeUpdate();	// �Խñ� ����
		} catch(Exception e) {
			System.out.println("reviewUpdate() ����");
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}
	
	
	public int reviewDelete(ReviewInfo reviewInfo) {
	// �Խñ� ������ ���� �޼ҵ�
		Statement stmt = null;
		String sql = null;
		int result = 0;		// ���� ���� ��� ������ ������ ����

		try {
			String where = " where rl_idx = " + reviewInfo.getRl_idx();
				where += " and fl_writer = '" + reviewInfo.getMl_id() + "' ";
			sql = "update t_review_list set rl_status = 'b' " + where;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("reviewDelete() ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}
	
	
	public ReviewInfo getArticleUp(int idx, String uid) {
	// ������ �ۿ� ���� ������ ���� ��� �ش� �����͸� ������ ������ �޼ҵ�
		ReviewInfo article = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			sql = "select * from t_review_list where rl_status = 'a' and rl_idx = " + idx;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				article = new ReviewInfo();
				// �˻��� �Խù��� ������ ������ FreeInfo�� �ν��Ͻ� article ����
				article.setCatabig(rs.getString("cb_name"));
				article.setPlname(rs.getString("pl_name"));
				article.setRl_idx(rs.getInt("rl_idx"));
				article.setPl_id(rs.getString("pl_id"));
				article.setMl_id(rs.getString("ml_id"));
				article.setRl_title(rs.getString("rl_title"));
				article.setRl_content(rs.getString("rl_content"));
				article.setRl_date(rs.getString("rl_date"));
				article.setRl_status(rs.getString("rl_status"));
				article.setRl_ip(rs.getString("rl_ip"));
				// �޾ƿ� ���ڵ��� article �ν��Ͻ��� ��� ������ ���� ����
			}
		} catch(Exception e) {
			System.out.println("getArticleUp() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return article;
	}
}
