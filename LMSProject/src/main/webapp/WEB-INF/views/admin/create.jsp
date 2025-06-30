<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>계정 생성</title>
		<script>
		    function validateForm(form) { 
		        if (form.title.value == "") {
		            alert("제목을 입력하세요.");
		            form.title.focus();
		            return false;
		        }
		        if (form.ofile.value == "") {
		            alert("첨부파일은 필수 입력입니다.");
		            return false;
		        }
		    }
		</script>
	</head>
	<body>
		<div class="form-section">
            <h2>새 회원 계정 생성</h2>
            <form action="<c:url value='/admin/create'/>" method="post">
                <div class="form-group">
                    <label for="userId">고유 번호 (ID):</label>
                    <input type="text" id="userId" name="userId" required placeholder="예: u001, i005, a001">
                </div>
                <div class="form-group">
                    <label for="userPw">비밀번호:</label>
                    <input type="password" id="userPw" name="userPw" required placeholder="비밀번호 입력">
                </div>
                <div class="form-group">
                    <label for="userName">이름:</label>
                    <input type="text" id="userName" name="userName" required placeholder="사용자 이름">
                </div>
                <div class="form-group">
                    <label for="userBirthdate">생년월일:</label>
                    <input type="date" id="userBirthdate" name="userBirthdate" required>
                </div>
                <div class="form-group">
                    <label for="userPhoto">사진파일: </label>
                    <input type="file" name="ofile" multiple />
                </div>
                <div class="form-group">
                    <label>권한:</label>
                    <div class="radio-group">
                        <input type="radio" id="auth_user" name="authority" value="1" checked>
						<label for="auth_user">일반 사용자</label>
						<input type="radio" id="auth_instructor" name="authority" value="2">
						<label for="auth_instructor">강사</label>
						<input type="radio" id="auth_admin" name="authority" value="3">
						<label for="auth_admin">관리자</label>
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit">계정 생성</button>
                </div>
            </form>
        </div>
	</body>
</html>