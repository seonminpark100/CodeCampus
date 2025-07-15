<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Q&A 작성</title>
		<style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                background-color: #f4f4f4;
                display: flex;
                flex-direction: column;
                align-items: center;
                min-height: 90vh;
            }

            .main-wrapper {
                width: 100%;
                max-width: 1600px;
                margin: 40px auto;
                padding: 0 20px;
                box-sizing: border-box;
            }

            .qa-form-container {
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                width: 100%;
                box-sizing: border-box;
            }

            h2 {
                color: #333;
                text-align: center;
                margin-bottom: 30px;
                font-size: 2em;
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
                padding: 15px;
                text-align: left;
                vertical-align: top;
            }

            th {
                background-color: #f9f9f9;
                color: #555;
                font-weight: bold;
                width: 20%;
                white-space: nowrap;
            }
            td {
                background-color: #fff;
                width: 80%;
            }

            input[type="text"], textarea, select {
                width: 100%;
                padding: 10px;
                margin: 0;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 0.95em;
                line-height: 1.5;
            }
            textarea {
                min-height: 180px;
                resize: vertical;
            }

            input[name="userId"][readonly],
            input[name="lectureCode"] {
                background-color: #e9ecef;
                cursor: not-allowed;
                width: auto;
                max-width: 200px;
            }

            table tr:last-child td[colspan="2"] {
                padding-top: 25px;
                padding-bottom: 25px;
            }

            .button-group {
                display: flex; /* flex 컨테이너 유지 */
                justify-content: center; /* 내부 버튼 중앙 정렬 */
                gap: 15px;
                width: fit-content; /* ★ 버튼 그룹의 너비를 내용에 맞게 조절 */
                margin: 0 auto; /* ★ 블록 요소로 중앙 정렬 */
                padding: 0;
            }

            button {
                background-color: #007bff;
                color: white;
                padding: 12px 25px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 1.05em;
                text-decoration: none;
                transition: background-color 0.3s ease;
                white-space: nowrap;
            }

            button:hover {
                background-color: #0056b3;
            }

            button[type="reset"] {
                background-color: #6c757d;
            }
            button[type="reset"]:hover {
                background-color: #5a6268;
            }
            button[onclick*="qnaboardlist.do"] {
                background-color: #17a2b8;
            }
            button[onclick*="qnaboardlist.do"]:hover {
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
        </style>
		<script>
        let validateForm = (frm) => {
            if(frm.board_Title.value.trim() == ''){
                alert('제목을 입력하세요.');
                frm.board_Title.focus();
                return false;
            }
            if(frm.boardContent.value.trim() == ''){
                alert('내용을 입력하세요.');
                frm.board_Content.focus();
                return false;
            }
            return true;
        }
        </script>
	</head>
	<body>
        <div class="main-wrapper">
            <div class="qa-form-container">
                <h2>Q&A 작성</h2>

                <form name="writeFrm" method="post"
                    action="/qnaboard/qnawrite.do" onsubmit="return validateForm(this);">
                <table>
                    <tr>
                        <th>제목</th>
                        <td>
                            <input type="text" name="board_Title" placeholder="제목을 입력하세요." />
                        </td>
                    </tr>
                    <tr>
                        <th>작성자</th>
                        <td>
                            <sec:authentication property="principal.username" var="loggedInUserId" />
                            <input type="text" name="user_Id" value="${loggedInUserId}" readonly="readonly" />
                        </td>
                    </tr>
                    <tr>
                        <th>강의 코드 (선택 사항)</th>
                        <td>
                            <input type="text" name="lecture_Code" placeholder="관련 강의 코드를 입력하세요." />
                        </td>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td>
                            <textarea name="board_Content" placeholder="내용을 입력하세요."></textarea>
                        </td>
                    </tr>
                    <tr>
                        <th>카테고리</th>
                        <td>
                            <input type="hidden" name="category" value="Q" />
                            Q&A
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="button-group-wrapper">
                            <div class="button-group">
                                <button type="submit">작성 완료</button>
                                <button type="reset">초기화</button>
                                <button type="button" onclick="location.href='/qnaboard/qnaboardlist.do';">
                                    목록으로 돌아가기
                                </button>
                            </div>
                        </td>
                    </tr>
                </table>
                </form>
            </div>
        </div>
	</body>
</html>