<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	</head>
	<body>
		<div class="container" style="text-align: center; max-width: 100%;">
			<div class="row">
<%-- 				<%@ include file="navBar/buttonBar.jsp" %> --%>
				<%@ include file="../navBar/navBar.jsp" %>
			</div>
		    <div class="row m-3 p-5 border border-3 border-warning rounded" style="height: 540px;">
		    	<div class="col-4 border border-3 border-primary mx-4 rounded">
		    		<img src="images/image.PNG" alt="login" class="img-fluid" />
		    	</div>
		    	<div class="col-7 border border-3 border-primary mx-3 p-3 rounded">
		    		<h3 class="mb-5">로그인</h3>
		    		<form class="row" action="loginAction.do" method="post">
						<div class="col-m-3 form-floating w-50">
							<input type="text" class="form-control" id="id" name="id"  placeholder="ID" required>
							<label for="id">아이디</label> 
						</div>
						<div class="col-m-3 form-floating w-50">
							<input type="password" class="form-control" id="pass" name="pass" placeholder="PASSWORD" required>
							<label for="pass">패스워드</label> 
						</div>
						<div class="col mt-5">
							<button class="btn btn-primary" type="submit">로그인</button>
						</div>
					</form>
		    	</div>
		    </div>
		</div>
		<footer>
			<%@ include file="../footer.jsp" %>
		</footer>
	</body>
</html>