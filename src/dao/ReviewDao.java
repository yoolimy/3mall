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
	// 기본생성자로 외부에서 함부로 생성하지 못하게 private으로 선언
	
	public static ReviewDao getInstance() {
		if (reviewDao == null) {	// 현재 생성된 인스턴스가 없으면
			reviewDao = new ReviewDao();	// 새롭게 인스턴스를 생성
		}
		return reviewDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public int getArticleCount(String where) {
	// 검색된 게시글의 총 개수를 리턴하는 메소드
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
			// 검색된 게시물의 개수를 result에 담음
		} catch(Exception e) {
			System.out.println("getArticleCount() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}
	public ArrayList<ReviewInfo> getArticleList(String where, int cpage, int limit) {
	// 검색된 게시글 목록을 ArrayList형태로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<ReviewInfo> reviewList = new ArrayList<ReviewInfo>();
		// 검색 결과를 저장할 ArrayList객체
		ReviewInfo reviewInfo = null;
		// reviewList에 담을 레코드를 저장할 인스턴스
		int snum = (cpage - 1) * limit;
		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호

		try {
			sql = "select cb.cb_name, p.pl_name, r.* from t_review_list r, t_product_list p, t_cata_small cs, " +
				" t_cata_big cb where r.pl_id = p.pl_id and p.cs_idx = cs.cs_idx and cs.cb_idx = cb.cb_idx " +
				" and r.rl_status = 'a' " + where + " order by r.rl_idx desc limit " + snum + ", " + limit;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
			// rs가 비었을 경우 빈 상태로 리턴하기 위해 if를 사용하지 않고 바로 while사용
				reviewInfo = new ReviewInfo();
				// 하나의 레코드(게시글)를 저장할 인스턴스 생성

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
				// 받아온 레코드들로 reviewInfo 인스턴스에 멤버변수 값으로 넣음

				reviewList.add(reviewInfo);
				// 생성된 FreeInfo형 인스턴스를 하나씩 저장
			}
		} catch(Exception e) {
			System.out.println("getArticleList() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return reviewList;
	}
	
	
	public ReviewInfo getArticle(int idx) {
		// 지정된 게시물의 데이터를 Review형 인스턴스로 리턴하는 메소드
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
				// rs에 데이터가 있으면(해당 게시물이 있으면)
					article = new ReviewInfo();
					// 리턴할 인스턴스(데이터를 저장할 인스턴스) 생성
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
					// 받아온 레코드들로 article 인스턴스의 멤버 변수에 값을 넣음
				}
			} catch(Exception e) {
				System.out.println("getArticle() 오류");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}

			return article;
		}
	
	public int reviewInsert(ReviewInfo reviewInfo) {
	// 게시글 등록 메소드
		PreparedStatement pstmt = null;
		ResultSet rs = null;		// 등록할 게시글의 번호를 얻기 위한 ResultSet
		int idx = 1, result = 0;	// 새로운 글번호와 쿼리 실행 결과 개수를 저장할 변수
		String sql = null;

		try {
			sql = "select max(Rl_idx) + 1 from t_review_list";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())	idx = rs.getInt(1);
			// 등록할 게시글의 새로운 글번호 생성

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
			// 새로운 게시글 등록

		} catch(Exception e) {
			System.out.println("reviewInsert() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(pstmt);
		}

		return result;
	}
	
	
	public int reviewUpdate(ReviewInfo reviewInfo) {
	// 게시글 수정을 위한 메소드
		PreparedStatement pstmt = null;
		String sql = null;
		int result = 0;		// 쿼리 실행 결과 개수를 저장할 변수

		try {
			sql = "update t_review_list set rl_title = ?, rl_content = ? where rl_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reviewInfo.getRl_title());
			pstmt.setString(2, reviewInfo.getRl_content());
			pstmt.setInt(3, reviewInfo.getRl_idx());
			result = pstmt.executeUpdate();	// 게시글 수정
		} catch(Exception e) {
			System.out.println("reviewUpdate() 오류");
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}
	
	
	public int reviewDelete(ReviewInfo reviewInfo) {
	// 게시글 삭제를 위한 메소드
		Statement stmt = null;
		String sql = null;
		int result = 0;		// 쿼리 실행 결과 개수를 저장할 변수

		try {
			String where = " where rl_idx = " + reviewInfo.getRl_idx();
				where += " and fl_writer = '" + reviewInfo.getMl_id() + "' ";
			sql = "update t_review_list set rl_status = 'b' " + where;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("reviewDelete() 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}
	
	
	public ReviewInfo getArticleUp(int idx, String uid) {
	// 수정할 글에 대한 권한이 있을 경우 해당 데이터를 가져와 리턴할 메소드
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
				// 검색된 게시물의 정보를 저장할 FreeInfo형 인스턴스 article 생성
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
				// 받아온 레코드들로 article 인스턴스의 멤버 변수에 값을 넣음
			}
		} catch(Exception e) {
			System.out.println("getArticleUp() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return article;
	}
}
