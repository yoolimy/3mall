package action;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

public class JoinProcAction implements Action {
// 회원가입 등록 폼으로의 이동을 위한 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	// 오버 라이딩
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();	// ActionForward 인스턴스 생성
		// 작업 후 이동할 때 이동하는 방식(redirect or dispatch)을 정해서 저장하는 인스턴스
		
		JoinProcSvc joinProcSvc = new JoinProcSvc(); 
		

		String id = request.getParameter("uid");
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String agrEmail = request.getParameter("agrEmail");
		String gender = request.getParameter("gender");
		String p1 = request.getParameter("p1");
		String p2 = request.getParameter("p2");
		String p3 = request.getParameter("p3");
		String e1 = request.getParameter("e1");
		String e2 = request.getParameter("e2");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		
		String basicAddr = request.getParameter("basicAddr");
		String zip = request.getParameter("zip");
		String addr1 = request.getParameter("addr1");
		String addr2 = request.getParameter("addr2");
		
		String phone = p1 + "-" + p2 + "-" + p3;
		String email = e1 + "@" + e2;
		String birth = year + month + day;
		
		
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setMl_id(id);
		memberInfo.setMl_name(name);
		memberInfo.setMl_pwd(pwd);
		memberInfo.setMl_phone(phone);
		memberInfo.setMl_email(email);
		memberInfo.setMl_agremail(agrEmail);
		memberInfo.setMl_birth(birth);
		memberInfo.setMl_gender(gender);
		

		MemberAddrInfo memberAddrInfo = new MemberAddrInfo();
		if (!"".equals(basicAddr) && basicAddr != null) {
			basicAddr = "y";
			memberAddrInfo.setMa_zip(zip);
			memberAddrInfo.setMa_addr1(addr1);
			memberAddrInfo.setMa_addr2(addr2);
			memberAddrInfo.setMa_basic(basicAddr);
		}
		
		boolean isSuccess = joinProcSvc.JoinInsert(memberInfo);
		boolean isClear = joinProcSvc.JoinAddrInsert(id, memberAddrInfo);
		
		if (!isSuccess) {	// 회원가입에 실패했으면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		if (!isClear) {	// 회원가입 주소 등록에 실패했으면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원가입 주소 등록이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		forward.setPath("login_form.jsp");
		
		return forward; // --> 컨트롤러(불렀던 곳으로 감) execute()로 감
	}	
}
