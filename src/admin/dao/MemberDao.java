package admin.dao;

import static db.JdbcUtil.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import vo.*;

public class MemberDao {
	private static MemberDao memberDao;
	private Connection conn;

	private MemberDao() {}
	
	public static MemberDao getInstance() {
		if (memberDao == null) {
			memberDao = new MemberDao();
		}
		return memberDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public int getMemberCount(String where) {
	// ������ �޾ƿ� ���ǿ� �´� ȸ������ ������ �����ϴ� �޼ҵ�
			int rcnt = 0;
			Statement stmt = null;
			ResultSet rs = null;
			String sql = null;
			try {
				sql = "select count(*) from t_member_list " + where;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					rcnt = rs.getInt(1);
				}
				
			} catch(Exception e) {
				System.out.println("getMemberCount() ����");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}

			return rcnt;
	}
		
	public ArrayList<MemberInfo> getMemberList(String where, String orderby, int cpage, int psize) {
	// �˻����ǰ� ���������� �޾ƿ� ���ǿ� �´� ��ǰ���� �����Ͽ� �� ����� ArrayList<MemberInfo>������ �����ϴ� �޼ҵ�
		ArrayList<MemberInfo> memList = new ArrayList<MemberInfo>();
		// ��ǰ ����� ������ ArrayList��ü�� MemberInfo�� �ν��Ͻ��� ������
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		MemberInfo memInfo = null; 			// �ϳ��� ȸ�������� ������ �� memList�� ����� �ν��Ͻ�
		int snum = (cpage - 1) * psize;		// ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ
		
		try {
			sql = "select * from t_member_list " + where + orderby + " limit " + snum + ", " + psize;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				memInfo = new MemberInfo();
				memInfo.setMl_id(rs.getString("ml_id"));
				memInfo.setMl_pwd(rs.getString("ml_pwd"));
				memInfo.setMl_name(rs.getString("ml_name"));
				memInfo.setMl_gender(rs.getString("ml_gender"));
				memInfo.setMl_birth(rs.getString("ml_birth"));
				memInfo.setMl_phone(rs.getString("ml_phone"));
				memInfo.setMl_email(rs.getString("ml_email"));
				memInfo.setMl_agremail(rs.getString("ml_agremail"));
				memInfo.setMl_date(rs.getString("ml_date"));
				memInfo.setMl_last(rs.getString("ml_last"));
				memInfo.setMl_status(rs.getString("ml_status"));
				memInfo.setMl_memo(rs.getString("ml_memo"));
				
				memList.add(memInfo);
			}
			
		} catch(Exception e) {
			System.out.println("getMemberList() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return memList;
	}	
	public MemberInfo getMemberInfo(String id) {
	// ������ id�� �ش��ϴ� �ϳ��� ȸ�� ������ MemberInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		MemberInfo memberInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			stmt = conn.createStatement();
			sql = "select * from t_member_list where ml_id = '" + id + "' ";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				memberInfo = new MemberInfo();
				memberInfo.setMl_id(rs.getString("ml_id"));
				memberInfo.setMl_pwd(rs.getString("ml_pwd"));
				memberInfo.setMl_name(rs.getString("ml_name"));
				memberInfo.setMl_gender(rs.getString("ml_gender"));
				memberInfo.setMl_birth(rs.getString("ml_birth"));
				memberInfo.setMl_phone(rs.getString("ml_phone"));
				memberInfo.setMl_email(rs.getString("ml_email"));
				memberInfo.setMl_agremail(rs.getString("ml_agremail"));
				memberInfo.setMl_date(rs.getString("ml_date"));
				memberInfo.setMl_last(rs.getString("ml_last"));
				memberInfo.setMl_status(rs.getString("ml_status"));
				memberInfo.setMl_memo(rs.getString("ml_memo"));
			}
			
		} catch(Exception e) {
			System.out.println("getMemberInfo() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return memberInfo;
	}
	public MemberAddrInfo getMemberAddrInfo(String id, String basicYN) {
	// ������ id�� �ش��ϴ� �ϳ��� ȸ�� �ּҸ� MemberAddrInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		MemberAddrInfo memberAddrInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			stmt = conn.createStatement();
			sql = " select ML.*, Ma.ma_zip, Ma.ma_addr1, Ma.ma_addr2, Ma.ma_basic " + 
					"from t_member_list ML, t_member_addr MA  where ML.ml_id = MA.ml_id and ML.ml_id = '" + id
					+ "'  and MA.ma_basic = '" + basicYN.toLowerCase() + "'";
//			if (memberAddrInfo.getMa_basic().equals("y")) {
//				where += " and MA.ma_basic = 'y' ";
//			} 
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				memberAddrInfo = new MemberAddrInfo();
				memberAddrInfo.setMl_id(rs.getString("ml_id"));
				memberAddrInfo.setMa_zip(rs.getString("ma_zip"));
				memberAddrInfo.setMa_addr1(rs.getString("ma_addr1"));
				memberAddrInfo.setMa_addr2(rs.getString("ma_addr2"));
				memberAddrInfo.setMa_basic(rs.getString("ma_basic"));
			}
			
		} catch(Exception e) {
			System.out.println("getMemberAddrInfo() ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return memberAddrInfo;
	}
	public int MemberUpdate(MemberInfo memUpInfo) {
	// ȸ�� ���� ó���� ���� �޼ҵ�
		int result = 0;
		Statement stmt = null;
		String sql = null;
		
		try {
			sql = "update t_member_list set ml_status = '" + memUpInfo.getMl_status() + "', ml_memo = '" + memUpInfo.getMl_memo() + 
					"' where ml_id = '" + memUpInfo.getMl_id() + "'";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("MemberUpdate() ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return result;
	}
}
