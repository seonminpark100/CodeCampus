<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>OO대학교 eCampus</title>
<!-- 		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"> -->
<!-- 		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script> -->
	</head>
	<body class="sb-nav-fixed">
		<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
		    <div id="layoutSidenav_content" class="row p-3" style="height: 50%;">
		    	<div class="row row-cols-3">
					<c:forEach items="${ list }" var="row">						
		    			<div class="col mx-1" style="width: 30%;">
		    				<button class="btn btn-outline-success" onclick="location.href='lectureList.do?lecture_code=${ row.lecture_code }'" style="width: 100%; height: 100%; font-size: 1.5em">${ row.lecture_name }<br/><br/>기간 : ${ row.lecture_start_date } ~ ${ row.lecture_end_date }</button>
<%-- 							<a href="lectureList.do?lecture_code=${ row.lecture_code }">${ row.lecture_name }</a> --%>
			    		</div>
		    		</c:forEach>
		    	</div>
		  	</div>
		</main>
	</body>
</html>