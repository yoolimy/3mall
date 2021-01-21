package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartListAction implements Action {
// 장바구니에서 보여줄 해당 사용자의 장바구니 목록을 가져오는 클래스
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
				out.println("alert('로그인 후 사용할 수 있습니다.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			CartListSvc cartListSvc = new CartListSvc();
			cartList = cartListSvc.getCartList(mlId);
			request.setAttribute("cartList", cartList);

			ActionForward forward = new ActionForward();
			forward.setPath("/order/cart_list.jsp");	// 이동할 URL 지정
			return forward;
		}
}
