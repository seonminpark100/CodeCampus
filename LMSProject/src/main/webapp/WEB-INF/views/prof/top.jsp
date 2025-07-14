<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!-- CDN 방식: Bootstrap, jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>

<style>
 .img-concert {
   width: 100px;
   height: 100px;
 }

 .login {
 	float: right;
 }
</style>

<div class="container" align="center">
	<div class="login">
		<c:choose>
		    <c:when test="${empty principal}">
		        <ul class="navbar-nav">
		            <li class="nav-item"><a class="nav-link" href="/myLogin.do">로그인</a></li>
		        </ul>
		    </c:when>
		    <c:otherwise>
		        <ul class="navbar-nav">
			        <li class="nav-item"><a class="nav-link" href="/prof/mypage.do">마이페이지</a></li>
		        </ul>
		        <ul class="navbar-nav">
		            <li class="nav-item"><a class="nav-link" href="/myLogout.do">로그아웃</a></li>
		        </ul>
		         <ul class="navbar-nav">
			        <li class="nav-item"><a class="nav-link" href="/prof/index.do">내 강의실</a></li>
		        </ul>
		    </c:otherwise>
	    </c:choose>
	</div>
	<a href="/prof/index.do">
	 	<img class="img-concert" src="../images/logo.jpeg" />
	</a>
	<br/><br/>
	
	<br/><br/><br/>
</div>