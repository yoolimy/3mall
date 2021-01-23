package dao;

import static db.JdbcUtil.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
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

	public MemberInfo getLoginMember(HttpServletResponse response, String uid, String pwd) throws ServletException, IOException {
		MemberInfo loginMember = null;	// ������ �ν��Ͻ� ����
		Statement stmt = null;
		ResultSet rs = null;
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		try {
			stmt = conn.createStatement();
			String sql = "select * from t_member_list where ml_status = 'a' and ml_id = '" + uid + "'";
		      rs = stmt.executeQuery(sql);
		      if (rs.next()) {   // ���̵� �����ϸ�
		         if (pwd.equals(rs.getString("ml_pwd"))) {   // �α��ο� ����������
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
		         } else {   // ��ȣ�� Ʋ������
		            out.println("<script>");
		            out.println("alert('��й�ȣ�� Ʋ�Ƚ��ϴ�.');");
		            out.println("history.back();");
		            out.println("</script>");
		         }
		      } else {   // ���̵� Ʋ������
		            out.println("<script>");
		            out.println("alert('���̵� Ʋ�Ƚ��ϴ�.');");
		            out.println("history.back();");
		            out.println("</script>");
		      }

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
