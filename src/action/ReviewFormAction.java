package action;

import javax.servlet.http.*;	// �޾� �� request�� response�� ���� import
import java.io.PrintWriter;
import java.util.ArrayList;

import svc.*;
import vo.*;

public class ReviewFormAction implements Action{
// ���� �Խ��� �� ��� �� ���� �������� �̵��� ���� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");	// ���(in) / ����(up) ����
		ActionForward forward = new ActionForward();
		// �۾��� �̵��� �� �̵��ϴ� ���(redirect or dispatch)�� ���ؼ� �����ϴ� �ν��Ͻ�
		
		
		
		if (wtype.equals("up")) {
		// �� �����̸�(����� ��� ���� ������ �۾��� ����)
			HttpSession session = request.getSession();
			MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
			// ���� �α����� ȸ���� ������ MemberInfo�� �ν��Ͻ� loginMember�� ����
			String uid = null;
			if (loginMember != null)	uid = loginMember.getMl_id();
			// ���� �α����� ȸ���� ���̵� uid�� ����

			int idx = Integer.parseInt(request.getParameter("idx"));
			// �۹�ȣ�� ������ ����ȯ�� ��Ŵ

			
			ReviewFormSvc reviewFormSvc = new ReviewFormSvc();
			ReviewInfo article = reviewFormSvc.getArticleUp(idx, uid);
			if (article != null) 
			// �޾ƿ� �Խù��� ������(������ ������ ������)
				request.setAttribute("article", article);
				// ������ �Խñ��� �����͸� request��ü�� ����
				// dispatcher�� �̵��ϹǷ� request�� ���� �������� ������� �ѱ�Ƿ� request�� �Խù��� ��� ��
			 
		}

		forward.setPath("/bbs/review_form.jsp");	// �̵��� URL ����
		return forward;
	}
}
