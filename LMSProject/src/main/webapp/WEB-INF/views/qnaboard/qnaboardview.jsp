<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Q&A 상세 보기</title>
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
                max-width: 800px;
                width: 100%;
                margin: 20px auto;
                box-sizing: border-box;
            }

            h2 {
                color: #333;
                text-align: center;
                margin-bottom: 30px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                table-layout: fixed;
            }

            table, th, td {
                border: 1px solid #ddd;
            }

            th, td {
                padding: 5px 8px; /* 패딩 값을 줄여서 공간 확보 */
                text-align: left;
            }

            th {
                background-color: #f2f2f2;
                color: #555;
                font-weight: bold;
            }
            td {
                background-color: #fff;
            }

            table tr th:nth-child(1) { width: 100px; }
            table tr td:nth-child(2) { width: 30%; }
            table tr th:nth-child(3) { width: 100px; }
            table tr td:nth-child(4) { width: auto; }


            table tr:nth-child(4) td {
                text-align: left;
                white-space: pre-wrap;
            }

            table tr:not(:nth-child(4)) td:nth-child(2),
            table tr:not(:nth-child(4)) td:nth-child(4) {
                text-align: center;
            }

            /* .button-group 클래스를 이제 td 내부의 div에 적용합니다. */
            .button-group {
                display: flex;
                justify-content: center; /* 버튼들을 가운데 정렬 */
                gap: 10px;
                margin-top: 20px; /* 이미지처럼 버튼이 테이블에 붙어있지 않도록 마진 추가 */
                width: 100%; /* 부모 td의 너비를 꽉 채우도록 설정 */
            }

            .button-group button, .button-group a.button {
                background-color: #007bff;
                color: white;
                padding: 12px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 1em;
                text-decoration: none;
                transition: background-color 0.3s ease;
                white-space: nowrap;
            }

            .button-group button:hover, .button-group a.button:hover {
                background-color: #0056b3;
            }

            .button-group .delete-button {
                background-color: #dc3545;
            }
            .button-group .delete-button:hover {
                background-color: #c82333;
            }
            .button-group .list-button {
                background-color: #17a2b8;
            }
            .button-group .list-button:hover {
                background-color: #138496;
            }

            .fixed-buttons-group {
                position: fixed;
                top: 20px;
                left: 20px;
                display: flex;
                flex-direction: column;
                gap: 10px;
                z-index: 1000;
            }

            .fixed-nav-button {
                background-color: #007bff;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                text-decoration: none;
                font-size: 1em;
                transition: background-color 0.3s ease;
                white-space: nowrap;
                display: block;
            }

            .fixed-nav-button:hover {
                background-color: #0056b3;
            }

            .answers-section {
                margin-top: 30px;
                padding-top: 20px;
                border-top: 1px solid #eee;
            }
            .answers-section h3 {
                color: #333;
                margin-bottom: 20px;
            }
            .answer-item {
                background-color: #fdfdfd;
                border: 1px solid #e0e0e0;
                padding: 15px;
                margin-bottom: 15px;
                border-radius: 6px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.05);
            }
            .answer-item p {
                margin-bottom: 5px;
                line-height: 1.6;
            }
            .answer-item strong {
                color: #007bff;
            }
            .answer-item small {
                color: #777;
                font-size: 0.85em;
                margin-left: 10px;
            }
            .answer-item p[style="white-space: pre-wrap;"] {
                color: #444;
            }
        </style>
        <script>
        function deletePost(board_Idx){
            var confirmed = confirm("정말로 삭제하시겠습니까?");
            if(confirmed){
                var form = document.writeFrm;
                form.method = "post";
                form.action = "/qnaboard/qnadelete.do";
                form.submit();
            }
        }
        </script>
    </head>
     <body>
        <div class="fixed-buttons-group">
            <a href="<c:url value='/admin'/>" class="fixed-nav-button">관리자 페이지로</a>
            <a href="<c:url value='/prof'/>" class="fixed-nav-button">교수 페이지로</a>
            <a href="<c:url value='/'/>" class="fixed-nav-button">홈으로</a>
        </div>

        <div class="container">
            <h2>Q&A 상세 보기</h2>
            <form name="writeFrm" method="post">
                <input type="hidden" name="board_Idx" value="${qnaBoardDTO.board_Idx }" />
            </form>
            <table border="1">
                <tr>
                    <th>게시물 번호</th> <td>${ qnaBoardDTO.board_Idx }</td>
                    <th>작성자 ID</th> <td>${ qnaBoardDTO.user_Id }</td>
                </tr>
                <tr>
                    <th>제목</th> <td>${ qnaBoardDTO.board_Title }</td>
                    <th>카테고리</th> <td>${ qnaBoardDTO.category }</td>
                </tr>
                <tr>
                    <th>작성일</th> <td>${ qnaBoardDTO.board_Postdate }</td>
                    <th>조회수</th> <td>${ qnaBoardDTO.visitcount }</td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3" style="white-space: pre-wrap;">${ qnaBoardDTO.board_Content }</td>
                </tr>
                <tr>
                     <td colspan="4" style="text-align: center;"> <div class="button-group">  <c:set var="qnaBoard" value="${qnaBoardDTO}" />
                            <c:if test="${qnaBoard != null}">
                                <sec:authorize access="hasAnyRole('ADMIN', 'PROFESSOR')">
                                    <button type="button" onclick="location.href='<c:url value="/qnaboard/qnaedit.do?board_Idx=${ qnaBoard.board_Idx }"/>';">
                                        수정하기
                                    </button>
                                    <button type="button" class="delete-button" onclick="deletePost('${ qnaBoard.board_Idx }');">
                                        삭제하기
                                    </button>
                                    <button type="button" onclick="location.href='<c:url value="/qnaboard/qnanswer.do?board_Idx=${ qnaBoard.board_Idx }&bgroup=${qnaBoard.bgroup}&bstep=${qnaBoard.bstep}&bindent=${qnaBoard.bindent}"/>';">
                                        답변하기
                                    </button>
                                </sec:authorize>
                                <sec:authorize access="isAuthenticated()">
                                    <c:if test="${authentication.name eq qnaBoard.user_Id}">
                                        <sec:authorize access="!hasAnyRole('ADMIN', 'PROFESSOR')">
                                            <button type="button" onclick="location.href='<c:url value="/qnaboard/qnaedit.do?board_Idx=${ qnaBoard.board_Idx }"/>';">
                                                수정하기
                                            </button>
                                            <button type="button" class="delete-button" onclick="deletePost('${ qnaBoard.board_Idx }');">
                                                삭제하기
                                            </button>
                                        </sec:authorize>
                                    </c:if>
                                </sec:authorize>
                            </c:if>

                            <button type="button" class="list-button" onclick="location.href='<c:url value="/qnaboard/qnaboardlist.do"/>';">
        						목록으로 돌아가기
    						</button>
                        </div> </td>
                </tr>
            </table>
            <h3>답변 목록</h3>
            <div class="answers-section">
                <c:choose>
                    <c:when test="${not empty qnaBoard.answers}">
                        <c:forEach var="answer" items="${qnaBoard.answers}">
                            <c:if test="${answer.board_Idx ne qnaBoard.board_Idx}">
                                <div class="answer-item" style="margin-left: ${answer.bindent * 20}px;">
                                    <p>
                                        <strong>
                                            <c:if test="${answer.bindent > 0}">
                                                RE:
                                            </c:if>
                                            ${answer.board_Title}
                                        </strong>
                                        <small>(작성자: ${answer.user_Id}, 작성일: ${answer.board_Postdate})</small>
                                    </p>
                                    <p style="white-space: pre-wrap;">${answer.board_Content}</p>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>등록된 답변이 없습니다.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>