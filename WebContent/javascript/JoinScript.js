// 회원가입 유효성 검사
function chkData(chk) {
	var uid = chk.uid.value;
	var name = chk.name.value;
	var pwd = chk.pwd.value;
	var pwd2 = chk.pwd2.value;
	var p1 = chk.p1.value;
	var p2 = chk.p2.value;
	var p3 = chk.p3.value;
	var e1 = chk.e1.value;
	var e2 = chk.e1.value;
	var year = chk.year.value;
	var month = chk.month.value;
	var day = chk.day.value;
//	var gender = chk.gender.value;
	
	if (uid == "") {
		alert("아이디를 입력해주세요.");
		chk.uid.focus();
		return false;
	}
		if (name == "") {
		alert("비밀번호를 입력해주세요.");
		chk.name.focus();
		return false;
	}
	if (pwd == "") {
		alert("비밀번호를 입력해주세요.");
		chk.pwd.focus();
		return false;
	}
	if (pwd2 == "") {
		alert("비밀번호 확인을 입력해주세요.");
		chk.pwd2.focus();
		return false;
	}
	if (p2 == "") {
		alert("전화번호를 입력해주세요.");
		chk.p2.focus();
		return false;
	}
	if (p2 == "") {
		alert("전화번호를 입력해주세요.");
		chk.p2.focus();
		return false;
	}
	if (e1 == "") {
		alert("이메일을 입력해주세요.");
		chk.e1.focus();
		return false;
	}
	var checkbox = false;
	for (var i = 0; document.frmJoin.agrEmail.length ; i++) {
		if (agrEmail[i].checked) {
			checkbox = true;
		}	
	}
	if (!checkbox) {
		alert("이메일 수신 동의를 체크해주세요.");
	}
	if (!e2.selectedIndex < 1) {
		alert("이메일 도메인을 선택해주세요.");
		e1.options[0].focus();
		return false;
	}
	
	if (frmJoin.e2.value == 'none') {	
		alert("이메일 도메인을 선택해주세요.");
//		e2.options[0].focus();
		return false;
	}

	if (year == "") {
		alert("생년월일을 입력해주세요.");
		chk.pwd.focus();
		return false;
	}
	if (month == "") {
		alert("생년월일을 입력해주세요.");
		chk.month.focus();
		return false;
	}
	if (day == "") {
		alert("생년월일을 입력해주세요.");
		chk.day.focus();
		return false;
	}
	var isChk = document.getElementsByName('gender'); 
	var chk = null;
	for (var i = 0; i < isChk.length ; i++ ) {
		if (isChk[i].checked == true)	{
			chk = isChk[i].value;
		}
	}
	if (chk == null) {
		alert("성별을 체크해주세요.");
		return false;
	} 
}

