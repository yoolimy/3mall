package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class ReviewListAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<ReviewInfo> reviewList = new ArrayList<ReviewInfo>();

		request.setCharacterEncoding("utf-8");
		int cpage = 1, limit = 10;
		// ���� ��������ȣ�� ������������ ������ �Խñ� ������ �����ϴ� ����
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		String schtype = request.getParameter("schtype");
		// �˻��������� �ۼ���, ����, ����, ����+����
		String keyword = request.getParameter("keyword");	// �˻���

		String where = "";	// ���� �۾��� ����� ������ ������ ����
		if (keyword != null && !keyword.equals("")) {
		// �˻�� ������ �˻������� ����
			if (schtype.equals("tc")) {	// �˻� ������ '����+����' �̸�
				where = " and (fl_title like '%" + keyword + "%' " + 
					" or fl_content like '%" + keyword + "%') ";
			} else {	// �˻������� ���� �Ǵ� ���� �Ǵ� �ۼ��� �̸�
				where = " and fl_" + schtype + " like '%" + keyword + "%' ";
			}
		}

		ReviewListSvc reviewListSvc = new ReviewListSvc();
		// �Խ��� ����� ���� ����Ͻ� ������ ó���ϱ� ���� FreeListSvc �ν��Ͻ� ����

		int rcnt = reviewListSvc.getArticleCount(where);
		// �� ���ڵ� ������ �޾ƿ�(��ü ���������� ����ϱ� ����)

		reviewList = reviewListSvc.getArticleList(where, cpage, limit);
		// ���ȭ�鿡�� ������ �Խñ� ����� ArrayList������ �޾ƿ�

		int pcnt = rcnt / limit;
		if (rcnt % limit > 0)	pcnt++;
		// ��ü �������� ����
		int spage = (cpage - 1) / limit * limit + 1;
		// ��� ���������� ��ȣ
		int epage = spage + limit - 1;
		if (epage > pcnt)	epage = pcnt;
		// ��� ���������� ��ȣ

		PageInfo pageInfo = new PageInfo();
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü �Խñ� ��
		pageInfo.setPcnt(pcnt);			// ��ü ������ ��
		pageInfo.setSpage(spage);		// ��� ���� ������ ��ȣ
		pageInfo.setEpage(epage);		// ��� ���� ������ ��ȣ
		pageInfo.setSchtype(schtype);	// �˻�����
		pageInfo.setKeyword(keyword);	// �˻���
		// ����¡ ���� ������� �˻������� PageInfo �ν��Ͻ��� ����
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("reviewList", reviewList);
		// review_list.jsp�� request�� ���� �������� ��ü�� request�� ����

		ActionForward forward = new ActionForward();
		forward.setPath("/bbs/review_list.jsp");
		// �̵��� �������� URL�� forward�ν��Ͻ��� ����
		return forward;
	}
}
