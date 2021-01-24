package controller;

import java.io.IOException;
// �������� ����ڿ��� ������ �� ��� java.io.PrintWriter Ŭ������ import�ؾ� ��
// ���� : �ڹٽ�ũ��Ʈ�� html ���� ����ϴ� ��
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
// Session ��ü�� ����� ��� http��Ű���� �ִ� HttpSesisonŬ������ import�ؾ� ��

@WebServlet("/logout")
public class LogoutCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LogoutCtrl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		// ���ǿ� �ִ� ��� �Ӽ�(attribute)�� ����
		// �α���/�ƿ��� ������� ���� �Ӽ��� ���� ��� 
		// removeAttribute()�� Ư�� �Ӽ��� �����ϸ� ��
		response.sendRedirect("main.jsp");
	}
}
