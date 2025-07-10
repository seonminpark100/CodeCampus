<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Q&A 답변 작성</title>
    <style>
        /* 여기에 CSS 스타일 추가 */
        .container { width: 80%; margin: 20px auto; }
        h2 { color: #01579b; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], textarea { width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { background-color: #007bff; color: white; padding: 10px 15px; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        .original-qna { border: 1px solid #eee; padding: 10px; background-color: #f9f9f9; margin-bottom: 20px; }
        .original-qna p { margin: 5px 0; }
    </style>
</head>
<body>
    <%@ include file="../top.jsp" %>
    <div class="container">
        <h2>Q&A 답변 작성</h2>

        <div class="original-qna">
            <h3>원본 질문</h3>
            <p><strong>제목:</strong> ${parentBoardTitle}</p>
            <p><strong>강의 코드:</strong> ${parentLectureCode}</p>

            <c:if test="${not empty originalQuestion}">
                <p><strong>작성자:</strong> ${originalQuestion.userId}</p>
                <p><strong>내용:</strong></p>
                <div style="white-space: pre-wrap; border: 1px solid #ddd; padding: 10px; background-color: #fff;">${originalQuestion.boardContent}</div>
            </c:if>
            <c:if test="${empty originalQuestion}">
                <p style="color: gray;">원본 질문의 상세 내용은 불러올 수 없습니다. (콘텐츠 표시를 위한 DTO가 모델에 없습니다)</p>
            </c:if>
        </div>


        <form action="/qnaboard/qnanswer.do" method="post">
            <input type="hidden" name="parentBoardIdx" value="${parentBoardIdx}">
            <input type="hidden" name="parentBgroup" value="${parentBgroup}">
            <input type="hidden" name="parentBstep" value="${parentBstep}">
            <input type="hidden" name="parentBindent" value="${parentBindent}">

            <label for="boardTitle">답변 제목:</label>
            <input type="text" id="boardTitle" name="boardTitle" value="Re: ${parentBoardTitle}" required><br>

            <label for="userId">작성자 ID:</label>
     
            <input type="text" id="userId" name="userId" value="${loggedInUserId}" readonly required><br>

            <label for="boardContent">답변 내용:</label>
            <textarea id="boardContent" name="boardContent" rows="10" required></textarea><br>

            <button type="submit">답변 등록</button>
            <button type="button" onclick="history.back()">취소</button>
        </form>
    </div>
</body>
</html>