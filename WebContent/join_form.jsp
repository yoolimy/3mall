<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%
String user_id = (String)session.getAttribute("uid");
if (user_id != null) {
	out.println("<script>");
	out.println("history.back();");
	out.println("</script>");
}
// 생년월일 선택 작업
Calendar sdate = Calendar.getInstance();
int year, month, date, cYear;
cYear = sdate.get(Calendar.YEAR);			// 올해 연도
year = sdate.get(Calendar.YEAR);		// 올해 연도
month = sdate.get(Calendar.MONTH) + 1;	// 현재 월
date = sdate.get(Calendar.DATE);		// 현재 일

// asterisk 이미지 삽입
String img = "&nbsp;<img src=\"images/asterisk.png\" width=\"5\" height=\"5\" align=\"absmiddle\" />";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.fontRed { color:red; font-weight:bold; }
.fontBlue { color:blue; font-weight:bold; }
</style>
<script src="jquery-3.5.1.js"></script>
<script src = "javascript/JoinScript.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>

// 우편번호 찾기
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
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
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("roadAddress").value = roadAddr;

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

// 아이디 중복 확인
function chkDupId(){
	var uid = $("#uid").val();
	if (uid.length >= 4) {
		$.ajax({
			type : "POST", 			// 데이터 전송방법
			url : "/3mall/chkID", 	// 중복체크를 위해 이동할 URL(컨트롤러)
			data : {"uid":uid}, 	// url로 지정한 곳으로 보낼 데이터
			success : function(chkRst) {
			// 함수 실행시 url로 지정한 곳에서 작업한 결과를 chkRst로 받아 옴
				var msg = "";
				if(chkRst == 0) {
					msg = "<span class='fontBlue'>사용하실 수 있는 ID입니다.</span>";
					$("#idChk").val("Y");
				} else {
					msg = "<span class='fontRed'>이미 사용중 인 ID입니다.</span>";
					$("#idChk").val("N");
				}
				document.getElementById("idMsg").innerHTML = msg;
			}
		});
	}
}
function onlyNumber(obj) {
	if(isNaN(obj.value)) {
		obj.value = "";
	}
}
</script>
</head>
<body>
<h2>회원가입 폼</h2>
<form name="frmJoin" action="join_proc.mem" method="post" onsubmit="return chkData(this);">
<input type="hidden" name="idChk" id="idChk" value="N" />
<table cellpadding="5" border="1">
	<tr>
		<th>아이디<%=img %></th>
		<td><input type="text" name="uid" id="uid" onkeyup="chkDupId();" />
	<br /><span id="idMsg">아이디는 4~20자 이내의 영문, 숫자 조합으로 입력하세요.</span></td>
	</tr>
	<tr>
		<th>이름<%=img %></th>
		<td><input type="text" name="name" /></td>
	</tr>
	<tr>
		<th>비밀번호<%=img %></th>
		<td><input type="password" name="pwd" id="pwd1" onchange="testPwd1();" /><br />
			<span id="chkPwd">비밀번호는 6~20자 이내의 영문, 숫자 조합으로 입력하세요.</span>
		</td>
	</tr>
	<tr>
		<th>비밀번호 확인<%=img %></th>
		<td>
			<input type="password" name="pwd2" id="pwd2" onchange="testPwd2();"/><br />
			<span id="pwdMsg"></span>
		</td>
	</tr>
	<tr>
		<th>전화번호<%=img %></th>
		<td>
			<select name="p1">
				<option value="010">010</option>
				<option value="011">011</option>
				<option value="016">016</option>
				<option value="019">019</option>
			</select> -
			<input type="text" name="p2" size="4" maxlength="4" onkeyup="onlyNumber(this);" /> -
			<input type="text" name="p3" size="4" maxlength="4" onkeyup="onlyNumber(this);" />
		</td>
	</tr>
	<tr>
		<th>이메일<%=img %></th>
		<td>
			<input type="text" name="e1" /> @
			<select name="e2">
				<option selected="selected">도메인 선택</option>
				<option value="naver.com">네이버</option>
				<option value="gmail.com">지메일</option>
				<option value="hanmail.net">한메일</option>
				<option value="nate.com">네이트</option>
			</select>
			<input type="checkbox" value="y" name="agrEmail" /> 메일 수신 동의 
		</td>
	</tr>
	<tr>
		<th>생년월일<%=img %></th>
		<td>
			<select name="year" id="year" >
			<% for (int i = 1930 ; i <= (cYear + 1) ; i++) { %>
				<option value="<%=i %>" <% if (year == i) { %>selected="selected"<% } %>><%=i %></option>
			<% } %>
			</select>
			<select name="month" >
			<% 
				for (int i = 1 ; i <= 12 ; i++) { 
					String m = String.format("%02d", i );
					
			%>
					<option value="<%=m %>" <% if (month == i) { %>selected="selected"<% } %>><%=m %></option>
			<% 
					
				}			
			%>
			</select>
			<select name="day" >
			<% for (int i = 1 ; i <= 31 ; i++) { 
					String d = String.format("%02d", i );
			%>
					<option value="<%=d %>" <% if (date == i) { %>selected="selected"<% } %>><%=d %></option>
			<% } %>
			</select>
		</td>
	</tr>
	<tr>
		<th>성별<%=img %></th>
		<td>
			<input type="radio" name="gender" value="M" />남
			<input type="radio" name="gender" value="F" />여
		</td>
	</tr>
	<tr>
		<th>주소1</th>
		<td>
			<input type="text" name="zip" id="postcode" placeholder="우편번호" />
			<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기" />
			<input type="radio" name="basicAddr" value="first" checked="checked"/>&nbsp;기본배송지<br />
			<input type="text" name="addr1" id="roadAddress" placeholder="도로명주소" /><br />
			<span id="guide" style="color:#999;display:none;font-size:5px;"></span>
			<input type="text" name="addr2" id="detailAddress" placeholder="상세주소" />
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="submit" value="회원 가입" />
			<input type="reset" value="취소" onclick="location.href='history.back()';" />
		</td>
	</tr>
</table>
</form>
</body>
</html>