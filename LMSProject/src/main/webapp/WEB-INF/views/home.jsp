<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>OO대학교 eCampus</title><title>home 화면</title>
		<script src="./js/commons.js"></script>
		<link rel="stylesheet" href="./css/style.css" />
		<!-- FULL CALENDAR -->
		<script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.17/index.global.min.js"></script>
		<script>
	    document.addEventListener('DOMContentLoaded', function() {
	    var calendarEl = document.getElementById('calendar');
	    var calendar = new FullCalendar.Calendar(calendarEl, {
	      initialView: 'dayGridMonth'
	      });
	    calendar.render();
	    });
	  </script>
		
	</head>
	<body>
	<%@ include file = "top.jsp" %>
		<div class="container" id="calendar">
			<div class="bl-left">
	            <span class="bluelight">
	                <a href="#">일정 or 맵</a>
	            </span>
	        </div>
	        <div class="bl-right">
	            <span class="bluelight">
	                <h2>Quick Menu</h2>
	                <button>개설과목</button>
	                <button>FAQ</button>
	            </span>
	        </div>
	        <div class="bl-right">
	            <span class="bluelight">
	                <a href="#">
	                	<button>공지사항</button>
					</a>
	            </span>
	        </div>
		</div>
	</body>
	 <script>
    var calendarEl = document.getElementById('calendar');
    var request = $.ajax({
      url: "/calendar/event",
      method: "GET",
    });
    request.done(function(data){
      var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        events: data
      });
      calendar.render();		
    });
  </script>
</html>