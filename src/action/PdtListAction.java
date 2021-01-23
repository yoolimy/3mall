package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class PdtListAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		// ��ǰ ����� ������ ArrayList��ü�� PdtInfo�� �ν��Ͻ��� ������

		request.setCharacterEncoding("utf-8");
		int cpage = 1, pcnt, spage, epage, rcnt, bsize = 10, psize = 12;
		// ����¡�� �ʿ��� ������ ������ ���� ���� �� �ʱ�ȭ
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)
			psize = Integer.parseInt(request.getParameter("psize"));

		// �˻����� ������Ʈ���� ����
		String keyword, bcata, scata, rent;
		keyword = request.getParameter("keyword");	// �˻���
		bcata	= request.getParameter("bcata");	// ��з�
		scata	= request.getParameter("scata");	// �Һз�
		rent	= request.getParameter("rent");
		
		// �������� : ����price(��a��d), ��ǰ��name(��a), �����date(��d), �α�salecnt(��d), ����review(��d)
		String ord = request.getParameter("ord");

		String where = "and a. pl_view = 'y' ", orderby = "";
		if (bcata != null && !bcata.equals(""))		where += " and b.cb_idx  = '" + bcata + "' ";
		if (scata != null && !scata.equals(""))		where += " and a.cs_idx = '" + scata + "' ";
		if (rent != null && !rent.equals(""))		where += " and a.pl_rent = '" + rent + "' ";
		if (keyword != null && !keyword.equals(""))	where += " and a.pl_name like '%" + keyword + "%' ";
		// �˻����ǿ� where�� ����

		if (ord != null && !ord.equals(""))
			orderby = " order by a.pl_" + ord.substring(0, ord.length() - 1) + 
			(ord.substring(ord.length() - 1).equals("d") ? " desc" : " asc");
		// ���İ� : pricea, priced, namea, dated, salecntd, reviewd

		PdtListSvc pdtListSvc = new PdtListSvc();
		
		rcnt = pdtListSvc.getPdtCount(where);	// �˻��� ��ǰ�� �� ����(������ ������ ���ϱ� ���� �ʿ�)

		pdtList = pdtListSvc.getPdtList(where, orderby, cpage, psize);
		// �� ���������� ������ �˻��� ��ǰ���
		// �˻�����, ��������, limit���� ����� ���� ���ϱ� ���� ������������ ������ũ�⸦ �μ��� ������

		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// ��ü �������� ����
		spage = (cpage - 1) / psize * psize + 1;	// ��� ���������� ��ȣ
		epage = spage + psize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���������� ��ȣ

		PdtPageInfo pageInfo = new PdtPageInfo();	// ����¡�� �ʿ��� ������ ���� �ν��Ͻ� ����
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setPcnt(pcnt);			// ��ü ������ ����
		pageInfo.setSpage(spage);		// ��� ���������� ��ȣ
		pageInfo.setEpage(epage);		// ��� ���������� ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü ��ǰ(���ڵ�) ����
		pageInfo.setBsize(bsize);		// ��ϳ� ������ ����
		pageInfo.setPsize(psize);		// �������� ��ǰ ����
		pageInfo.setRent(rent);			// �뿩�� ����
		pageInfo.setBcata(bcata);		// ��з�
		pageInfo.setScata(scata);		// �Һз�
		pageInfo.setOrd(ord);			// ��������

		ArrayList<CataBigInfo> cataBigList = pdtListSvc.getCataBigList();		// ��з� ���
		ArrayList<CataSmallInfo> cataSmallList = pdtListSvc.getCataSmallList();	// �Һз� ���

		
		request.setAttribute("pdtList", pdtList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("cataBigList", cataBigList);
		request.setAttribute("cataSmallList", cataSmallList);

		// ��ǰ��� ȭ��(pdt_list.jsp)���� ���(pdtList)�� ����¡ ����(pageInfo), �з����� request�� ��� ������

		ActionForward forward = new ActionForward();
		forward.setPath("/product/pdt_list.jsp");
		return forward;
	}
}