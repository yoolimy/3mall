package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewListSvc {
	public int getArticleCount(String where) {
	// �Խñ��� ��ü ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü ���ڵ� ������ ������ ����
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		rcnt = reviewDao.getArticleCount(where);
		close(conn);

		return rcnt;
	}
	public ArrayList<ReviewInfo> getArticleList(String where, int cpage, int limit) {
	// �Խñ� ����� ArrayList�� �����ϸ�, �ݵ�� FreeInfo�� �ν��Ͻ��� ����Ǿ�� ��
	// �Ű����� : ����, ���� ������ ��ȣ, ������ ���ڵ� ����
		ArrayList<ReviewInfo> articleList = null;
		// �Խù� ����� ���� ArrayList ����
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		articleList = reviewDao.getArticleList(where, cpage, limit);
		close(conn);

		return articleList;
	}
}
