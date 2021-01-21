package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CartListSvc {
	public ArrayList<CartInfo> getCartList(String mlId) {
		ArrayList<CartInfo> cartList = null;
		Connection conn = getConnection();
		OrdDao ordDao = OrdDao.getInstance();
		ordDao.setConnection(conn);
		cartList = ordDao.getCartList(mlId);
		close(conn);

		return cartList;
	}
}
