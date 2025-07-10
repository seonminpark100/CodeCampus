<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> <%-- Spring Security 태그 라이브러리 --%>
<%-- ⭐⭐ JSTL core 태그 라이브러리 추가 ⭐⭐ --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Q&A 작성</title>
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

            .container { /* 이 클래스는 현재 코드에서 사용되지 않지만, 유지합니다. */
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

            input[type="text"], textarea, select {
                width: calc(100% - 20px);
                padding: 10px;
                margin: 5px 0;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 0.95em;
            }
            /* userId readonly 필드에 대한 스타일 추가 */
            input[name="userId"][readonly] {
                background-color: #e9ecef; /* 연한 회색 배경 */
                cursor: not-allowed; /* 마우스 커서 변경 */
            }

            .button-group {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin-top: 20px;
            }

            button {
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

            button:hover {
                background-color: #0056b3;
            }

            button[type="reset"] {
                background-color: #6c757d;
            }
            button[type="reset"]:hover {
                background-color: #5a6268;
            }
            /* 버튼 선택자에 qnaboardlist.do 로 변경 */
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
            if(frm.boardTitle.value.trim() == ''){
                alert('제목을 입력하세요.');
                frm.boardTitle.focus();
                return false;
            }
            // userId는 컨트롤러에서 설정하므로, 여기서 유효성 검사 필요 없음

            if(frm.boardContent.value.trim() == ''){
                alert('내용을 입력하세요.');
                frm.boardContent.focus();
                return false;
            }

            return true;
        }
        </script>
	</head>
	<body>
        <%@ include file="../top.jsp" %>
        <div class="container"> 
            <h2>Q&A 작성</h2>

            <form name="writeFrm" method="post"
                action="/qnaboard/qnawrite.do" onsubmit="return validateForm(this);">
            <table border="1" width="90%">
                <tr>
                    <th>제목</th>
                    <td>
                        <input type="text" name="boardTitle" placeholder="제목을 입력하세요." style="width:90%;" />
                    </td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>
                        <sec:authentication property="principal.username" var="loggedInUserId" />
                        <input type="text" name="userId" value="${loggedInUserId}" readonly="readonly" style="width:150px;" />
                    </td>
                </tr>
                <tr>
                    <th>강의 코드 (선택 사항)</th>
                    <td>
                        <input type="text" name="lectureCode" placeholder="관련 강의 코드를 입력하세요." style="width:150px;" />
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td>
                        <textarea name="boardContent" placeholder="내용을 입력하세요." style="width:90%;height:150px;"></textarea>
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
                    <td colspan="2" align="center" class="button-group">
                        <button type="submit">작성 완료</button>
                        <button type="reset">초기화</button>
                      
                        <button type="button" onclick="location.href='/qnaboard/qnaboardlist.do';">
                            목록으로 돌아가기
                        </button>
                    </td>
                </tr>
            </table>
            </form>
        </div> 
	</body>
</html>