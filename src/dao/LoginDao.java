package dao;

import static db.JdbcUtil.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import vo.*;

public class LoginDao {
// 로그인 관련 쿼리를 만들어 실행시키는 클래스
	private static LoginDao loginDao;
	private Connection conn;
	// 클래스 전체에서 공유하기 위해 멤버변수로 선언

	private LoginDao() {}
	// 외부에서 직접적으로 인스턴스 생성 하는 것을 막기 위해 private으로 선언

	public static LoginDao getInstance() {
	// 인스턴스를 생성해주는 메소드로 하나의 인스턴스만 생성시킴(Singleton 방식)
	// DB작업을 많이 하는 클래스 특성상 여러 개의 인스턴스가 생성되면 그만큼 많은 수의 
	// DB연결(Connetion)이 생기므로 전체적으로 속도 저하의 우려가 있어 싱글톤 방식을 사용
		if (loginDao == null) {
		// 멤버로 선언된 LoginDao 형 인스턴스 loginDao가 null이면
		// 즉, 인스턴스가 생성되지 않았으면
			loginDao = new LoginDao();
			// LoginDao 형 인스턴스 loginDao 생성
			// 같은 클래스이므로 private으로 선언된 생성자를 호출할 수 있음
		}
		return loginDao;
	}
	public void setConnection(Connection conn) {
	// LoginSvc 클래스에서 보낸 Connection객체를 받아 멤버에 넣는 메소드
	// 외부에서 Connection객체를 받는 이유는 DB작업이 여러 번일 경우 
	// Connection객체를 여러 번 생성하는 것을 막기 위해 멤버로 만들어 사용
		this.conn = conn;
	}

	public MemberInfo getLoginMember(HttpServletResponse response, String uid, String pwd) throws ServletException, IOException {
		MemberInfo loginMember = null;	// 리턴할 인스턴스 선언
		Statement stmt = null;
		ResultSet rs = null;
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		try {
			stmt = conn.createStatement();
			String sql = "select * from t_member_list where ml_status = 'a' and ml_id = '" + uid + "'";
		      rs = stmt.executeQuery(sql);
		      if (rs.next()) {   // 아이디가 존재하면
		         if (pwd.equals(rs.getString("ml_pwd"))) {   // 로그인에 성공했으면
		        	 loginMember = new MemberInfo();
					// 로그인한 회원정보를 저장할 loginMember인스턴스 생성
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
					// MemberInfo클래스의 인스턴스 loginMember에 회원정보를 저장
		         } else {   // 암호가 틀렸으면
		            out.println("<script>");
		            out.println("alert('비밀번호가 틀렸습니다.');");
		            out.println("history.back();");
		            out.println("</script>");
		         }
		      } else {   // 아이디가 틀렸으면
		            out.println("<script>");
		            out.println("alert('아이디가 틀렸습니다.');");
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
