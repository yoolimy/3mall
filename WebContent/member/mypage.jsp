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

//asterisk 이미지 삽입
String img = "&nbsp;<img src=\"images/asterisk.png\" width=\"5\" height=\"5\" align=\"absmiddle\" />";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="javascript/mypageScript.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>

//우편번호 찾기
function execDaumPostcode(num) {
 new daum.Postcode({
     oncomplete: function(data) {
         // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

         // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
         // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
         var roadAddr = data.roadAddress; // 도로명 주소 변수 1
         var extraRoadAddr = ''; // 참고 항목 변수

         // 법정동명이 있을 경우 추가한다. (법정리는 제외)
         // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
         if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
             extraRoadAddr += data.bname;
         }
         // 건물명이 있고, 공동주택일 경우 추가한다.
         if(data.buildingName !== '' && data.apartment === 'Y'){
            extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
         }
         // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
         if(extraRoadAddr !== ''){
             extraRoadAddr = ' (' + extraRoadAddr + ')';
         }

         // 우편번호와 주소 정보를 해당 필드에 넣는다.
         document.getElementById('postcode' + num).value = data.zonecode;
         document.getElementById("roadAddress" + num).value = roadAddr;

         var guideTextBox = document.getElementById("guide");
         // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
         if(data.autoRoadAddress) {
             var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
             guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
             guideTextBox.style.display = 'block';

         } else if(data.autoJibunAddress) {
             var expJibunAddr = data.autoJibunAddress;
             guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
             guideTextBox.style.display = 'block';
         } else {
             guideTextBox.innerHTML = '';
             guideTextBox.style.display = 'none';
         }
     }
 }).open();
}
</script>
<style>
table { margin-left:370px; }
h1 { margin-left:370px; text-align:left; color:#383226;}
th { color:#383226; text-align:left; padding:8px; } 
table td .box {  border-bottom:1px solid #383226; border-right:0; border-left:0; border-top:0; padding:7px;}
table span { font-size:12px; }
table td .buttonBox { margin-left:100px; cursor:pointer; margin-top:10px; }
</style>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="main">
<h1>마이페이지</h1>
<form name="frmJoin" id="frmJoin" action="mypage_proc.mem" method="post" onsubmit="return chkInfo(this);">
<input type="hidden" name="id" value="<%=loginMember.getMl_id() %>" />
<table cellpadding="5">
	<tr>
		<th>아이디<%=img %></th>
		<td><%=loginMember.getMl_id()%></td>
	</tr>
	<tr>
		<th>이름<%=img %></th>
		<td><input type="text" name="name" value="<%=name %>" class="box" /></td>
	</tr>
	<tr>
		<th>비밀번호<%=img %></th>
		<td>
			<input type="password" name="pwd" value="" id="pwd1" class="box"  onchange="testPwd1();" /><br/>
			<span id="chkPwd"></span>
		</td>
	</tr>
	<tr>
		<th>비밀번호 확인<%=img %></th>
		<td>
			<input type="password" name="pwd2" value="" id="pwd2" class="box"  onchange="testPwd2();"/><br/>
			<span id="pwdMsg"></span>
		</td>

	</tr>
	<tr>
		<th>전화번호<%=img %></th>
		<td>
			<select name="p1">
				<option value="010" <%if (p1.equals("010")) { %>selected="selected"<%} %>>010</option>
				<option value="011" <%if (p1.equals("011")) { %>selected="selected"<%} %>>011</option>
				<option value="016" <%if (p1.equals("016")) { %>selected="selected"<%} %>>016</option>
				<option value="019" <%if (p1.equals("019")) { %>selected="selected"<%} %>>019</option>
			</select> -
			<input type="text" name="p2" size="4" maxlength="4" value="<%=p2%>" onkeyup="onlyNumber(this);"/> -
			<input type="text" name="p3" size="4" maxlength="4" value="<%=p3%>" onkeyup="onlyNumber(this);"/>
		</td>
	</tr>
	<tr>
		<th>이메일<%=img %></th>
		<td>
			<input type="text" name="e1"  value="<%=e1%>" class="box"  /> @
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
		<th>생년월일<%=img %></th>
		<td><input type="text" name="birth" class="box"  value="<%=loginMember.getMl_birth()%>" onkeyup="onlyNumber(this);" />(yyyymmdd)</td>
	</tr>
	<tr>
		<th>성별<%=img %></th>
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
			<input type="hidden" name="idxB" id="idxB" value="<%=memberAddrInfoFirst.getMa_idx() %>" />
			<input type="hidden" name="firstAddrChkStatus" id="firstAddrChkStatus" value=""/>
			<input type="radio" name="basicAddr" value="first" <% if(memberAddrInfoFirst.getMa_basic().equals("y")) { %>checked="checked"<%} %> />&nbsp;기본배송지<br />
			<input type="text" name="firstZip"  value="<%=memberAddrInfoFirst.getMa_zip() %>" id="postcode1"placeholder="우편번호" readonly="readonly" />
			<input type="button" onclick="execDaumPostcode('1')" value="우편번호 찾기" /><br />
			<input type="text" name="firstAddr1" value="<%=memberAddrInfoFirst.getMa_addr1() %>"  id="roadAddress1" placeholder="도로명주소" readonly="readonly"/><br />
			<span id="guide" style="color:#999;display:none;font-size:5px;"></span>
			<input type="text" name="firstAddr2" value="<%=memberAddrInfoFirst.getMa_addr2() %>" id="detailAddress1" placeholder="상세주소" />
		</td>
	</tr>

<%
	} else {
%>
<tr>
	<th>주소1</th>
		<td>
			<input type="hidden" name="idxB" id="idxB" value="" />
			<input type="hidden" name="firstAddrChkStatus" id="firstAddrChkStatus" value=""/>
			<% if(memberAddrInfoFirst == null && memberAddrInfoSecond == null) {%>
			<input type="radio" name="basicAddr" value="first" checked="checked" />&nbsp;기본배송지<br />
			<% } else { %>
			<input type="radio" name="basicAddr" value="first" />&nbsp;기본배송지<br />
			<% } %>
			<input type="text" name="firstZip"  id="postcode1" class="firstAddr"  placeholder="우편번호" />
			<input type="button" onclick="execDaumPostcode('1')" value="우편번호 찾기" /><br />
			<input type="text" name="firstAddr1" id="roadAddress1" class="firstAddr"  placeholder="도로명주소" /><br />
			<span id="guide" style="color:#999;display:none;font-size:5px;"></span>
			<input type="text" name="firstAddr2" id="detailAddress1" class="firstAddr"  placeholder="상세주소" />
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
			<input type="hidden" name="secondAddrChkStatus" id="secondAddrChkStatus" value=""/>
			<input type="radio" name="basicAddr" value="second" <% if(memberAddrInfoSecond.getMa_basic().equals("y")) { %>checked="checked"<%} %> />&nbsp;기본배송지<br />
			<input type="text" name="secondZip"  value="<%=memberAddrInfoSecond.getMa_zip() %>" id="postcode2" class="secondAddr"  placeholder="우편번호" />
			<input type="button" onclick="execDaumPostcode('2')" value="우편번호 찾기" /><br />
			<input type="text" name="secondAddr1" value="<%=memberAddrInfoSecond.getMa_addr1() %>"  id="roadAddress2" class="secondAddr"  placeholder="도로명주소" /><br />
			<span id="guide" style="color:#999;display:none;font-size:5px;"></span>
			<input type="text" name="secondAddr2" value="<%=memberAddrInfoSecond.getMa_addr2() %>" id="detailAddress2" class="secondAddr"  placeholder="상세주소" />
		</td>
	</tr>

<%
	} else {
%>
<tr>
	<th>주소2</th>
		<td>
			<input type="hidden" name="idxS" value="" />
			<input type="hidden" name="secondAddrChkStatus" id="secondAddrChkStatus" value=""/>
			<input type="radio" name="basicAddr" value="second" />&nbsp;기본배송지<br />
			<input type="text" name="secondZip"  id="postcode2" class="secondAddr"  placeholder="우편번호" />
			<input type="button" onclick="execDaumPostcode('2')" value="우편번호 찾기" /><br />
			<input type="text" name="secondAddr1" id="roadAddress2" class="secondAddr"  placeholder="도로명주소"><br />
			<span id="guide" style="color:#999;display:none;font-size:5px;"></span>
			<input type="text" name="secondAddr2" id="detailAddress2" class="secondAddr"  placeholder="상세주소" />
		</td>
	</tr>
<%	}	%>
	<tr>
		<td colspan="2">
			<input type="submit" value="수정 하기" class="buttonBox" />
			<input type="button" value="취소" class="buttonBox" onclick="location.href='main.jsp';" />
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>