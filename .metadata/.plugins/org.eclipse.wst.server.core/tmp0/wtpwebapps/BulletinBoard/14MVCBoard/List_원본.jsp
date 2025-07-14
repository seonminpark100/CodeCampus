<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>자유게시판</title>
		<style> 
			a{text-decoration: none;}
		</style> 
	</head>
	<body>
		<jsp:include page="../Common/Link.jsp"></jsp:include>
		
		<div class="container">
		<h2>자유게시판 - 현재 페이지: ${ map.pageNum }</h2>
		<form method="get">  <!-- 액션 값을 정하지 않았으므로 현재페이지로 전송됨. -->
	    <table border="1" width="90%">
	    <tr>
	        <td align="center">
	            <select name="searchField"> 
	                <option value="title">제목</option> 
	                <option value="content">내용</option>
	            </select>
	            <input type="text" name="searchWord" />
	            <input type="submit" value="검색하기" />
	        </td>
	    </tr>   
	    </table>
	    </form>
	    
	    <table border="1" width="90%">
	        <tr>
	            <th width="10%">번호</th>
	            <th width="*">제목</th>
	            <th width="15%">작성자</th>
	            <th width="10%">조회수</th>
	            <th width="15%">작성일</th>
	            <th width="8%">첨부</th>
	        </tr>

       <c:choose>
	       	<c:when test="${ empty boardLists }">
	       		<tr>
		            <td colspan="6" align="center">
		                등록된 게시물이 없습니다^^*
		            </td>
		        </tr>
	       	</c:when>
	       	<c:otherwise>
	       		<c:forEach items = "${ boardLists }" var="row" varStatus="loop">
	       		<tr align="center">
		            <td>
		            <!-- 전체 게시물갯수 - (((페이지번호-1) * 페이지당 게시물수) + 해당루프의 index) -->
		            	${ map.totalCount - (( (map.pageNum - 1) * map.pageSize) + loop.index)}
		            </td> 
		            <td align="left"> 
		             <!-- 제목을 누르면  View.jsp로 넘어가는데 이때, num을 url로 보낸다-->
		                <a href="../mvcboard/view.do?idx=${ row.idx }"> ${ row.title }</a>
		            </td>
		            <td> ${ row.name }</td>           
		            <td> ${ row.visitcount }</td>   
		            <td> ${ row.postdate }</td>   
		            <td>
		            <c:if test="${ not empty row.ofile }">
		            	<a href="../mvcboard/download.do?ofile=${ row.ofile }&sfile=${ row.sfile}&idx=${row.idx}">
		            	[Down]</a>
		            </c:if>
		            </td>    
		        </tr>
	       		</c:forEach>
	       	</c:otherwise>
       </c:choose>
       
	    </table>
	    <!-- 하단 메뉴(바로가기, 글쓰기) -->
	    <table border="1" width="90%">
	        <tr align="center">
	        	<td>
	        		${ map.pagingImg }
	        	</td>
	            <td width="100"><button type="button" onclick="location.href='../mvcboard/write.do';">글쓰기</button></td>
	        </tr>
	    </table>
	    </div> <br/>
	    <jsp:include page="../Common/Bottom.jsp"></jsp:include>
	</body>
</html>