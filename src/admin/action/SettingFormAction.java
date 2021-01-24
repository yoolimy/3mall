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

public class SettingFormAction implements action.Action { // action Ŭ�������� action�� �޾ƿ�
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();	
		SettingFormSvc settingFormSvc = new SettingFormSvc();
		request.setCharacterEncoding("utf-8");
		
		String uploadPath = "D:/bay/jsp/work/3Mall/WebContent/ramtSystemPage/images";
		// ������ ������ ���� ��ġ�� ����
		int maxSize = 5 * 1024 * 1024;		// ���ε� �ִ� �뷮���� 5MB�� ����
		String rantSystem = "", main1 = "", main2 = "", main3 = "", popup = "";
		
		MultipartRequest multi = new MultipartRequest(
				request, 	// request��ü�� multi�� �����͵��� �ޱ� ����
				uploadPath, // ������ ������ ������ ����� ��ġ ����
				maxSize, 	// �� ���� ���ε��� �� �ִ� �ִ�ũ��(byte����)
				"utf-8", 	// ������ ���ڵ� ���(utf-8, euc-kr, ksc-5601 ��)
				new DefaultFileRenamePolicy());	// ���� �̸��� �ߺ� ó��
		
		Enumeration files = multi.getFileNames();	// ���ε��� ���� �̸����� Enumeration������ �޾� ��
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
		if (!isSuccess) {	// ��ǰ��Ͽ� ����������
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� ����� �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		forward.setPath("/rantSystemPage/settingForm.jsp");
		forward.setRedirect(true);
		return forward;
	}

}
