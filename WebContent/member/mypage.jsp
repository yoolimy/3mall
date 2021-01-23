<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="vo.*" %>
<%

MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
MemberAddrInfo memberAddrInfoFirst = (MemberAddrInfo)request.getAttribute("memberAddrInfoFirst");
MemberAddrInfo memberAddrInfoSecond = (MemberAddrInfo)request.getAttribute("memberAddrInfoSecond");


String name = "", p1 = "", p2 = "", p3 = "", e1 = "", e2 = "";

if (loginMember != null) { // 로그인 한 회원일 경우
	name = loginMember.getMl_name();
	String[] arrPhone = loginMember.getMl_phone().split("-");
	p1 = arrPhone[0];
	p2 = arrPhone[1];
	p3 = arrPhone[2];
	// 전화번호 값이 무조건 있으니까 위와 같이 넣는게 가능
	String[] arrEmail = loginMember.getMl_email().split("@");
	e1 = arrEmail[0];
	e2 = arrEmail[1];
}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>마이페이지 폼</h2>
<form name="frmJoin" action="mypage_proc.mem" method="post">
<input type="hidden" name="id" value="<%=loginMember.getMl_id() %>" />
<table cellpadding="5">
	<tr>
		<th>아이디</th>
		<td><%=loginMember.getMl_id()%></td>
	</tr>
	<tr>
		<th>이름</th>
		<td><input type="text" name="name" value="<%=name %>" /></td>
	</tr>
	<tr>
		<th>비밀번호</th>
		<td><input type="password" name="pwd" value="" /></td>
	</tr>
	<tr>
		<th>비밀번호 확인</th>
		<td><input type="password" name="pwd2" value="" /></td>
	</tr>
	<tr>
		<th>전화번호</th>
		<td>
			<select name="p1">
				<option value="010" <%if (p1.equals("010")) { %>selected="selected"<%} %>>010</option>
				<option value="011" <%if (p1.equals("011")) { %>selected="selected"<%} %>>011</option>
				<option value="016" <%if (p1.equals("016")) { %>selected="selected"<%} %>>016</option>
				<option value="019" <%if (p1.equals("019")) { %>selected="selected"<%} %>>019</option>
			</select> -
			<input type="text" name="p2" size="4" maxlength="4" value="<%=p2%>"/> -
			<input type="text" name="p3" size="4" maxlength="4" value="<%=p3%>" />
		</td>
	</tr>
	<tr>
		<th>이메일</th>
		<td>
			<input type="text" name="e1"  value="<%=e1%>" /> @
			<select name="e2">
				<option value="naver.com" <%if (e2.equals("naver.com")) { %>selected="selected"<%} %>>네이버</option>
				<option value="gmail.com" <%if (e2.equals("gmail.com")) { %>selected="selected"<%} %>>지메일</option>
				<option value="hanmail.net" <%if (e2.equals("hanmail.net")) { %>selected="selected"<%} %>>한메일</option>
				<option value="nate.com" <%if (e2.equals("nate.com")) { %>selected="selected"<%} %>>네이트</option>
				<option value="direct" <%if (e2.equals("direct")) { %>selected="selected"<%} %>>직접입력</option>
			</select>
			<input type="checkbox" name="agrEmail" value="y" checked="checked" /> 메일 수신 동의
		</td>
	</tr>
	<tr>
		<th>생년월일</th>
		<td><input type="text" name="birth" value="<%=loginMember.getMl_birth()%>" />(yyyymmdd)</td>
	</tr>
	<tr>
		<th>성별</th>
		<td>
			<input type="radio" name="gender" value="M" <%if (loginMember.getMl_gender().equals("M")) { %>checked="checked"<%} %> />남
			<input type="radio" name="gender" value="F" <%if (loginMember.getMl_gender().equals("F")) { %>checked="checked"<%} %> />여
		</td>
	</tr>
<%
	if(memberAddrInfoFirst != null && "y".equals(memberAddrInfoFirst.getMa_basic())) {
%>
	<tr>
		<th>주소1</th>
		<td>
			<input type="hidden" name="idxB" value="<%=memberAddrInfoFirst.getMa_idx() %>" />
			<input type="radio" name="basicAddr" value="first" />&nbsp;기본배송지<br />
			<input type="text" name="firstZip" value="<%=memberAddrInfoFirst.getMa_zip() %>" />&nbsp;우편번호<br />
			<input type="text" name="firstAddr1" value="<%=memberAddrInfoFirst.getMa_addr1() %>" />&nbsp;기본주소<br />
			<input type="text" name="firstAddr2" value="<%=memberAddrInfoFirst.getMa_addr2() %>" />&nbsp;상세주소
		</td>
	</tr>

<%
	} else {
%>
<tr>
	<th>주소1</th>
		<td>
			<input type="radio" name="basicAddr" value="first"  />&nbsp;기본배송지<br />
			<input type="text" name="firstZip" value="" />&nbsp;우편번호<br />
			<input type="text" name="firstAddr1" value="" />&nbsp;기본주소<br />
			<input type="text" name="firstAddr2" value="" />&nbsp;상세주소
		</td>
	</tr>
<%	}	%>
<%
	if(memberAddrInfoSecond != null && "n".equals(memberAddrInfoSecond.getMa_basic())) {
%>
	<tr>
		<th>주소2</th>
		<td>
			<input type="hidden" name="idxS" value="<%=memberAddrInfoSecond.getMa_idx() %>" />
			<input type="radio" name="basicAddr" value="second"  />&nbsp;기본배송지<br />
			<input type="text" name="secondZip" value="<%=memberAddrInfoSecond.getMa_zip() %>" />&nbsp;우편번호<br />
			<input type="text" name="secondAddr1" value="<%=memberAddrInfoSecond.getMa_addr1() %>" />&nbsp;기본주소<br />
			<input type="text" name="secondAddr2" value="<%=memberAddrInfoSecond.getMa_addr2() %>" />&nbsp;상세주소
		</td>
	</tr>

<%
	} else {
%>
<tr>
	<th>주소2</th>
		<td>
			<input type="radio" name="basicAddr" value="second"  />&nbsp;기본배송지<br />
			<input type="text" name="secondZip" value="" />&nbsp;우편번호<br />
			<input type="text" name="secondAddr1" value="" />&nbsp;기본주소<br />
			<input type="text" name="secondAddr2" value="" />&nbsp;상세주소
		</td>
	</tr>
<%	}	%>
	<tr>
		<th colspan="2">
			<input type="submit" value="수정 하기" />
			<input type="reset" value="다시 작성" />
		</th>
	</tr>
</table>
</form>
</body>
</html>