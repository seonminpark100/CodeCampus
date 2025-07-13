<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="UTF-8">
		<title>registLecture</title>
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
	function sendLecture_code(lecture_code) {
		let f = document.createElement('form');
		    
		    let obj;
		    obj = document.createElement('input');
		    obj.setAttribute('type', 'hidden');
		    obj.setAttribute('name', 'lecture_code');
		    obj.setAttribute('value', lecture_code);
		    f.appendChild(obj);
		    
		    f.setAttribute('method', 'post');
		    f.setAttribute('action', 'registLecture.do');
		    document.body.appendChild(f);
		    f.submit();
		}
	</script>
	<body>
		<div class="container" style="text-align:left; max-width: 100%;">
			<div class="row">
<%-- 				<%@ include file="navBar/buttonBar.jsp" %> --%>
				<%@ include file="../../navBar/navBar.jsp" %>
			</div>
		    <div class="row m-3 p-3 border border-3 border-warning rounded" style="height: 740px">
			    <h3>수강신청</h3>	    	
		    	<div class="row d-flex justify-content-center border border-3 border-primary rounded" style="height: 90%; text-align: center;">
		    		<form method="get" action="registLecture.do?pageNum=1">
				    	<div class="row-4 input-group border border-3 border-primary rounded w-50" style="height: 50px;">
							<select class="form-select" id="searchField" name="searchField">
								<option value="lecture_name" selected>강의명</option>
								<option value="user_name">교수</option>
							</select> 
							<input type="text" class="form-control w-75" id="searchKeyword" name="searchKeyword">
							<button class="btn btn-primary" type="submit">검색</button>
						</div>	    	
					</form>
			    	<div class="row border border-3 border-primary rounded p-1" style="overflow: auto; height: 80%">
						<table class="table table-striped table-bordered border-3 border-warning rounded p-1">
							<thead>
								<tr>
									<th style="width: 10%">구분</th>
									<th style="width: 60%;">강의명</th>
									<th>교수명</th>
									<th>신청하기</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${ empty list }"> 
								        <tr>
								            <td colspan="5" align="center">
								                등록된 게시물이 없습니다^^*
								            </td>
								        </tr>
								    </c:when> 
								    <c:otherwise>
	<!-- 			    		실제 값을 받을땐 foreach문을 사용 -->
										<c:forEach items="${ list }" var="row">																			
											<tr>
												<td style="height: 10%;">${ row.major_name }</td>
												<td style="height: 10%;">${ row.lecture_name }</td>
												<td style="height: 10%;">${ row.user_name }</td>
												<td style="height: 10%;" onclick="sendLecture_code('${ row.lecture_code }')"><button class="btn btn-success">신청하기</button></td>
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
			<%@ include file="../../footer.jsp" %>
		</footer>
	</body>
</html>