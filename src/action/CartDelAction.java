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
		String buyer = null;
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if (loginMember != null) {	
			buyer = loginMember.getMlid();
		}

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		CartDelSvc cartDelSvc = new CartDelSvc();
		int result = cartDelSvc.cartDelete(idx, buyer);
		out.println(result);
		out.close();

		return null;
	}
}
