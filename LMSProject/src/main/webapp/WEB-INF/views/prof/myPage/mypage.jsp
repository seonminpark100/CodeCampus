<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>마이페이지</title>
	</head>
	<body>
	<%@ include file = "../top.jsp" %>
		<div class="container">
			<h2>마이페이지</h2>
			
		    <div class="row m-3 p-3 border border-3 border-warning rounded" style="height: 540px">
		    	<div class="p-3" style="height: 45%; text-align: left;">
					<img class="m-1" src="/uploads/${ dto.saveFile }" alt="프로필사진" style="float: left; width: 10%;"/>
					이름 : ${ dto.user_name } <br/>
					<button class="btn btn-outline-primary" onclick="location.href='infoEdit.do'">정보 수정</button>
		    	</div>
		  	</div>
		</div>
	</body>
</html>