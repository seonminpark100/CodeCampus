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
		   		<div>
				    <h3>${ dto.lecture_name } | ${ dto.user_name } 교수</h3>	    
				    <h3>점수 : 
					    <c:choose>
					    	<c:when test="${ dto.score eq -1 }">
					    		미채점
					    	</c:when>
					    	<c:otherwise>
					     		${ dto.score }
					    	</c:otherwise>
					    </c:choose>
				    </h3>
				    <hr/>
		    		<div style="height: 10%;">
		    			<h4>과제명 : ${ dto.assignment_title }</h4> 
		    		</div>
		    		<div class="p-2">
		    			<h5>${ dto.assignment_content }</h5><br/><br/>
		    			<textarea id="assignment_content_s" name="assignment_content_s" style="width: 90%; height: 200px;" readonly>${ dto.assignment_content_s }</textarea>
		    		</div>
		    		<div class="p-2">
		    			${ file }
		    		</div>
		    		<div class="p-2">
		    			<h5>Comment : ${ dto.assignment_comment }</h5>
		    		</div>
		    		<div style="text-align: center;">
		    			<c:if test="${ canEdit }">
			    			<button class="btn btn-warning" onclick="location.href='assignmentSubmitEdit.do?assignment_idx=${ dto.assignment_idx }'">수정</button>
			    		</c:if>
			    		<button class="btn btn-primary" onclick="location.href='assignmentList.do'">목록으로</button>
		    		</div>
	    		</div>
    		</div>
		</main>
	</body>
</html>