package admin.action;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class PdtListAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		// 상품 목록을 저장할 ArrayList객체로 PdtInfo형 인스턴스만 저장함

		request.setCharacterEncoding("utf-8");
		int cpage = 1, pcnt, spage, epage, rcnt, bsize = 10, psize = 10;
		// 페이징에 필요한 값들을 저장할 변수 선언 및 초기화
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)
			psize = Integer.parseInt(request.getParameter("psize"));

		// 검색조건 쿼리스트링을 받음
		String isview, schtype, keyword, bcata, scata, rent;
		isview	= request.getParameter("isview");	// 게시여부(y, n)
		schtype = request.getParameter("schtype");	// 검색조건으로 상품아이디(id)와 상품명(name)
		keyword = request.getParameter("keyword");	// 검색어
		bcata	= request.getParameter("bcata");	// 대분류
		scata	= request.getParameter("scata");	// 소분류
		rent	= request.getParameter("rent");		// 대여가능



		String where = "";
		if (isview != null && !isview.equals(""))	where += " and a.pl_view = '" + isview + "' ";
		if (bcata != null && !bcata.equals(""))		where += " and b.cb_idx  = '" + bcata + "' ";
		if (scata != null && !scata.equals(""))		where += " and a.cs_idx = '" + scata + "' ";
		if (keyword != null && !keyword.equals(""))	where += " and a.pl_" + schtype + " like '%" + keyword + "%' ";
		if (rent != null && !rent.equals(""))		where += " and a.pl_rent = '"+ rent + "' " ;	



		PdtListSvc pdtListSvc = new PdtListSvc();
		
		rcnt = pdtListSvc.getPdtCount(where);	// 검색된 상품의 총 개수(페이지 개수를 구하기 위해 필요)

		pdtList = pdtListSvc.getPdtList(where, cpage, psize);
		// 현 페이지에서 보여줄 검색된 상품목록
		// 검색조건, 정렬조건, limit에서 사용할 값을 구하기 위해 현재페이지와 페이지크기를 인수로 가져감

		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// 전체 페이지수 구함
		spage = (cpage - 1) / psize * psize + 1;	// 블록 시작페이지 번호
		epage = spage + psize - 1;
		if (epage > pcnt)	epage = pcnt;			// 블록 종료페이지 번호

		PdtPageInfo pageInfo = new PdtPageInfo();	// 페이징에 필요한 정보를 담을 인스턴스 생성
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setPcnt(pcnt);			// 전체 페이지 개수
		pageInfo.setSpage(spage);		// 블록 시작페이지 번호
		pageInfo.setEpage(epage);		// 블록 종료페이지 번호
		pageInfo.setRcnt(rcnt);			// 전체 상품(레코드) 개수
		pageInfo.setBsize(bsize);		// 블록내 페이지 개수
		pageInfo.setPsize(psize);		// 페이지내 상품 개수

		pageInfo.setIsview(isview);		// 게시여부(전체게시, 게시상품, 미게시상품)
		pageInfo.setSchtype(schtype);	// 검색조건(상품코드, 상품명)
		pageInfo.setKeyword(keyword);	// 검색어
		pageInfo.setBcata(bcata);		// 대분류
		pageInfo.setScata(scata);		// 소분류
		pageInfo.setRent(rent);			// 대여가능


		PdtInFormSvc pdtInFormSvc = new PdtInFormSvc();
		// 대분류, 소분류, 브랜드 목록을 가져오기 위한 Svc클래스
		ArrayList<CataBigInfo> cataBigList = pdtInFormSvc.getCataBigList();			// 대분류 목록
		ArrayList<CataSmallInfo> cataSmallList = pdtInFormSvc.getCataSmallList();	// 소분류 목록

		request.setAttribute("pdtList", pdtList);
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("cataBigList", cataBigList);
		request.setAttribute("cataSmallList", cataSmallList);
		// 상품목록 화면(pdt_list.jsp)으로 목록(pdtList)과 페이징 정보(pageInfo), 분류들을 request에 담아 가져감

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/pdt_list.jsp");
		return forward;
	}
}
