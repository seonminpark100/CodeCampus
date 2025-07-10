<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%    
	response.setHeader("Cache-Control","no-store");    
	response.setHeader("Pragma","no-cache");    
	response.setDateHeader("Expires",0);    
	if (request.getProtocol().equals("HTTP/1.1"))  
	        response.setHeader("Cache-Control", "no-cache");  
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>assignmentSubmitEdit</title>
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
			    <h3>${ dto.lecture_name } | ${ dto.user_name }</h3>	    
			    	
			    <form action="assignmentSubmitEditAction.do" method="post" enctype="multipart/form-data">
			    	<div class="border border-3 border-primary rounded p-2" style="height: 90%; text-align: left;">
			    		<input type="hidden" id="assignment_sfile" name="assignment_sfile" value="${ dto.assignment_sfile }" />
			    		<input type="hidden" id="assignment_idx" name="assignment_idx" value="${ dto.assignment_idx }" />
			    		<div class="border border-3 border-primary rounded" style="height: 10%;">
			    			${ dto.assignment_title }
			    		</div>
			    		<div class="border border-3 border-primary rounded p-2" style="height: 75%; overflow: auto;">
			    			${ dto.assignment_content }<br/>
			    			<textarea rows="10" cols="200" id="assignment_content_s" name="assignment_content_s">${ dto.assignment_content_s }</textarea>
<!-- 			    		파일넣기 나중에 수정해-->
			    			<input type="file" class="form-control" id="assignmentFile" name="assignmentFile" value="">
			    		</div>
			    		<div class="border border-3 border-primary rounded" style="text-align: center;">
				    		<button type="submit" class="btn btn-primary">수정</button>
				    		<button type="button" class="btn btn-primary" onclick="location.href='assignmentSubmitView.do'">취소</button>
			    		</div>
			    	</div>
		    	</form>
		  	</div>
		</div>
		<footer>
			<%@ include file="../footer.jsp" %>
		</footer>
	</body>
</html>