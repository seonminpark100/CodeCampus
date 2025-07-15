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
	<script>
		function passCheck(){
			var pass1 = document.getElementById('user_pw');
			var pass2 = document.getElementById('user_pw_check');
			if(pass1.value != pass2.value){
				alert("잘못된 확인");
				pass2.value = '';
				pass2.focus();
				return false;
			}
		}
	</script>
	<body>
		<div class="container" style="text-align:left; max-width: 100%;">
			<div class="row">
				<%@ include file = "../top.jsp" %>
			</div>
		    <div class="m-3 p-3 border border-3 border-warning rounded" style="height: 540px; text-align: center;">
			    <h3>정보 수정</h3>	  
			    <form action="infoEdit.do" method="post" enctype="multipart/form-data"
			    	onsubmit="return passCheck();">
			    	<img class="m-1" src="/uploads/${ dto.saveFile }" alt="프로필사진" style="float: left; width: 10%;"/>
			    	<input type="hidden" id="saveFile" name="saveFile" value="${ dto.saveFile }" />
		    		<div class="input-group w-25" style="margin-top: 2%; margin-left: 38%;">		    			
						<input type="file" class="form-control" id="profileImg" name="profileImg" accept=".png, .jpg, .gif" value="${ dto.originalFile }" multiple>
					</div>
					<input type="hidden" id="user_id" name="user_id" value="${ dto.user_id }" />
		    		<div class="form-floating w-25 bordered border-3 border-primary" style="margin-left: 38%; margin-top: 2%;">
						<input type="text" class="form-control" id="user_name" name="user_name" placeholder="ID" value="${ dto.user_name }" required>
						<label for="id">아이디</label> 
			    	</div>
		    		<div class="form-floating w-25 bordered border-3 border-primary" style="margin-left: 38%; margin-top: 2%;">
						<input type="password" class="form-control" id="user_pw" name="user_pw" placeholder="PASSWORD" value="" required>
						<label for="pass">패스워드</label> 
			    	</div>
		    		<div class="form-floating w-25 bordered border-3 border-primary" style="margin-left: 38%; margin-top: 2%;">
						<input type="password" class="form-control" id="user_pw_check" name="user_pw_check" placeholder="PASSWORD" value="" required>
						<label for="passCheck">패스워드 확인</label> 
			    	</div>
			    	<button class="btn btn-outline-primary m-3" type="submit">확인</button>
		    	</form>
		  	</div>
		</div>
		<footer>
			
		</footer>
	</body>
</html>