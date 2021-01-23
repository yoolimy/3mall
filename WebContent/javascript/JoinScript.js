// 회원가입 유효성 검사
function chkData(chk) {
	var uid = chk.uid.value;
	var name = chk.name.value;
	var pwd = chk.pwd.value;
	var pwd2 = chk.pwd2.value;
	var num = pwd.search(/[0-9]/g);
	var eng = pwd.search(/[a-z]/ig);
	var p1 = chk.p1.value;
	var p2 = chk.p2.value;
	var p3 = chk.p3.value;
	var e1 = chk.e1.value;
	var e2 = chk.e2.value;
	var box1 = document.frmJoin.agrEmail.checked;
	var year = chk.year.value;
	var month = chk.month.value;
	var day = chk.day.value;
//	var gender = chk.gender.value;
	
	if (uid == "") {
		alert("아이디를 입력해주세요.");
		chk.uid.focus();
		return false;
	}
	if (uid.length < 4 || uid.length > 20) {
		document.getElementById("idMsg").innerHTML = "<span class='fontRed'>아이디는 4~20자 이내의 영문, 숫자 조합으로 입력하세요.</span>";
	}
	if (name == "") {
		alert("이름을 입력해주세요.");
		chk.name.focus();
		return false;
	}
	if (pwd == "") {
		alert("비밀번호를 입력해주세요.");
		chk.pwd.focus();
		return false;
	}
    if(pwd.length < 6 || pwd.length > 20) {
        alert('비밀번호는 6글자 이상, 20글자 미만이어야 합니다.');
        return false;
    } else if(num < 1 && eng < 1) {
    	alert('비밀번호는 영문, 숫자를 혼합하여 입력해주세요.');
        return false;
    } 
	if (pwd2 == "") {
		alert("비밀번호 확인을 입력해주세요.");
		chk.pwd2.focus();
		return false;
	}
	if (p1 == "") {
		alert("전화번호 앞자리를 선택해주세요.");
		chk.p1.focus();
		return false;
	}
	if (p2 == "") {
		alert("전화번호를 입력해주세요.");
		chk.p2.focus();
		return false;
	}
	if (p3 == "") {
		alert("전화번호를 입력해주세요.");
		chk.p3.focus();
		return false;
	}
	if (e1 == "") {
		alert("이메일을 입력해주세요.");
		chk.e1.focus();
		return false;
	}
	if (e2 == "") {
		alert("이메일 도메인을 선택해주세요.");
		chk.e2.focus();
		return false;
	}
	if (!box1) {
		alert("메일 수신 동의를 체크해주세요.");
		return false;
	}

	if (year == "") {
		alert("생년월일을 입력해주세요.");
		chk.year.focus();
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
	var genderChk = null;
	for (var i = 0; i < isChk.length ; i++ ) {
		if (isChk[i].checked == true)	{
			genderChk = isChk[i].value;
		}
	}
	if (genderChk == null) {
		alert("성별을 체크해주세요.");
		return false;
	} 
}

// 비밀번호 확인1
function testPwd1() {
    var p1 = document.getElementById('pwd1').value;
    var p2 = document.getElementById('pwd2').value;
    var num = p1.search(/[0-9]/g);
    var eng = p1.search(/[a-z]/ig);
    if(p1.length < 6 || p1.length > 20) {
    	document.getElementById("chkPwd").innerHTML = "<span class='fontRed'>비밀번호는 4~20자 이내로 입력하세요.</span>";
        return false;
    } else if(num < 1 && eng < 1) {
    	document.getElementById("chkPwd").innerHTML = "<span class='fontRed'>비밀번호는 영문, 숫자 조합으로 입력하세요.</span>";
        return false;
    } else {
   	 	document.getElementById("chkPwd").innerHTML = "<span class='fontBlue'>OK</span>";
   	 	return true;
    	
    }
}

function testPwd2() {
    var p1 = document.getElementById('pwd1').value;
    var p2 = document.getElementById('pwd2').value;
    if( p1 != p2 ) {
    	document.getElementById('pwdMsg').innerHTML="<span class='fontRed'>비밀번호가 일치 하지 않습니다</span>";
      return false;
    } else {
    	document.getElementById('pwdMsg').innerHTML="<span class='fontBlue'>비밀번호가 일치합니다</span>";
      return true;
    }
}


