package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewFormSvc {
	public ReviewInfo getArticleUp(int idx, String uid) {
	// 수정할 글에 대한 권한이 있을 경우 해당 데이터를 가져와 리턴할 메소드
		ReviewInfo article = null;
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);

		article = reviewDao.getArticleUp(idx, uid);
		// 수정할 글에 대한 권한이 있을 경우 해당 데이터를 가져옴

		close(conn);
		return article;
	}

}
