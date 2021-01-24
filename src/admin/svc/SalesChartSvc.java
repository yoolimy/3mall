package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class SalesChartSvc {
	public ArrayList<SalesInfo> getSalesList() {
		ArrayList<SalesInfo> salesList = null;	// ������ ��ǰ ������ ���� �ν��Ͻ� ����
		Connection conn = getConnection();
		SalesDao salesDao = SalesDao.getInstance();
		salesDao.setConnection(conn);

		salesList = salesDao.getSalesList();	// ������ ��ǰ���� �ޱ�

		close(conn);
		return salesList;
	}
}
