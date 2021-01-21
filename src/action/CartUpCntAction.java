package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartUpCntAction implements Action {
// 장바구니에 특정 상품의 수량을 변경하는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String idx = request.getParameter("idx");	// 장바구니 아이디
		String cnt = request.getParameter("cnt");	// 변경할 상품의 수량
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
