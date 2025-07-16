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
			    <h3>${ lecture_name } | ${ user_name } 교수</h3>
			    <hr/>    	
		    	<div style="height: 90%; text-align: left;">
		    		<h4>${ dto.board_title } | ${ dto.board_postdate } | 조회수 : ${ dto.visitCount }</h4>
		    		<div class="p-2">
						<h5>${ dto.board_content }</h5><br/>
						${ video }
					</div>
					<div class="p-2">
						${ file }
					</div>
					<div style="text-align: center;">
	 					<button type="button" class="btn btn-primary" onclick="location.href='lectureList.do?lecture_code=${ dto.lecture_code }'">강의 목록으로</button>
					</div>
		    	</div>
		  	</div>
		</main>
	</body>
</html>