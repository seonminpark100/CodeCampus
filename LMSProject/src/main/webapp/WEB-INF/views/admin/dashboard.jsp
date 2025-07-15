<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CodeCampus 관리자 대시보드</title>
<style>
    /* body 전체 레이아웃: flex를 사용하여 사이드바와 메인 컨텐츠 영역 분리 */
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        background-color: #f4f4f4;
        display: flex; /* Flexbox를 사용하여 전체 페이지 레이아웃 구성 */
        min-height: 100vh; /* 최소 높이를 뷰포트 전체 높이로 설정 */
    }

    /* 관리자 사이드바 (왼쪽 메뉴 영역) */
    .admin-sidebar {
        background-color: #f8f9fa; /* 배경색 */
        width: 220px; /* 사이드바 너비 */
        padding: 20px;
        box-sizing: border-box;
        border-right: 1px solid #dee2e6; /* 오른쪽 경계선 */
        display: flex;
        flex-direction: column;
        align-items: center; /* 사이드바 내 아이템들을 중앙으로 정렬 (제목, 메뉴 버튼) */
    }

    /* '관리자 시스템' 제목 링크 스타일 */
    .admin-sidebar .admin-title-link {
        color: #333;
        text-decoration: none; /* 기본 밑줄 제거 */
        font-size: 1.5em; /* 제목 크기 조절 */
        font-weight: bold; /* 굵게 */
        margin-top: 0;
        margin-bottom: 25px; /* 하단 여백 추가 */
        text-align: center; /* 중앙 정렬 */
        cursor: pointer; /* 마우스 오버 시 포인터 변경 */
        transition: color 0.2s ease;
    }

    .admin-sidebar .admin-title-link:hover {
        color: #007bff; /* 호버 시 파란색으로 변경 (선택 사항) */
    }


    .admin-sidebar ul {
        list-style: none;
        padding: 0;
        margin: 0;
        width: 100%; /* 메뉴 목록의 너비를 사이드바에 맞춤 */
    }

    .admin-sidebar li {
        margin-bottom: 8px; /* 각 메뉴 아이템 간 간격 */
    }

    /* 모든 메뉴 버튼에 대한 기본 스타일 */
    .admin-sidebar .btn {
        display: block; /* 블록 요소로 만들어 너비 100% 적용 */
        padding: 10px 15px; /* 내부 여백 */
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 1em;
        text-align: center; /* 버튼 내 텍스트 중앙 정렬 */
        text-decoration: none; /* 링크 밑줄 제거 */
        width: 100%; /* 너비 100% */
        box-sizing: border-box; /* 패딩, 보더가 너비에 포함되도록 */
        transition: background-color 0.2s ease, color 0.2s ease;
    }

    /* 파란색 버튼 스타일 통일 */
    .admin-sidebar .btn-blue {
        background-color: #007bff; /* 기본 파란색 */
        color: white;
    }

    .admin-sidebar .btn-blue:hover {
        background-color: #0056b3; /* 호버 시 진한 파란색 */
    }

    /* 활성화된 버튼 스타일 (Gemini UI처럼 강조) */
    .admin-sidebar .btn.active {
        background-color: #e0e0e0; /* 연한 회색 배경 */
        color: #007bff; /* 파란색 텍스트 */
        font-weight: bold;
        box-shadow: 0 1px 3px rgba(0,0,0,0.1); /* 그림자 효과 */
    }
    .admin-sidebar .btn.active:hover {
         background-color: #d0d0d0; /* 활성 상태에서 호버 시 */
    }


    /* 메인 컨텐츠 영역 (사이드바 옆의 나머지 공간) */
    .admin-content {
        flex-grow: 1; /* 남은 공간을 모두 차지 */
        padding: 20px;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        align-items: flex-end; /* 로그인/로그아웃 링크를 우측 상단으로 이동 */
    }

    /* 메인 컨텐츠 영역 내의 로그인/로그아웃 섹션 */
    .admin-content .login {
        margin-bottom: 20px;
        width: 100%; /* 너비를 100%로 설정하여 우측 정렬 */
        text-align: right; /* 로그아웃 링크 우측 정렬 */
    }

    .admin-content .login ul {
        list-style: none;
        padding: 0;
        margin: 0;
        display: inline-block; /* ul을 inline-block으로 만들어 텍스트 정렬 적용 */
    }

    .admin-content .login ul li {
        display: inline-block; /* li를 inline-block으로 만들어 가로 정렬 */
        margin-left: 15px; /* 링크 사이 간격 */
    }

    .admin-content .login .nav-link {
        text-decoration: none;
        color: #007bff; /* 파란색 */
        font-weight: bold;
    }

    .admin-content .login .nav-link:hover {
        color: #0056b3; /* 호버 시 진한 파란색 */
    }

    /* 메인 컨텐츠 영역의 실제 내용에 대한 스타일 */
    .main-content-area {
        background-color: #fff;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        /* ★★★ 변경된 부분 시작 ★★★ */
        width: 70vw; /* 뷰포트 너비의 70%를 차지 */
        max-width: 1200px; /* 최대 너비 설정 (너무 커지지 않게) */
        margin: 20px auto; /* 상단 간격 유지, 좌우 마진 auto로 중앙 정렬 */
        /* ★★★ 변경된 부분 끝 ★★★ */
        box-sizing: border-box;
        text-align: left; /* 텍스트 정렬은 왼쪽으로 변경 */
    }
    .main-content-area h2 {
        color: #333;
        margin-bottom: 20px;
        text-align: center; /* 섹션 제목은 중앙 정렬 */
    }
    .main-content-area h3 {
        color: #555;
        margin-top: 25px;
        margin-bottom: 15px;
    }

    /* 통계 섹션 스타일 */
    .statistics-section p {
        font-size: 1.1em;
        margin-bottom: 10px;
    }
    .statistics-section strong {
        color: #007bff;
        font-size: 1.2em;
    }

    /* 테이블 스타일 */
    .statistics-section table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 15px;
        box-shadow: 0 1px 3px rgba(0,0,0,0.1);
    }
    .statistics-section th, .statistics-section td {
        border: 1px solid #ddd;
        padding: 10px;
        text-align: left;
    }
    .statistics-section th {
        background-color: #f2f2f2;
        font-weight: bold;
        color: #333;
    }
    .statistics-section tr:nth-child(even) {
        background-color: #f9f9f9;
    }
    .statistics-section tr:hover {
        background-color: #f1f1f1;
    }

</style>

</head>
<body>
	
    <div class="admin-sidebar">
        <a href="<c:url value='/admin/dashboard'/>" class="admin-title-link">관리자 시스템</a>
        <ul>
        	<li><a href="<c:url value='/'/>"
                class="btn btn-blue">홈 화면으로</a></li>
            <li><a href="<c:url value='/admin/accountedit/list.do'/>"
                class="btn btn-blue">회원 관리 화면</a></li>
            <li><a href="<c:url value='/admin/create'/>"
                class="btn btn-blue">계정 생성</a></li>
            <li>
                <a href="<c:url value='/admin_lectureboard/lecturelist.do'/>" class="btn btn-blue">전체 강의/과목 관리</a>
            </li>
            <li>
                <a href="<c:url value='/noticeboard/noticelist.do'/>" class="btn btn-blue">공지사항 등록/수정/삭제</a>
            </li>
            <li>
            	<a href="<c:url value='/qnaboard/qnaboardlist.do'/>" class="btn btn-blue">Q&A 게시판</a>
            </li>
        </ul>
    </div>
    <div class="admin-content">
        <div class="login">
            <c:choose>
                <c:when test="${empty principal}">
                    <ul class="navbar-nav">
                        <li class="nav-item"><a class="nav-link" href="/myLogin.do">로그인</a></li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <ul class="navbar-nav">
                        <li class="nav-item"><a class="nav-link" href="/myLogout.do">로그아웃</a></li>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="main-content-area">
            <h2>강의 통계 대시보드</h2>
			<h3>학생 현황</h3>
                <p>총 학생 수: <strong>${totalStudentCount}</strong>명</p>
            <div class="statistics-section">
                <h3>전체 강의 현황</h3>
                <p>총 강의 수: <strong>${totalLectureCount}</strong>개</p>

                <h3>신규 강의 목록 (최신 5개)</h3>
                <c:if test="${not empty newLectures}">
                    <table>
                        <thead>
                            <tr>
                                <th>강의명</th>
                                <th>교수명</th>
                                <th>시작 날짜</th>
                                <th>종료 날짜</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="lecture" items="${newLectures}">
                            <tr>
                                <td>${lecture.lecture_Name}</td>         
                        		<td>${lecture.prof_Id}</td>             
                        		<td>${lecture.lecture_Start_Date}</td>
                        		<td>${lecture.lecture_End_Date}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    </table>
                </c:if>
                                <c:if test="${empty newLectures}">
                    <p>신규 강의 데이터가 없습니다.</p>
                </c:if>

                 <h3>인기 게시글 (조회수 많은 순 3개)</h3> 
                <c:if test="${not empty topVisitedBoards}"> 
                    <table>
                        <thead>
                            <tr>
                                <th>게시글 제목</th>
                                <th>작성자 ID</th>
                                <th>작성일</th>
                                <th>조회수</th> 
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="board" items="${topVisitedBoards}"> 
                            <tr>
                                <td title="${board.board_Content}">
                                    ${board.board_Title}
                                </td>
                                <td>${board.user_Id}</td>
                                <td>${board.board_PostDate}</td>
                                <td><strong>${board.visitCount}</strong></td> 
                            </tr>
                        </c:forEach>
                    </tbody>
                    </table>
                </c:if>
                <c:if test="${empty topVisitedBoards}"> 
                    <p>조회수가 많은 게시글 데이터가 없습니다.</p> 
                </c:if>
                
            </div>
        </div>
    </div>
</body>
</html>