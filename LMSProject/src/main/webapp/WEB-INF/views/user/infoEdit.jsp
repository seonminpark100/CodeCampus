<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>infoEdit</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	</head>
	<body>
		<div class="container" style="text-align:left; max-width: 100%;">
			<div class="row">
				<%@ include file="../navBar/navBar.jsp" %>
			</div>
		    <div class="m-3 p-3 border border-3 border-warning rounded" style="height: 540px; text-align: center;">
			    <h3>정보 수정</h3>	  
			    <form action="" method="post">
			    	<img class="m-1" src="images/image.PNG " alt="프로필사진" style="float: left; width: 20%; margin-left: 38%;"/>
		    		<div class="input-group w-25" style="margin-top: 2%; margin-left: 38%;">		    			
						<input type="file" class="form-control" id="profileImage">
					</div>
		    		<div class="form-floating w-25 bordered border-3 border-primary" style="margin-left: 38%; margin-top: 2%;">
						<input type="text" class="form-control" id="user_id" name="user_id" placeholder="ID" value="테스트" required>
						<label for="id">아이디</label> 
			    	</div>
		    		<div class="form-floating w-25 bordered border-3 border-primary" style="margin-left: 38%; margin-top: 2%;">
						<input type="password" class="form-control" id="user_pass" name="user_pass" placeholder="PASSWORD" value="1234" required>
						<label for="pass">패스워드</label> 
			    	</div>
		    		<div class="form-floating w-25 bordered border-3 border-primary" style="margin-left: 38%; margin-top: 2%;">
						<input type="password" class="form-control" id="user_pass_check" name="user_pass_check" placeholder="PASSWORD" value="1234" required>
						<label for="passCheck">패스워드</label> 
			    	</div>
			    	<button class="btn btn-outline-primary m-3" type="submit">확인</button>
		    	</form>
		  	</div>
		</div>
		<footer>
			<%@ include file="../footer.jsp" %>
		</footer>
	</body>
</html>