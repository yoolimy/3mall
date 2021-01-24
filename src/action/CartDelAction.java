package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartDelAction implements Action {
// ��ٱ��Ͽ� ������ ��ǰ�� �����ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String idx = request.getParameter("idx");	// ��ٱ��� �ε���(��)
		String mlId = "";
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if (loginMember != null) {
			mlId = loginMember.getMl_id();
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�α��� �� ����� �� �ֽ��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}


		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		CartDelSvc cartDelSvc = new CartDelSvc();
		int result = cartDelSvc.cartDelete(idx, mlId);
		out.println(result);
		out.close();

		return null;
	}
}
