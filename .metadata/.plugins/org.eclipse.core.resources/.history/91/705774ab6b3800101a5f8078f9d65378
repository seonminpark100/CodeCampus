<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>메인페이지</title>
		<!-- bootstrap  -->
		<!-- bootstrap CSS만 유지 -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
		
	    <style type="text/css">
	    	th{text-align: center;}
	    </style>
	</head>
	<body>
		<nav class="navbar navbar-inverse">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">
                  <img src="../images/logo.png" alt="" style="height: 30px;">
                </a>
            </div>
            <div class="container">
                <div class="btn-group">
                    <ul class="nav navbar-nav">
                        <li><a href="../mvcboard/default.do">메인화면</a></li>
                        <li><a href="../mvcboard/list.do">회원게시판</a></li>
                        <li >
                        <% if(session.getAttribute("id") == null) {%>
                            <a href="../mvcboard/signup.do">회원가입</a>
                            <% } %>
                        </li>
                        <!-- <li><a href="../mvcboard/signup.do">회원가입</a></li> -->
                        <li>
                        <% if (session.getAttribute("id") == null) { %>
                            <a href="../mvcboard/login.do">로그인</a>
                        <% } else { %>
                            <a href="../mvcboard/logout.do">로그아웃</a>
                        <% } %>
                        </li>
                        
                        
                        <!-- <li><a href="../mvcboard/login.do">로그인</a></li> -->
                    </ul>
                </div>
            </div>
        </nav>
	</body>
</html>