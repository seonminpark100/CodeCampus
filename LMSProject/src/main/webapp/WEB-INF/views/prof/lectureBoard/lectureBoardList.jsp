<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>대학교 eCampus</title>
		
	</head>
	<body>
	<%@ include file = "../sidebars.jsp" %>
	<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
        <div class="pt-3">
		<h2>강의 목록 </h2>

		<!-- 검색 폼 -->
		<form method="get" action="/prof/lectureList.do">
		 <input type="hidden" name="lectureCode" value="${ lectureCode }" />
		<table class="table table-hover" border="1" width="90%">
		<tr>
			<td>
				<select name="searchField">
					<option value="board_title">강의명</option>
				</select>
				<input type="text" name="searchKeyword" />
				<input type="submit" value="검색하기" />
			</td>
		</tr>
		</table>		
		</form>
		
		<!-- 목록 테이블 -->
	    <table class="table table-hover" border="1" width="90%">
	        <tr align="ce">
	            <th width="15%">번호</th>
	            <th width="*">강의명</th>
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
	                <a href="./lectureView.do?lectureCode=${row.lecture_code}&board_idx=${row.board_idx}">${ row.board_title }</a> 
	            </td> 
	        </tr>
	        </c:forEach>        
	    </c:otherwise>    
	</c:choose>
	    </table>
	    
	    <!-- 하단 메뉴(바로가기, 글쓰기) -->
	    <table class="table table-hover" border="1" width="90%">
	        <tr align="center">
	            <td>
	                ${ pagingImg }
	            </td>
	            <td width="200px"><button type="button" class="btn btn-dark"
	                onclick="location.href='lectureUpload.do?lectureCode=${ lectureCode }';">강의 업로드</button>
	            </td>
	        </tr>
	    </table>
		</div>
		</main>
	</body>
</html>