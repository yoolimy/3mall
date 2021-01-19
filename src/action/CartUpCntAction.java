package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartUpCntAction implements Action {
// ��ٱ��Ͽ� Ư�� ��ǰ�� ������ �����ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String idx = request.getParameter("idx");	// ��ٱ��� ���̵�
		String cnt = request.getParameter("cnt");	// ������ ��ǰ�� ����
		String buyer = null;
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if (loginMember != null) {	
			buyer = loginMember.getMlid();
		}

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		CartUpCntSvc cartUpCntSvc = new CartUpCntSvc();
		int result = cartUpCntSvc.cartCntUpdate(cnt, idx, buyer);
		out.println(result);
		out.close();

		return null;
	}
}
