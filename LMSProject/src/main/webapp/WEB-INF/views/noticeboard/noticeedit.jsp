<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>공지사항 수정</title>
        <style>
            /* 이전 페이지들에서 사용한 기본 레이아웃 및 폼 스타일 재사용 */
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f4f4f4;
                display: flex;
                flex-direction: column;
                align-items: center;
                min-height: 90vh;
            }
            .form-section {
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                max-width: 600px;
                width: 100%;
                margin: 20px auto;
                box-sizing: border-box;
            }
            h2 {
                color: #333;
                text-align: center;
                margin-bottom: 30px;
            }
            .form-group {
                margin-bottom: 15px;
            }
            .form-group label {
                display: block;
                margin-bottom: 5px;
                color: #555;
                font-weight: bold;
            }
            .form-group input[type="text"],
            .form-group input[type="password"],
            .form-group input[type="email"],
            .form-group input[type="tel"],
            .form-group input[type="date"],
            .form-group select,
            .form-group textarea {
                width: calc(100% - 22px);
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 1em;
            }
            /* 버튼 그룹 스타일 */
            .button-group {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin-top: 20px;
            }
            /* 모든 버튼에 대한 기본 스타일 */
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
            /* hover 스타일 */
            .button-group button:hover, .button-group a.button:hover {
                background-color: #0056b3;
            }
            /* 특정 버튼 색상 변경 (초기화, 목록) */
            .button-group button[type="reset"] {
                background-color: #6c757d;
            }
            .button-group button[type="reset"]:hover {
                background-color: #5a6268;
            }
            .button-group a.button-cancel {
                background-color: #17a2b8;
            }
            .button-group a.button-cancel:hover {
                background-color: #138496;
            }
            /* 테이블 레이아웃 수정 페이지에 맞게 조정 */
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
        </style>
        <script type="text/javascript">
        function validateForm(form) {
            if (form.board_Title.value == "") { 
                alert("제목을 입력하세요.");
                form.board_Title.focus();
                return false;
            }
            if (form.user_Id.value == "") { 
                alert("작성자를 입력하세요.");
                form.user_Id.focus();
                return false;
            }
            if (form.board_Content.value == "") { 
                alert("내용을 입력하세요.");
                form.board_Content.focus();
                return false;
            }
            return true;
        }
        </script>
    </head>
    <body>
        <h2>공지사항 수정(Mybatis)</h2>
        <form name="writeFrm" method="post"
            action="/noticeboard/noticeedit.do" onsubmit="return validateForm(this);">
        <input type="hidden" name="board_Idx" value="${noticeBoardDTO.board_Idx }" /> 
        <table border="1" width="90%">
            <tr>
                <td>게시글 번호</td>
                <td>${noticeBoardDTO.board_Idx }</td> 
            </tr>
            <tr>
                <td>제목</td>
                <td>
                    <input type="text" name="board_Title" style="width:90%;"
                        value="${noticeBoardDTO.board_Title }"/> 
                </td>
            </tr>
            <tr>
                <td>작성자</td>
                <td>
                    <input type="text" name="user_Id" style="width:150px;"
                        value="${noticeBoardDTO.user_Id }" readonly="readonly" /> 
                </td>
            </tr>
            <tr>
                <td>작성일</td>
                <td>
                    <input type="date" name="board_Postdate" style="width:150px;"
                        value="${noticeBoardDTO.board_Postdate }" readonly="readonly" /> 
                </td>
            </tr>
            <tr>
                <td>내용</td>
                <td>
                    <textarea name="board_Content" style="width:90%;
                        height:100px;">${noticeBoardDTO.board_Content }</textarea> 
                </td>
            </tr>
            <tr>
                <td>조회수</td>
                <td>${noticeBoardDTO.visitcount }</td> 
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <button type="submit">수정 완료</button>
                    <button type="reset">초기화</button>
                    <button type="button" onclick="location.href='/noticeboard/noticelist.do';">
                        목록 바로가기
                    </button>
                </td>
            </tr>
        </table>
        </form>
    </body>
</html>