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
		    	<div class="border border-3 border-primary rounded" style="height: 90%; text-align: center;">
			    	<button class="btn btn-outline-primary m-1" type="button" onclick="location.href='lectureList.do?lecture_idx=${ lecture.lecture_idx }'" style="width: 20%">강의 목록</button>
					<button class="btn btn-outline-primary m-1" type="button" onclick="location.href='lectureResource.do?lecture_idx=${ lecture.lecture_idx }'" style="width: 20%">자료실</button>	    	
					<button class="btn btn-outline-primary m-1" type="button" onclick="location.href='lectureResource.do?lecture_idx=${ lecture.lecture_idx }'" style="width: 20%">과제</button>	    	
					<button class="btn btn-outline-primary m-1" type="button" onclick="location.href='lectureBoard.do?lecture_idx=${ lecture.lecture_idx }'" style="width: 20%">커뮤니티</button>	    	
		    	</div>
		  	</div>
		</div>
		<footer>
			<%@ include file="../footer.jsp" %>
		</footer>
	</body>
</html>