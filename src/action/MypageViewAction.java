package action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import svc.*;
import vo.*;

public class MypageViewAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		MypageViewSvc mypageViewSvc = new MypageViewSvc();
		HttpSession session = request.getSession();
		
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		String id = loginMember.getMl_id();
		
		MemberInfo memberList = mypageViewSvc.getMypageView(id);
		MemberAddrInfo memberAddrInfoFirst = mypageViewSvc.getMemberAddrInfo(id, "y");
		MemberAddrInfo memberAddrInfoSecond = mypageViewSvc.getMemberAddrInfo(id, "n");

		
		request.setAttribute("memberList", memberList);
		request.setAttribute("memberAddrInfoFirst", memberAddrInfoFirst);
		request.setAttribute("memberAddrInfoSecond", memberAddrInfoSecond);
		forward.setPath("/member/mypage.jsp");
		
		return forward;
	}
}
