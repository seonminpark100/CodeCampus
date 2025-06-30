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
					<option value="lecture_name">강의명</option>
					<option value="prof_name">교수명</option>
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
	            <th width="*">강의명</th>
	            <th width="15%">교수명</th>
	            <th width="10%">시작일</th>
	            <th width="15%">종료일</th>
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
	                <a href="./view.do?idx=${row.idx}&vNum=${vNum}">${ row.LECTURE_NAME }</a> 
	            </td> 
	            <td>${ row.PROF_NAME }</td> 
	            <td>${ row.LECTURE_START_DATE }</td> 
	            <td>${ row.LECTURE_END_DATE }</td> 
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
	                onclick="location.href='lectureUpload.do';">강의 업로드</button>
	            </td>
	        </tr>
	    </table>
		</div>
	</body>
</html>