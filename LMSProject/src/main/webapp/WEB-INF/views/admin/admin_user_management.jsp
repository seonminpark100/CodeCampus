<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Insert title here</title>
	</head>
	<body>
		<div class="container">
            <div>
                <h3>관리자 시스템</h3>
                <ul>
                    <li><a href="<c:url value='/admin'/>" class="active">회원 관리 화면</a></li>
                    <li><a href="#">전체 강의/과목 관리</a></li>
                    <li><a href="#">공지사항 등록/수정/삭제</a></li>
                    <li><a href="#">통계 대시보드</a></li>
                    <li><a href="#">게시판/댓글 건의사항 통계</a></li>
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

                <div class="form-section">
                    <h2>새 회원 계정 생성</h2>
                    <form action="<c:url value='/admin/create'/>" method="post"> <%-- /admin/users/create 대신 /admin/create --%>
                        <div class="form-group">
                            <label for="userId">고유 번호 (ID):</label>
                            <input type="text" id="userId" name="userId" required placeholder="예: u001, i005, a001">
                        </div>
                        <div class="form-group">
                            <label for="userPw">비밀번호:</label>
                            <input type="password" id="userPw" name="userPw" required placeholder="비밀번호 입력">
                        </div>
                        <div class="form-group">
                            <label for="userName">이름:</label>
                            <input type="text" id="userName" name="userName" required placeholder="사용자 이름">
                        </div>
                        <div class="form-group">
                            <label for="userBirthdate">생년월일:</label>
                            <input type="date" id="userBirthdate" name="userBirthdate" required>
                        </div>
                        <div class="form-group">
                            <label>권한:</label>
                            <div class="radio-group">
                                <input type="radio" id="auth_user" name="authority" value="ROLE_USER" checked> <%-- ROLE_ 접두사 추가 --%>
                                <label for="auth_user">일반 사용자</label>
                                <input type="radio" id="auth_instructor" name="authority" value="ROLE_INSTRUCTOR"> <%-- ROLE_ 접두사 추가 --%>
                                <label for="auth_instructor">강사</label>
                                <input type="radio" id="auth_admin" name="authority" value="ROLE_ADMIN"> <%-- ROLE_ 접두사 추가 --%>
                                <label for="auth_admin">관리자</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <button type="submit">계정 생성</button>
                        </div>
                    </form>
                </div>

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