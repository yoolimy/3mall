package dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class PdtDao {
	private static PdtDao pdtDao;
	private Connection conn;

	private PdtDao() {}
	
	public static PdtDao getInstance() {
		if (pdtDao == null) {
			pdtDao = new PdtDao();
		}
		return pdtDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<CataBigInfo> getCataBigList() {
	// DB에서 대분류 목록을 받아 리턴하는 메소드
		ArrayList<CataBigInfo> cataBigList = new ArrayList<CataBigInfo>();
		CataBigInfo bigInfo = null;
		Statement stmt = null;
		ResultSet rs = null;		
		try {
			String sql = "select * from t_cata_big";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				bigInfo = new CataBigInfo();
				// cataBigList에 담을 CataBigInfo형 인스턴스 생성

				bigInfo.setCb_idx(rs.getInt("cb_idx"));
				bigInfo.setCb_name(rs.getString("cb_name"));
				cataBigList.add(bigInfo);
				// 리턴할 ArrayList에 생성한 CataBigInfo형 인스턴스 저장
			}
		} catch(Exception e) {
			System.out.println("getCataBigList() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return cataBigList;
	}

	public ArrayList<CataSmallInfo> getCataSmallList() {
	// DB에서 소분류 목록을 받아 리턴하는 메소드
		ArrayList<CataSmallInfo> cataSmallList = new ArrayList<CataSmallInfo>();
		CataSmallInfo smallInfo = null;
		Statement stmt = null;
		ResultSet rs = null;		
		try {
			String sql = "select * from t_cata_small order by cb_idx, cs_idx";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				smallInfo = new CataSmallInfo();
				// cataBigList에 담을 CataBigInfo형 인스턴스 생성

				smallInfo.setCs_idx(rs.getInt("cs_idx"));
				smallInfo.setCb_idx(rs.getInt("cb_idx"));
				smallInfo.setCs_name(rs.getString("cs_name"));

				cataSmallList.add(smallInfo);
				// 리턴할 ArrayList에 생성한 CataSmallInfo형 인스턴스 저장
			}
		} catch(Exception e) {
			System.out.println("getCataSmallList() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return cataSmallList;
	}


	public int getPdtCount(String where) {
	// 조건을 받아와 조건에 맞는 상품들의 총 개수를 리턴하는 메소드
		int rcnt = 0;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			sql = "select count(*) from t_product_list a, t_cata_big b, t_cata_small c " + 
				" where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	rcnt = rs.getInt(1);
		} catch(Exception e) {
			System.out.println("getPdtCount() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return rcnt;
	}

	public ArrayList<PdtInfo> getPdtList(String where, String orderby, int cpage, int psize) {
	// 검색조건과 정렬조건을 받아와 조건에 맞는 상품들을 정렬하여 그 목록을 ArrayList<PdtInfo>형으로 리턴하는 메소드
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		// 상품 목록을 저장할 ArrayList객체로 PdtInfo형 인스턴스만 저장함
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		PdtInfo pdtInfo = null;		// 하나의 상품정보를 저장한 후 pdtList에 저장될 인스턴스
		int snum = (cpage - 1) * psize;		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호

		try {
			sql = "select a.*, b.cb_name, c.cs_name from t_product_list a, t_cata_big b, " + 
				" t_cata_small c where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx " + 
				where + orderby + " limit " + snum + ", " + psize;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pdtInfo = new PdtInfo();
				pdtInfo.setPl_id(rs.getString("pl_id"));
				pdtInfo.setCs_idx(rs.getInt("cs_idx"));
				pdtInfo.setPl_name(rs.getString("pl_name"));
				pdtInfo.setPl_price(rs.getInt("pl_price"));
				pdtInfo.setPl_mainimg(rs.getString("pl_mainimg"));
				pdtInfo.setPl_img1(rs.getString("pl_img1"));
				pdtInfo.setPl_img2(rs.getString("pl_img2"));
				pdtInfo.setPl_rent(rs.getInt("pl_rent"));
				pdtInfo.setPl_salecnt(rs.getInt("pl_salecnt"));
				pdtInfo.setPl_review(rs.getInt("pl_review"));
				pdtInfo.setPl_view(rs.getString("pl_view"));
				pdtInfo.setPl_date(rs.getString("pl_date"));
				pdtInfo.setAl_idx(rs.getInt("al_idx"));
				pdtInfo.setPl_detail(rs.getString("pl_detail"));
				pdtInfo.setPl_deInfo(rs.getString("pl_deInfo"));
				pdtInfo.setCb_name(rs.getString("cb_name"));
				pdtInfo.setCs_name(rs.getString("cs_name"));
				
				pdtList.add(pdtInfo);
			}
		} catch(Exception e) {
			System.out.println("getPdtList() 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return pdtList;
	}
	public PdtInfo getPdtInfo(String id) {
		// 지정된 id에 해당하는 하나의 상품정보를 PdtInfo형 인스턴스로 리턴하는 메소드
			PdtInfo pdtInfo = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = conn.createStatement();
				String sql = "select a.*, b.cb_name, c.cs_name " + 
						" from t_product_list a, t_cata_big b, t_cata_small c " + 
						" where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx and " + 
						" a.pl_view = 'y' and a.pl_id = '" + id + "' ";
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					pdtInfo = new PdtInfo();
					pdtInfo.setPl_id(rs.getString("pl_id"));
					pdtInfo.setCs_idx(rs.getInt("cs_idx"));
					pdtInfo.setPl_name(rs.getString("pl_name"));
					pdtInfo.setPl_price(rs.getInt("pl_price"));
					pdtInfo.setPl_mainimg(rs.getString("pl_mainimg"));
					pdtInfo.setPl_img1(rs.getString("pl_img1"));
					pdtInfo.setPl_img2(rs.getString("pl_img2"));
					pdtInfo.setPl_rent(rs.getInt("pl_rent"));
					pdtInfo.setPl_srent(rs.getString("pl_srent"));
					pdtInfo.setPl_erent(rs.getString("pl_erent"));
					pdtInfo.setPl_salecnt(rs.getInt("pl_salecnt"));
					pdtInfo.setPl_review(rs.getInt("pl_review"));
					pdtInfo.setPl_view(rs.getString("pl_view"));
					pdtInfo.setPl_date(rs.getString("pl_date"));
					pdtInfo.setAl_idx(rs.getInt("al_idx"));
					pdtInfo.setPl_detail(rs.getString("pl_detail"));
					pdtInfo.setPl_deInfo(rs.getString("pl_deInfo"));
					pdtInfo.setCb_name(rs.getString("cb_name"));
					pdtInfo.setCs_name(rs.getString("cs_name"));
				}
			} catch(Exception e) {
				System.out.println("getPdtInfo() 오류");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}

			return pdtInfo;
		}
}
