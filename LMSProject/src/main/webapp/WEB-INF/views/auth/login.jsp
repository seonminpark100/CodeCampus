<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <title>OO대학교 eCampus</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" 
			rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" 
			crossorigin="anonymous">
    <style type="text/css">
	body {
	    display: flex;
	    align-items: center;
	    justify-content: center;
	    font-family: sans-serif;
	    line-height: 1.5;
	    min-height: 100vh;
	    background: #f3f3f3;
	    flex-direction: column;
	    margin: 0;
	}
	
	.main {
	    background-color: #fff;
	    border-radius: 15px;
	    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
	    padding: 10px 20px;
	    transition: transform 0.2s;
	    width: 500px;
	    text-align: center;
	}
	
	h1 {
	    color: #4CAF50;
	}
	
	label {
	    display: block;
	    width: 100%;
	    margin-top: 10px;
	    margin-bottom: 5px;
	    text-align: left;
	    color: #555;
	    font-weight: bold;
	}
	
	input {
	    display: block;
	    width: 100%;
	    margin-bottom: 15px;
	    padding: 10px;
	    box-sizing: border-box;
	    border: 1px solid #ddd;
	    border-radius: 5px;
	}
	
	button {
	    padding: 15px;
	    border-radius: 10px;
	    margin-top: 15px;
	    margin-bottom: 15px;
	    border: none;
	    color: white;
	    cursor: pointer;
	    background-color: #4CAF50;
	    width: 100%;
	    font-size: 16px;
	}
	
	.wrap {
	    display: flex;
	    justify-content: center;
	    align-items: center;
	}
    </style>
</head>

<body>
    <div class="main">
        <h1>OO대학교 eCampus</h1>
        
        <c:if test="${empty user_id }" var="loginResult">
			<c:if test="${param.error != null}">
			<p>
				Login Error! <br />
				${errorMsg}
			</p>
			</c:if>
			
			<!-- 시큐리티 설정파일에 지정된 요청명으로 action 속성값 기술 -->
			<form action="/myLoginAction.do" method="post">
				<div class="form-floating mb-3 mt-3">
					<input type="text" class="form-control" id="user_id" 
						placeholder="Enter email" name="my_id">
					<label for="user_id">아이디</label>
				</div>	
				<div class="form-floating mt-3 mb-3">
					<input type="password" class="form-control" id="user_pwd" 
						placeholder="Enter password" name="my_pass">
					<label for="user_pwd">비밀번호</label>
				</div>	
				<div class="d-grid">
					<button type="submit" class="btn btn-success btn-block">
						로그인</button>
				</div>
			</form>
		</c:if>
    </div>
</body>

</html>