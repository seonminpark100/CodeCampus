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
		    <div id="layoutSidenav_content" class="p-3" style="height: 50%;">
		    	<h3>답글 작성</h3>
		    	<hr/>
		    	<div class="p-2" style="height: 90%; text-align: left;">
		    		<form action="replyWriteAction.do" method="post" enctype="multipart/form-data">
		    			<input type="hidden" id="bGroup" name="bGroup" value="${ dto.BGroup }" />
		    			<input type="hidden" id="bStep" name="bStep" value="${ dto.BStep }" />
		    			<input type="hidden" id="bIndent" name="bIndent" value="${ dto.BIndent }" />
			    		<div style="height: 12%;">
			    			<input type="text" name="board_title" id="board_title" class="m-2" placeholder="제목을 입력해주세요" style="font-size: 1.5em; font-weight: bold;" value="${ dto.board_title }" required />
			    		</div>
			    		<div class="border border-3 border-primary rounded p-2" style="height: 78%; overflow: auto;">
			    			<textarea name="board_content" id="board_content" style="width: 90%; height: 200px;" placeholder="내용을 입력해주세요" required>${ dto.board_content }</textarea>
			    		</div>
			    		<input type="file" class="form-control" id="files" name="files" value="" style="width: 90%;" multiple><br/><br/>
			    		<div style="height: 10%; text-align: center;">
			    			<button type="submit" class="btn btn-success mx-1">작성</button>
			    			<button type="button" class="btn btn-danger mx-1" onclick="location.href='lmsBoard.do'">취소</button>
			    		</div>
		    		</form>
		    	</div>
		  	</div>
		</main>
	</body>
</html>