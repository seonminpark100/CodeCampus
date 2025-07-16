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
	<body class="sb-nav-fixed">
		<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
		    <div id="layoutSidenav_content" class="p-3" style="height: 90%;">
			    <h3>수강신청</h3>
			    <hr/>	    	
		    	<div class="row d-flex justify-content-center" style="height: 90%; text-align: center;">
		    		<form method="get" action="registLecture.do?pageNum=1">
				    	<div class="row-4 input-group" style="height: 50px;">
							<select class="form-select" id="searchField" name="searchField">
								<option value="lecture_name" selected>강의명</option>
								<option value="user_name">교수명</option>
								<option value="major_name">전공명</option>
							</select> 
							<input type="text" class="form-control w-75" id="searchKeyword" name="searchKeyword">
							<button class="btn btn-primary" type="submit">검색</button>
						</div>	    	
					</form>
			    	<div class="row p-1" style="overflow: auto; height: 80%">
						<table class="table table-hover table-bordered h-25">
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
										<c:forEach items="${ list }" var="row">																			
											<tr>
												<td>${ row.major_name }</td>
												<td>${ row.lecture_name }</td>
												<td>${ row.user_name }</td>
												<td><button class="btn btn-success" onclick="sendLecture_code('${ row.lecture_code }')">신청하기</button></td>
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