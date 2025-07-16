<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.OutputStream"%>
<%@page import="com.lms.springboot.utils.FileUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>OO대학교 eCampus</title>
	</head>
	<script>
		function deleteConfirm(board_idx){
			if(!confirm("삭제하시겠습니까?")){
				return false;
			}
			window.location.href="deleteBoard.do?board_idx=" + board_idx;
		}
	
		function sendBoard_idx(board_idx, action) {
			let f = document.createElement('form');
			  
			let obj
		 	obj = document.createElement('input');
		    obj.setAttribute('type', 'hidden');
		    obj.setAttribute('name', 'board_idx');
		    obj.setAttribute('value', board_idx);
		    f.appendChild(obj);
		    
		    f.setAttribute('method', 'get');
		    f.setAttribute('action', action);
		    document.body.appendChild(f);
		    f.submit();
		}
	</script>
	<body class="sb-nav-fixed">
		<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
		    <div id="layoutSidenav_content" class="p-3" style="height: 50%;">
		    	<div>
	    			<h4>${ dto.board_title }</h4> 
	    			<h5>작성자 : ${ dto.user_name }</h5>
	    			<h6>조회수 : ${ dto.visitCount }</h6>
		    		<hr/>
		    		<div class="p-2">
		    			<textarea id="board_content" name="board_content" style="width: 90%; height: 200px;" readonly>${ dto.board_content }</textarea>
		    		</div>
		    		<div class="p-2">
		    			${ files }
		    		</div>
		    		<div style="height: 10%; text-align: center;">
						<c:if test="${ isWriter eq true }">
			    			<div style="float: right;">
			    				<button class="btn btn-success mx-2" onclick="sendBoard_idx(${ dto.board_idx }, 'lmsBoardEdit.do')">수정</button>
	<%-- 			    				<button class="btn btn-outline-primary mx-2" onclick="lmsBoardEdit.do?board_idx=${ dto.board_idx }">수정</button> --%>
	<%-- 			    				<button class="btn btn-outline-primary mx-2" onclick="sendBoard_idx('${ dto.board_idx }', 'deleteBoard.do')">삭제</button> --%>
			    				<button class="btn btn-success mx-2" onclick="deleteConfirm(${ dto.board_idx });">삭제</button>
		    				</div>
	    				</c:if>
		    			<div class="mx-2" style="float: right;">		    				
		    				<button class="btn btn-success mx-2" onclick="location.href='replyWrite.do?board_idx=${ dto.board_idx }'">답글쓰기</button>
	    				</div>
						<button class="btn btn-primary" onclick="location.href='lmsBoard.do'">목록으로</button>
			    	</div>
		    	</div>
	    	</div>
		</main>
	</body>
</html>