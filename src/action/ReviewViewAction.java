package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class ReviewViewAction implements Action {
// �ϳ��� �Խñ��� �� �� ��������ִ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));	// �۹�ȣ
		int cpage = Integer.parseInt(request.getParameter("cpage"));// ���� ��������ȣ
		// �۹�ȣ�� ��������ȣ�� �ʼ��̹Ƿ� �����鼭 �ٷ� ���������� ��ȯ��Ŵ

		String schtype = request.getParameter("schtype");	// �˻�����
		String keyword = request.getParameter("keyword");	// �˻���
		
		ReviewViewSvc reviewViewSvc = new ReviewViewSvc();
		ReviewInfo article = reviewViewSvc.getArticle(idx);
		// ������ �Խñ��� ReviewInfo�� �ν��Ͻ��� �޾ƿ�
		
		ActionForward forward = new ActionForward();
		request.setAttribute("article", article);
		// �̵��� �������� request�� �Խù� �����͸� ��� �Ѱ���(dispatch����̹Ƿ� request�� �ѱ�� ����)
		forward.setPath("/bbs/review_view.jsp");
		// �̵��� ������ ����
		return forward;
	}
}
