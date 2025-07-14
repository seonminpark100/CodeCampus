<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<<<<<<< HEAD
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<s:authorize access="isAuthenticated()">
	<s:authentication property="principal" var="principal"/>
</s:authorize>

=======
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
>>>>>>> origin/master
<!-- CDN 방식: Bootstrap, jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>

<<<<<<< HEAD
<!-- <style>
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
</style> -->

<script type="text/javascript">
function disableOtherButtons(clickedId) {
    const buttons = document.querySelectorAll('button');
    buttons.forEach(button => {
        button.disabled = false; // 모든 버튼 활성화
    });
    document.getElementById(clickedId).disabled = true; // 클릭된 버튼 비활성화
}
</script>

<div class="container" align="center">
	<div class="login" >
=======
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
>>>>>>> origin/master
		<c:choose>
		    <c:when test="${empty principal}">
		        <ul class="navbar-nav">
		            <li class="nav-item"><a class="nav-link" href="/myLogin.do">로그인</a></li>
		        </ul>
		    </c:when>
		    <c:otherwise>
<<<<<<< HEAD
		        <ul class="navbar-nav" >
		        	<s:authorize access="isAuthenticated()"><s:authentication property="name"/>님 반갑습니다.</s:authorize>
		            <a class="nav-link" href="#"> 마이페이지 </a>
		            <a class="nav-link" href="/myLogout.do"> 로그아웃 </a>
=======
		        <ul class="navbar-nav">
			        <li class="nav-item"><a class="nav-link" href="/prof/mypage.do">마이페이지</a></li>
		        </ul>
		        <ul class="navbar-nav">
		            <li class="nav-item"><a class="nav-link" href="/myLogout.do">로그아웃</a></li>
		        </ul>
		         <ul class="navbar-nav">
			        <li class="nav-item"><a class="nav-link" href="/prof/index.do">내 강의실</a></li>
>>>>>>> origin/master
		        </ul>
		    </c:otherwise>
	    </c:choose>
	</div>
<<<<<<< HEAD
	<a href="#">
	 	<!-- <img class="img-concert" src="../images/logo.jpeg" /> -->
	</a>
	<br/><br/>
	<div class="btn-group">
		   <button class="btn btn-success" id="button1" onclick="disableOtherButtons('button1'); location.href = '#'">교육현황</button>&nbsp;&nbsp;
		   <button class="btn btn-success" id="button2" onclick="disableOtherButtons('button2'); location.href = '#'">커뮤니티</button>&nbsp;&nbsp;
		   <button class="btn btn-success" id="button3" onclick="disableOtherButtons('button3'); location.href = '#'">학교소개</button>
	</div>
=======
	<a href="/prof/index.do">
	 	<img class="img-concert" src="../images/logo.jpeg" />
	</a>
	<br/><br/>
	
>>>>>>> origin/master
	<br/><br/><br/>
</div>