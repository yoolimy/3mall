package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewViewSvc {
// �Խñ� ������ ����Ͻ� ������ ó���ϴ� Ŭ����
	public ReviewInfo getArticle(int idx) {
	// �μ��� �޾ƿ� idx�� �ش��ϴ� �Խù� �ϳ��� ������ �����Ͽ� FreeInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ReviewInfo article = null;	// ������ �Խù� ������ ���� �ν��Ͻ� ����
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);

		article = reviewDao.getArticle(idx);	// ������ �Խñ� �ޱ�

		close(conn);
		return article;
	}
}
