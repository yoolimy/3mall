package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartInAction implements Action {
// 장바구니에 선택한 상품정보를 저장하는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String plid = request.getParameter("id");		// 상품아이디
		String price = request.getParameter("price");	// 실구매가
		String sdate = request.getParameter("sdate");	// 대여 시작일
		String edate = request.getParameter("edate");	// 대여 종료일
		String rdate = request.getParameter("rentDate");	// 대여일

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
		if (result == 0) {	// 카트등록에 실패했으면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('장바구니에 담기가 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		ActionForward forward = new ActionForward();
		forward.setPath("cart_list.ord" + request.getParameter("args"));	// 이동할 URL 지정
		forward.setRedirect(true);
		return forward;
	}
}
