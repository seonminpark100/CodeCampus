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
			    <h3>${ lecture.lecture_name }</h3>	    	
		    	<div class="row d-flex justify-content-center border border-3 border-primary rounded" style="height: 90%; text-align: center;">
			    	<div class="row border border-3 border-primary rounded p-1" style="overflow: auto; height: 80%">
<!-- 			    		실제 값을 받을땐 foreach문을 사용 -->
			    		<div class="border border-3 border-success rounded my-1 h-25">강의 1</div>
			    		<div class="border border-3 border-success rounded my-1 h-25">강의 2</div>
			    		<div class="border border-3 border-success rounded my-1 h-25">강의 3</div>
			    		<div class="border border-3 border-success rounded my-1 h-25">강의 4</div>
			    		<div class="border border-3 border-success rounded my-1 h-25">강의 5</div>
			    		<div class="border border-3 border-success rounded my-1 h-25">강의 6</div>
			    	</div>
		    	</div>
		  	</div>
		</div>
		<footer>
			<%@ include file="../footer.jsp" %>
		</footer>
	</body>
</html>