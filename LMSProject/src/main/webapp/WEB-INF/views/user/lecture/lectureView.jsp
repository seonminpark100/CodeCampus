<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>lectureView</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	</head>
	<body>
		<div class="container" style="text-align:left; max-width: 100%;">
			<div class="row">
				<%@ include file="../../navBar/navBar.jsp" %>
			</div>
		    <div class="row m-3 p-3 border border-3 border-warning rounded" style="height: 540px">
			    <h3>${ lecture_name } | ${ user_name }</h3>	    	
		    	<div class="border border-3 border-primary rounded" style="height: 90%; text-align: left: ;">
		    		<h3>${ dto.board_title } | ${ dto.board_postdate } | 조회수 : ${ dto.visitCount }</h3>
<!-- 		    		<button class="btn btn-success w-25 h-25" type="button" onclick="location.href='myLectureList.do'">내 강의 목록</button> -->
					<div class="border border-3 border-primary rounded" style="height: 90%; position: relative; overflow: auto;">
						${ dto.board_content } <br/>
						${ video }
					</div>
					<div class="border border-3 border-primary rounded">
						${ file }
					</div>
					<div class="border border-3 border-primary rounded"  style="text-align: center;">
	 					<button type="button" class="btn btn-primary" onclick="location.href='lectureList.do?lecture_code=${ dto.lecture_code }'">강의 목록으로</button>
					</div>
		    	</div>
		  	</div>
		</div>
		<footer>
			<%@ include file="../../footer.jsp" %>
		</footer>
	</body>
</html>