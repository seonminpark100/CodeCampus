<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html> 
<html>
	<head>
		<meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
		<title>OO대학교 eCampus</title>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="./js/commons.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.17/index.global.min.js"></script>
	</head>
	<script>
		document.addEventListener('DOMContentLoaded', function() {
	        // var calendarEl = document.getElementById('calendar'); // 기존 코드
	        var calendarEl = document.getElementById('fullcalendar-container'); // 변경된 ID 사용
	        // FullCalendar 이벤트 로드
	        var requestCalendar = $.ajax({
	            url: "/calendar/event", // FullCalendar 이벤트를 제공하는 컨트롤러 엔드포인트
	            method: "GET",
	        });
	
	        requestCalendar.done(function(data){
	            var calendar = new FullCalendar.Calendar(calendarEl, {
	                initialView: 'dayGridMonth', // 초기 뷰 설정
	                events: data, // AJAX로 가져온 이벤트 데이터
	                // 캘린더 높이 조절 옵션 추가 (선택 사항)
	                // height: 'auto', // 내용에 따라 자동으로 높이 조절
	                // aspectRatio: 1.8 // 너비/높이 비율. 1보다 크면 가로가 길어짐
	                // contentHeight: 'auto', // 이벤트를 표시하는 영역의 높이만 조절
	                // initialDate: '2025-07-01' // 특정 날짜로 시작 (테스트용)
	            });
	            calendar.render(); // 캘린더 렌더링
	        });
	        
	        requestCalendar.fail(function(jqXHR, textStatus) {
                console.error("FullCalendar events loading failed: " + textStatus);
                var calendar = new FullCalendar.Calendar(calendarEl, {
                    initialView: 'dayGridMonth'
                });
                calendar.render();
            });
		});
	</script>
	<body class="sb-nav-fixed">
		<%@ include file = "sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
		    <div id="layoutSidenav_content" class="p-3" style="height: 50%; text-align: center;">
		    	<div class="container" style="text-align: center; max-width: 100%;">
				    <div class="row m-3" style="height: 540px">
				        <div class="col m-3">			       		
							 <div id="fullcalendar-container" >
				            </div>
				        </div>
				        <div class="col m-3 p-5" style="text-align: center;">
							<div class="row p-3" style="height: 50%; text-align: center;">
								<h3>Quick Menu</h3>
								<div class="col">
									<button class="btn btn-outline-success mx-1" type="button" onclick="location.href='registLecture/registLecture.do'" style="width: 20%">수강신청</button>
									<button class="btn btn-outline-success mx-1" type="button" onclick="location.href='lecture/myLectureList.do'" style="width: 20%">내 강의 목록</button>
									<button class="btn btn-outline-success mx-1" type="button" onclick="location.href='assignment/assignmentList.do'" style="width: 20%">내 과제</button>
								</div>
				            </div>
				    	</div>
				  	</div>
				</div>
		  	</div>
		</main>
	</body>
</html>