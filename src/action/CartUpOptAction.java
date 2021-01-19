package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartUpOptAction implements Action {
// ��ٱ��Ͽ� Ư�� ��ǰ�� �ɼ��� �����ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String idx = request.getParameter("idx");	// ��ٱ��� ���̵�
		String pid = request.getParameter("pid");	// ��ǰ ���̵�
		String cnt = request.getParameter("cnt");	// �ɼ� �����ϴ� ��ǰ�� ����(������ �ɼ��� ��� �߰��ϱ� ����)
		String opt = request.getParameter("opt");	// ������ �ɼ�(��) - ���� ���� ����(�� ���ڿ� ��)
		String buyer = null;
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if (loginMember != null) {
			buyer = loginMember.getMlid();
		}

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		CartUpOptSvc cartUpOptSvc = new CartUpOptSvc();
		int result = cartUpOptSvc.cartOptUpdate(opt, cnt, idx, pid, buyer);
		out.println(result);
		out.close();

		return null;
	}
}
