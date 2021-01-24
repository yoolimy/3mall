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
	// 사용자의 요청이 get이든 post이든 모두 처리하는 메소드 / 서블릿,IO(input, output?)에 관한 예외를 던짐
	// 자유게시판에 대한 모든 요청을 처리함
		
		request.setCharacterEncoding("utf-8");
		String requestUri = request.getRequestURI();
		// URI를 받아옴(도메인과 쿼리스트링을 뺀 주소 문자열) : /mvcMall/brd_list.free
		String contextPath = request.getContextPath();
		// URI에서 파일명 부분을 제외한 문자열 : /mvcMall
		String command = requestUri.substring(contextPath.length());
		// requestUri에서 contextPath를 뺀 문자열 : /brd_list.free
		
		ActionForward forward = null;
		Action action = null;
		// 인터페이스 action형으로 인스턴스 선언(인터페이스이므로 생성은 불가)
		// 상위클래스로 선언 switch문에서 하위클래스로 받음 -> try, catch 문에서 excute() 한번에 실행하려고
//		System.out.println(requestUri);
//		System.out.println(contextPath);
//		System.out.println(command);
		
		// 사용자의 요청 종류에 따라 각각 다른 곳으로 action을 취함
		switch (command) {
			case "/join_proc.mem" :			// 회원가입 등록 처리기능
				action = new JoinProcAction();
				break;
			case "/mypage_view.mem" :		// 마이페이지 화면
				action = new MypageViewAction();
				break;
			case "/mypage_proc.mem" :		// 마이페이지 수정 기능
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
				/* RequestDispatcher를 통해 페이지를 이동시킴
				(dispatcher의 특징)
				RequestDispatcher를 통해 페이지를 이동시키면 이동한 페이지의 URL이 변하지 않고, 이동한 페이지로 현재 가지고 있는 
				request와 response객체를  그대로 넘겨줌 */
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

