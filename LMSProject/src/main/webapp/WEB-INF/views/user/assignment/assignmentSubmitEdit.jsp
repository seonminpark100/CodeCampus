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
		<title>OO대학교 eCampus</title>
	</head>
	<body class="sb-nav-fixed">
		<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
		    <div id="layoutSidenav_content" class="p-3" style="height: 90%;">
			    <form action="assignmentSubmitEditAction.do" method="post" enctype="multipart/form-data">
			    	<h3>${ dto.lecture_name } | ${ dto.user_name } 교수</h3>
			    	<hr/>	    
		    		<input type="hidden" id="assignment_sfile" name="assignment_sfile" value="${ dto.assignment_sfile }" />
		    		<input type="hidden" id="assignment_idx" name="assignment_idx" value="${ dto.assignment_idx }" />
		    		<div style="height: 10%;">
		    			<h4>과제명 : ${ dto.assignment_title }</h4> 
		    		</div>
		    		<div class="p-2">
		    			<h5>${ dto.assignment_content }</h5><br/><br/>
		    			<textarea id="assignment_content_s" name="assignment_content_s" style="width: 90%; height: 200px;" >${ dto.assignment_content_s }</textarea>
		    		</div>
		    		<input type="file" class="form-control" id="assignmentFile" name="assignmentFile" value="" style="width: 90%;"><br/><br/>
		    		<div style="text-align: center;">
			    		<button type="submit" class="btn btn-success">수정</button>
			    		<button type="button" class="btn btn-danger" onclick="location.href='assignmentSubmitView.do?assignment_idx=${ dto.assignment_idx }'">취소</button>
		    		</div>
		    	</form>
		  	</div>
		</main>
	</body>
</html>