<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
ArrayList<AdminMemberInfo> adminMemList = (ArrayList<AdminMemberInfo>)request.getAttribute("adminMemList");
int adminCnt = (int)request.getAttribute("adminCnt");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>관리자 관리 페이지</h2>
<div align="left">
	관리자ID / 총 <%=adminCnt %>명
</div>
<table width="1000" cellpadding="0" border="1" cellspacing="0">
	<tr>
		<th width="3%">No</th>
		<th width="11%">아이디</th>
		<th width="11%">이름</th>
		<th width="15%">전화번호</th>
		<th width="*">이메일</th>
		<th width="12%">등록일</th>
		<th width="10%">부서</th>
		<th width="10%">직급</th>
		<th width="7%">상태</th>
	</tr>
<% for(int i = 0 ; i < adminMemList.size() ; i++) { %>
	<tr align="center">
		<td><%=adminMemList.get(i).getAl_idx() %></td>
		<td><%=adminMemList.get(i).getAl_id() %></td>
		<td><%=adminMemList.get(i).getAl_name() %>
		<td><%=adminMemList.get(i).getAl_phone() %></td>
		<td><%=adminMemList.get(i).getAl_email() %></td>
		<td><%=adminMemList.get(i).getAl_date().substring(0, 10) %></td>
		<td><%=adminMemList.get(i).getAl_team() %></td>
		<td><%=adminMemList.get(i).getAl_position() %></td>
		<td>
		<% if (adminMemList.get(i).getAl_status().equals("a")) { %>
			사용중
		<% } else { %>
			비활성화
		<% } %>
		</td>
	</tr>
<% } %>
</table>
<br />
<table width="1000" cellpadding="0" border="0" cellspacing="0">
	<tr align="center">
		<td>
			<input type="button" value="어드민 추가" onclick="location.href='./admin/admin_mem_add.jsp'" />&nbsp;&nbsp;&nbsp;
			<input type="button" value="목록으로" onclick="history.back();" />
		</td>
	</tr>
</table>
</body>
</html>