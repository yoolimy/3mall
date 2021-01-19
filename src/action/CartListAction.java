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

			String where = "";
			HttpSession session = request.getSession();
			MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
			if (loginMember != null) {
				where = " and c.cl_buyer = '" + loginMember.getMlid() + "' ";
			}
			CartListSvc cartListSvc = new CartListSvc();
			cartList = cartListSvc.getCartList(where);
			request.setAttribute("cartList", cartList);

			ActionForward forward = new ActionForward();
			forward.setPath("/order/cart_list.jsp");	// �̵��� URL ����
			return forward;
		}
}
