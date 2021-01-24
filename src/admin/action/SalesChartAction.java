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
		// 지정한 아이디에 해당하는 상품의 정보들을 PdtInfo형 인스턴스로 받아 옴
		request.setAttribute("salesList", salesList);

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/sales_chart.jsp");

		return forward;
	}
}
