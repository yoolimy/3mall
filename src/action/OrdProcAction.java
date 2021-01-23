package action;

import java.io.PrintWriter;	// ��� ���н� �ڹٽ�ũ��Ʈ ����� ���� import
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import svc.*;
import vo.*;

public class OrdProcAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String kind = request.getParameter("kind");
		String[] clIdxs = request.getParameter("clIdxs").split(",");	// ��ٱ��� idx ������ �迭
		String ismember = request.getParameter("ismember");
		String bname = request.getParameter("bname");
		String bphone = request.getParameter("bp1") + "-" + 
			request.getParameter("bp2") + "-" + request.getParameter("bp3");
		String bemail = request.getParameter("be1") + "@" + request.getParameter("be2");
		String rname = request.getParameter("rname");
		String rphone = request.getParameter("rp1") + "-" + 
			request.getParameter("rp2") + "-" + request.getParameter("rp3");
		String rzip = request.getParameter("rzip");
		String raddr1 = request.getParameter("raddr1");
		String raddr2 = request.getParameter("raddr2");
		String payment = request.getParameter("payment");
		String strUsePnt = request.getParameter("usePnt");
		String strSavePnt = request.getParameter("savePnt");
		int total = Integer.parseInt(request.getParameter("total"));
		int usePnt = 0, savePnt = 0;
		if (strUsePnt != null && !strUsePnt.equals("")) {
			usePnt = Integer.parseInt(strUsePnt);
			total = total - usePnt;
		}
		if (strSavePnt != null && !strSavePnt.equals("")) {
			savePnt = Integer.parseInt(strSavePnt);
			savePnt = total / 100;
		}

		HttpSession session = request.getSession();
		String buyer = session.getId();
		// ������ ȸ��ID �Ǵ� ����ID(��ȸ��ID)
		if (ismember.equals("y")) {
			MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
			buyer = loginMember.getMlid();
		}

		OrderInfo ord = new OrderInfo();
		ord.setOl_ismember(ismember);	ord.setOl_buyer(buyer);
		ord.setOl_bname(bname);			ord.setOl_bphone(bphone);
		ord.setOl_bmail(bemail);		ord.setOl_rname(rname);
		ord.setOl_rphone(rphone);		ord.setOl_rzip(rzip);
		ord.setOl_raddr1(raddr1);		ord.setOl_raddr2(raddr2);
		ord.setOl_payment(payment);		ord.setOl_usepnt(usePnt);
		ord.setOl_savepnt(savePnt);		ord.setOl_pay(total);
		// ������ �� ����� ������ OrderInfo�� �ν��Ͻ��� ��Ƽ� �̵���Ŵ

		OrdProcSvc ordProcSvc = new OrdProcSvc();
		String result = ordProcSvc.orderProc(kind, clIdxs, ord);
		String[] arrResult = result.split(":");

		ActionForward forward = null;
		if (arrResult[0].equals("1")) {	// ������
			forward = new ActionForward();
			forward.setRedirect(true);	// �̵������ redirect�� �ϰڴٴ� �ǹ�
			forward.setPath("order_success.ord?olid=" + arrResult[1]);
		}

		return forward;
	}
}
