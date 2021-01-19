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
				reviewInfo.setRl_rate(rs.getInt("rl_rate"));
				reviewInfo.setRl_reply(rs.getInt("rl_reply"));
				reviewInfo.setRl_rept(rs.getString("rl_rept"));
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
}
