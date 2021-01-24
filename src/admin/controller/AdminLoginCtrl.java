package admin.controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import admin.svc.*;
import vo.*;

@WebServlet("/admLogin")
public class AdminLoginCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminLoginCtrl() {
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String aid = request.getParameter("aid");
		String pwd = request.getParameter("pwd");

		AdminSvc adminSvc = new AdminSvc();

		AdminMemberInfo adminMember = adminSvc.getAdminMember(aid, pwd);
		// ��, �α��� ���н� null���� ����

		if (adminMember != null) {	// �α��� ������
			HttpSession session = request.getSession();
			// JSP�� �ٸ��� ���������� ������ ����ϱ� ���� ���� ���� �ν��Ͻ��� �����ؾ� ��
			session.setAttribute("adminMember", adminMember);
			// �α��� ���¸� �����ϱ� ���� ���� �Ӽ����� ����
			
			String url = (String)session.getAttribute("url");
			// �α��� �� Ư�� �������� �̵��ϱ� ���� �ּҸ� �޾ƿ�
			session.removeAttribute("url");		// �̵� �Ŀ��� url ���� �Ӽ��� ����
			if (url == null) 	url = "./admin/main.jsp";
			response.sendRedirect(url);
		} else {	// �α��� ���н�
			response.setContentType("text/html;charset=utf-8");
			// �����ϴ� �������� Ÿ���� text�� html�� ���� - �����ڵ�
			PrintWriter out = response.getWriter();
			// PrintWriter �� �ν��Ͻ� out�� ����(����ڿ��� �����ϴ� ��ü)

			out.println("<script>");
			out.println("alert('�α��ο� �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
		}
	}
}
