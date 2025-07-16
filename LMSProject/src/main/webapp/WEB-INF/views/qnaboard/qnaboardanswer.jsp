<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Q&A 답변 작성</title>
	<style>
	table th, table td {
		white-space: nowrap; /* 내용이 길어져도 줄바꿈되지 않도록 설정 (선택 사항) */
	}
	
	/* "번호" 열 */
	table th:nth-child(1), table td:nth-child(1) {
		width: 80px; /* 번호 열 너비 */
		text-align: center;
	}
	
	/* "제목" 열 */
	table th:nth-child(2), table td:nth-child(2) {
		width: auto; /* 남은 공간을 자동으로 채우도록 설정 */
		min-width: 300px; /* 최소 너비 지정 (내용이 너무 짧아도 보기 좋게) */
	}
	
	/* "작성자" 열 */
	table th:nth-child(3), table td:nth-child(3) {
		width: 120px; /* 작성자 열 너비 */
		text-align: center;
	}
	
	/* "작성일" 열 */
	table th:nth-child(4), table td:nth-child(4) {
		width: 150px; /* 작성일 열 너비 */
		text-align: center;
	}
	
	/* "조회수" 열 */
	table th:nth-child(5), table td:nth-child(5) {
		width: 80px; /* 조회수 열 너비 */
		text-align: center;
	}
	
	body {
		font-family: Arial, sans-serif;
		margin: 20px;
		background-color: #f4f4f4;
		display: flex; /* Flexbox를 사용하여 컨텐츠 중앙 정렬 */
		flex-direction: column; /* 세로 방향으로 정렬 */
		align-items: center; /* 가로 중앙 정렬 */
		min-height: 90vh; /* 뷰포트 높이의 최소 90% */
	}
	
	/* 컨테이너 스타일 (Q&A 목록 페이지와 동일) */
	.container {
		background-color: #fff;
		padding: 30px;
		border-radius: 8px;
		box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
		max-width: 1000px; /* 답변 작성 페이지에 맞게 너비 조정 (목록 페이지보다 좁게) */
		width: 100%; /* 너비 100% 사용 (max-width 내에서) */
		margin: 20px auto; /* 상하 여백, 좌우 자동 (중앙 정렬) */
		box-sizing: border-box; /* 패딩이 너비에 포함되도록 */
	}
	
	h2 {
		color: #333; /* Q&A 목록 페이지와 동일한 색상 */
		text-align: center;
		margin-bottom: 30px;
	}
	
	label {
		display: block;
		margin-bottom: 8px;
		font-weight: bold;
		color: #555; /* 텍스트 색상 */
	}
	
	input[type="text"], textarea {
		width: 100%;
		padding: 10px; /* 패딩 증가 */
		margin-bottom: 15px; /* 간격 증가 */
		border: 1px solid #ddd; /* 경계선 색상 */
		border-radius: 4px;
		box-sizing: border-box;
		font-size: 1em;
	}
	
	/* 원본 질문 영역 스타일 (기존 스타일 유지하며 간격 등 조정) */
	.original-qna {
		border: 1px solid #eee;
		padding: 20px; /* 패딩 증가 */
		background-color: #f9f9f9;
		border-radius: 6px; /* 모서리 둥글게 */
		margin-bottom: 30px; /* 하단 간격 증가 */
	}
	
	.original-qna h3 {
		color: #007bff; /* 제목 색상 */
		margin-top: 0;
		margin-bottom: 15px;
		border-bottom: 1px solid #e0e0e0;
		padding-bottom: 10px;
	}
	
	.original-qna p {
		margin: 8px 0; /* 단락 간 간격 조정 */
		color: #666;
		line-height: 1.6;
	}
	
	.original-qna strong {
		color: #333;
	}
	
	.original-qna div {
		white-space: pre-wrap;
		border: 1px solid #ddd;
		padding: 15px; /* 패딩 증가 */
		background-color: #fff;
		border-radius: 4px;
		margin-top: 10px;
	}
	
	.original-qna p[style="color: gray;"] {
		font-style: italic;
	}
	
	/* 버튼 스타일 (Q&A 목록 페이지와 동일) */
	.action-button {
		background-color: #007bff; /* 파란색 (주요 버튼) */
		color: white;
		padding: 10px 20px;
		border: none;
		border-radius: 4px;
		cursor: pointer;
		font-size: 1em;
		text-decoration: none;
		transition: background-color 0.3s ease;
		margin-right: 10px; /* 버튼 사이 간격 */
	}
	
	.action-button:hover {
		background-color: #0056b3;
	}
	
	/* 취소 버튼에 다른 색상을 주고 싶다면 (선택 사항) */
	.cancel-button {
		background-color: #6c757d; /* 회색 */
		color: white;
		padding: 10px 20px;
		border: none;
		border-radius: 4px;
		cursor: pointer;
		font-size: 1em;
		text-decoration: none;
		transition: background-color 0.3s ease;
	}
	
	.cancel-button:hover {
		background-color: #5a6268;
	}
	
	/* 고정된 네비게이션 버튼 그룹 (목록 페이지와 동일하게 좌측 상단에 배치) */
	.fixed-buttons-group {
		position: fixed;
		top: 20px;
		left: 20px;
		display: flex;
		flex-direction: column;
		gap: 10px; /* 버튼들 사이의 간격 */
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
		white-space: nowrap; /* 텍스트가 줄바꿈되지 않도록 */
		display: block; /* 세로로 정렬 */
	}
	
	.fixed-nav-button:hover {
		background-color: #0056b3;
	}
	</style>
</head>
<body>
    <div class="container">
        <h2>Q&A 답변 작성</h2>
		
        <div class="original-qna">
            <h3>원본 질문</h3>
            <p><strong>제목:</strong> ${parentBoardTitle}</p>
            <p><strong>강의 코드:</strong> ${parentLectureCode}</p>

            <c:if test="${not empty originalQuestion}">
                <p><strong>작성자:</strong> ${originalQuestion.user_Id}</p>
                <p><strong>내용:</strong></p>
                <div style="white-space: pre-wrap; border: 1px solid #ddd; padding: 10px; background-color: #fff;">${originalQuestion.board_Content}</div>
            </c:if>
            <c:if test="${empty originalQuestion}">
                <p style="color: gray;">원본 질문의 상세 내용은 불러올 수 없습니다. (콘텐츠 표시를 위한 DTO가 모델에 없습니다)</p>
            </c:if>
        </div>


        <form action="/qnaboard/qnanswer.do" method="post">
            <input type="hidden" name="parentBoard_Idx" value="${parentBoard_Idx}">
            <input type="hidden" name="parentBgroup" value="${parentBgroup}">
            <input type="hidden" name="parentBstep" value="${parentBstep}">
            <input type="hidden" name="parentBindent" value="${parentBindent}">

            <label for="boardTitle">답변 제목:</label>
            <input type="text" id="board_Title" name="board_Title" value="Re: ${parentBoard_Title}" required><br>

            <label for="userId">작성자 ID:</label>
     
            <input type="text" id="user_Id" name="user_Id" value="${loggedInUserId}" readonly required><br>

            <label for="board_Content">답변 내용:</label>
            <textarea id="board_Content" name="board_Content" rows="10" required></textarea><br>

            <button type="submit">답변 등록</button>
            <button type="button" onclick="history.back()">취소</button>
        </form>
    </div>
</body>
</html>