<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Q&A 수정</title>
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
            .form-section { /* 컨테이너 역할을 할 클래스 */
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
            .form-group { /* 이 클래스는 현재 코드에서 직접 사용되지 않지만, 유지합니다. */
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
            if (form.board_Title.value.trim() == "") { // .trim() 추가
                alert("제목을 입력하세요.");
                form.board_Title.focus();
                return false;
            }
            // userId는 readonly이므로 여기서 검증할 필요 없음 (백엔드에서 처리)

            if (form.board_Content.value.trim() == "") { // .trim() 추가
                alert("내용을 입력하세요.");
                form.board_Content.focus();
                return false;
            }
            return true;
        }
        </script>
    </head>
    <body>
        <div class="form-section"> 
            <h2>Q&A 수정(Mybatis)</h2>
            <form name="writeFrm" method="post"
                action="/qnaboard/qnaedit.do" onsubmit="return validateForm(this);">
            <input type="hidden" name="board_Idx" value="${qnaBoardDTO.board_Idx }" />
            <table border="1" width="90%">
                <tr>
                    <th>게시글 번호</th> 
                    <td>${qnaBoardDTO.board_Idx }</td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td>
                        <input type="text" name="board_Title" style="width:90%;"
                            value="${qnaBoardDTO.board_Title }"/>
                    </td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>
                        <input type="text" name="user_Id" style="width:150px;"
                            value="${qnaBoardDTO.user_Id }" readonly="readonly" />
                    </td>
                </tr>
                <tr>
                    <th>작성일</th>
                    <td>
                        <input type="date" name="board_Postdate" style="width:150px;"
                            value="<fmt:formatDate value="${qnaBoardDTO.board_Postdate}" pattern="yyyy-MM-dd"/>" readonly="readonly" />
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td>
                        <textarea name="board_Content" style="width:90%;
                            height:100px;">${qnaBoardDTO.board_Content }</textarea>
                    </td>
                </tr>
                <tr>
                    <th>조회수</th>
                    <td>${qnaBoardDTO.visitcount }</td>
                </tr>
                <tr>
                    <td colspan="2" align="center" class="button-group"> 
                        <button type="submit">수정 완료</button>
                        <button type="reset">초기화</button>
                        <button type="button" onclick="location.href='/qnaboard/qnaboardlist.do';">
                            목록 바로가기
                        </button>
                    </td>
                </tr>
            </table>
            </form>
        </div> 
    </body>
</html>