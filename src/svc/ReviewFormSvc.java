package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewFormSvc {
	public ReviewInfo getArticleUp(int idx, String uid) {
	// ������ �ۿ� ���� ������ ���� ��� �ش� �����͸� ������ ������ �޼ҵ�
		ReviewInfo article = null;
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);

		article = reviewDao.getArticleUp(idx, uid);
		// ������ �ۿ� ���� ������ ���� ��� �ش� �����͸� ������

		close(conn);
		return article;
	}

}
