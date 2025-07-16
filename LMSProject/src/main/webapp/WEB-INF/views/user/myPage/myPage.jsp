<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>OO대학교 eCampus</title>
	</head>
	<body class="sb-nav-fixed">
		<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
		    <div id="layoutSidenav_content" class="p-3" style="height: 90%;">
		    	<div class="p-3" style="text-align: left;">
		    		<h3>마이페이지</h3>
		    		<hr/>
					<img class="m-1" src="/uploads/${ dto.saveFile }" alt="프로필사진" style="float: left; width: 10%;"/>
					이름 : ${ dto.user_name } <br/>
					<button class="btn btn-outline-primary" onclick="location.href='infoEdit.do'">정보 수정</button>
		    	</div>
		  	</div>
		</main>
	</body>
</html>