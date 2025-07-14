<%@ page language="java" contentType="text/html; charset=UTF-8"
<<<<<<< HEAD
     pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>OO대학교 eCampus</title><title>home 화면</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="./js/commons.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.17/index.global.min.js"></script>
        
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

            // 공지사항 로드
            loadLatestNotices(); // 페이지 로드 시 공지사항 로드 함수 호출

            // 공지사항을 로드하는 함수
            function loadLatestNotices() {
                $.ajax({
                    url: "/noticeboard/api/latestNotices", // 새로 만든 공지사항 API 엔드포인트
                    method: "GET",
                    dataType: "json", // JSON 형태로 데이터를 받음
                    success: function(notices) {
                        var noticeListDiv = $('#latestNotices'); // 공지사항을 표시할 div
                        noticeListDiv.empty(); // 기존 내용 비우기

                        if (notices.length > 0) {
                            var html = '<ul>';
                            $.each(notices, function(index, notice) {
                                // boardIdx를 사용하여 상세 보기 링크 생성
                                html += '<li><a href="/noticeboard/noticeview.do?boardIdx=' + notice.boardIdx + '">';
                                html += '[' + notice.category + '] ' + notice.boardTitle;
                                html += '</a></li>';
                            });
                            html += '</ul>';
                            noticeListDiv.append(html);
                        } else {
                            noticeListDiv.append('<p>등록된 공지사항이 없습니다.</p>');
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.error("Failed to load notices: " + textStatus, errorThrown);
                        $('#latestNotices').append('<p>공지사항을 불러오는 데 실패했습니다.</p>');
                    }
                });
            }
        });
        </script>
        <style>
            .container {
                width: 90%; /* 전체 컨테이너 너비 */
                max-width: 1200px; /* 최대 너비 설정 */
                margin: 20px auto; /* 중앙 정렬 */
                display: flex; /* Flexbox 사용 */
                flex-wrap: wrap; /* 내용이 넘치면 다음 줄로 */
                gap: 20px; /* 요소들 간의 간격 */
                justify-content: center; /* 요소들을 중앙으로 정렬 */
            }

            .bl-item { /* 캘린더, 퀵메뉴, 공지사항을 위한 공통 클래스 */
                background-color: #e0f2f7;
                border: 1px solid #b3e5fc;
                padding: 15px;
                border-radius: 8px;
                box-sizing: border-box;
                flex: 1; /* 기본적으로 공간을 균등하게 차지 */
                min-width: 300px; /* 최소 너비 설정 (좁은 화면에서 한 줄에 하나씩) */
                max-width: 48%; /* 큰 화면에서 한 줄에 2개씩 */
            }
            /* FullCalendar 전용 스타일 (캘린더 크기 조절) */
            #fullcalendar-container {
                height: 400px; /* 고정 높이 설정 */
                /* width: 100%; */ /* .bl-item이 이미 100%를 차지하려고 하므로 필요 없음 */
            }
            .bluelight h2 {
                color: #01579b;
                margin-top: 0;
                margin-bottom: 15px;
                font-size: 1.5em;
            }
            .bluelight button {
                background-color: #007bff;
                color: white;
                padding: 8px 15px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-right: 5px;
                margin-bottom: 5px;
            }
            .bluelight button:hover {
                background-color: #0056b3;
            }
            .bluelight ul {
                list-style: none;
                padding: 0;
                margin: 0;
            }
            .bluelight ul li {
                margin-bottom: 8px;
            }
            .bluelight ul li a {
                text-decoration: none;
                color: #333;
                font-size: 1.1em;
            }
            .bluelight ul li a:hover {
                color: #007bff;
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
    <%@ include file = "top.jsp" %>
        <div class="container">
            <div id="fullcalendar-container" class="bl-item bluelight">
                <h2>학과 일정</h2>
            </div>
            
            <div class="bl-item bluelight">
                <h2>Quick Menu</h2>
                <button>개설과목</button>
                <button>FAQ</button>
                <a href="/qnaboard/qnaboardlist.do">
                    <button>Q&A 게시판</button>
                </a>
            </div>
            
            <div class="bl-item bluelight">
                <h2>최신 공지사항</h2>
                <div id="latestNotices">
                    <p>공지사항을 불러오는 중...</p>
                </div>
                <a href="/noticeboard/noticelist.do"> <button style="margin-top: 10px;">전체 공지사항</button>
                </a>
            </div>
        </div>
    </body>
=======
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>OO대학교 eCampus</title>
		
		<style>
		* {
		  box-sizing: border-box;
		}
		
		body {
		  font-family: Arial;
		  padding: 10px;
		  background: #f1f1f1;
		}
		
		/* Header/Blog Title */
		.header {
		  padding: 30px;
		  text-align: center;
		  background: white;
		}
		
		.header h1 {
		  font-size: 50px;
		}
		
		/* Style the top navigation bar */
		.topnav {
		  overflow: hidden;
		  background-color: #333;
		}
		
		/* Style the topnav links */
		.topnav a {
		  float: left;
		  display: block;
		  color: #f2f2f2;
		  text-align: center;
		  padding: 14px 16px;
		  text-decoration: none;
		}
		
		/* Change color on hover */
		.topnav a:hover {
		  background-color: #ddd;
		  color: black;
		}
		
		/* Create two unequal columns that floats next to each other */
		/* Left column */
		.leftcolumn {   
		  float: left;
		  width: 75%;
		}
		
		/* Right column */
		.rightcolumn {
		  float: left;
		  width: 25%;
		  background-color: #f1f1f1;
		  padding-left: 20px;
		}
		
		/* Fake image */
		.fakeimg {
		  background-color: #aaa;
		  width: 100%;
		  padding: 20px;
		}
		
		/* Add a card effect for articles */
		.card {
		  background-color: white;
		  padding: 20px;
		  margin-top: 20px;
		}
		
		/* Clear floats after the columns */
		.row::after {
		  content: "";
		  display: table;
		  clear: both;
		}
		
		/* Footer */
		.footer {
		  padding: 20px;
		  text-align: center;
		  background: #ddd;
		  margin-top: 20px;
		}
		
		/* Responsive layout - when the screen is less than 800px wide, make the two columns stack on top of each other instead of next to each other */
		@media screen and (max-width: 800px) {
		  .leftcolumn, .rightcolumn {   
		    width: 100%;
		    padding: 0;
		  }
		}
		
		/* Responsive layout - when the screen is less than 400px wide, make the navigation links stack on top of each other instead of next to each other */
		@media screen and (max-width: 400px) {
		  .topnav a {
		    float: none;
		    width: 100%;
		  }
		}
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
		<div class="row">
		  <div class="leftcolumn">
		    <div class="card">
		      <h2>TITLE HEADING</h2>
		      <h5>Title description, Dec 7, 2017</h5>
		      <div class="fakeimg" style="height:200px;">Image</div>
		      <p>Some text..</p>
		      <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
		    </div>
		    <div class="card">
		      <h3>학교 주요 일정</h3>
		      <p class="container" id="calendar"></p>
		    </div>
		    <div class="card">
		      <h2>TITLE HEADING</h2>
		      <h5>Title description, Sep 2, 2017</h5>
		      <div class="fakeimg" style="height:200px;">Image</div>
		      <p>Some text..</p>
		      <p>Sunt in culpa qui officia deserunt mollit anim id est laborum consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
		    </div>
		  </div>
		  <div class="rightcolumn">
		    <div class="card">
		      <h2>About Me</h2>
		      <div class="fakeimg" style="height:100px;">Image</div>
		      <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
		    </div>
		    <div class="card">
		      <h3>Popular Post</h3>
		      <div class="fakeimg"><p>Image</p></div>
		      <div class="fakeimg"><p>Image</p></div>
		      <div class="fakeimg"><p>Image</p></div>
		    </div>
		    
		  </div>
		</div>
		
	</body>
>>>>>>> origin/master
</html>