package controller;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.mem")
public class MemberJoinCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberJoinCtrl() {
        super();
    }

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// ������� ��û�� get�̵� post�̵� ��� ó���ϴ� �޼ҵ� / ����,IO(input, output?)�� ���� ���ܸ� ����
	// �����Խ��ǿ� ���� ��� ��û�� ó����
		
		request.setCharacterEncoding("utf-8");
		String requestUri = request.getRequestURI();
		// URI�� �޾ƿ�(�����ΰ� ������Ʈ���� �� �ּ� ���ڿ�) : /mvcMall/brd_list.free
		String contextPath = request.getContextPath();
		// URI���� ���ϸ� �κ��� ������ ���ڿ� : /mvcMall
		String command = requestUri.substring(contextPath.length());
		// requestUri���� contextPath�� �� ���ڿ� : /brd_list.free
		
		ActionForward forward = null;
		Action action = null;
		// �������̽� action������ �ν��Ͻ� ����(�������̽��̹Ƿ� ������ �Ұ�)
		// ����Ŭ������ ���� switch������ ����Ŭ������ ���� -> try, catch ������ excute() �ѹ��� �����Ϸ���
//		System.out.println(requestUri);
//		System.out.println(contextPath);
//		System.out.println(command);
		
		// ������� ��û ������ ���� ���� �ٸ� ������ action�� ����
		switch (command) {
			case "/join_proc.mem" :			// ȸ������ ��� ó�����
				action = new JoinProcAction();
				break;
			case "/mypage_view.mem" :		// ���������� ȭ��
				action = new MypageViewAction();
				break;
			case "/mypage_proc.mem" :		// ���������� ���� ���
				action = new MypageProcAction();
				break;
		}
		
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(forward != null) {
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = 
						request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
				/* RequestDispatcher�� ���� �������� �̵���Ŵ
				(dispatcher�� Ư¡)
				RequestDispatcher�� ���� �������� �̵���Ű�� �̵��� �������� URL�� ������ �ʰ�, �̵��� �������� ���� ������ �ִ� 
				request�� response��ü��  �״�� �Ѱ��� */
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

