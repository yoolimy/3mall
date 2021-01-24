package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartInAction implements Action {
// ��ٱ��Ͽ� ������ ��ǰ������ �����ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String plid = request.getParameter("id");		// ��ǰ���̵�
		String price = request.getParameter("price");	// �Ǳ��Ű�
		String sdate = request.getParameter("sdate");	// �뿩 ������
		String edate = request.getParameter("edate");	// �뿩 ������
		String rdate = request.getParameter("rentDate");	// �뿩��

		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		CartInfo cart = new CartInfo();
		cart.setPl_id(plid);
		cart.setPrice(price);
		cart.setCl_sdate(sdate);
		cart.setCl_edate(edate);
		cart.setCl_rdate(rdate);
		cart.setMl_id(loginMember.getMl_id());

		
		CartInSvc cartInSvc = new CartInSvc();
		int result = cartInSvc.cartInsert(cart);
		if (result == 0) {	// īƮ��Ͽ� ����������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��ٱ��Ͽ� ��Ⱑ �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		ActionForward forward = new ActionForward();
		forward.setPath("cart_list.ord" + request.getParameter("args"));	// �̵��� URL ����
		forward.setRedirect(true);
		return forward;
	}
}
