<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Q&A 목록</title>
		<style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f4f4f4;
                display: flex;
                flex-direction: column;
                align-items: center;
                min-height: 90vh;
            }

            .container {
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                max-width: 1000px;
                width: 100%;
                margin: 20px auto;
                box-sizing: border-box;
            }

            h2 {
                color: #333;
                text-align: center;
                margin-bottom: 30px;
            }

            .search-form-group {
                margin-bottom: 20px;
                display: flex;
                gap: 10px;
                justify-content: center;
                align-items: center;
                width: 100%;
                max-width: 600px;
                margin-bottom: 30px;
            }
            .search-form-group select,
            .search-form-group input[type="text"] {
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 1em;
                flex-grow: 1;
                max-width: 200px;
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

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 0;
                margin-bottom: 20px;
            }

            table, th, td {
                border: 1px solid #ddd;
            }

            th, td {
                padding: 12px 15px;
                text-align: left;
            }

            th {
                background-color: #f2f2f2;
                color: #555;
                font-weight: bold;
            }

            tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            tr:hover {
                background-color: #f1f1f1;
            }

            table a {
                color: #007bff;
                text-decoration: none;
                transition: color 0.3s ease;
            }
            table a:hover {
                color: #0056b3;
                text-decoration: underline;
            }

            .bottom-section {
                width: 100%;
                max-width: 1000px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-top: 10px;
            }

            .bottom-section .pagination {
                flex-grow: 1;
                text-align: center;
            }
            .bottom-section .pagination span {
                font-size: 1.1em;
                color: #555;
            }
            .bottom-section .pagination a {
                text-decoration: none;
                color: #007bff;
                padding: 5px 8px;
                border: 1px solid #add8e6;
                border-radius: 4px;
                margin: 0 2px;
                transition: background-color 0.2s, color 0.2s;
            }
            .bottom-section .pagination a:hover {
                background-color: #e6f7ff;
                color: #0056b3;
            }
            .bottom-section .pagination strong {
                background-color: #007bff;
                color: white;
                padding: 5px 8px;
                border-radius: 4px;
                margin: 0 2px;
            }

            .action-button {
                background-color: #28a745;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 1em;
                text-decoration: none;
                transition: background-color 0.3s ease;
                white-space: nowrap;
            }
            .action-button:hover {
                background-color: #218838;
            }

         
            .fixed-nav-button { /* 공통 스타일 */
                background-color: #007bff;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                text-decoration: none;
                font-size: 1em;
                transition: background-color 0.3s ease;
                position: fixed; /* 뷰포트 기준으로 고정 */
                z-index: 1000; /* 다른 요소 위에 표시 */
            }

            .fixed-nav-button:hover {
                background-color: #0056b3;
            }

            /* 개별 버튼의 위치 지정 */
            .button-admin {
                top: 20px;
                left: 20px;
            }

            .button-home {
                top: 70px; /* 두 번째 버튼보다 아래에 위치 */
                left: 20px;
            }

            .reply-indent-img {
                vertical-align: middle;
                margin-right: 5px;
            }
        </style>
	</head>
	<body>
        <%-- 컨테이너 바깥에 버튼 그룹을 배치 --%>
        <div class="fixed-buttons-group">
            <a href="<c:url value='/admin'/>" class="fixed-nav-button button-admin">관리자 페이지로</a>
            <a href="<c:url value='/'/>" class="fixed-nav-button button-home">홈으로</a>
        </div>

        <div class="container">
            <h2>Q&A 목록(MyBatis)</h2>

            <form method="get" action="/qnaboard/qnaboardlist.do" class="search-form-group">
                <select name="search_Field">
                    <option value="BOARD_TITLE" <c:if test="${param.search_Field eq 'BOARD_TITLE'}">selected</c:if>>제목</option>
                    <option value="USER_ID" <c:if test="${param.search_Field eq 'USER_ID'}">selected</c:if>>작성자</option>
                </select>
                <input type="text" name="search_Keyword" value="${param.search_Keyword}" placeholder="검색어를 입력하세요"/>
                <input type="submit" value="검색하기" />
            </form>

            <table border="1" width="90%">
                <thead>
                    <tr>
                        <th width="10%">번호</th>
                        <th width="*">제목</th>
                        <th width="15%">작성자</th>
                        <th width="15%">작성일</th>
                        <th width="10%">조회수</th>
                    </tr>
                </thead>
                <tbody>
            <c:choose>
                <c:when test="${ empty lists }">
                    <tr>
                        <td colspan="5" align="center">
                            등록된 Q&A 게시글이 없습니다^^*
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${ lists }" var="row" varStatus="loop">
                    <tr align="center">
                        <td>

                            <c:set var="vNum" value="${ maps.totalCount - (((maps.pageNum-1) * maps.pageSize) + loop.index)}" />
                            ${vNum}
                        </td>
                        <td align="left" style="padding-left: ${row.bindent * 20}px;">
                            <c:if test="${row.bindent > 0}">
                                <img src="/images/paging3.gif" class="reply-indent-img"/>
                            </c:if>
                            <a href="/qnaboard/qnaboardview.do?board_Idx=${row.board_Idx}">${ row.board_Title }</a>
                        </td>
                        <td>${ row.user_Id }</td>
                        <td>${ row.board_Postdate }</td>
                        <td>${ row.visitcount }</td>
                    </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
                </tbody>
            </table>

            <div class="bottom-section">
                <div class="pagination">
                    ${ maps.pagingImg }
                </div>
                <div class="action-buttons">
                    <button type="button" onclick="location.href='/qnaboard/qnawrite.do';">
                        Q&A 등록
                    </button>
                </div>
            </div>
        </div>
	</body>
</html>