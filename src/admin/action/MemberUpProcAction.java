package admin.action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import admin.svc.*;
import vo.*;

public class MemberUpProcAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		String status = request.getParameter("memberStatus");
		String memo = request.getParameter("memo");
		
		MemberUpProcSvc memberUpProcSvc = new MemberUpProcSvc();
		
		MemberInfo memUpInfo = new MemberInfo();
		memUpInfo.setMl_id(id);
		memUpInfo.setMl_status(status);
		memUpInfo.setMl_memo(memo);
		
		boolean isSuccess = memberUpProcSvc.MemberUpdate(memUpInfo);
		
		if (!isSuccess) {	// 회원수정에 실패했으면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('회원 수정이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		String args = request.getParameter("args");
		request.setAttribute("memUpInfo", memUpInfo);
		forward.setPath("member_view.amem" + args + "&id=" + id);
		forward.setRedirect(true);	// 디스패쳐방식이 아닌 리다이렉트 방식으로 화면을 이동시킴
		
		return forward;
	}
}
