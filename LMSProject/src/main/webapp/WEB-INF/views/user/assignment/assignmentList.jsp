<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="UTF-8">
		<title>OO대학교 eCampus</title>
	</head>
	<script>
		window.onload = () => {
			message = '${ message }';
			if(message != ''){
				alert(message);
			}
		}
	</script>
	<script>
		function sendAssignment(idx) {
			let f = document.createElement('form');
			    
			    let obj;
			    obj = document.createElement('input');
			    obj.setAttribute('type', 'hidden');
			    obj.setAttribute('name', 'assignment_idx');
			    obj.setAttribute('value', idx);
			    f.appendChild(obj);
			    
			    f.setAttribute('method', 'post');
			    f.setAttribute('action', 'assignmentSubmitWrite.do');
			    document.body.appendChild(f);
			    f.submit();
		}
	</script>
	<body class="sb-nav-fixed">
		<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
		    <div id="layoutSidenav_content" class="row p-3" style="height: 50%;">
			    <h3>과제목록</h3>	
			    <hr/>    	
		    	<div class="row d-flex justify-content-center" style="height: 90%; text-align: center;">
		    		<form method="get">
				    	<div class="row-4 input-group" style="height: 50px;">
							<select class="form-select" id="searchField" name="searchField">
								<option value="l.lecture_name" selected>강의명</option>
								<option value="p.user_name">교수</option>
							</select> 
							<input type="text" class="form-control w-75" id="searchKeyword" name="searchKeyword">
							<button class="btn btn-primary" type="submit" onclick="location.href='registLecture.do?pageNum=1'">검색</button>
						</div>	    	
					</form>
			    	<div class="row p-1" style="overflow: auto; height: 80%">
						<table class="table table-hover table-bordered h-25">
							<thead>
								<tr>
									<th style="width: 10%">강의명</th>
									<th style="width: 10%">교수명</th>
									<th style="width: 60%;">과제명</th>
									<th>마감날</th>
									<th>과제보기</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${ empty list }"> 
								        <tr>
								            <td colspan="5" align="center">
								                과제가 없습니다~~
								            </td>
								        </tr>
								    </c:when> 
								    <c:otherwise>
										<c:forEach items="${ list }" var="row">																			
											<tr>
												<td>${ row.lecture_name }</td>
												<td>${ row.user_name }</td>
												<td>${ row.assignment_title }</td>
												<td>${ row.deadline }</td>
												<td><button class="btn btn-success" onclick="sendAssignment(${ row.assignment_idx });">GO!</button></td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
			    	</div>
			    	<div style="text-align: center;">
					  	<table border="1">
							<tr align="center">
								${ pagingImg }
							</tr>
						</table>
					</div>
		    	</div>
		  	</div>
		</main>
	</body>
</html>