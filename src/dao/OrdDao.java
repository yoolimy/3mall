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

	public ArrayList<CartInfo> getCartList(String mlId) {
	// 장바구니에서 보여줄 특정 사용자의 장바구니 목록을 리턴하는 메소드
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select c.cl_idx, p.pl_id, p.pl_name, p.pl_mainimg, " + 
				" c.cl_sdate, c.cl_edate, c.cl_rdate, p.pl_price from t_cart_list c, t_product_list p " + 
				" where c.pl_id = p.pl_id and p.pl_view = 'y' and c.ml_id = '" +  mlId +
				"' order by p.pl_id";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CartInfo cart = new CartInfo();
				cart.setCl_idx(rs.getInt("cl_idx"));
				cart.setPl_id(rs.getString("pl_id"));
				cart.setPl_name(rs.getString("pl_name"));
				cart.setPl_mainimg(rs.getString("pl_mainimg"));
				cart.setCl_rdate(rs.getString("cl_rdate"));
				cart.setCl_sdate(rs.getString("cl_sdate"));
				cart.setCl_edate(rs.getString("cl_edate"));
				cart.setPrice(rs.getString("pl_price"));

				cartList.add(cart);
			}
		} catch(Exception e) {
			System.out.println("getCartList() 오류");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return cartList;
	}

	public int cartInsert(CartInfo cart) {
	// 사용자가 선택한 상품을 장바구니에 담는 메소드
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String sql = "select c.cl_idx from t_cart_list c, t_product_list p " + 
				" where c.pl_id = p.pl_id and c.ml_id = '" + cart.getMl_id() + "' " + 
				" and c.pl_id = '" + cart.getPl_id() + "' ";
			
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	// 장바구니에 해당 상품이 있을 경우
				sql = "update t_cart_list set cl_rdate = '" + cart.getCl_rdate() + "', cl_sdate = '" +
						cart.getCl_sdate() + "', cl_edate = '" + cart.getCl_edate() + "' where pl_id = '"
						+ cart.getPl_id() +"' ";
				System.out.println(sql + "y");	
			} else {	// 처음 장바구니에 담는 상품일 경우
				sql = "insert into t_cart_list (ml_id, pl_id, cl_rdate, cl_sdate, cl_edate) " +
						" values ('" + cart.getMl_id() + "', '" + cart.getPl_id() + "', '" + 
						cart.getCl_rdate() + "', '" + cart.getCl_sdate() + "', '" + cart.getCl_edate() + "') ";
				
			}
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("cartInsert() 오류");		e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return result;
	}


	public int cartDelete(String idx, String mlId) {
	// 사용자가 선택한 상품(들)을 장바구니에서 삭제하는 메소드
		int result = 0;
		Statement stmt = null;

		try {
			String[] arrIdx = idx.split(",");
			String where = "";
			for (int i = 0 ; i < arrIdx.length ; i++) {
				where += " or cl_idx = " + arrIdx[i];
			}
			where = " and (" + where.substring(4) + ")";
			String sql = "delete from t_cart_list where ml_id = '" + mlId + 
				"' " + where;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("cartDelete() 오류");		e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}
	public ArrayList<CartInfo> getOrdFrmPdtList(String kind, String where) {
		// 장바구니나 바로구매를 통해 결제하려는 사용자에게 보여줄 상품목록을 리턴하는 메소드
			ArrayList<CartInfo> pdtList = new ArrayList<CartInfo>();
			Statement stmt = null;
			ResultSet rs = null;

			try {
				String sql = "";
				if (kind.equals("cart")) {	// 장바구니를 통한 구매일 경우
					sql = "select c.cl_idx, p.pl_name, p.pl_mainimg, c.cl_sdate, c.cl_rdate," + 
					" c.cl_edate, p.pl_price, p.pl_id from t_cart_list c, t_product_list p " + 
					" where c.pl_id = p.pl_id and p.pl_view = 'y' " + where + 
					" order by p.pl_id";
				
				} else {	// 바로 구매를 통한 구매일 경우
					
				}
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					CartInfo cart = new CartInfo();
					if (kind.equals("cart")) {
						cart.setCl_idx(rs.getInt("cl_idx"));
						cart.setCl_sdate(rs.getString("cl_sdate"));
						cart.setCl_edate(rs.getString("cl_edate"));
						cart.setCl_rdate(rs.getString("cl_rdate"));
					} else {
						
					}

					cart.setPl_id(rs.getString("pl_id"));
					cart.setPl_name(rs.getString("pl_name"));
					cart.setPl_mainimg(rs.getString("pl_mainimg"));
					cart.setPl_price(rs.getInt("pl_price"));
					pdtList.add(cart);
				}
			} catch(Exception e) {
				System.out.println("getOrdFrmPdtList() 오류");		e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}

			return pdtList;
		}
}
