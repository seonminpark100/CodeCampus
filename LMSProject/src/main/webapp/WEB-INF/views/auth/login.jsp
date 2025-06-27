<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login 화면</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
	</head>
	<body>
		<div style="width:600px;" class="border border-2 border-success rounded p-5">

        <c:if test="${empty user_id }" var="loginResult">
            <c:if test="${param.error != null}">
            <p>
                Login Error! <br />
                ${errorMsg}
            </p>
            </c:if>

            <form action="/myLoginAction.do" method="post">
                <div class="form-floating mb-3 mt-3">
                    <input type="text" class="form-control" id="user_id"
                        placeholder="Enter Id" name="my_id">
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

        <c:if test="${not loginResult }">
             ${user_id } 님, 좋은 아침입니다. <br />
            <a href="<c:url value='/'/>">Root</a> <%-- Context Path 고려하여 <c:url> 사용 --%>
            <a href="<c:url value='/myLogout.do'/>">Logout</a>
        </c:if>
        </div>
	</body>
</html>
