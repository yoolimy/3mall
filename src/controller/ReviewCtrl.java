package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.review")
public class ReviewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReviewCtrl() {
        super();
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());

		ActionForward forward = null;
		Action action = null;

		switch (command) {
			case "/review_list.review" :	// 리뷰게시판 목록 보기
				action = new ReviewListAction();	break;
		//	case "/brd_form.free" :		// 게시글 등록/수정 폼
		//		action = new FreeFormAction();	break;
		//	case "/brd_view.free" :		// 게시글 보기
		//		action = new FreeViewAction();	break;
		//	case "/brd_pwd.free" :		// 비밀번호 입력 폼
		//		action = new FreePwdAction();	break;
		//	case "/brd_proc.free" :		// 게시글 처리(등록, 수정, 삭제)
		//		action = new FreeProcAction();	break;
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
