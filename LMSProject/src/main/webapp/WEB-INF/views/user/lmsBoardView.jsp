<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.OutputStream"%>
<%@page import="com.lms.springboot.utils.FileUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<title>lmsBoardView</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	</head>
	<script>
		
		function sendBoard_idx(board_idx) {
			let f = document.createElement('form');
			  
			let obj
		 	obj = document.createElement('input');
		    obj.setAttribute('type', 'hidden');
		    obj.setAttribute('name', 'board_idx');
		    obj.setAttribute('value', board_idx);
		    f.appendChild(obj);
		    
		    f.setAttribute('method', 'post');
		    f.setAttribute('action', 'lmsBoardEdit.do');
		    document.body.appendChild(f);
		    f.submit();
		}
	</script>
	<body>
		<div class="container" style="text-align:left; max-width: 100%;">
			<div class="row">
<%-- 				<%@ include file="navBar/buttonBar.jsp" %> --%>
				<%@ include file="../navBar/navBar.jsp" %>
			</div>
		    <div class="row m-3 p-3 border border-3 border-warning rounded" style="height: 540px">
		    	<div class="border border-3 border-primary rounded p-2" style="text-align: left;">
		    		<div class="border border-3 border-primary rounded" style="height: 12%;">
		    			<span class="m-2" style="font-size: 1.5em; font-weight: bold;">${ dto.board_title } | ${ dto.user_name }</span>
		    			<c:if test="${ isWriter eq true }">
			    			<div class="border border-3 border-primary rounded" style="float: right;">
			    				<button class="btn btn-outline-primary mx-2" onclick="sendBoard_idx('${ dto.board_idx }')">수정</button>
			    				<button class="btn btn-outline-primary mx-2">삭제</button>
		    				</div>
	    				</c:if>
		    			<div class="border border-3 border-primary rounded mx-2" style="float: right;">
		    				<button class="btn btn-outline-primary mx-2">bb</button>
		    				<button class="btn btn-outline-primary mx-2">qq</button>
	    				</div>
		    		</div>
		    		<div class="border border-3 border-primary rounded p-2" style="height: 60%; overflow: auto;">
		    			${ dto.board_content }
		    		</div>
		    		<div class="border border-3 border-primary rounded p-2" style="overflow: auto;">
		    			${ files }
		    		</div>
		    	</div>
		  	</div>
		</div>
		<footer>
			<%@ include file="../footer.jsp" %>
		</footer>
	</body>
</html>