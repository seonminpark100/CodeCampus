<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>  
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>OO대학교 eCampus</title>
		<style type="text/css">
		.bl-left { background-color:red; position:relative; float:left; width:50%; height:100px; display: flex; justify-content: center; align-items: center; }
		.bl-right{ background-color:blue; position:relative; float:right; width:50%; height:100px; display: flex; justify-content: center; align-items: center; }
		</style>
	</head>
	<body>
	<div class="container">
	<%@ include file = "top.jsp" %>
		ADMIN or PROF 권한이 있어야 접근할 수 있습니다. <br/>
		
		<!-- 로그인을 통해 '인증'되었다면 이부분이 출력된다.  -->
		<s:authorize access="isAuthenticated()">
			<!-- name속성을 통해 로그인 아이디를 출력한다. -->
			로그인 아이디 : <s:authentication property="name"/><br/>
		</s:authorize>
	
		<div class="container">
				<div class="bl-left">
		            <span class="bluelight">
		                <a href="#">일정 or 맵</a>
		            </span>
		        </div>
		        <div class="bl-right">
		            <span class="bluelight">
		                <h2>Quick Menu</h2>
		                <a href="/prof/noticeboard.do"><button>공지사항</button></a>
		                <button>메뉴2</button>
		                <button>메뉴3</button>
		                <button>메뉴4</button>
		                <button>메뉴5</button>
		                <button>메뉴6</button>
		            </span>
		        </div>
			</div>
		</div>
		
	</body>
</html>