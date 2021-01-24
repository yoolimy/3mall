package controller;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.ord")
public class OrdCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public OrdCtrl() {
        super();
    }

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// ������� ��û�� get�̵� post�̵� ��� ó���ϴ� �޼ҵ�
		request.setCharacterEncoding("utf-8");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());

		ActionForward forward = null;
		Action action = null;

		switch (command) {
			case "/cart_in.ord" :			// ��ٱ��� ��� ���
				action = new CartInAction();		break;
			case "/cart_list.ord" :			// ��ٱ��� ȭ��
				action = new CartListAction();		break;
			case "/cart_del.ord" :			// ��ٱ��� ���� ���
				action = new CartDelAction();		break;
			case "/order_form.ord" :		// ����â���� �̵���Ŵ
				action = new OrdFormAction();		break;
			case "/order_proc.ord" :		// ����ó�� ���
				action = new OrdProcAction();		break;
//			case "/order_success.ord" :		// ���ſϷ� ȭ��
//				action = new OrdSuccessAction();	break;
		}

		try {
			forward = action.execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = 
					request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
