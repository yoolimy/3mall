package admin.action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import admin.svc.*;
import vo.*;

public class MemberViewAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		String id = request.getParameter("id");
		MemberViewSvc memberViewSvc = new MemberViewSvc();
		MemberInfo memberInfo = memberViewSvc.getMemberInfo(id);
		// 지정한 아이디에 해당하는 상품의 정보들을 MemberInfo형 인스턴스로 받아 옴
		MemberAddrInfo memberAddrInfoFirst = memberViewSvc.getMemberAddrInfo(id, "y");
		MemberAddrInfo memberAddrInfoSecond = memberViewSvc.getMemberAddrInfo(id, "n");
		// memberAddrInfoFirst : 기본 주소
		// memberAddrInfoSecond : 주소2
		
		request.setAttribute("memberInfo", memberInfo);
		request.setAttribute("memberAddrInfoFirst", memberAddrInfoFirst);
		request.setAttribute("memberAddrInfoSecond", memberAddrInfoSecond);
		forward.setPath("/admin/member_view.jsp");
		
		return forward;
	}
}
