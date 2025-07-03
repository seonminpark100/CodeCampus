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
		if (form.userEmail.value.trim() == "") {
			alert("이메일을 입력하세요.");
			form.userEmail.focus();
			return false;
		}
		if (form.userPhoneNum.value.trim() == "") {
			alert("연락처를 입력하세요.");
			form.userPhoneNum.focus();
			return false;
		}
		// 연락처 패턴 검사 추가
		var phonePattern = /^\d{2,3}-\d{3,4}-\d{4}$/;
		if (!phonePattern.test(form.userPhoneNum.value)) {
			alert("유효한 연락처를 입력하세요. 예: 010-1234-5678");
			form.userPhoneNum.focus();
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
		if (form.majorId.value.trim() == "") {
			alert("학과를 입력하세요.");
			form.majorId.focus();
			return false;
		}
		if (!form.userGender.value) {
			alert("성별을 선택하세요.");
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
		<form action="/admin/create" method="post" enctype="multipart/form-data" onsubmit="return validateForm(this);">
			<div class="form-group">
				<label for="userId">계정 (ID):</label>
				<input type="text" id="userId" name="userId" placeholder="사용할 아이디를 입력하세요." required>
			</div>
			<div class="form-group">
				<label for="userName">이름:</label>
				<input type="text" id="userName" name="userName" placeholder="이름을 입력하세요." required>
			</div>
			<div class="form-group">
				<label for="userGender">성별:</label>
				<div class="radio-group">
					<label><input type="radio" name="userGender" value="남" required> 남</label>
					<label><input type="radio" name="userGender" value="여" required> 여</label>
				</div>
			</div>
			<div class="form-group">
				<label for="userEmail">이메일:</label>
				<input type="email" id="userEmail" name="userEmail" placeholder="이메일을 입력하세요." required>
			</div>
			<div class="form-group">
				<label for="userPhoneNum">연락처:</label>
				<input type="tel" id="userPhoneNum" name="userPhoneNum" placeholder="예: 010-1234-5678" required>
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
			<div class="form-group">
				<label for="ofile">사진파일:</label>
				<input type="file" name="ofile" /> 
			</div>
			<button type="submit">계정 생성</button>
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
