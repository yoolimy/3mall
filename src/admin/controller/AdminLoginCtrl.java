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
		// 단, 로그인 실패시 null값이 들어옴

		if (adminMember != null) {	// 로그인 성공시
			HttpSession session = request.getSession();
			// JSP와 다르게 서블릿에서는 세션을 사용하기 위해 직접 세션 인스턴스를 생성해야 함
			session.setAttribute("adminMember", adminMember);
			// 로그인 상태를 유지하기 위해 세션 속성으로 저장
			
			String url = (String)session.getAttribute("url");
			// 로그인 후 특정 페이지로 이동하기 위한 주소를 받아옴
			session.removeAttribute("url");		// 이동 후에는 url 세션 속성을 삭제
			if (url == null) 	url = "./admin/main.jsp";
			response.sendRedirect(url);
		} else {	// 로그인 실패시
			response.setContentType("text/html;charset=utf-8");
			// 응답하는 페이지의 타입을 text나 html로 지정 - 유니코드
			PrintWriter out = response.getWriter();
			// PrintWriter 형 인스턴스 out을 생성(사용자에게 응답하는 객체)

			out.println("<script>");
			out.println("alert('로그인에 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
	}
}
