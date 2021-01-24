package action;

import java.io.PrintWriter;	// ��� ���н� �ڹٽ�ũ��Ʈ ����� ���� import
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import svc.*;
import vo.*;

public class ReviewProcAction implements Action {
// �Խñ� ��� ó�� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		ReviewInfo reviewInfo = new ReviewInfo();
		// ����ڰ� �Է��� ������(�Խñ�)���� ������ �ν��Ͻ�

		if (wtype.equals("in") || wtype.equals("up")) {
		// �۵���̳� ������ ��� �Խñ� �����͵��� �޾ƿ�
			reviewInfo.setMl_id(request.getParameter("id"));
			reviewInfo.setRl_title(request.getParameter("title"));
			reviewInfo.setRl_content(request.getParameter("content"));
			// �޾� �� �����͵��� �ϳ��� �Խù��� �����ϴ� reviewInfo �ν��Ͻ��� setter�� �̿��Ͽ� ����
		}

		if (wtype.equals("del") || wtype.equals("up")) {
		// �ۻ����� ������ ��� �Խñ� ��ȣ�� �޾ƿ�
			int idx = Integer.parseInt(request.getParameter("idx"));
			reviewInfo.setRl_idx(idx);
		}

		ReviewProcSvc reviewProcSvc = new ReviewProcSvc();
		// ����Ͻ� ������ ó���� ���� Ŭ������ �ν��Ͻ� ����
		boolean isSuccess = false;	// ����(���, ����, ����) �������θ� ������ ����
		String link = null;			// �۾�(���, ����, ����) �� �̵��� URL�� ������ ����
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		// ��Ͻ� �ۼ���, ����/���� �� ���� üũ������ �α��� ������ ����

		if (wtype.equals("in")) {
			if (loginMember != null) {	// ȸ�� �۵���� ���
				reviewInfo.setMl_id(loginMember.getMl_id());	// �α����� ���̵� �ۼ��ڷ� ����
			}
			reviewInfo.setRl_ip(request.getRemoteAddr());	// ����� IP�ּ� ����
			isSuccess = reviewProcSvc.reviewInsert(reviewInfo);
			link = "review_list.review";

		} else if (wtype.equals("up")) {
			isSuccess = reviewProcSvc.reviewUpdate(reviewInfo);
			int cpage = Integer.parseInt(request.getParameter("cpage"));
			String args = "?cpage=" + cpage;
			String schtype = request.getParameter("schtype");
			String keyword = request.getParameter("keyword");
			if (keyword != null && !keyword.equals("")) {
				args += "&schtype=" + schtype + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}
			link = "review_view.review" + args + "&idx=" + reviewInfo.getRl_idx();

		} else {
			if (loginMember != null) {
			// �α��� ���� ȸ���̸鼭 ȸ���� �Է��� ���� ���
				reviewInfo.setMl_id(loginMember.getMl_id());
				// reviewInfo �ν��Ͻ��� writer ���� ���� �α����� ȸ���� ���̵�� ����
			}
			isSuccess = reviewProcSvc.reviewDelete(reviewInfo);
			if (!isSuccess) {	// ���� ���н�
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
			link = "review_list.review";
		}

		if (isSuccess) {	// ������
			forward = new ActionForward();
			forward.setRedirect(true);	// �̵������ redirect�� �ϰڴٴ� �ǹ�
			forward.setPath(link);
		}

		return forward;
	}
}
