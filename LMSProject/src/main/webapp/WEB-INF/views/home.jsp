<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
	<title>메인 화면 - CodeCampus</title>
	<link rel="stylesheet" href="/css/style.css">
	</head>
	<body>
		<header>
    <div class="header-left">
        <a href="main.jsp">
            <img src="images/logo.png" alt="CodeCampus 로고" class="logo">
        </a>
        <nav class="main-nav">
            <div class="menu-item">
                <a href="#" class="menu-link" data-menu="education">교육현황</a>
                <div class="sub-menu" id="sub-menu-education">
                    <a href="#">온라인 강의</a>
                    <a href="#">오프라인 수업</a>
                    <a href="#">수강 신청</a>
                </div>
            </div>
            <div class="menu-item">
                <a href="#" class="menu-link" data-menu="community">커뮤니티</a>
                <div class="sub-menu" id="sub-menu-community">
                    <a href="#">공지사항</a>
                    <a href="#">자유 게시판</a>
                    <a href="#">Q&A</a>
                </div>
            </div>
            <div class="menu-item">
                <a href="#" class="menu-link" data-menu="about">소개</a>
                <div class="sub-menu" id="sub-menu-about">
                    <a href="#">학원 소개</a>
                    <a href="#">강사진</a>
                    <a href="#">오시는 길</a>
                </div>
            </div>
        </nav>
    </div>
    <div class="login-btn">
        <a href="/myLogin.do">로그인</a>
    </div>
</header>

     <div class="container">
        <div class="left-section">
            <div class="schedule-container">
                <h2>일정</h2>
                <p>
                    이곳은 학원 일정 정보를 표시하는 영역입니다.
                    데이터베이스에서 가져온 최신 일정 정보를 동적으로 표시합니다.
                </p>
                <ul>
                    <li>2025년 7월 1일 - 여름방학 특강 개강</li>
                    <li>2025년 7월 15일 - 파이썬 기초반 종강</li>
                    <li>2025년 8월 1일 - 웹 개발 고급 과정 모집 시작</li>
                    <li>2025년 8월 10일 - 해커톤 대회 안내</li>
                </ul>
                <p><a href="schedule_full.jsp">전체 일정 보기</a></p>
            </div>
            </div>

        <div class="right-section">
            <div>
                <h3>Quick Menu</h3>
                <div class="quick-menu-grid">
                    <a href="course_list.jsp">개설과목</a>
                    <a href="faq.jsp">FAQ</a>
                    <a href="mypage.jsp">마이페이지</a>
                    <a href="contact.jsp">오시는 길</a>
                </div>
            </div>

            <div>
                <h3>공지사항</h3>
                <ul>
                    <li><a href="notice_detail.jsp?id=1">공지사항 제목 1입니다. (2025.06.28)</a></li>
                    <li><a href="notice_detail.jsp?id=2">새로운 강의가 개설되었습니다. (2025.06.25)</a></li>
                    <li><a href="notice_detail.jsp?id=3">시스템 점검 안내 (2025.06.20)</a></li>
                    <li><a href="notice_detail.jsp?id=4">수강료 할인 이벤트! (2025.06.15)</a></li>
                    <%--
                    <jsp:useBean id="noticeList" class="java.util.ArrayList" scope="request" />
                    <%
                        // 실제 DB에서 공지사항 데이터를 가져오는 로직 (서블릿에서 처리 후 request에 저장)
                        // List<NoticeVO> notices = (List<NoticeVO>) request.getAttribute("noticeList");
                        // if (notices != null) {
                        //    for (NoticeVO notice : notices) {
                        //        out.println("<li><a href=\"notice_detail.jsp?id=" + notice.getId() + "\">" + notice.getTitle() + " (" + notice.getDate() + ")</a></li>");
                        //    }
                        // }
                    %>
                    --%>
                </ul>
                <p style="text-align: right;"><a href="notice_list.jsp">더보기</a></p>
            </div>
        </div>
    </div>

    <footer>
        <p>&copy; 2025 CodeCampus. All Rights Reserved.</p>
    </footer>

</body>
</html>