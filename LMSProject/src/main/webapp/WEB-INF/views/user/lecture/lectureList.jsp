<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			    <h3>${ lecture_name } | ${ user_name } 교수</h3>
			    <hr/>	    	
		    	<div class="row d-flex justify-content-center" style="height: 90%; text-align: center;">
			    	<div class="p-1" style="overflow: auto; height: 80%">
						<c:forEach items="${ list }" var="row">	
			    			<div class="my-1 h-25">
			    				<button class="btn btn-outline-success" onclick="location.href='lectureView.do?board_idx=${ row.board_idx }'" style="width: 100%; height: 100%; font-size: 3.0em">${ row.board_title }</button>
			    			</div>
			    		</c:forEach>
			    	</div>
			    	<div style="text-align: center;">
	 					<button type="button" class="btn btn-primary" onclick="location.href='myLectureList.do'">내 강의로</button>
					</div>
		    	</div>
		  	</div>
		</main>
	</body>
</html>