<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!-- CDN 방식: Bootstrap, jQuery -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<style>
 .img-concert {
   width: 100px;
   height: 100px;
 }
 .btn-group {
	width: 300px; /* 기존 너비 유지 */
   height: 50px;
 }

 /* 새로운 헤더 레이아웃을 위한 스타일 */
 .top-header-container {
    display: flex; /* Flexbox 사용 */
    justify-content: space-between; /* 요소들을 양 끝으로 정렬 */
    align-items: center; /* 세로 중앙 정렬 */
    padding: 10px 20px; /* 패딩 추가 */
    width: 90%; /* 메인 컨테이너와 동일한 너비 유지 */
    margin: 0 auto; /* 중앙 정렬 */
 }

 .logo-area {
    text-align: center; /* 로고 이미지 가운데 정렬 */
 }

 .login-area {
    display: flex; /* 로그인 버튼들을 가로로 정렬 */
    gap: 10px; /* 버튼 사이 간격 */
 }

 /* 로그인/로그아웃/마이페이지 버튼 스타일 (이미지 배경) */
 .login-button {
    display: inline-block; /* 링크를 블록처럼 만듦 */
    width: 80px; /* 버튼의 너비 (이미지 크기에 따라 조절) */
    height: 30px; /* 버튼의 높이 (이미지 크기에 따라 조절) */
    text-indent: -9999px; /* 텍스트 숨김 */
    overflow: hidden; /* 텍스트 숨긴 후 넘치는 부분 감춤 */
    background-size: contain; /* 배경 이미지를 요소에 맞춤 */
    background-repeat: no-repeat;
    background-position: center;
    border: none; /* 기본 테두리 제거 */
    cursor: pointer;
    line-height: 30px; /* 텍스트가 보인다면 세로 중앙 정렬 */
    text-align: center; /* 텍스트가 보인다면 가로 중앙 정렬 */
 }


 .main-nav-buttons .btn {
    min-width: 90px; /* 버튼 최소 너비 확보 */
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
<div class="top-header-container">
	<div class="login">
		<c:choose>
		    <c:when test="${empty principal}">
		        <ul class="navbar-nav">
		            <li class="nav-item"><a class="nav-link" href="/myLogin.do">로그인</a></li>
		        </ul>
		    </c:when>
		    <c:otherwise>
		        <ul class="navbar-nav">
			        <li class="nav-item"><a class="nav-link" href="#">마이페이지</a></li>
		        </ul>
		        <ul class="navbar-nav">
		            <li class="nav-item"><a class="nav-link" href="/myLogout.do">로그아웃</a></li>
		        </ul>
		    </c:otherwise>
	    </c:choose>
	</div>
	<a href="#">
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