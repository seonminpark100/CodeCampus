<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>대학교 eCampus</title>
<<<<<<< HEAD
	</head>
	<body>
	<%@ include file = "../top.jsp" %>
		<div class="container">
		<h2>강의 목록 </h2>

		<!-- 검색 폼 -->
		<form method="get">
		<table border="1" width="90%">
		<tr>
			<td>
				<select name="searchField">
					<option value="lecture_name">강의명</option>
					<option value="prof_name">교수명</option>
=======
		
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
>>>>>>> origin/master
				</select>
				<input type="text" name="searchKeyword" />
				<input type="submit" value="검색하기" />
			</td>
		</tr>
		</table>		
		</form>
		
		<!-- 목록 테이블 -->
<<<<<<< HEAD
	    <table border="1" width="90%">
	        <tr>
	            <th width="10%">번호</th>
	            <th width="*">강의명</th>
	            <th width="15%">교수명</th>
	            <th width="10%">시작일</th>
	            <th width="15%">종료일</th>
=======
	    <table class="table table-hover" border="1" width="90%">
	        <tr align="ce">
	            <th width="15%">번호</th>
	            <th width="*">강의명</th>
>>>>>>> origin/master
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
<<<<<<< HEAD
	                <a href="./view.do?idx=${row.idx}&vNum=${vNum}">${ row.LECTURE_NAME }</a> 
	            </td> 
	            <td>${ row.PROF_NAME }</td> 
	            <td>${ row.LECTURE_START_DATE }</td> 
	            <td>${ row.LECTURE_END_DATE }</td> 
=======
	                <a href="./lectureView.do?lectureCode=${row.lecture_code}&board_idx=${row.board_idx}">${ row.board_title }</a> 
	            </td> 
>>>>>>> origin/master
	        </tr>
	        </c:forEach>        
	    </c:otherwise>    
	</c:choose>
	    </table>
	    
	    <!-- 하단 메뉴(바로가기, 글쓰기) -->
<<<<<<< HEAD
	    <table border="1" width="90%">
=======
	    <table class="table table-hover" border="1" width="90%">
>>>>>>> origin/master
	        <tr align="center">
	            <td>
	                ${ pagingImg }
	            </td>
<<<<<<< HEAD
	            <td width="100"><button type="button"
	                onclick="location.href='lectureUpload.do';">강의 업로드</button>
=======
	            <td width="200px"><button type="button" class="btn btn-dark"
	                onclick="location.href='lectureUpload.do?lectureCode=${ lectureCode }';">강의 업로드</button>
>>>>>>> origin/master
	            </td>
	        </tr>
	    </table>
		</div>
<<<<<<< HEAD
=======
		</main>
>>>>>>> origin/master
	</body>
</html>