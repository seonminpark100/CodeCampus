<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<<<<<<< HEAD
=======
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
>>>>>>> origin/master
<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>대학교 eCampus</title>
<<<<<<< HEAD
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
=======
		<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
		 <script>
			function insertData(selectedValue) {
			    const attendanceTime = document.getElementById("lecture_date").value;
			    if(attendanceTime === ''){
			        alert("날짜를 입력해주세요.");
			        document.getElementById("lecture_date").focus();
			        return false;
			    }
			    var xhr = new XMLHttpRequest();
			    xhr.open("POST", "/prof/absentProc.do", true);
			    xhr.setRequestHeader("Content-Type", "application/json");
			    xhr.onreadystatechange = function() {
			        if (xhr.readyState === 4 && xhr.status === 200) {
			            alert("저장 성공!");
			        }
			    };
			    xhr.send(JSON.stringify({ data: selectedValue, date: attendanceTime }));
			}
		</script>
	</head>
	<body>
		<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
		<h2>출결 수동 관리</h2>
		<!-- 검색 폼 -->
		<form method="get" action="/prof/absentboard.do">
		<input type="hidden" name="lectureCode" value="${ lectureCode }" />
		<table class="table table-hover" border="1" style="width: 90%">
>>>>>>> origin/master
		<tr>
			<td>
				<select name="searchField">
					<option value="user_id">학번</option>
<<<<<<< HEAD
					<option value="content">내용</option>
=======
					<option value="user_name">이름</option>
>>>>>>> origin/master
				</select>
				<input type="text" name="searchKeyword" />
				<input type="submit" value="검색하기" />
			</td>
		</tr>
		</table>		
		</form>
		
<<<<<<< HEAD
		<!-- 목록 테이블 -->
	    <table border="1" width="90%">
=======
		<form name="writeFrm" method="post" enctype="multipart/form-data"
			action="./absentProc.do?lectureCode=${ lectureCode }" onsubmit="return validateForm(this);">
		
		출석 날짜: <input type="date" id="lecture_date" name="lecture_date">
		<!-- 목록 테이블 -->
	    <table class="table table-hover" border="1" style="width: 90%;">
>>>>>>> origin/master
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
<<<<<<< HEAD
	            <td colspan="5" align="center">
=======
	            <td colspan="8" align="center">
>>>>>>> origin/master
	                수강생이 없습니다.
	            </td>
	        </tr>
	    </c:when> 
	    <c:otherwise> 
	        <c:forEach items="${ lists }" var="row" varStatus="loop">
		        <tr align="center">
<<<<<<< HEAD
		            <td><img width="40px" alt="사진" src="../images/logo.jpeg"></td> 
		            <td>${ row.USER_MAJOR }</td> 
		            <td>${ row.USER_ID }</td> 
		            <td>${ row.USER_NAME }</td> 
		            <!-- 라디오의 name이 달라야 각각읜 학생의 출결 관리 가능 -->
			        <td> <input type="radio" name="ABSENT_STATE_${ row.USER_ID }" value="출석">출석 </td>
		            <td> <input type="radio" name="ABSENT_STATE_${ row.USER_ID }" value="결석">결석 </td>
		            <td> <input type="radio" name="ABSENT_STATE_${ row.USER_ID }" value="지각">지각 </td>
=======
		            <td>
		            	<c:choose>
		            		<c:when test="${ row.savefile eq null }">
		            			<img width="40px" alt="사진" src="../images/person.png">
		            		</c:when>
		            		<c:otherwise>
		            			<img width="40px" alt="사진" src="../images/${ row.savefile }">
		            		</c:otherwise>
		            	</c:choose>
		            	<%-- <c:if test="${ row.savefile } == null">
			            	<img width="40px" alt="사진" src="../images/logo.jpeg">
		            	</c:if>
			            	<img width="40px" alt="사진" src="../images/${ row.savefile }"> --%>
		            </td> 
		            <td>${ row.major_id }</td> 
		            <td> <input name="user_id" value="${ row.user_id }" readonly="readonly"></td> 
		            <td>${ row.user_name }</td> 
		            
		            <!-- 라디오의 name이 달라야 각각읜 학생의 출결 관리 가능 -->
 			        <td> <input type="radio" name="absent_state_${ row.user_id }" value="출석" onclick="insertData('출석.${ row.user_id }.${lectureCode }');">출석 </td>
		            <td> <input type="radio" name="absent_state_${ row.user_id }" value="결석" onclick="insertData('결석.${ row.user_id }.${lectureCode }');">결석 </td>
		            <td> <input type="radio" name="absent_state_${ row.user_id }" value="지각" onclick="insertData('지각.${ row.user_id }.${lectureCode }');">지각 </td>
>>>>>>> origin/master
	       		</tr>
	        </c:forEach>        
	    </c:otherwise>    
	</c:choose>
	    </table>
<<<<<<< HEAD
		
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
=======
		<!-- 날짜 -->
		
	    
		<!-- 하단 메뉴(바로가기) -->
	    <!-- <table class="table table-hover" border="1" width="90%">
	        <tr align="center">

	            <td width="100">
	            	<button class="btn btn-dark" type="submit">작성 완료</button>
	            </td>
	        </tr>
	    </table> -->
	    </form>
		</main>
>>>>>>> origin/master
	</body>
</html>