package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class OrdSuccessAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String olid = request.getParameter("olid");	// ������ �ֹ� ��ȣ
		
		OrdSuccessSvc ordSuccessSvc = new OrdSuccessSvc();
		OrderInfo ord = ordSuccessSvc.getOrderInfo(olid);
		
		if (ord == null) {	// �ֹ������� ���� ���
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=utf-8");
			out.println("<script>");
			out.println("alert('�߸��� ��η� �����̽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		request.setAttribute("ord", ord);

		ActionForward forward = new ActionForward();
		forward.setPath("/order/order_success.jsp");	// �̵��� URL ����
		return forward;
	}
}