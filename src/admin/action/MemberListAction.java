package admin.action;
import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class MemberListAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();
		// 상품 목록을 저장할 ArrayList 객체로 MemberInfo형 인스턴스만 저장함
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, pcnt , spage, epage, rcnt, bsize = 10, psize = 10;
		// 페이징에 필요한 값들을 저장할 변수 선언 및 초기화
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}
		if (request.getParameter("psize") != null) {
			psize = Integer.parseInt(request.getParameter("psize"));
		}
		
		String schtype, keyword, gender;
		// 검색 조건 쿼리스트링을 받음
		
		schtype = request.getParameter("schtype");	// 검색조건으로 상품아이디(id)와 상품명(name)
		keyword = request.getParameter("keyword");	// 검색어
		gender = request.getParameter("gender");	// 검색조건 성별
		
		// 정렬조건 쿼리스트링을 받음(가입일순date(오a내d), 최종로그인순login(오a내d))
		String ord = request.getParameter("ord");
		
		String where = "", orderby = "";
		
		if (keyword != null && !keyword.equals("")) {
			where += " where ml_" + schtype + " like '%" + keyword + "%' ";
		} 
		if (gender != null && !gender.equals("")) {
			where += " where ml_gender = '" + gender + "' ";
		}
		
		// order by 만들기
		if (ord != null && !ord.equals("")) {	//	null이 아니여야만 다음꺼 검사가능
			orderby = " order by ml_" + ord.substring(0, ord.length() - 1) +
			(ord.substring(ord.length() - 1).equals("d") ? " desc" : " asc");
		// 정렬값 : datea, dated, logind, logind
		// ord.substring(ord.length() - 1) : 마지막 글자 
		}
		
		// 목록리스트
		MemberListSvc memberListSvc = new MemberListSvc();
		rcnt = memberListSvc.getMemberCount(where); // 검색된 회원의 총 수(페이지 개수를 구하기 위해 필요)
		
		memberList = memberListSvc.getMemberList(where, orderby, cpage, psize);	// 검색된 상품 목록을 받아오기 위함(검색조건, 정렬조건, 현재페이지, 페이지크기)
		// 현 페이지에서 보여줄 검색된 상품 목록
		// 검색조건, 정렬조건, limit에서 사용할 값을 구하기 위해 현재 페이지와 페이지 크기를 인수로 가져감
		
		pcnt = rcnt / psize;
		
		// 전체 페이지수 구함
		if (rcnt % psize > 0) 	
			pcnt++;				
		
		// 블록 시작페이지 번호
		spage = (cpage - 1) / psize * psize + 1;	
		epage = spage + psize - 1;
		
		// 블록 종료페이지 번호
		if (epage > pcnt)	{
			epage = pcnt;		
		}
		
		// 페이징을 위한 정보
		MemberPageInfo memberPageInfo = new MemberPageInfo();	//페이징에 필요한 정보를 담을 인스턴스 생성
		memberPageInfo.setCpage(cpage);	// 현재 페이지 번호
		memberPageInfo.setPcnt(pcnt);	// 전체 페이지 개수
		memberPageInfo.setSpage(spage);	// 블록 시작페이지 번호
		memberPageInfo.setEpage(epage);	// 블록 종료페이지 번호
		memberPageInfo.setRcnt(rcnt);	// 전체 상품(레코드) 개수
		memberPageInfo.setBsize(bsize);	// 블록내 페이지 개수
		memberPageInfo.setPsize(psize);	// 페이지내 상품 개수
		
		// 검색을 위한 정보
		memberPageInfo.setSchtype(schtype);	// 검색조건(상품코드, 상품명)
		memberPageInfo.setKeyword(keyword);	// 검색어
		memberPageInfo.setGender(gender);	// 등록일 검색 시작일
		memberPageInfo.setOrd(ord);			// 정렬조건
		
		
		// 위에 받아온 정보들을 다음페이지로 들고 가기 위함
		// 상품목록화면(pdt_list.jsp)으로 목록(pdtList)과 페이징 정보(pageInfo), 분류들을 request에 담아 가져감
		request.setAttribute("memberList", memberList);
		request.setAttribute("memberPageInfo", memberPageInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/member_list.jsp");
		return forward;
	}
}
