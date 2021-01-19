package action;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import svc.*;
import vo.*;

public class MypageProcAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String idxB = request.getParameter("idxB");
		String idxS = request.getParameter("idxS");
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
		
		System.out.println(request.getParameter("idxB"));
		System.out.println(request.getParameter("idxS"));
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
		
		memberAddrInfoFirst.setMa_zip(firstZip);
		memberAddrInfoFirst.setMa_addr1(firstAddr1);
		memberAddrInfoFirst.setMa_addr2(firstAddr2);
		if(idxB == null) {
			addrFirstInsertResult = mypageProcSvc.getMypageAddrInsert(id, memberAddrInfoFirst);
		} else {
			memberAddrInfoFirst.setMa_idx(Integer.parseInt(idxB));
			addrFirstUpdateResult = mypageProcSvc.getMypageAddrUpdate(memberAddrInfoFirst);
		}

		
		boolean addrSecondInsertResult = true;
		boolean addrSecondUpdateResult = true;
		
		memberAddrInfoFirst.setMa_zip(secondZip);
		memberAddrInfoFirst.setMa_addr1(secondAddr1);
		memberAddrInfoFirst.setMa_addr2(secondAddr2);
		if(idxS == null) {
			addrSecondInsertResult = mypageProcSvc.getMypageAddrInsert(id, memberAddrInfoSecond);
		} else {
			memberAddrInfoSecond.setMa_idx(Integer.parseInt(idxB));
			addrSecondUpdateResult = mypageProcSvc.getMypageAddrUpdate(memberAddrInfoSecond);
		}
		
		boolean isSuccess = mypageProcSvc.getMypageUpdate(mypageInfo);

		if (!addrFirstInsertResult) {	// 회원주소1등록에 실패했으면
			out.println("<script>");
			out.println("alert('회원주소1 등록이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		if (!addrSecondInsertResult) {	// 회원주소1등록에 실패했으면
			out.println("<script>");
			out.println("alert('회원주소1 등록이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		if (!addrFirstUpdateResult) {	// 회원주소1등록에 실패했으면
			out.println("<script>");
			out.println("alert('회원주소1 등록이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		if (!addrSecondUpdateResult) {	// 회원주소1등록에 실패했으면
			out.println("<script>");
			out.println("alert('회원주소1 등록이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		if (!isSuccess) {	// 회원수정에 실패했으면
			out.println("<script>");
			out.println("alert('회원 수정이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		request.setAttribute("mypageInfo", mypageInfo);
		request.setAttribute("memberAddrInfoFirst", memberAddrInfoFirst);
		request.setAttribute("memberAddrInfoSecond", memberAddrInfoSecond);
		forward.setPath("mypage_view.mem?id=" + id);
		forward.setRedirect(true);	// 디스패쳐방식이 아닌 리다이렉트 방식으로 화면을 이동시킴
		
		return forward;
	}
}
