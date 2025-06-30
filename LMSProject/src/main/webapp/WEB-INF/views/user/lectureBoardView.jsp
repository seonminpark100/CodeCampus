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
			    <h3>강의명 & 강사명</h3>	    	
		    	<div class="border border-3 border-primary rounded p-2" style="height: 90%; text-align: left;">
		    		<div class="border border-3 border-primary rounded" style="height: 12%;">
		    			<span class="m-2" style="font-size: 1.5em; font-weight: bold;">안녕하세요</span>
		    			<div class="border border-3 border-primary rounded" style="float: right;">
		    				<button class="btn btn-outline-primary mx-2">수정</button>
		    				<button class="btn btn-outline-primary mx-2">삭제</button>
	    				</div>
		    			<div class="border border-3 border-primary rounded mx-2" style="float: right;">
		    				<button class="btn btn-outline-primary mx-2">bb</button>
		    				<button class="btn btn-outline-primary mx-2">qq</button>
	    				</div>
		    		</div>
		    		<div class="border border-3 border-primary rounded p-2" style="height: 58%; overflow: auto;">
		    			게시판 내용은 여기에!
		    		</div>
		    		<div class="border border-3 border-primary rounded p-2" style="height: 30%; overflow: auto;">
		    			댓글 창은 여기에!
		    		</div>
		    	</div>
		  	</div>
		</div>
		<footer>
			<%@ include file="../footer.jsp" %>
		</footer>
	</body>
</html>