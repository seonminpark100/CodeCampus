<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="UTF-8">
		<title>assignmentList</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
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
// 			    f.setAttribute('action', 'assignmentSubmitView.do');
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
		    <div class="row m-3 p-3 border border-3 border-warning rounded" style="height: 740px">
			    <h3>수강신청</h3>	    	
		    	<div class="row d-flex justify-content-center border border-3 border-primary rounded" style="height: 90%; text-align: center;">
		    		<form method="get">
				    	<div class="row-4 input-group border border-3 border-primary rounded w-50" style="height: 50px;">
							<select class="form-select" id="searchField" name="searchField" aria-label="Default select example">
								<option value="l.lecture_name" selected>강의명</option>
								<option value="p.user_name">교수</option>
							</select> 
							<input type="text" class="form-control w-75" id="searchKeyword" name="searchKeyword">
							<button class="btn btn-primary" type="submit" onclick="location.href='registLecture.do?pageNum=1'">검색</button>
						</div>	    	
					</form>
			    	<div class="row border border-3 border-primary rounded p-1" style="overflow: auto; height: 80%">
						<table class="table table-striped table-bordered border-3 border-warning rounded p-1">
							<thead>
								<tr>
									<th style="width: 10%">강의명</th>
									<th style="width: 10%">교수명</th>
									<th style="width: 60%;">과제명</th>
									<th>마감날</th>
									<th>점수</th>
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
	<!-- 			    		실제 값을 받을땐 foreach문을 사용 -->
										<c:forEach items="${ list }" var="row">																			
											<tr>
												<td style="height: 10px; vertical-align: middle; ">${ row.lecture_name }</td>
												<td style="height: 10px; vertical-align: middle; ">${ row.user_name }</td>
												<td style="height: 10px; vertical-align: middle; ">${ row.assignment_title }</td>
												<td style="height: 10px; vertical-align: middle; ">${ row.deadline }</td>
												<td style="height: 10px; vertical-align: middle; ">
													<c:choose>
												    	<c:when test="dto.score != null">
												     		${ row.score }
												    	</c:when>
												    	<c:otherwise>
												    		-
												    	</c:otherwise>
												    </c:choose>	
												</td>
												<td style="height: 10px; vertical-align: middle; "><button class="btn btn-success" onclick="sendAssignment(${ row.assignment_idx });">GO!</button></td>
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
		</div>
		<footer>
			<%@ include file="../footer.jsp" %>
		</footer>
	</body>
</html>