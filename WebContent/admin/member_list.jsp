<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
ArrayList<MemberInfo> memberList = (ArrayList<MemberInfo>)request.getAttribute("memberList");
MemberPageInfo memberPageInfo = (MemberPageInfo)request.getAttribute("memberPageInfo");

String schtype = memberPageInfo.getSchtype();	// 검색조건(상품코드, 상품명)
String keyword = memberPageInfo.getKeyword();	// 검색어
String gender = memberPageInfo.getGender();	// 검색어 성별
String ord = memberPageInfo.getOrd();			// 정렬조건


String args = "", schArgs = "";

if (gender != null) {
	schArgs += "&gender='" + gender;
} else {
	gender = "";
}

if (keyword != null && !keyword.equals("")) {
	schArgs += "&schtype=" + schtype + "&keyword=" + keyword;
} else {
	schtype = "";	keyword = "";
} 
if (ord != null) {
	schArgs += "&ord=" + ord;
} else {
	ord = "";
}

int cpage = memberPageInfo.getCpage();	// 현재 페이지 번호
int pcnt = memberPageInfo.getPcnt();	// 전체 페이지 수
int psize = memberPageInfo.getPsize();	// 페이지 크기
int bsize = memberPageInfo.getBsize();	// 블록 페이지 개수
int spage = memberPageInfo.getSpage();	// 블록 시작 페이지 번호
int epage = memberPageInfo.getEpage();	// 블록 종료 페이지 번호
int rcnt = memberPageInfo.getRcnt();	// 검색된 게시물 개수
schArgs = "&psize=" + psize + schArgs;
args = "&cpage=" + cpage + schArgs;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function chkAll(all) {
	var arrChk = document.adminMFrm.chk;
	for (var i = 0 ; i < arrChk.length ; i++) {
		arrChk[i].checked = all.checked;
	}
}
</script>
</head>
<body>
<h2>회원관리 관리자 페이지</h2>
<form name="adminMFrm" action="" method="get">
	<table width="700" cellpadding="5">
		<tr>
			<td align="right">
				<select name="schtype">
					<option value="id" <% if (schtype.equals("id")) { %> selected="selected" <% } %>>아이디</option>
					<option value="name" <% if (schtype.equals("name")) { %> selected="selected" <% } %>>이름</option>
					<option value="status" <% if (schtype.equals("status")) { %> selected="selected" <% } %>>상태</option>
					<option value="gender" <% if (schtype.equals("gender")) { %> selected="selected" <% } %>>성별</option>
				</select>
				<input type="text" name="keyword" value="<%=keyword %>" />
				<input type="submit" value="검색" />
			</td>
		</tr>
	</table>
	<div align="left">
			총 회원수 []
	</div>

<table width="1000" cellpadding="5">
	<%
	int max = 1;
	if (memberList != null && rcnt > 0) {	// 검색결과가 있으면
		if (max == 1) {		// 한 행에서 보여줄 상품의 최대 개수가 1이면 게시판 형식으로 보여줌
	%>
	<tr>
		<th width="2%"><input type="checkbox" onclick="chkAll(this);" /></th>
		<th width="2%">No</th>
		<th width="10%">아이디</th>
		<th width="10%">이름</th>
		<th width="8%">성별</th>
		<th width="10%">생년월일</th>
		<th width="15%">전화번호</th>
		<th width="10%">이메일</th>
		<th width="10%">가입일</th>
		<th width="10%">최종로그인</th>
		<th width="7%">상태</th>
	</tr>
	<%
		int seq = rcnt - (10 * (cpage - 1));	// 현재 페이지에서의 시작번호
		String lnk = "";
			if (memberList != null && !memberList.equals("")) {
				for (int i = 0 ; i < memberList.size() ; i++) {
					lnk = "<a href='member_view.amem?id=" + memberList.get(i).getMl_id() + args + "'>";
	%>
	<tr align="center">
		<td><input type="checkbox" name="chk" value="<%=memberList.get(i).getMl_id()%>"/></td>
		<td><%=seq--%></td>
		<td><%=lnk + memberList.get(i).getMl_id()%></a></td>
		<td><%=lnk + memberList.get(i).getMl_name()  %></a></td>
		<td><%=lnk + memberList.get(i).getMl_gender() %></a></td>
		<td><%=lnk + memberList.get(i).getMl_birth() %></a></td>
		<td><%=lnk + memberList.get(i).getMl_phone() %></a></td>
		<td><%=lnk + memberList.get(i).getMl_email() %></a></td>
		<td><%=lnk + memberList.get(i).getMl_date().substring(2, 10).replace("-", ".") %></a></td>
		<td><%=lnk + memberList.get(i).getMl_last().substring(2, 10).replace("-", ".") %></a></td>
		<td><%=lnk + memberList.get(i).getMl_status() %></a></td>
	</tr>
	<%
				}
			}
		}
	} else {
		out.println("등록된 회원이 없습니다.");
	}
	%>
</table>
</form>
<table width="700" cellpadding="5">
	<tr>
		<td width="*" align="center">
		<%
			if (rcnt > 0) {	// 검색결과 게시물이 있을 경우에만 페이징을 함
				pcnt = rcnt / 10;
				if (rcnt % 10 > 0) { 
					pcnt++; // 전체 페이지수 = 전체게시물수 / 페이지 크기 -> 나머지가 0보다 크면 1증가
				}
				
				// 이전페이지 버튼
				if (cpage == 1) {
					out.println("[<<]&nbsp;&nbsp;[<]&nbsp;&nbsp;");
				} else {
					out.print("<a href='member_list.amem?cpage=1" + schArgs + "'>");
					out.println("[<<]</a>&nbsp;&nbsp;");
					out.print("<a href='member_list.amem?cpage=" + (cpage - 1) + schArgs + "'>");
					out.println("[<]</a>&nbsp;&nbsp;");
				}
				
				// 페이징
				for (int i = 1, j = spage ; i <= 10 && j <= pcnt ; i++, j++) {
				/* i : 루프돌릴 횟수를 지정하기 위해 사용되는 변수 
				   j : 페이지 번호 출력용 변수
				   조건 : 10번을 도는데 페이지가 마지막 페이지일 경우, 10보다 작아도 멈춤
				*/
					if (cpage == j) {	// 현재 페이지일 경우 링크 없이 굵은 글씨체로 출력
						out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
					} else {
						out.println("&nbsp;<a href='member_list.amem?cpage=" + j + schArgs + "'>");
						out.println(j + "</a>&nbsp;");
					}
				}
				
				// 다음페이지 버튼
				if (cpage == pcnt) {
					out.println("&nbsp;&nbsp;[>]&nbsp;&nbsp;[>>]");
				} else {
					out.print("<a href='member_list.amem?cpage=" + (cpage + 1) + schArgs + "'>");
					out.println("&nbsp;&nbsp;[>]</a>");
					out.print("<a href='member_list.amem?cpage=" + pcnt + schArgs + "'>");
					out.println("&nbsp;&nbsp;[>>]</a>");
				}
			}
		%>
		</td>
		<td width="10%">
			<input type="button" value="선택수정" onclick="location.href='member_up_form.amem?wtype=up';" />
		</td>
	</tr>
</table>
</body>
</html>