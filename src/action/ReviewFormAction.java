package action;

import javax.servlet.http.*;	// 받아 온 request와 response를 위해 import
import java.io.PrintWriter;
import java.util.ArrayList;

import svc.*;
import vo.*;

public class ReviewFormAction implements Action{
// 자유 게시판 글 등록 및 수정 폼으로의 이동을 위한 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");	// 등록(in) / 수정(up) 여부
		ActionForward forward = new ActionForward();
		// 작업후 이동할 때 이동하는 방식(redirect or dispatch)을 정해서 저장하는 인스턴스
		
		
		
		if (wtype.equals("up")) {
		// 글 수정이면(등록의 경우 따로 진행할 작업이 없음)
			HttpSession session = request.getSession();
			MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
			// 현재 로그인한 회원의 정보를 MemberInfo형 인스턴스 loginMember에 담음
			String uid = null;
			if (loginMember != null)	uid = loginMember.getMl_id();
			// 현재 로그인한 회원의 아이디를 uid에 저장

			int idx = Integer.parseInt(request.getParameter("idx"));
			// 글번호로 정수로 형변환을 시킴

			
			ReviewFormSvc reviewFormSvc = new ReviewFormSvc();
			ReviewInfo article = reviewFormSvc.getArticleUp(idx, uid);
			if (article != null) 
			// 받아온 게시물이 있으면(수정할 권한이 있으면)
				request.setAttribute("article", article);
				// 수정할 게시글의 데이터를 request객체에 담음
				// dispatcher로 이동하므로 request를 다음 페이지로 제어권을 넘기므로 request에 게시물을 담아 감
			 
		}

		forward.setPath("/bbs/review_form.jsp");	// 이동할 URL 지정
		return forward;
	}
}
