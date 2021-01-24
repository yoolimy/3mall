package admin.action;

import javax.servlet.http.*;

import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminMemListAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<AdminMemberInfo> adminMemList = new ArrayList<AdminMemberInfo>();
		int adminCnt = 0;
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		AdminMemberInfo adminMember = (AdminMemberInfo)session.getAttribute("adminMember");
		String loginaid = adminMember.getAl_id();
		if (!loginaid.equals("admin")) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('해당 페이지의 권한이 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} else {
			AdminMemListSvc adminMemListSvc = new AdminMemListSvc();
			adminMemList = adminMemListSvc.getAdminMemList();
			adminCnt = adminMemListSvc.getAdminMemCnt();
			
			request.setAttribute("adminMemList", adminMemList);
			request.setAttribute("adminCnt", adminCnt);
		}

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/admin_mem_list.jsp");
		return forward;
	}
}
