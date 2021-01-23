package action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import svc.*;
import vo.*;

public class MypageProcAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		System.out.println("firstAddrChkStatus :: " + request.getParameter("firstAddrChkStatus"));
		System.out.println("secondAddrChkStatus :: " + request.getParameter("secondAddrChkStatus"));
		String idxB = request.getParameter("idxB");
		String idxS = request.getParameter("idxS");
		String firstAddrChkStatus = request.getParameter("firstAddrChkStatus");
		String secondAddrChkStatus = request.getParameter("secondAddrChkStatus");
		String id = request.getParameter("id");
		String name = request.getParameter("name").trim();
		String pwd = request.getParameter("pwd").trim();
		String phone = request.getParameter("p1") + "-" + request.getParameter("p2") + "-" + request.getParameter("p3");
		String email = request.getParameter("e1") + "@" + request.getParameter("e2");
		String agrEmail = request.getParameter("agrEmail");
		String birth = request.getParameter("birth");
		String gender = request.getParameter("gender");
		
		String basicAddr = request.getParameter("basicAddr");
		String firstZip = request.getParameter("firstZip");
		String firstAddr1 = request.getParameter("firstAddr1");
		String firstAddr2 = request.getParameter("firstAddr2");
		
		String secondZip = request.getParameter("secondZip");
		String secondAddr1 = request.getParameter("secondAddr1");
		String secondAddr2 = request.getParameter("secondAddr2");
		
		MypageProcSvc mypageProcSvc = new MypageProcSvc();
		
		MemberInfo mypageInfo = new MemberInfo();
		mypageInfo.setMl_id(id);
		mypageInfo.setMl_name(name);
		mypageInfo.setMl_pwd(pwd);
		mypageInfo.setMl_phone(phone);
		mypageInfo.setMl_email(email);
		mypageInfo.setMl_agremail(agrEmail);
		mypageInfo.setMl_birth(birth);
		mypageInfo.setMl_gender(gender);
		
		MemberAddrInfo memberAddrInfoFirst = new MemberAddrInfo();
		MemberAddrInfo memberAddrInfoSecond = new MemberAddrInfo();
		
		if("first".equals(basicAddr)) {
			memberAddrInfoFirst.setMa_basic("y"); 
			memberAddrInfoSecond.setMa_basic("n"); 
		} else if ("second".equals(basicAddr)) {
			memberAddrInfoFirst.setMa_basic("n"); 
			memberAddrInfoSecond.setMa_basic("y"); 
		}
		
		boolean addrFirstInsertResult = true;
		boolean addrFirstUpdateResult = true;
		boolean addrFirstDeleteResult = true;
		
		memberAddrInfoFirst.setMa_zip(firstZip);
		memberAddrInfoFirst.setMa_addr1(firstAddr1);
		memberAddrInfoFirst.setMa_addr2(firstAddr2);
		if	(firstAddrChkStatus.equals("insert")) {
			addrFirstInsertResult = mypageProcSvc.getMypageAddrInsert(id, memberAddrInfoFirst);
		} else if (firstAddrChkStatus.equals("update")) {
			memberAddrInfoFirst.setMa_idx(Integer.parseInt(idxB));
			addrFirstUpdateResult = mypageProcSvc.getMypageAddrUpdate(memberAddrInfoFirst);
		} else if (firstAddrChkStatus.equals("delete")) {
			memberAddrInfoFirst.setMa_idx(Integer.parseInt(idxB));
			addrFirstDeleteResult = mypageProcSvc.getMypageAddrDelete(id, memberAddrInfoFirst);
		} 

		
		boolean addrSecondInsertResult = true;
		boolean addrSecondUpdateResult = true;
		boolean addrSecondDeleteResult = true;
		
		System.out.println(memberAddrInfoFirst.getMa_basic());
		System.out.println(memberAddrInfoSecond.getMa_basic());
		
		memberAddrInfoSecond.setMa_zip(secondZip);
		memberAddrInfoSecond.setMa_addr1(secondAddr1);
		memberAddrInfoSecond.setMa_addr2(secondAddr2);
		if	(secondAddrChkStatus.equals("insert")) {
			addrSecondInsertResult = mypageProcSvc.getMypageAddrInsert(id, memberAddrInfoSecond);
		} else if (secondAddrChkStatus.equals("update")) {
			memberAddrInfoSecond.setMa_idx(Integer.parseInt(idxS));
			addrSecondUpdateResult = mypageProcSvc.getMypageAddrUpdate(memberAddrInfoSecond);
		} else if (secondAddrChkStatus.equals("delete")) {
			memberAddrInfoSecond.setMa_idx(Integer.parseInt(idxS));
			addrSecondDeleteResult = mypageProcSvc.getMypageAddrDelete(id, memberAddrInfoSecond);
		} 
		
		boolean isSuccess = mypageProcSvc.getMypageUpdate(mypageInfo);

		if (!addrFirstInsertResult) {	// ȸ���ּ�1��Ͽ� ����������
			out.println("<script>");
			out.println("alert('ȸ���ּ�1 ����� �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		if (!addrSecondInsertResult) {	// ȸ���ּ�2��Ͽ� ����������
			out.println("<script>");
			out.println("alert('ȸ���ּ�2 ����� �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		if (!addrFirstUpdateResult) {	// ȸ���ּ�1 ������ ����������
			out.println("<script>");
			out.println("alert('ȸ���ּ�1 ������ �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		if (!addrFirstDeleteResult) {	// ȸ���ּ�1 ������ ����������
			out.println("<script>");
			out.println("alert('ȸ���ּ�1 ������ �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		if (!addrSecondUpdateResult) {	// ȸ���ּ�2 ������ ����������
			out.println("<script>");
			out.println("alert('ȸ���ּ�2 ������ �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} 
		if (!addrSecondDeleteResult) {	// ȸ���ּ�2 ������ ����������
			out.println("<script>");
			out.println("alert('ȸ���ּ�2 ������ �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		if (!isSuccess) {	// ȸ�������� ����������
			out.println("<script>");
			out.println("alert('ȸ�� ������ �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		request.setAttribute("mypageInfo", mypageInfo);
		request.setAttribute("memberAddrInfoFirst", memberAddrInfoFirst);
		request.setAttribute("memberAddrInfoSecond", memberAddrInfoSecond);
		forward.setPath("mypage_view.mem?id=" + id);
		forward.setRedirect(true);	// �����Ĺ���� �ƴ� �����̷�Ʈ ������� ȭ���� �̵���Ŵ
		
		return forward;
	}
}
