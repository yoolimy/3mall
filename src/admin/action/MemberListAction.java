package admin.action;
import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class MemberListAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();
		// ��ǰ ����� ������ ArrayList ��ü�� MemberInfo�� �ν��Ͻ��� ������
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, pcnt , spage, epage, rcnt, bsize = 10, psize = 10;
		// ����¡�� �ʿ��� ������ ������ ���� ���� �� �ʱ�ȭ
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}
		if (request.getParameter("psize") != null) {
			psize = Integer.parseInt(request.getParameter("psize"));
		}
		
		String schtype, keyword, gender;
		// �˻� ���� ������Ʈ���� ����
		
		schtype = request.getParameter("schtype");	// �˻��������� ��ǰ���̵�(id)�� ��ǰ��(name)
		keyword = request.getParameter("keyword");	// �˻���
		gender = request.getParameter("gender");	// �˻����� ����
		
		// �������� ������Ʈ���� ����(�����ϼ�date(��a��d), �����α��μ�login(��a��d))
		String ord = request.getParameter("ord");
		
		String where = "", orderby = "";
		
		if (keyword != null && !keyword.equals("")) {
			where += " where ml_" + schtype + " like '%" + keyword + "%' ";
		} 
		if (gender != null && !gender.equals("")) {
			where += " where ml_gender = '" + gender + "' ";
		}
		
		// order by �����
		if (ord != null && !ord.equals("")) {	//	null�� �ƴϿ��߸� ������ �˻簡��
			orderby = " order by ml_" + ord.substring(0, ord.length() - 1) +
			(ord.substring(ord.length() - 1).equals("d") ? " desc" : " asc");
		// ���İ� : datea, dated, logind, logind
		// ord.substring(ord.length() - 1) : ������ ���� 
		}
		
		// ��ϸ���Ʈ
		MemberListSvc memberListSvc = new MemberListSvc();
		rcnt = memberListSvc.getMemberCount(where); // �˻��� ȸ���� �� ��(������ ������ ���ϱ� ���� �ʿ�)
		
		memberList = memberListSvc.getMemberList(where, orderby, cpage, psize);	// �˻��� ��ǰ ����� �޾ƿ��� ����(�˻�����, ��������, ����������, ������ũ��)
		// �� ���������� ������ �˻��� ��ǰ ���
		// �˻�����, ��������, limit���� ����� ���� ���ϱ� ���� ���� �������� ������ ũ�⸦ �μ��� ������
		
		pcnt = rcnt / psize;
		
		// ��ü �������� ����
		if (rcnt % psize > 0) 	
			pcnt++;				
		
		// ��� ���������� ��ȣ
		spage = (cpage - 1) / psize * psize + 1;	
		epage = spage + psize - 1;
		
		// ��� ���������� ��ȣ
		if (epage > pcnt)	{
			epage = pcnt;		
		}
		
		// ����¡�� ���� ����
		MemberPageInfo memberPageInfo = new MemberPageInfo();	//����¡�� �ʿ��� ������ ���� �ν��Ͻ� ����
		memberPageInfo.setCpage(cpage);	// ���� ������ ��ȣ
		memberPageInfo.setPcnt(pcnt);	// ��ü ������ ����
		memberPageInfo.setSpage(spage);	// ��� ���������� ��ȣ
		memberPageInfo.setEpage(epage);	// ��� ���������� ��ȣ
		memberPageInfo.setRcnt(rcnt);	// ��ü ��ǰ(���ڵ�) ����
		memberPageInfo.setBsize(bsize);	// ��ϳ� ������ ����
		memberPageInfo.setPsize(psize);	// �������� ��ǰ ����
		
		// �˻��� ���� ����
		memberPageInfo.setSchtype(schtype);	// �˻�����(��ǰ�ڵ�, ��ǰ��)
		memberPageInfo.setKeyword(keyword);	// �˻���
		memberPageInfo.setGender(gender);	// ����� �˻� ������
		memberPageInfo.setOrd(ord);			// ��������
		
		
		// ���� �޾ƿ� �������� ������������ ��� ���� ����
		// ��ǰ���ȭ��(pdt_list.jsp)���� ���(pdtList)�� ����¡ ����(pageInfo), �з����� request�� ��� ������
		request.setAttribute("memberList", memberList);
		request.setAttribute("memberPageInfo", memberPageInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/member_list.jsp");
		return forward;
	}
}
