package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class OrdSuccessAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String olid = request.getParameter("olid");	// 보여줄 주문 번호
		
		OrdSuccessSvc ordSuccessSvc = new OrdSuccessSvc();
		OrderInfo ord = ordSuccessSvc.getOrderInfo(olid);
		
		if (ord == null) {	// 주문정보가 없을 경우
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=utf-8");
			out.println("<script>");
			out.println("alert('잘못된 경로로 들어오셨습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		request.setAttribute("ord", ord);

		ActionForward forward = new ActionForward();
		forward.setPath("/order/order_success.jsp");	// 이동할 URL 지정
		return forward;
	}
}