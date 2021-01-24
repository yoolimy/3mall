package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewProcSvc {
// �Խñۿ� ���� ���� �۾��� ó���ϴ� Ŭ����
	public boolean reviewInsert(ReviewInfo reviewInfo) throws Exception {
	// �Խñ� ����� ���� �޼ҵ�
		boolean isSuccess = false;
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);

		int result = reviewDao.reviewInsert(reviewInfo);
		if (result > 0) {
		// �۵���ϴ� ������ �������� �� ��ϵ� ���ڵ尡 ������
			commit(conn);	// ������ ������ �����Ŵ
			isSuccess = true;
		} else {
		// �۵���ϴ� ������ �������� �� ��ϵ� ���ڵ尡 ������
			rollback(conn);	// �����ߴ� ��� ������ ��ȿȭ ��Ŵ
		}

		close(conn);
		return isSuccess;
	}
	public boolean reviewUpdate(ReviewInfo reviewInfo) throws Exception {
	// �Խñ� ������ ���� �޼ҵ�
		boolean isSuccess = false;
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);

		int result = reviewDao.reviewUpdate(reviewInfo);
		if (result > 0) {
			commit(conn);
			isSuccess = true;
		} else {
			rollback(conn);
		}

		close(conn);
		return isSuccess;
	}
	public boolean reviewDelete(ReviewInfo reviewInfo) throws Exception {
	// �Խñ� ������ ���� �޼ҵ�
		boolean isSuccess = false;
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);

		int result = reviewDao.reviewDelete(reviewInfo);
		if (result > 0) {
			commit(conn);
			isSuccess = true;
		} else {
			rollback(conn);
		}

		close(conn);
		return isSuccess;
	}
	
}
