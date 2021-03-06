package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartDelAction implements Action {
// 장바구니에 선택한 상품을 삭제하는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String idx = request.getParameter("idx");	// 장바구니 인덱스(들)
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
