<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>OO대학교 eCampus</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" 
			rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" 
			crossorigin="anonymous">
	    
	</head>
	<body class="m-5" align="center">
	<div class="container" align="center">
	
	
	<%@ include file = "../top.jsp" %>
		<!-- div의 테두리에 두께, 라운딩, 색깔 등을 지정 -->
		<div style="width:600px;" class="border border-2 border-primary rounded p-5" align="center">
		<!-- 로그인 아이디가 없는경우 로그인 폼을 출력한다. -->
		<c:if test="${empty user_id }" var="loginResult">
			<c:if test="${param.error != null}">
			<p>
				Login Error! <br />
				${errorMsg}
			</p>
			</c:if>
			
			<!-- 시큐리티 설정파일에 지정된 요청명으로 action 속성값 기술 -->
			<form action="/myLoginAction.do" method="post">
				<!-- 아이디와 비밀번호도 설정파일에 지정된 값으로 속성값을 기술한다. -->
				<div class="form-floating mb-3 mt-3">
					<input type="text" class="form-control" id="user_id" 
						placeholder="Enter email" name="my_id">
					<label for="user_id">아이디</label>
				</div>	
				<div class="form-floating mt-3 mb-3">
					<input type="password" class="form-control" id="user_pwd" 
						placeholder="Enter password" name="my_pass">
					<label for="user_pwd">비밀번호</label>
				</div>	
				<div class="d-grid">
					<button type="submit" class="btn btn-primary btn-block">
						Submit</button>
				</div>
			</form>
			</div>
		</c:if>
		
		<!-- 로그인 되었을때 출력되는 부분. 로그인 아이디와 로그아웃 버튼 출력. -->
		<c:if test="${not loginResult }">
			 ${user_id } 님, 좋은 아침입니다. <br />
			<a href="/">Root</a>
			<a href="/myLogout.do">Logout</a>	
		</c:if>	
		</div>
		
	</body>
</html>
