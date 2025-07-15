<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>lecture</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
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
	<body>
		<div class="container" style="text-align:left; max-width: 100%;">
			<div class="row">
<%-- 				<%@ include file="navBar/buttonBar.jsp" %> --%>
				<%@ include file="../navBar/navBar.jsp" %>
			</div>
		    <div class="row m-3 p-3 border border-3 border-warning rounded" style="height: 540px">
		    	<div class="row d-flex justify-content-center border border-3 border-primary rounded" style="height: 90%; text-align: center;">
		    		<form method="get" action="lmsBoard.do?pageNum=1">
				    	<div class="row-4 input-group border border-3 border-primary rounded w-50" style="height: 50px;">
							<select class="form-select" id="searchField" name="searchField">
								<option value="board_title" selected>제목</option>
								<option value="user_name">작성자</option>
							</select> 
							<input type="text" class="form-control w-75" id="searchKeyword" name="searchKeyword">
							<button class="btn btn-primary" type="submit">검색</button>
						</div>	    	
					</form>
			    	<div class="row border border-3 border-primary rounded p-1" style="height: 80%">
						<table class="table table-striped table-bordered border-3 border-warning rounded p-1">
							<thead>
								<tr>
									<th style="width: 60%;">제목</th>
									<th>작성자</th>
									<th>작성일</th>
									<th>조회수</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${ empty list }"> 
								        <tr>
								            <td colspan="6" align="center">
								                등록된 게시물이 없습니다^^*
								            </td>
								        </tr>
								    </c:when> 
								    <c:otherwise>
	<!-- 			    		실제 값을 받을땐 foreach문을 사용 -->
										<c:forEach items="${ list }" var="row">																			
											<tr>
<%-- 												<td style="width: 60%;"><a href="javascript:sendBoard_idx('${ row.board_idx }')">${ row.board_title }</a></td> --%>
												<td align="left" style="width: 60%; padding-left: ${ row.BIndent * 20 + 10 }px;">
													<c:if test="${ row.BIndent > 0 }"><img src="images/paging3.gif" /></c:if>
													<a href="lmsBoardView.do?board_idx=${ row.board_idx }">${ row.board_title }</a></td>
												<td>${ row.user_name }</td>
												<td>${ row.board_postdate }</td>
												<td>${ row.visitCount }</td>
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
								<button class="btn btn-primary" onclick="location.href='lmsBoardWrite.do'">글쓰기</button>
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