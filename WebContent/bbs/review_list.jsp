<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
// dispatch방식으로 이동했기 때문에 이전 파일의 request와 response를 사용할 수 있음
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<ReviewInfo> reviewList = (ArrayList<ReviewInfo>)request.getAttribute("reviewList");
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");

String schtype = null, keyword = null, schargs = "", args = "";
if (pageInfo.getSchtype() == null || pageInfo.getKeyword() == null){
schtype = "";   keyword = "";
//검색을 하지 않은 경우에는 검색조건과 검색어를 빈 문자열로 지정
} else {	// 검색을 했을 경우
	schtype = pageInfo.getSchtype();	// 검색조건
	keyword = pageInfo.getKeyword();	// 검색어
	if (keyword != null && !keyword.equals("")){
	   schargs = "&schtype=" + schtype + "&keyword=" + keyword;
	   // 검색어가 있을 경우 검색관련 데이터를 쿼리 스트링으로 지정(패이징할때 사용할 링크)
	}
}
int cpage = pageInfo.getCpage();   // 현재 페이지번호
int pcnt = pageInfo.getPcnt();      // 전체 페이지수
int spage = pageInfo.getSpage();   // 블록 시작 페이지 번호
int epage = pageInfo.getEpage();   // 블록 종료 페이지 번호
int rcnt = pageInfo.getRcnt();      // 검색된 게시물 개수

args = "&cpage=" + cpage + schargs;   // 목록에서 게시글을 보러갈때 사용할 링크
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
th { background:#D7AC87; }
#brdList tr { height:40px; }
#brdList td, #brdList th { border-bottom:1px black solid; }
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:black; text-decoration:none; }
a:hover { color:green; text-decoration:underline; font-weight:bold;}
a:active { color:#f00; text-decoration:none; }   
a:focus { color:#f00; text-decoration:underline; }
</style>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="main" align="center">
<h2>리뷰 게시판</h2>
<form name="frmSch" method="get">
<table width="700" cellpadding="5">
<tr>
<td align="center">
<select name="cata">
	<option value="woman">여자한복</option>
	<option value="man">남자한복</option>
	<option value="accessory">장신구</option>
</select>
<select name="schtype">
	<option value="name" <% if (schtype.equals("name")) { %>
         selected="selected"<% } %>>상품명</option>
	<option value="title" <% if (schtype.equals("title")) { %>
         selected="selected"<% } %>>제목</option>
	<option value="nt" <% if (schtype.equals("nt")) { %>
         selected="selected"<% } %>>제목+내용</option>
</select>
	<input type="text" name="keyword" value="<%=keyword %>"/>
	<input type="submit" value="검색" />
</td>
</tr>
</table>
</form>
<table width="700" cellpadding="5" cellspacing="0" id="brdList">
<tr>
<th width="7%">번호</th><th width="15%">카테고리</th><th width="18%">상품명</th><th width="*">제목</th><th width="10%">작성자</th><th width="15%">등록일</th>
</tr>
<%
if (reviewList != null && rcnt > 0){   // 검색결과가 있으면
	   int seq = rcnt - (10 * (cpage - 1));		// 현재 페이지에의 시작번호
	   String title = "", reply = "", lnk = "";
	   for (int i = 0 ; i < reviewList.size() ; i++){
		// articleList에 들어있는 데이터의 개수만큼 루프를 돔	
	      title = reviewList.get(i).getRl_title();
	      lnk = "<a href='review_view.review?idx=" + reviewList.get(i).getRl_idx() + args +
	            "' title='" + title + "'>";
	            // 제목에 달릴 링크 제작(title속성으로 제목을 툴팁을 통해서도 보여줌)
	      if (title.length() > 26)
	         title = title.substring(0, 24) + "...";
			// 제목이 너무길어  두 줄이 되는 것을 막아줌

%>
<tr align="center" onmouseover="this.style.background='#efefef';" onmouseout="this.style.background='';">
<td><%=seq-- %></td>
<td><%=reviewList.get(i).getCatabig() %></td>
<td><%=reviewList.get(i).getPlname() %></td>
<td><%=lnk + title + "</a>" %></td>
<td><%=reviewList.get(i).getMl_id() %></td>
<td><%=reviewList.get(i).getRl_date().substring(2,10).replace('.', '-') %></td>
</tr>
<%
   }
} else { // 검색결과가 없으면
   out.println("<tr align='center'><td colspan='5'>");
   out.println("검색 결과가 없습니다.</td></tr>");
}
%>
</table>
<br />
<table width="700" cellpadding="5">
<tr align="center">
<td width="*" align="center">
<%
if (rcnt > 0) {
// 검색결과 게시물이 있을 경우에만 페이징을 함
   pcnt = rcnt / 10;
   if (rcnt % 10 > 0)   pcnt++;
   // 전체 페이지수 = 전체게시물수 / 페이지크기 -> 나머지가 있으면 1증가

   if (cpage == 1) {
      out.println("[처음]&nbsp;&nbsp;[이전]&nbsp;&nbsp;");
   } else {
      out.print("<a href='brd_list.review?cpage=1" + schargs + "'>");
      out.println("[처음]</a>&nbsp;&nbsp;");
      out.print("<a href='brd_list.review?cpage=" + (cpage - 1) + schargs + "'>");
      out.println("[이전]</a>&nbsp;&nbsp;");
   }

   for (int i = 1, j = spage ; i <= 10 && j <= pcnt ; i++, j++) {
   // i : 루프돌릴 횟수를 지정하기 위해 사용되는 변수
   // j : 페이지 번호 출력용 변수
   // 조건 : 10번을 도는데 페이지가 마지막 페이지 일 경우 10보다 작아도 멈춤
      if (cpage == j) {
      // 현재 페이지일 경우 링크 없이 굵은 글씨체로 출력
         out.println("&nbsp;<strong>" + j + "</strong>&nbsp;");
      } else {
         out.print("&nbsp;<a href='brd_list.review?cpage=" + j + schargs + "'>");
         out.println(j + "</a>&nbsp;");
      }
   }

   if (cpage == pcnt) {
      out.println("&nbsp;&nbsp;[다음]&nbsp;&nbsp;[마지막]");
   } else {
      out.print("&nbsp;&nbsp;<a href='brd_list.review?cpage=" + (cpage + 1) + schargs + "'>");
      out.println("[다음]</a>");
      out.print("&nbsp;&nbsp;<a href='brd_list.review?cpage=" + pcnt + schargs + "'>");
      out.println("[마지막]</a>");
   }
}
%>
</td>
<td width="10%">
   <input type="button" value="글 등록" onclick="location.href='review_form.review?wtype=in';" />
</td>
</tr>
</table>
</div>
</body>
</html>