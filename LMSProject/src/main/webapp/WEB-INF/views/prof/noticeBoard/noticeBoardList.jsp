<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>대학교 eCampus</title>
	</head>
	<body>
	<%@ include file = "../top.jsp" %>
		<div class="container">
		<h2>공지사항 목록 </h2>
		<!-- 검색 폼 -->
		<form method="get">
		<table border="1" width="90%">
		<tr>
			<td>
				<select name="searchField">
					<option value="title">제목</option>
					<option value="content">내용</option>
				</select>
				<input type="text" name="searchKeyword" />
				<input type="submit" value="검색하기" />
			</td>
		</tr>
		</table>		
		</form>
		
		<!-- 목록 테이블 -->
	    <table border="1" width="90%">
	        <tr>
	            <th width="10%">번호</th>
	            <th width="*">제목</th>
	            <th width="15%">작성자</th>
	            <th width="10%">조회수</th>
	            <th width="15%">작성일</th>
	        </tr>
	<c:choose>
	    <c:when test="${ empty lists }"> 
	        <tr>
	            <td colspan="5" align="center">
	                등록된 게시물이 없습니다^^*
	            </td>
	        </tr>
	    </c:when> 
	    <c:otherwise> 
	        <c:forEach items="${ lists }" var="row" varStatus="loop">    
	        <tr align="center">
	            <td> 
	            <!-- 게시물의갯수, 페이지번호, 페이지사이즈를 통해 가상번호를 계산해서
	            출력한다. -->
	            <c:set var="vNum" value="${ maps.totalCount - 
	                (((maps.pageNum-1) * maps.pageSize)	+ loop.index)}" />
	            	${vNum}
	            </td>
	            <td align="left"> 
	                <a href="./view.do?idx=${row.idx}&vNum=${vNum}">${ row.title }</a> 
	            </td> 
	            <td>${ row.name }</td> 
	            <td>${ row.visitcount }</td> 
	            <td>${ row.postdate }</td> 
	        </tr>
	        </c:forEach>        
	    </c:otherwise>    
	</c:choose>
	    </table>
	    
	    <!-- 하단 메뉴(바로가기, 글쓰기) -->
	    <table border="1" width="90%">
	        <tr align="center">
	            <td>
	                ${ pagingImg }
	            </td>
	            <td width="100"><button type="button"
	                onclick="location.href='./write.do';">글쓰기</button>
	            </td>
	        </tr>
	    </table>
		</div>
	</body>
</html>