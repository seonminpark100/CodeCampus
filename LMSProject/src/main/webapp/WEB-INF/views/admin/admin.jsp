<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원 관리 - CodeCampus 관리자</title>
</head>
<body>
	<div class="container">
		<div class="login">
			<c:choose>
				<c:when test="${empty principal}">
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
		<div>
			<h3>관리자 시스템</h3>
			<ul>
				<li><a href="<c:url value='/admin/accountedit/list.do'/>"
					class="btn btn-primary btn-block mb-2 active" style="width: 100%;">회원
						관리 화면</a></li>
				<li><a href="<c:url value='/admin/create'/>"
					class="btn btn-primary btn-block mb-2 active" style="width: 100%;">계정
						생성</a></li>
				<li><a href="#" class="btn btn-secondary btn-block mb-2"
					style="width: 100%;">전체 강의/과목 관리</a></li>
				<li><a href="#" class="btn btn-secondary btn-block mb-2"
					style="width: 100%;">공지사항 등록/수정/삭제</a></li>
				<li><a href="#" class="btn btn-secondary btn-block mb-2"
					style="width: 100%;">통계 대시보드</a></li>
				<li><a href="#" class="btn btn-secondary btn-block mb-2"
					style="width: 100%;">게시판/댓글 건의사항 통계</a></li>
			</ul>
		</div>
	</div>
</body>
</html>