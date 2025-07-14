<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!DOCTYPE html>
<html>
	<body>
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
			<div class="container-fluid">
				<c:choose>
				    <c:when test="${not empty principal}">  <!-- "$ 띄어쓰기 하면 절대안됨 -->
				        <a class="navbar-brand" href="/user/index.do"><img src="/images/image.PNG" alt="hello" style="height: 50px; width: 50px;" /></a>
						<div class="collapse navbar-collapse" id="navbarNavDropdown">
							<ul class="navbar-nav">
								<li class="nav-item"><a class="nav-link" href="#">교육현황</a></li>
								<li class="nav-item"><a class="nav-link" href="/user/LMSBoard/lmsBoard.do">커뮤니티</a>
								</li>
								<li class="nav-item"><a class="nav-link" href="#">소개</a>
								</li>
							</ul>
						</div>
						<div class="d-flex">
					        <button class="btn btn-success" type="button" onclick="location.href='/user/myPage/myPage.do'">마이페이지</button>
					        <button class="btn btn-success" type="button" onclick="location.href='/myLogout.do'">로그아웃</button>
				        </div>
				    </c:when>
				    <c:otherwise>
				    	<a class="navbar-brand" href="/user/index.do"><img src="../images/image.PNG" alt="hello" style="height: 50px; width: 50px;" /></a>
				    	<button class="btn btn-success" type="button" onclick="location.href='/myLogin.do'">로그인</button>
				    </c:otherwise>
			    </c:choose>
			</div>
		</nav>
	</body>
</html>