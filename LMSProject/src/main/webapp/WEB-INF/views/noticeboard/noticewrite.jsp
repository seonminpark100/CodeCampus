<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 등록</title>
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
            button[onclick*="noticelist.do"] {
                background-color: #17a2b8;
            }
            button[onclick*="noticelist.do"]:hover {
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
            if(frm.board_Title.value == ''){
                alert('제목을 입력하세요.');
                frm.boardTitle.focus();
                return false;
            }

            if(frm.board_Content.value == ''){
                alert('내용을 입력하세요.');
                frm.board_Content.focus();
                return false;
            }
            // CATEGORY 필드 선택 검증 강화
            if(frm.category && frm.category.value == ''){ // '선택'이 선택되었을 경우
                alert('카테고리를 선택하세요.');
                frm.category.focus();
                return false;
            }
            return true;
        }
        </script>
	</head>
	<body>
		<h2>공지사항 등록</h2>
        <form name="writeFrm" method="post"
            action="/noticeboard/noticewrite.do" onsubmit="return validateForm(this);">
        <table border="1" width="90%">
            <tr>
                <td>제목</td>
                <td>
                    <input type="text" name="board_Title" style="width:90%;" />
                </td>
            </tr>
            <tr>
                <td>작성자</td>
                <td>
                    <input type="hidden" name="user_Id" value="공지담당자" />
                    공지담당자
                </td>
            </tr>
            <tr>
                <td>내용</td>
                <td>
                    <textarea name="board_Content" style="width:90%;height:100px;"></textarea>
                </td>
            </tr>
            <tr>
                <td>카테고리</td>
                <td>
                    <select name="category" style="width:150px;">
                        <option value="">선택</option> <option value="N">일반 공지</option>
                        <option value="L">강의 공지</option> </select>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <button type="submit">등록</button>
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