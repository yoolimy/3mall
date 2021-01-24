package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class OrdFormAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String kind = request.getParameter("kind");
		// ��ٱ��� �Ǵ� �ٷ� ���Ÿ� �����ϴ� ������(cart : ��ٱ��ϸ� ���� ����, direct : �ٷ� ����)
		String idxs = request.getParameter("idxs");
		// ��ٱ��Ϸ� ���Ž� ������ ��ǰ(��)�� īƮ�ε���(cl_idx) ��ȣ(��)�� ��ǥ�� ���е�
		ArrayList<CartInfo> pdtList = new ArrayList<CartInfo>();
		// �����Ϸ��� ��ǰ(��)�� ���� ArrayList

		String where = "";
		HttpSession session = request.getSession();
		
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		if (loginMember == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�α��� �� ����� �� �ֽ��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		if (kind.equals("cart")) {	// ��ٱ��ϸ� ���� ������ ���
			String[] arrIdxs = idxs.split(",");
			for (int i = 0 ; i < arrIdxs.length ; i++) {
				where += " or c.cl_idx = " + arrIdxs[i];
			}
			where = " and (" + where.substring(4) + ")  and c.ml_id = '"
			+ loginMember.getMl_id() + "' ";

		} else {	// �ٷ� ������ ���
			
		}
		
		MypageViewSvc mypageViewSvc = new MypageViewSvc();
		MemberAddrInfo memberAddrInfoFirst = mypageViewSvc.getMemberAddrInfo(loginMember.getMl_id(), "y");
		MemberAddrInfo memberAddrInfoSecond = mypageViewSvc.getMemberAddrInfo(loginMember.getMl_id(), "n");		

		
		OrdFormSvc ordFormSvc = new OrdFormSvc();
		pdtList = ordFormSvc.getOrdFrmPdtList(kind, where);
		request.setAttribute("pdtList", pdtList);
		request.setAttribute("memberAddrInfoFirst", memberAddrInfoFirst);
		request.setAttribute("memberAddrInfoSecond", memberAddrInfoSecond);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/order/order_form.jsp");	// �̵��� URL ����
		return forward;
	}
}
