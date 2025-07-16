<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>  
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>OO대학교 eCampus</title>
		<style type="text/css">
		.bl-left { position:relative; float:left; width:50%; height:300px; display: flex; justify-content: center; align-items: center; }
		.bl-right{ position:relative; float:right; width:50%; height:300px; display: flex; justify-content: center; align-items: center; }
		</style>
		<script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.17/index.global.min.js"></script>
		<script>
		    document.addEventListener('DOMContentLoaded', function() {
		    var calendarEl = document.getElementById('calendar');
		    var calendar = new FullCalendar.Calendar(calendarEl, {
		      initialView: 'dayGridMonth',
		      aspectRatio: 1.5,
		      events: [
		    	  {title: '테스트 일정1', start: '2025-07-03'},
		    	  {title: '테스트 일정2', start: '2025-07-05'}
		      ]
		      });
		    calendar.render();
		    });
		  </script>
	  
	</head>
	
	<body>
	<%@ include file = "top.jsp" %>
	<div class="container">
		<div class="row">
		  <div class="leftcolumn">
		  
		    <div class="card">
		      <h2>내 강의 목록</h2>
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
				            <p><a href="./submain.do?lectureCode=${row.lecture_code}">${ row.lecture_name }</a></p><br/>
				        </c:forEach>        
				    </c:otherwise>    
				  </c:choose>
			    </div>
			    
			    <div class="card">
			      <h3>학교 주요 일정</h3>
			      <p class="container" id="calendar"></p>
			    </div>
		  	</div>
		 </div>
	  </div>
	</body>
</html>