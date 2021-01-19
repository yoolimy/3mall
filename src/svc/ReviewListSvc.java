package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewListSvc {
	public int getArticleCount(String where) {
	// 게시글의 전체 개수를 리턴하는 메소드
		int rcnt = 0;	// 전체 레코드 개수를 저장할 변수
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		rcnt = reviewDao.getArticleCount(where);
		close(conn);

		return rcnt;
	}
	public ArrayList<ReviewInfo> getArticleList(String where, int cpage, int limit) {
	// 게시글 목록을 ArrayList로 리턴하며, 반드시 FreeInfo형 인스턴스만 저장되어야 함
	// 매개변수 : 조건, 현재 페이지 번호, 가져올 레코드 개수
		ArrayList<ReviewInfo> articleList = null;
		// 게시물 목록을 담을 ArrayList 선언
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		articleList = reviewDao.getArticleList(where, cpage, limit);
		close(conn);

		return articleList;
	}
}
