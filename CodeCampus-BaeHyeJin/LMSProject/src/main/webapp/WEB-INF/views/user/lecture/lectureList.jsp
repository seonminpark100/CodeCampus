<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>lecture</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	</head>
	<body>
		<div class="container" style="text-align:left; max-width: 100%;">
			<div class="row">
				<%@ include file="../navBar/navBar.jsp" %>
			</div>
		    <div class="row m-3 p-3 border border-3 border-warning rounded" style="height: 540px">
			    <h3>${ lecture_name } | ${ user_name }</h3>	    	
		    	<div class="row d-flex justify-content-center border border-3 border-primary rounded" style="height: 90%; text-align: center;">
			    	<div class="row border border-3 border-primary rounded p-1" style="overflow: auto; height: 80%">
<!-- 			    		실제 값을 받을땐 foreach문을 사용 -->
						<c:forEach items="${ list }" var="row">	
<%-- 			    			<div class="border border-3 border-success rounded my-1 h-25"><button class="btn btn-primary" onclick="sendLectureCode('${ row.category }', ${ row.board_idx });">${ row.board_title }</button></div> --%>
			    			<div class="border border-3 border-success rounded my-1 h-25">
			    				<button class="btn btn-primary" onclick="location.href='lectureView.do?board_idx=${ row.board_idx }'">${ row.board_title }</button>
			    			</div>
			    		</c:forEach>
			    	</div>
			    	<div style="text-align: center;">
	 					<button type="button" class="btn btn-primary" onclick="location.href='myLectureList.do'">내 강의로</button>
					</div>
		    	</div>
		  	</div>
		</div>
		<footer>
			<%@ include file="../footer.jsp" %>
		</footer>
	</body>
</html>