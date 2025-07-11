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
                width: 120px;
            }
            td {
                background-color: #fff;
            }

            .button-group {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin-top: 20px;
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

            .back-to-admin-button {
                position: absolute;
                top: 20px;
                left: 20px;
                background-color: #007bff;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                text-decoration: none;
                font-size: 1em;
                transition: background-color 0.3s ease;
                z-index: 1000;
            }

            .back-to-admin-button:hover {
                background-color: #0056b3;
            }
            .answers-section {
                margin-top: 30px;
                padding-top: 20px;
                border-top: 1px solid #eee;
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
            }
            .answer-item small {
                color: #777;
                font-size: 0.85em;
            }
        </style>
        <script>
        function deletePost(boardIdx){
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
        <div class="container">
            <h2>Q&A 상세 보기</h2>
            <form name="writeFrm" method="post">
                <input type="hidden" name="boardIdx" value="${qnaBoardDTO.boardIdx }" />
            </form>
            <table border="1">
                <colgroup>
                    <col width="15%"/> <col width="35%"/>
                    <col width="15%"/> <col width="*"/>
                </colgroup>
                <tr>
                    <th>게시물 번호</th> <td>${ qnaBoardDTO.boardIdx }</td>
                    <th>작성자 ID</th> <td>${ qnaBoardDTO.userId }</td>
                </tr>
                <tr>
                    <th>제목</th> <td>${ qnaBoardDTO.boardTitle }</td>
                    <th>카테고리</th> <td>${ qnaBoardDTO.category }</td>
                </tr>
                <tr>
                    <th>작성일</th> <td>${ qnaBoardDTO.boardPostdate }</td>
                    <th>조회수</th> <td>${ qnaBoardDTO.visitcount }</td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3" style="white-space: pre-wrap;">${ qnaBoardDTO.boardContent }</td>
                </tr>
                <tr>
                     <td colspan="4" align="center" class="button-group">
                        <c:set var="qnaBoard" value="${qnaBoardDTO}" />
                        <c:if test="${qnaBoard != null}">
                            <sec:authorize access="hasAnyRole('ADMIN', 'PROFESSOR')">
                                <button type="button" onclick="location.href='<c:url value="/qnaboard/qnaedit.do?boardIdx=${ qnaBoard.boardIdx }"/>';">
                                    수정하기
                                </button>
                                <button type="button" class="delete-button" onclick="deletePost('${ qnaBoard.boardIdx }');">
                                    삭제하기
                                </button>
                                <button type="button" onclick="location.href='<c:url value="/qnaboard/qnanswer.do?boardIdx=${ qnaBoard.boardIdx }&bgroup=${qnaBoard.bgroup}&bstep=${qnaBoard.bstep}&bindent=${qnaBoard.bindent}"/>';">
                                    답변하기
                                </button>
                            </sec:authorize>
                            <sec:authorize access="isAuthenticated()">
                                <c:if test="${authentication.name eq qnaBoard.userId}">
                                    <sec:authorize access="!hasAnyRole('ADMIN', 'PROFESSOR')">
                                        <button type="button" onclick="location.href='<c:url value="/qnaboard/qnaedit.do?boardIdx=${ qnaBoard.boardIdx }"/>';">
                                            수정하기
                                        </button>
                                        <button type="button" class="delete-button" onclick="deletePost('${ qnaBoard.boardIdx }');">
                                            삭제하기
                                        </button>
                                    </sec:authorize>
                                </c:if>
                            </sec:authorize>
                        </c:if>

                        <button type="button" class="list-button" onclick="location.href='<c:url value="/qnaboard/qnaboardlist.do"/>';">
                            목록으로 돌아가기
                        </button>
                    </td>
                </tr>
            </table>
            <h3>답변 목록</h3>
            <div class="answers-section">
                <c:choose>
                    <c:when test="${not empty qnaBoard.answers}"> 
                        <c:forEach var="answer" items="${qnaBoard.answers}">
                            <c:if test="${answer.boardIdx ne qnaBoard.boardIdx}"> 
                                <div class="answer-item" style="margin-left: ${answer.bindent * 20}px;"> 
                                    <p>
                                        <strong>
                                            <c:if test="${answer.bindent > 0}">
                                                RE:
                                            </c:if>
                                            ${answer.boardTitle}
                                        </strong>
                                        <small>(작성자: ${answer.userId}, 작성일: ${answer.boardPostdate})</small>
                                    </p>
                                    <p style="white-space: pre-wrap;">${answer.boardContent}</p>
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