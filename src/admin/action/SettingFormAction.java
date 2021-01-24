package admin.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import admin.svc.*;
import vo.*;

public class SettingFormAction implements action.Action { // action 클래스에서 action을 받아옴
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();	
		SettingFormSvc settingFormSvc = new SettingFormSvc();
		request.setCharacterEncoding("utf-8");
		
		String uploadPath = "D:/bay/jsp/work/3Mall/WebContent/ramtSystemPage/images";
		// 파일을 저장할 실제 위치를 구함
		int maxSize = 5 * 1024 * 1024;		// 업로드 최대 용량으로 5MB로 지정
		String rantSystem = "", main1 = "", main2 = "", main3 = "", popup = "";
		
		MultipartRequest multi = new MultipartRequest(
				request, 	// request객체로 multi로 데이터들을 받기 위함
				uploadPath, // 서버에 실제로 파일이 저장될 위치 지정
				maxSize, 	// 한 번에 업로드할 수 있는 최대크기(byte단위)
				"utf-8", 	// 파일의 인코딩 방식(utf-8, euc-kr, ksc-5601 등)
				new DefaultFileRenamePolicy());	// 파일 이름의 중복 처리
		
		Enumeration files = multi.getFileNames();	// 업로드할 파일 이름들을 Enumeration형으로 받아 옴
		while (files.hasMoreElements()) {
			String f = (String)files.nextElement();
			switch (f) {
				case "main1" : main1 = multi.getFilesystemName(f);	break;
				case "main2" : main2 = multi.getFilesystemName(f);	break;
				case "main3" : main3 = multi.getFilesystemName(f);	break;
				case "rantSystem" : rantSystem = multi.getFilesystemName(f);	break;
				case "popup" : popup = multi.getFilesystemName(f);	break;
			}
		}
		SettingInfo set = new SettingInfo();
		set.setAe_main1(main1);
		set.setAe_main2(main2);
		set.setAe_main3(main3);
		set.setAe_system(rantSystem);
		set.setAe_popup(popup);
		
		boolean isSuccess = settingFormSvc.settingForm(set);
		if (!isSuccess) {	// 상품등록에 실패했으면
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('파일 등록이 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		forward.setPath("/rantSystemPage/settingForm.jsp");
		forward.setRedirect(true);
		return forward;
	}

}
