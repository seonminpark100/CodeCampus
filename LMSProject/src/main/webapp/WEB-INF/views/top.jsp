<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<<<<<<< HEAD
=======
<!-- CDN 방식: Bootstrap, jQuery -->
>>>>>>> origin/master
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>

<style>
<<<<<<< HEAD

 .login-buttons { /* 로그인/로그아웃 버튼을 위한 새로운 스타일 */
    float: right;
    margin-right: 20px; /* 우측 여백 추가 */
    margin-top: 10px; /* 상단 여백 추가 (필요시 조절) */
 }
 .login-buttons .btn { /* 로그인/로그아웃 버튼 간 간격 조절 */
    margin-left: 5px;
=======
 .img-concert {
   width: 100px;
   height: 100px;
 }
 .login {
 	float: right;
>>>>>>> origin/master
 }
</style>

<script type="text/javascript">
function disableOtherButtons(clickedId) {
<<<<<<< HEAD
    const buttons = document.querySelectorAll('.btn-group button'); // .btn-group 내의 버튼만 선택
=======
    const buttons = document.querySelectorAll('button');
>>>>>>> origin/master
    buttons.forEach(button => {
        button.disabled = false; // 모든 버튼 활성화
    });
    document.getElementById(clickedId).disabled = true; // 클릭된 버튼 비활성화
}
</script>
<div class="container" align="center">
<<<<<<< HEAD
	<div class="login-buttons"> 
		<c:choose>
		    <c:when test="${empty principal}">
		        <a class="btn btn-primary" href="/myLogin.do">로그인</a> 
		    </c:when>
		    <c:otherwise>
		        <a class="btn btn-secondary" href="#">마이페이지</a> 
		        <a class="btn btn-danger" href="/myLogout.do">로그아웃</a> 
		    </c:otherwise>
	    </c:choose>
	</div>
	<a href="#">
	 	</a>
	<br/><br/>
	<div class="btn-group">
		   <button class="btn btn-success" id="button1" onclick="disableOtherButtons('button1'); location.href = '#'">교육현황</button>&nbsp;&nbsp;
		   <button class="btn btn-success" id="button2" onclick="disableOtherButtons('button2'); location.href = '#'">커뮤니티</button>&nbsp;&nbsp;
		   <button class="btn btn-success" id="button3" onclick="disableOtherButtons('button3'); location.href = '#'">학교소개</button>
	</div>
	<br/><br/><br/>
=======
	<div class="login">
		<c:choose>
		    <c:when test="${empty principal}">
		        <ul class="navbar-nav">
		            <li class="nav-item"><a class="nav-link" href="/myLogin.do">로그인</a></li>
		        </ul>
		        <ul class="navbar-nav">
			        <li class="nav-item"><a class="nav-link" href="/prof/index.do">내 강의실</a></li>
		        </ul>
		    </c:when>
		    <c:otherwise>
		        <ul class="navbar-nav">
			        <li class="nav-item"><a class="nav-link" href="/mypage.do">마이페이지</a></li>
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
	<a href="/">
	 	<img class="img-concert" src="images/logo.jpeg" />
	</a>
>>>>>>> origin/master
</div>