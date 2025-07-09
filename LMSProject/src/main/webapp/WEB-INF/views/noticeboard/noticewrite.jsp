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
                display: flex; /* Flexbox를 사용하여 컨텐츠 중앙 정렬 */
                flex-direction: column; /* 세로 방향으로 정렬 */
                align-items: center; /* 가로 중앙 정렬 */
                min-height: 90vh; /* 뷰포트 높이의 최소 90% */
            }

            /* 컨테이너 스타일 (공통) */
            .container {
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                max-width: 600px; /* 등록 페이지에 맞게 너비 조정 */
                width: 100%;
                margin: 20px auto;
                box-sizing: border-box;
            }

            h2 {
                color: #333;
                text-align: center;
                margin-bottom: 30px;
            }

            /* 테이블 스타일 (공통) */
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
                width: 120px; /* 라벨 컬럼 너비 고정 (필요시 조정) */
            }
            td {
                background-color: #fff; /* 데이터 셀 배경색 하얗게 */
            }

            /* 입력 필드 및 텍스트 영역 스타일 */
            input[type="text"], textarea, select {
                width: calc(100% - 20px); /* 패딩 고려하여 너비 조정 */
                padding: 10px;
                margin: 5px 0;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box; /* 패딩이 너비에 포함되도록 */
                font-size: 0.95em;
            }

            /* 버튼 그룹 스타일 */
            .button-group {
                display: flex;
                justify-content: center; /* 버튼들을 중앙으로 정렬 */
                gap: 10px; /* 버튼들 사이 간격 */
                margin-top: 20px; /* 테이블과의 간격 */
            }

            /* 모든 버튼에 대한 기본 스타일 */
            button {
                background-color: #007bff;
                color: white;
                padding: 12px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 1em;
                text-decoration: none; /* 링크 밑줄 제거 */
                transition: background-color 0.3s ease;
                white-space: nowrap; /* 버튼 텍스트가 줄바꿈되지 않도록 */
            }

            /* hover 스타일 */
            button:hover {
                background-color: #0056b3;
            }

            /* 특정 버튼 색상 변경 (초기화, 목록) */
            button[type="reset"] { /* 초기화 버튼 */
                background-color: #6c757d; /* 회색 */
            }
            button[type="reset"]:hover {
                background-color: #5a6268;
            }
            button[onclick*="noticelist.do"] { /* 목록 바로가기 버튼 */
                background-color: #17a2b8; /* 청록색 */
            }
            button[onclick*="noticelist.do"]:hover {
                background-color: #138496;
            }

            /* (선택 사항) 뒤로 가기 버튼 스타일 - 필요시 추가 */
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
            if(frm.boardTitle.value == ''){ 
                alert('제목을 입력하세요.');
                frm.boardTitle.focus();
                return false;
            }
            if(frm.userId.value == ''){ 
                alert('작성자를 입력하세요.');
                frm.userId.focus();
                return false;
            }
            if(frm.boardContent.value == ''){ 
                alert('내용을 입력하세요.');
                frm.boardContent.focus();
                return false;
            }
            // CATEGORY 필드가 있다면 추가 검증
            if(frm.category && frm.category.value == ''){
                alert('카테고리를 선택하세요.');
                frm.category.focus();
                return false;
            }
            return true;
        }
        </script>
        <style>
            /* 이전 공지사항 상세 보기 페이지의 스타일을 여기에 통합하거나,
               외부 CSS 파일로 관리하는 것이 좋습니다.
               지금은 필수적이지 않으므로 생략합니다. */
        </style>
	</head>
	<body>
		<h2>공지사항 등록</h2>
        <form name="writeFrm" method="post"
            action="/noticeboard/noticewrite.do" onsubmit="return validateForm(this);">
        <table border="1" width="90%">
            <tr>
                <td>제목</td>
                <td>
                    <input type="text" name="boardTitle" style="width:90%;" />
                </td>
            </tr>
            <tr>
                <td>작성자</td>
                <td>
                    <input type="text" name="userId" style="width:150px;" /> 
                </td>
            </tr>
            <tr>
                <td>내용</td>
                <td>
                    <textarea name="boardContent" style="width:90%;height:100px;"></textarea>
                </td>
            </tr>
            <tr>
                <td>카테고리</td> <td>
                    <select name="category" style="width:150px;">
                        <option value="">선택</option>
                        <option value="NOTICE">공지</option>
                        <option value="LECTURE">강의</option>
                        </select>
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