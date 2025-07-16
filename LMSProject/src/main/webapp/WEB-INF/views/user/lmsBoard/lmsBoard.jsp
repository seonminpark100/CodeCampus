<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
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
	<body class="sb-nav-fixed">
		<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
		    <div id="layoutSidenav_content" class="row p-3" style="height: 50%;">
		    	<h3>게시판</h3>
		    	<hr/>
		    	<div class="row d-flex justify-content-center" style="height: 90%; text-align: center;">
		    		<form method="get" action="lmsBoard.do?pageNum=1">
				    	<div class="row-4 input-group" style="height: 50px;">
							<select class="form-select" id="searchField" name="searchField">
								<option value="board_title" selected>제목</option>
								<option value="user_name">작성자</option>
							</select> 
							<input type="text" class="form-control w-75" id="searchKeyword" name="searchKeyword">
							<button class="btn btn-primary" type="submit">검색</button>
						</div>	    	
					</form>
			    	<div class="row p-1" style="overflow: auto; height: 80%">
						<table class="table table-hover table-bordered h-25">
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
										<c:forEach items="${ list }" var="row">																			
											<tr>
<%-- 												<td style="width: 60%;"><a href="javascript:sendBoard_idx('${ row.board_idx }')">${ row.board_title }</a></td> --%>
												<td align="left" style="width: 60%; padding-left: ${ row.BIndent * 20 + 10 }px;">
													<c:if test="${ row.BIndent > 0 }"><img src="/images/paging3.gif" /></c:if>
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
			    	<div class="d-flex justify-content-end mb-1">
			    		<button class="btn btn-primary" onclick="location.href='lmsBoardWrite.do'">글쓰기</button>
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