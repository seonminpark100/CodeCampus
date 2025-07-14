<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>lecture</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	</head>
	<body>
		<div class="container" style="text-align:left; max-width: 100%;">
			<div class="row">
<%-- 				<%@ include file="navBar/buttonBar.jsp" %> --%>
				<%@ include file="../navBar/navBar.jsp" %>
			</div>
		    <div class="row m-3 p-3 border border-3 border-warning rounded" style="height: 540px">
		    	<div class="border border-3 border-primary rounded p-3" style="height: 45%; text-align: left;">
					<img class="m-1" src="/uploads/${ dto.saveFile }" alt="프로필사진" style="float: left; width: 10%;"/>
					이름 : ${ dto.user_name } <br/>
					<button class="btn btn-outline-primary" onclick="location.href='infoEdit.do'">정보 수정</button>
		    	</div>
		    	<div class="border border-3 border-primary rounded p-3" style="height: 55%; text-align: left;">
		    	</div>
		  	</div>
		</div>
		<footer>
			<%@ include file="../footer.jsp" %>
		</footer>
	</body>
</html>