package admin.action;

import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.*;
import admin.svc.*;
import vo.*;

public class SalesChartAction implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SalesChartSvc salesChartSvc = new SalesChartSvc();
		ArrayList<SalesInfo> salesList = salesChartSvc.getSalesList();
		// ������ ���̵� �ش��ϴ� ��ǰ�� �������� PdtInfo�� �ν��Ͻ��� �޾� ��
		request.setAttribute("salesList", salesList);

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/sales_chart.jsp");

		return forward;
	}
}
