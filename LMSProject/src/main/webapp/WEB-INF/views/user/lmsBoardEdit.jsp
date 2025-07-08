<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>lmsBoardEdit</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	</head>
	<body>
		<div class="container" style="text-align:left; max-width: 100%;">
			<div class="row">
<%-- 				<%@ include file="navBar/buttonBar.jsp" %> --%>
				<%@ include file="../navBar/navBar.jsp" %>
			</div>
		    <div class="row m-3 p-3 border border-3 border-warning rounded" style="height: 540px">
		    	<div class="border border-3 border-primary rounded p-2" style="height: 90%; text-align: left;">
		    		<form action="lmsBoardEditAction.do" method="post" enctype="multipart/form-data">
			    		<div class="border border-3 border-primary rounded" style="height: 12%;">
			    			<input type="text" name="board_title" id="board_title" class="m-2"style="font-size: 1.5em; font-weight: bold;" value="${ dto.board_title }" required />
			    		</div>
			    		<div class="border border-3 border-primary rounded p-2" style="height: 78%; overflow: auto;">
			    			<textarea name="board_content" id="board_content" cols="30" rows="10" required>${ dto.board_content }</textarea>
			    			<input type="file" class="form-control" id="files" name="files" value="" multiple>
			    		</div>
			    		<div class="border border-3 border-primary rounded" style="height: 10%; text-align: center;">
			    			<button type="submit" class="btn btn-outline-primary mx-1">수정</button>
			    			<button type="button" class="btn btn-outline-primary mx-1" onclick="location.href='lmsBoard.do'">취소</button>
			    		</div>
		    		</form>
		    	</div>
		  	</div>
		</div>
		<footer>
			<%@ include file="../footer.jsp" %>
		</footer>
	</body>
</html>