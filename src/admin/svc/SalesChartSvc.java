package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class SalesChartSvc {
	public ArrayList<SalesInfo> getSalesList() {
		ArrayList<SalesInfo> salesList = null;	// 리턴할 상품 정보를 담을 인스턴스 선언
		Connection conn = getConnection();
		SalesDao salesDao = SalesDao.getInstance();
		salesDao.setConnection(conn);

		salesList = salesDao.getSalesList();	// 보여줄 상품정보 받기

		close(conn);
		return salesList;
	}
}
