<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html> 
<html>
	<head>
		<meta charset="UTF-8">
		<title>MainPage1</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	</head>

	<body>
		<div class="container" style="text-align: center; max-width: 100%;">
			<div class="row">
<%-- 				<%@ include file="navBar/buttonBar.jsp" %> --%>
				<%@ include file="navBar/navBar.jsp" %>
			</div>
		    <div class="row m-3 border border-3 border-warning rounded" style="height: 540px">
<%-- 		    	<input type="hidden" id="user_id" value="${ principal.username }" /> --%>
		        <div class="col border border-3 border-primary m-3 rounded">			       		
					지도영역
		        </div>
		        <div class="col border border-3 border-primary m-3 p-5 rounded" style="text-align: center;">
					<div class="row border border-3 border-primary p-3 rounded" style="height: 50%; text-align: center;">
						<h3>Quick Menu</h3>
						<div>
							<button class="btn btn-outline-primary mx-1" type="button" onclick="location.href='registLecture/registLecture.do'" style="width: 20%">개설과목</button>
<!-- 							<button class="btn btn-outline-primary mx-1" type="button" onclick="" style="width: 20%">FAQ</button> -->
						</div>
		            </div>
		    		<div class="row border border-3 border-primary mt-3 rounded" style="height: 50%">
		    			<button class="btn btn-success w-25 h-25" type="button" onclick="location.href='lecture/myLectureList.do'">내 강의 목록</button>
		    			<button class="btn btn-success w-25 h-25" type="button" onclick="location.href='assignment/assignmentList.do'">내 과제</button>
		            </div>
		    	</div>
		  	</div>
		</div>
		<footer style="height: 50px">
			<%@ include file="footer.jsp" %>
		</footer>
	</body>
</html>