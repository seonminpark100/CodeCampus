<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login 화면</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" 
			rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" 
			crossorigin="anonymous">
		
		<style>
	      .img-concert {
	        width: 100px;
	        height: 100px;
	      }
	      .btn-group {
	     	width: 300px;
	        height: 50px;
	      }
	      .login {
	      	float: right;
	      }
	    </style>
	    
	    
	    <script type="text/javascript">
	    function disableOtherButtons(clickedId) {
	        const buttons = document.querySelectorAll('button');
	        buttons.forEach(button => {
	            button.disabled = false; // 모든 버튼 활성화
	        });
	        document.getElementById(clickedId).disabled = true; // 클릭된 버튼 비활성화
	    }
	    </script>
	    
	</head>
	<body class="m-5">
	<div class="container" align="center">
			<div class="login">
				<c:choose>
				    <c:when test="${empty principal}">  <!-- "$ 띄어쓰기 하면 절대안됨 -->
				        <ul class="navbar-nav">
				            <li class="nav-item"><a class="nav-link" href="/myLogin.do">로그인</a></li>
				        </ul>
				    </c:when>
				    <c:otherwise>
				        <ul class="navbar-nav">
				            <li class="nav-item"><a class="nav-link" href="/myLogout.do">로그아웃</a></li>
				        </ul>
				    </c:otherwise>
			    </c:choose>
			</div>
			<a href="/">
			 	<img class="img-concert" src="images/logo.jpeg" />
			</a>
			<br/><br/>
			<div class="btn-group">
				   <button class="btn btn-success" id="button1" onclick="disableOtherButtons('button1'); location.href = '#'">교육현황</button>&nbsp;&nbsp;
				   <button class="btn btn-success" id="button2" onclick="disableOtherButtons('button2'); location.href = '#'">커뮤니티</button>&nbsp;&nbsp;
				   <button class="btn btn-success" id="button3" onclick="disableOtherButtons('button3'); location.href = '#'">학교소개</button>
			</div>
			<br/><br/><br/>
		</div>
		<!-- div의 테두리에 두께, 라운딩, 색깔 등을 지정 -->
		<div style="width:600px;" class="border border-2 border-success rounded p-5" align="center">
		<!-- 로그인 아이디가 없는경우 로그인 폼을 출력한다. -->
		<c:if test="${empty user_id }" var="loginResult">
			<!-- 
			파라미터로 전달된 error가 있는 경우 메세지를 출력한다. 
			failureUrl 혹은 failureHandler 메서드를 비활성화 해둔 경우에만
			이 부분을 사용할 수 있다. 즉 로그인에 실패한 경우 현재 페이지로 
			error 라는 파라미터가 전달된다. 
			-->
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
