<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>  
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 목록</title>
		<style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f4f4f4;
                display: flex; /* Flexbox를 사용하여 컨텐츠 중앙 정렬 */
                flex-direction: column; /* 세로 방향으로 정렬 */
                align-items: center; /* 가로 중앙 정렬 */
                min-height: 90vh; /* 뷰포트 높이의 최소 90% */
            }

            /* 컨테이너 스타일 (회원 목록 페이지와 동일) */
            .container {
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                max-width: 1000px; /* 테이블이 넓을 수 있으므로 max-width 증가 */
                width: 100%; /* 너비 100% 사용 (max-width 내에서) */
                margin: 20px auto; /* 상하 여백, 좌우 자동 (중앙 정렬) */
                box-sizing: border-box; /* 패딩이 너비에 포함되도록 */
            }

            h2 {
                color: #333;
                text-align: center;
                margin-bottom: 30px;
            }

            /* 검색 폼 스타일 (회원 목록 페이지와 동일) */
            .search-form-group {
                margin-bottom: 20px;
                display: flex; /* 요소를 한 줄에 정렬 */
                gap: 10px; /* 요소들 간의 간격 */
                justify-content: center; /* 가로 중앙 정렬 */
                align-items: center; /* 세로 중앙 정렬 */
                width: 100%; /* 검색 폼도 컨테이너 너비에 맞춤 */
                max-width: 600px; /* 검색 폼 최대 너비 */
                margin-bottom: 30px; /* 테이블과의 간격 확보 */
            }
            .search-form-group select,
            .search-form-group input[type="text"] {
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 1em;
                flex-grow: 1; /* 가용 공간을 차지하도록 */
                max-width: 200px; /* 최대 너비 제한 */
            }
            .search-form-group input[type="submit"] {
                background-color: #007bff;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 1em;
                transition: background-color 0.3s ease;
            }
            .search-form-group input[type="submit"]:hover {
                background-color: #0056b3;
            }

            /* 테이블 스타일 (회원 목록 페이지와 동일) */
            table {
                width: 100%;
                border-collapse: collapse; /* 테이블 셀 간의 여백 제거 */
                margin-top: 0; /* 검색폼과 테이블 사이 간격은 search-form-group에서 조정 */
                margin-bottom: 20px; /* 페이징/버튼과의 간격 */
            }

            table, th, td {
                border: 1px solid #ddd; /* 모든 경계선 통일 */
            }

            th, td {
                padding: 12px 15px; /* 패딩 증가 */
                text-align: left;
            }

            th {
                background-color: #f2f2f2;
                color: #555;
                font-weight: bold;
            }

            tr:nth-child(even) {
                background-color: #f9f9f9; /* 짝수 행 배경색 */
            }

            tr:hover {
                background-color: #f1f1f1; /* 마우스 오버 시 배경색 */
            }
            
            /* 링크 스타일 */
            table a {
                color: #007bff;
                text-decoration: none;
                transition: color 0.3s ease;
            }
            table a:hover {
                color: #0056b3;
                text-decoration: underline;
            }

            /* 하단 버튼 및 페이징 스타일 */
            .bottom-section {
                width: 100%;
                max-width: 1000px; /* 컨테이너의 max-width와 동일하게 */
                display: flex;
                justify-content: space-between; /* 페이징과 버튼을 양 끝으로 */
                align-items: center;
                margin-top: 10px; /* 위쪽 테이블과의 간격 */
            }

            .bottom-section .pagination {
                flex-grow: 1; /* 페이징이 공간을 더 많이 차지하도록 */
                text-align: center;
            }
            .bottom-section .pagination span { /* 페이징 이미지/텍스트 스타일 */
                font-size: 1.1em;
                color: #555;
            }
            .bottom-section .pagination a {
                text-decoration: none;
                color: #007bff;
                padding: 5px 8px;
                border: 1px solid #add8e6; /* 연한 파란색 테두리 */
                border-radius: 4px;
                margin: 0 2px;
                transition: background-color 0.2s, color 0.2s;
            }
            .bottom-section .pagination a:hover {
                background-color: #e6f7ff; /* 호버 시 연한 하늘색 배경 */
                color: #0056b3;
            }
            .bottom-section .pagination strong { /* 현재 페이지 번호 */
                background-color: #007bff;
                color: white;
                padding: 5px 8px;
                border-radius: 4px;
                margin: 0 2px;
            }


            /* 버튼 스타일 */
            .action-button {
                background-color: #28a745; /* 초록색 (등록 버튼) */
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 1em;
                text-decoration: none; /* a 태그로 사용할 경우 */
                transition: background-color 0.3s ease;
                white-space: nowrap; /* 텍스트가 줄바꿈되지 않도록 */
            }
            .action-button:hover {
                background-color: #218838; /* 호버 시 진한 초록색 */
            }

            /* 관리자 페이지로 돌아가는 버튼 (왼쪽 상단) */
            .back-to-admin-button {
                position: absolute; /* 절대 위치 지정 */
                top: 20px; /* 상단에서 20px */
                left: 20px; /* 왼쪽에서 20px */
                background-color: #007bff; /* 파란색 */
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                text-decoration: none; /* 링크 밑줄 제거 */
                font-size: 1em;
                transition: background-color 0.3s ease;
                z-index: 1000; /* 다른 요소 위에 표시 */
            }

            .back-to-admin-button:hover {
                background-color: #0056b3; /* 호버 시 진한 파란색 */
            }
        </style>
	</head>
	<body>
		<h2>공지사항 목록(MyBatis)</h2>
        <a href="<c:url value='/admin'/>" class="back-to-admin-button">관리자 페이지로</a>
        <form method="get" action="/noticeboard/noticelist.do">
        <table border="1" width="90%">
        <tr>
            <td>
                <select name="searchField">
                    <option value="BOARD_TITLE">제목</option> 
                    <option value="USER_ID">작성자</option> 
                </select>
                <input type="text" name="searchKeyword" />
                <input type="submit" value="검색하기" />
            </td>
        </tr>
        </table>

        <table border="1" width="90%">
            <tr>
                <th width="10%">번호</th>
                <th width="*">제목</th>
                <th width="15%">작성자</th>
                <th width="15%">작성일</th>
                <th width="10%">조회수</th>
                </tr>
        <c:choose>
            <c:when test="${ empty lists }">
                <tr>
                    <td colspan="5" align="center"> 
                        등록된 공지사항이 없습니다^^*
                    </td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach items="${ lists }" var="row" varStatus="loop">
                <tr align="center">
                    <td>
                    <c:set var="vNum" value="${ maps.totalCount -
                        (((maps.pageNum-1) * maps.pageSize) + loop.index)}" />
                        ${vNum}
                    </td>
                    <td align="left">
                        <a href="/noticeboard/noticeview.do?boardIdx=${row.boardIdx}&vNum=${vNum}">${ row.boardTitle }</a> 
                    </td>
                    <td>${ row.userId }</td> 
                    <td>${ row.boardPostdate }</td> 
                    <td>${ row.visitcount }</td> 
                   
                </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </table>

        <table border="1" width="90%">
            <tr align="center">
                <td>
                    ${ pagingImg }
                </td>
                <td width="100">
                    <button type="button" onclick="location.href='/noticeboard/noticewrite.do';">
                        공지사항 등록
                    </button>
                </td>
            </tr>
        </table>
	</body>
</html>