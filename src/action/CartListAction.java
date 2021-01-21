package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartListAction implements Action {
// ��ٱ��Ͽ��� ������ �ش� ������� ��ٱ��� ����� �������� Ŭ����
		public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			request.setCharacterEncoding("utf-8");
			ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();

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
			CartListSvc cartListSvc = new CartListSvc();
			cartList = cartListSvc.getCartList(mlId);
			request.setAttribute("cartList", cartList);

			ActionForward forward = new ActionForward();
			forward.setPath("/order/cart_list.jsp");	// �̵��� URL ����
			return forward;
		}
}
