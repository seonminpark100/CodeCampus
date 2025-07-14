<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>로그인</title>
		<script>
			function validateForm(form) {
				if(!form.id.value){
					alert("아이디를 입력하세요.");
					return false;
				}
				if(form.pass.value == ""){
					alert("패스워드를 입력하세요.");
					return false;
				}
			}
		</script>
	</head>
	<body>
		<jsp:include page="../Common/Link.jsp"></jsp:include>
		<div class="container">
			<form action="" method="post" name="loginFrm" onsubmit="return validateForm(this)">
				아이디: &nbsp;&nbsp;&nbsp;<input type="text" name="id"/><br/>
				패스워드: <input type="password" name="pass"/><br/>
				<input type="submit" value="로그인하기"/>
			</form>
		</div>
		
		<jsp:include page="../Common/Bottom.jsp"></jsp:include>
	</body>
</html>