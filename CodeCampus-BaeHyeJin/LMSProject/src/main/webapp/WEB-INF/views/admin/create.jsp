<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>

<html>
<head>
	<meta charset="UTF-8">
	<title>계정 생성</title>
	<style>
	    body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; display: flex; flex-direction: column; align-items: center; min-height: 90vh; }
	    .form-section { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); max-width: 600px; width: 100%; margin: 20px auto; box-sizing: border-box; }
	    h2 { color: #333; text-align: center; margin-bottom: 30px; }
	    .form-group { margin-bottom: 15px; }
	    .form-group label { display: block; margin-bottom: 5px; color: #555; font-weight: bold; }
	    .form-group input[type="text"],
	    .form-group input[type="password"],
	    .form-group input[type="email"],
	    .form-group input[type="tel"],
	    .form-group input[type="date"],
	    .form-group select {
	        width: calc(100% - 22px); /* 패딩 및 보더 고려 */
	        padding: 10px;
	        border: 1px solid #ccc;
	        border-radius: 4px;
	        box-sizing: border-box;
	    }
	    .radio-group label { display: inline-block; margin-right: 15px; font-weight: normal; }
	    .radio-group input[type="radio"] { margin-right: 5px; }
	    button[type="submit"] {
	        background-color: #007bff;
	        color: white;
	        padding: 12px 20px;
	        border: none;
	        border-radius: 4px;
	        cursor: pointer;
	        font-size: 1em;
	        margin-top: 20px;
	        width: 100%;
	        transition: background-color 0.3s ease;
	    }
	    button[type="submit"]:hover { background-color: #0056b3; }
	    .message {
	        background-color: #d4edda;
	        color: #155724;
	        border: 1px solid #c3e6cb;
	        padding: 10px;
	        margin-bottom: 20px;
	        border-radius: 4px;
	        text-align: center;
	    }
	</style>
	<script>
	function validateForm(form) {
		if (form.userId.value.trim() == "") {
			alert("계정(ID)을 입력하세요.");
			form.userId.focus();
			return false;
		}

		if (form.userName.value.trim() == "") {
			alert("이름을 입력하세요.");
			form.userName.focus();
			return false;
		}
		
		var userGenderSelected = false;
		for (var i = 0; i < form.userGender.length; i++) {
            if (form.userGender[i].checked) {
                userGenderSelected = true;
                break;
            }
        }
        if (!userGenderSelected) {
            alert("성별을 선택하세요.");
            return false;
        }
		if (form.userEmail.value.trim() == "") {
			alert("이메일을 입력하세요.");
			form.userEmail.focus();
			return false;
		}
		// ★ userPhonenum 으로 변경
		if (form.userPhonenum.value.trim() == "") {
			alert("연락처를 입력하세요.");
			form.userPhonenum.focus();
			return false;
		}
		// 연락처 패턴 검사 추가 (userPhonenum 으로 변경)
		var phonePattern = /^\d{2,3}-\d{3,4}-\d{4}$/;
		if (!phonePattern.test(form.userPhonenum.value)) {
			alert("유효한 연락처를 입력하세요. 예: 010-1234-5678");
			form.userPhonenum.focus();
			return false;
		}
		if (form.userAddr.value.trim() == "") {
			alert("주소를 입력하세요.");
			form.userAddr.focus();
			return false;
		}
		if (form.userBirthdate.value.trim() == "") {
			alert("생년월일을 입력하세요.");
			form.userBirthdate.focus();
			return false;
		}
		// 권한 선택 검사
		if (form.authority.value.trim() == "") {
			alert("권한을 선택하세요.");
			form.authority.focus();
			return false;
		}
		if (form.majorId.value.trim() == "") {
			alert("학과를 입력하세요.");
			form.majorId.focus();
			return false;
		}
		return true;
	}
	</script>
</head>
<body>
	<div class="form-section">
		<h2>새 계정 생성</h2>
        
		<c:if test="${not empty message}">
			<div class="message"><c:out value="${message}" /></div>
		</c:if>
		<form action="/admin/create" method="post" onsubmit="return validateForm(this);">
			<div class="form-group">
				<label for="userId">계정 (ID):</label>
				<input type="text" id="userId" name="userId" placeholder="사용할 아이디를 입력하세요." required>
			</div>
			<br>
			<div class="initial-password-info">
	            ※ 초기 비밀번호는 **생년월일 8자리** 입니다.<br>
	            (예: 2000년 04월 11일생 → 20000411) <br>생년월일 지정 시 자동 패스워드가 생성 됩니다.
	        </div>
	        <br>
			<div class="form-group">
				<label for="userName">이름:</label>
				<input type="text" id="userName" name="userName" placeholder="이름을 입력하세요." required>
			</div>
			<div class="form-group">
				<label for="userGender">성별:</label>
				<div class="radio-group">
					<label><input type="radio" name="userGender" value="남성" required> 남성</label> <%-- ★ '남' -> '남성'으로 변경 --%>
					<label><input type="radio" name="userGender" value="여성" required> 여성</label> <%-- ★ '여' -> '여성'으로 변경 --%>
				</div>
			</div>
			<div class="form-group">
				<label for="userEmail">이메일:</label>
				<input type="email" id="userEmail" name="userEmail" placeholder="이메일을 입력하세요." required>
			</div>
			<div class="form-group">
				<label for="userPhonenum">연락처:</label> <%-- ★ userPhoneNum -> userPhonenum 으로 변경 --%>
				<input type="tel" id="userPhonenum" name="userPhonenum" placeholder="예: 010-1234-5678" required> <%-- ★ id와 name도 userPhonenum으로 변경 --%>
			</div>
			<div class="form-group">
				<label for="userAddr">주소:</label>
				<input type="text" id="userAddr" name="userAddr" placeholder="주소를 입력하세요." required>
			</div>
			<div class="form-group">
				<label for="userBirthdate">생년월일:</label>
				<input type="date" id="userBirthdate" name="userBirthdate" required>
			</div>
			<div class="form-group">
				<label for="joindate">가입일 (자동 입력):</label>
				<input type="hidden" id="joindate" name="joindate">
				<input type="date" id="joindateDisplay" value="" readonly>
			</div>
			<div class="form-group">
				<label for="authority">권한:</label>
				<select id="authority" name="authority" required>
					<option value="">-- 선택하세요 --</option>
					<option value="ROLE_USER">사용자</option>
					<option value="ROLE_PROF">교수</option>
					<option value="ROLE_ADMIN">관리자</option>
				</select>
			</div>
			<div class="form-group">
    			<label for="majorId">학과:</label>
    			<input type="text" id="majorId" name="majorId" placeholder="학과를 입력하세요." required>
			</div>
			<div class="form-group">
				<label for="enable">계정 상태:</label>
				<select id="enable" name="enable" required>
					<option value="1">활성</option>
					<option value="0">비활성</option>
				</select>
			</div>			
			<button type="submit">계정 생성</button>
			<a href="<c:url value='/admin'/>" class="btn-back">관리자 시스템 페이지로</a>
		</form>
	</div>
	<script>
	// 현재 날짜를 가입일 필드에 자동 설정
	document.addEventListener('DOMContentLoaded', function() {
		var today = new Date();
		var yyyy = today.getFullYear();
		var mm = String(today.getMonth() + 1).padStart(2, '0');
		var dd = String(today.getDate()).padStart(2, '0');
		var formattedDate = yyyy + '-' + mm + '-' + dd;
		document.getElementById('joindate').value = formattedDate;
		document.getElementById('joindateDisplay').value = formattedDate;

		<c:if test="${not empty message}">
			alert('${message}');
		</c:if>
	});
	</script>
</body>
</html>