package dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class OrdDao {
	private static OrdDao ordDao;
	private Connection conn;

	private OrdDao() {}
	
	public static OrdDao getInstance() {
		if (ordDao == null) {
			ordDao = new OrdDao();
		}
		return ordDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public ArrayList<CartInfo> getCartList(String where) {
	// ��ٱ��Ͽ��� ������ Ư�� �����(ȸ��, ��ȸ��)�� ��ٱ��� ����� �����ϴ� �޼ҵ�
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select c.cl_idx, p.pl_id, p.pl_name, p.pl_mainimg, c.cl_opt, " + 
				" c.cl_cnt, c.cl_sdate, c.cl_edate, p.pl_price from t_cart_list c, t_product_list p " + 
				" where c.pl_id = p.pl_id and p.pl_view = 'y' " + where + 
				" order by p.pl_id, c.cl_opt";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CartInfo cart = new CartInfo();
				cart.setCl_idx(rs.getInt("cl_idx"));
				cart.setPl_id(rs.getString("pl_id"));
				cart.setPl_name(rs.getString("pl_name"));
				cart.setPl_mainimg(rs.getString("pl_mainimg"));
				cart.setCl_opt(rs.getString("cl_opt"));
				cart.setCl_cnt(rs.getInt("cl_cnt"));
				cart.setCl_sdate(rs.getString("cl_sdate"));
				cart.setCl_edate(rs.getString("cl_edate"));
				int price = rs.getInt("pl_price");
				cart.setPrice(price);
				cartList.add(cart);
			}
		} catch(Exception e) {
			System.out.println("getCartList() ����");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return cartList;
	}

	public int cartInsert(CartInfo cart) {
	// ����ڰ� ������ ��ǰ�� ��ٱ��Ͽ� ��� �޼ҵ�
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String sql = "select c.cl_idx from t_cart_list c, t_product_list p " + 
				" where c.pl_id = p.pl_id and c.cl_buyer = '" + cart.getCl_buyer() + "' " + 
				" and c.pl_id = '" + cart.getPl_id() + "' and c.cl_opt = '" + cart.getCl_opt() + "' ";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				sql = "insert into t_cart_list (cl_buyer, pl_id, cl_opt, cl_cnt) " +
				" values ('" + cart.getCl_buyer() + "', '" + cart.getPl_id() + "', '" + 
				cart.getCl_opt() + "', '" + cart.getCl_cnt() + "')";
			}
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("cartInsert() ����");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return result;
	}

	public int cartOptUpdate(String opt, String cnt, String idx, String pid, String buyer) {
	// ����ڰ� ������ ��ǰ�� �ɼ��� �����ϴ� �޼ҵ�
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String sql = "select cl_idx from t_cart_list " + 
				" where cl_buyer = '" + buyer + "' and cl_opt = '" + opt + "' and pl_id = '" + pid + "' ";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	// ������ ��ǰ�� �� �����Ϸ��� �ɼǰ� ������ ��ǰ�� ������
				sql = "update t_cart_list set cl_cnt = cl_cnt + " + cnt + 
					" where cl_buyer = '" + buyer + "' and cl_idx = " + rs.getInt("cl_idx");

				cartDelete(idx, buyer);	// ������ ���ϻ�ǰ�� ��ٱ��Ͽ��� ����
			} else {	// ������ ��ǰ�� �� �����Ϸ��� �ɼǰ� ������ ��ǰ�� ������
				sql = "update t_cart_list set cl_opt = '" + opt + "' where cl_buyer = '" + 
					buyer + "' and cl_idx = " + idx;
			}
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("cartUpdate() ����");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return result;
	}

	public int cartCntUpdate(String cnt, String idx, String buyer) {
	// ����ڰ� ������ ��ǰ�� ������ �����ϴ� �޼ҵ�
		int result = 0;
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			String sql = "update t_cart_list set cl_cnt = '" + cnt + "' where cl_buyer = '" + 
				buyer + "' and cl_idx = " + idx;
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("cartCntUpdate() ����");		e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}

	public int cartDelete(String idx, String buyer) {
	// ����ڰ� ������ ��ǰ(��)�� ��ٱ��Ͽ��� �����ϴ� �޼ҵ�
		int result = 0;
		Statement stmt = null;

		try {
			String[] arrIdx = idx.split(",");
			String where = "";
			for (int i = 0 ; i < arrIdx.length ; i++) {
				where += " or cl_idx = " + arrIdx[i];
			}
			where = " and (" + where.substring(4) + ")";
			String sql = "delete from t_cart_list where cl_buyer = '" + buyer + 
				"' " + where;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("cartDelete() ����");		e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}
}
