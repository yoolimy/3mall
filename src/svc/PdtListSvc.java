package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;;

public class PdtListSvc {
	public int getPdtCount(String where) {
	// �˻��� ��ǰ�� ��ü ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü ���ڵ� ������ ������ ����
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		rcnt = pdtDao.getPdtCount(where);
		close(conn);

		return rcnt;
	}

	public ArrayList<PdtInfo> getPdtList(String where, String orderby, int cpage, int psize) {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		// ��ǰ ����� ������ ArrayList��ü�� PdtInfo�� �ν��Ͻ��� ������
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		pdtList = pdtDao.getPdtList(where, orderby, cpage, psize);
		close(conn);

		return pdtList;
	}
	public ArrayList<CataBigInfo> getCataBigList() {
	// ��з� ����� ArrayList �� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<CataBigInfo> cataBigList = null;
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		cataBigList = pdtDao.getCataBigList();
		close(conn);
		return cataBigList;
	}
	public ArrayList<CataSmallInfo> getCataSmallList() {
	// �Һз� ����� ArrayList �� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<CataSmallInfo> cataSmallList = null;
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		cataSmallList = pdtDao.getCataSmallList();
		close(conn);
		return cataSmallList;
	}
}
