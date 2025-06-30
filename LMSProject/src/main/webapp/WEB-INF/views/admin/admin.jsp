<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원 관리 - CodeCampus 관리자</title>
</head>
<body>
<div class="container">
    <div>
        <h3>관리자 시스템</h3>
        <ul>
            <li><a href="<c:url value='/accountedit/list.do'/>" class="btn btn-primary btn-block mb-2 active" style="width: 100%;">회원 관리 화면</a></li>
            <li><a href="<c:url value='/create/index.do'/>" class="btn btn-primary btn-block mb-2 active" style="width: 100%;">계정 생성</a></li>         
            <li><a href="#" class="btn btn-secondary btn-block mb-2" style="width: 100%;">전체 강의/과목 관리</a></li>
            <li><a href="#" class="btn btn-secondary btn-block mb-2" style="width: 100%;">공지사항 등록/수정/삭제</a></li>
            <li><a href="#" class="btn btn-secondary btn-block mb-2" style="width: 100%;">통계 대시보드</a></li>
            <li><a href="#" class="btn btn-secondary btn-block mb-2" style="width: 100%;">게시판/댓글 건의사항 통계</a></li>
        </ul>
    </div>
    <div class="main-content">
        <div class="page-header">
            <h1>회원 관리</h1>
            <p>회원 정보를 직접 입력하고, 관리자 또는 강사 권한을 부여할 수 있습니다.</p>
        </div>
        <c:if test="${not empty message}">
            <div class="messages ${messageType}">
                <p>${message}</p>
            </div>
        </c:if>
        
        <div class="user-list-section">
            <h2>현재 사용자 목록</h2>
            <table class="user-list-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>이름</th>
                        <th>생년월일</th>
                        <th>가입일</th>
                        <th>권한</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty users}">
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td>${user.userId}</td>
                                    <td>${user.userName}</td>
                                    <td>${user.userBirthdate}</td>
                                    <td>${user.joinDate}</td>
                                    <td>
                                        <c:if test="${user.authority == 'ROLE_USER'}">일반 사용자</c:if>
                                        <c:if test="${user.authority == 'ROLE_INSTRUCTOR'}">강사</c:if>
                                        <c:if test="${user.authority == 'ROLE_ADMIN'}">관리자</c:if>
                                        <c:if test="${user.authority != 'ROLE_USER' && user.authority != 'ROLE_INSTRUCTOR' && user.authority != 'ROLE_ADMIN'}">${user.authority}</c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5">등록된 사용자가 없습니다.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>