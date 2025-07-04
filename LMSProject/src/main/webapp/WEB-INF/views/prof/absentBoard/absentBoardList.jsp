<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>대학교 eCampus</title>
		<!-- css -->
		<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
		<!-- js -->
		<!-- <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
		 -->
	</head>
	<body>
		<%@ include file = "../top.jsp" %>
		<div class="container">
		<h2>출석 관리</h2>
		<input type="date" id="ATTENDANCE_TIME" name="ATTENDANCE_TIME" onchange="checkValue()">
		<script>
		  function checkValue() {
		    const inputValue = document.getElementById("ATTENDANCE_TIME").value;
		    console.log("입력 값:", inputValue);
		    // 추가적인 로직 구현 가능
		  }
		</script>
		
		<!-- 검색 폼 -->
		<form method="get">
		<table border="1" width="90%">
		<tr>
			<td>
				<select name="searchField">
					<option value="user_id">학번</option>
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
	            <th width="">사진</th>
	            <th width="">학과</th>
	            <th width="">학번</th>
	            <th width="">이름</th>
	            <th width="">출석</th>
	            <th width="">결석</th>
	            <th width="">지각</th>
	            <th width="">기타</th>
	        </tr>
	        <c:choose>
	    <c:when test="${ empty lists }"> 
	        <tr>
	            <td colspan="5" align="center">
	                수강생이 없습니다.
	            </td>
	        </tr>
	    </c:when> 
	    <c:otherwise> 
	        <c:forEach items="${ lists }" var="row" varStatus="loop">
		        <tr align="center">
		            <td><img width="40px" alt="사진" src="../images/logo.jpeg"></td> 
		            <td>${ row.user_major }</td> 
		            <td>${ row.user_id }</td> 
		            <td>${ row.user_name }</td> 
		            <!-- 라디오의 name이 달라야 각각읜 학생의 출결 관리 가능 -->
			        <td> <input type="radio" name="ABSENT_STATE_${ row.user_id }" value="출석">출석 </td>
		            <td> <input type="radio" name="ABSENT_STATE_${ row.user_id }" value="결석">결석 </td>
		            <td> <input type="radio" name="ABSENT_STATE_${ row.user_id }" value="지각">지각 </td>
	       		</tr>
	        </c:forEach>        
	    </c:otherwise>    
	</c:choose>
	    </table>
		
		<!-- 하단 메뉴(바로가기) -->
	    <table border="1" width="90%">
	        <tr align="center">
	            <td>
	                ${ pagingImg }
	            </td>
	            <td width="100"><button type="button"
	                onclick="location.href='./absentProc.do';">저장</button>
	            </td>
	        </tr>
	    </table>
		</div>
	</body>
</html>