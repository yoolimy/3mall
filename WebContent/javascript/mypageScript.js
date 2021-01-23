function onlyNumber(obj) {
	if(isNaN(obj.value)) {
		obj.value = "";
	}
}

function chkInfo(chk) {
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
	var birth = chk.birth.value;
	var basicAddr = chk.basicAddr.value;
	var idxB = chk.idxB.value;
	var firstZip = chk.firstZip.value;
	var firstAddr1 = chk.firstAddr1.value;
	var firstAddr2 = chk.firstAddr2.value;
	var idxS = chk.idxS.value;
	var secondZip = chk.secondZip.value;
	var secondAddr1 = chk.secondAddr1.value;
	var secondAddr2 = chk.secondAddr2.value;
	
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
	if (pwd != pwd2) {
		alert("비밀번호가 같지 않습니다.");
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

	if (birth == "") {
		alert("생년월일을 입력해주세요.");
		chk.birth.focus();
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
	
	// 주소 1 유효성 검증 및 상태값 지정
//	console.log(firstZip != "");
//	console.log(firstAddr2 == "");
//	console.log(firstAddr2 != "");
//	console.log(firstZip == "");
	if (firstZip != "") {
		if (firstAddr2 == "") {
			alert("주소1의 상세 주소를 입력해주세요.");
			chk.firstAddr2.focus();
			return false;
		} 
	}
	if (firstAddr2 != "") {
		if (firstZip == "") {
			alert("주소1의 우편번호 및 도로명주소를 확인해주세요.");
			chk.firstZip.focus();
			return false;
		}
	}
	if (firstZip == "" || firstAddr1 == "" || firstAddr2 == "") {
		if (idxB != "") {
			chk.firstAddrChkStatus.value = "delete";
		} else {
			chk.firstAddrChkStatus.value = "none";
		}
	} else {
		if (idxB != "") {
			chk.firstAddrChkStatus.value = "update"
		} else {
			chk.firstAddrChkStatus.value = "insert"
		}
	}  
	// 주소 2 유효성 검증 및 상태값 지정
	if (secondZip != "") {
		if (secondAddr2 == "") {
			alert("주소2의 상세 주소를 입력해주세요.");
			chk.secondAddr2.focus();
			return false;
		} 
	}
	if (secondAddr2 != "") {
		if (secondZip == "") {
			alert("주소2의 우편번호 및 도로명주소를 확인해주세요.");
			chk.secondZip.focus();
			return false;
		}
	}
	if (secondZip == "" || secondAddr1 == "" || secondAddr2 == "") {
		if (idxS!= "") {
			chk.secondAddrChkStatus.value = "delete";
		} else {
			chk.secondAddrChkStatus.value = "none";
		}
	} else {
		if (idxS != "") {
			chk.secondAddrChkStatus.value = "update"
		} else {
			chk.secondAddrChkStatus.value = "insert"
		}
	}
}


//비밀번호 확인1
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

//비밀번호 확인2
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

