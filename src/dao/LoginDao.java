package dao;

import static db.JdbcUtil.*;
import java.sql.*;
import vo.*;

public class LoginDao {
// �α��� ���� ������ ����� �����Ű�� Ŭ����
	private static LoginDao loginDao;
	private Connection conn;
	// Ŭ���� ��ü���� �����ϱ� ���� ��������� ����

	private LoginDao() {}
	// �ܺο��� ���������� �ν��Ͻ� ���� �ϴ� ���� ���� ���� private���� ����

	public static LoginDao getInstance() {
	// �ν��Ͻ��� �������ִ� �޼ҵ�� �ϳ��� �ν��Ͻ��� ������Ŵ(Singleton ���)
	// DB�۾��� ���� �ϴ� Ŭ���� Ư���� ���� ���� �ν��Ͻ��� �����Ǹ� �׸�ŭ ���� ���� 
	// DB����(Connetion)�� ����Ƿ� ��ü������ �ӵ� ������ ����� �־� �̱��� ����� ���
		if (loginDao == null) {
		// ����� ����� LoginDao �� �ν��Ͻ� loginDao�� null�̸�
		// ��, �ν��Ͻ��� �������� �ʾ�����
			loginDao = new LoginDao();
			// LoginDao �� �ν��Ͻ� loginDao ����
			// ���� Ŭ�����̹Ƿ� private���� ����� �����ڸ� ȣ���� �� ����
		}
		return loginDao;
	}
	public void setConnection(Connection conn) {
	// LoginSvc Ŭ�������� ���� Connection��ü�� �޾� ����� �ִ� �޼ҵ�
	// �ܺο��� Connection��ü�� �޴� ������ DB�۾��� ���� ���� ��� 
	// Connection��ü�� ���� �� �����ϴ� ���� ���� ���� ����� ����� ���
		this.conn = conn;
	}
	public MemberInfo getLoginMember(String uid, String pwd) {
		MemberInfo loginMember = null;	// ������ �ν��Ͻ� ����
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			// ������ ����� Statement����
			String sql = "select * from t_member_list " + 
				" where ml_status = 'a' and ml_id = '" + uid + 
				"' and ml_pwd = '" + pwd + "'";
			rs = stmt.executeQuery(sql);
			// ���� ���� ����� ResultSet�� ����
			if (rs.next()) {	// �α��� ������
				loginMember = new MemberInfo();
				// �α����� ȸ�������� ������ loginMember�ν��Ͻ� ����

				loginMember.setMl_id(uid);
				loginMember.setMl_pwd(pwd);
				loginMember.setMl_name(rs.getString("ml_name"));
				loginMember.setMl_gender(rs.getString("ml_gender"));
				loginMember.setMl_birth(rs.getString("ml_birth"));
				loginMember.setMl_phone(rs.getString("ml_phone"));
				loginMember.setMl_email(rs.getString("ml_email"));
				loginMember.setMl_date(rs.getString("ml_date"));
				loginMember.setMl_last(rs.getString("ml_last"));
				loginMember.setMl_status("a");
				// MemberInfoŬ������ �ν��Ͻ� loginMember�� ȸ�������� ����
			}
			// �α��� ���нÿ��� loginMember�� ���� �����͸� ���� �ʰ� null�� ����
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close(rs);		close(stmt);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		return loginMember;
	}
}
