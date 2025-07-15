<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>강의 상세 보기</title>
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

            /* 컨테이너 스타일 (다른 페이지와 동일) */
            .container {
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                max-width: 800px; /* 상세 보기 페이지에 맞게 너비 조정 */
                width: 100%;
                margin: 20px auto;
                box-sizing: border-box;
            }

            h2 {
                color: #333;
                text-align: center;
                margin-bottom: 30px;
            }

            /* 테이블 스타일 (다른 페이지와 동일) */
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

            th { /* 상세 보기에서는 모든 첫 번째 컬럼이 라벨이므로 th로 통일 */
                background-color: #f2f2f2;
                color: #555;
                font-weight: bold;
                width: 120px; /* 라벨 컬럼 너비 고정 (필요시 조정) */
            }
            td {
                background-color: #fff; /* 데이터 셀 배경색 하얗게 */
            }

            /* 버튼 그룹 스타일 */
            .button-group {
                display: flex;
                justify-content: center; /* 버튼들을 중앙으로 정렬 */
                gap: 10px; /* 버튼들 사이 간격 */
                margin-top: 20px; /* 테이블과의 간격 */
            }

            /* 모든 버튼에 대한 기본 스타일 */
            .button-group button, .button-group a.button { /* a 태그도 버튼처럼 보이게 */
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
            .button-group button:hover, .button-group a.button:hover {
                background-color: #0056b3;
            }

            /* 특정 버튼 색상 변경 (삭제, 목록) */
            .button-group .delete-button {
                background-color: #dc3545; /* 빨간색 (삭제 버튼) */
            }
            .button-group .delete-button:hover {
                background-color: #c82333;
            }
            .button-group .list-button { /* '목록 바로가기' 버튼 */
                background-color: #17a2b8; /* 청록색 */
            }
            .button-group .list-button:hover {
                background-color: #138496;
            }

            /* 관리자 페이지로 돌아가는 버튼 (왼쪽 상단) */
            .back-to-admin-button {
                position: absolute; /* 절대 위치 지정 */
                top: 20px; /* 상단에서 20px */
                left: 20px; /* 왼쪽에서 20px */
                background-color: #007bff; /* 파란색 */
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                text-decoration: none; /* 링크 밑줄 제거 */
                font-size: 1em;
                transition: background-color 0.3s ease;
                z-index: 1000; /* 다른 요소 위에 표시 */
            }

            .back-to-admin-button:hover {
                background-color: #0056b3; /* 호버 시 진한 파란색 */
            }
        </style>
		<script>
		function deletePost(lecture_Idx){
			var confirmed = confirm("정말로 삭제하겠습니까?");
			if(confirmed){
				var form = document.writeFrm;
				form.method = "post";
				form.action = "/admin_lectureboard/lecturedelete.do";
				form.submit();
			}
		}
		</script>
	</head>
	<body>
		<h2>강의 상세 보기</h2>	
		<form name="writeFrm">
			<input type="hidden" name="lecture_Idx" value="${lectureBoardDTO.lecture_Idx }" />
		</form>
		<table border="1">
		<colgroup>
		<col width="15%"/> <col width="35%"/>
		<col width="15%"/> <col width="*"/>
		</colgroup>
			<tr>
				<td>강의 번호</td> <td>${ lectureBoardDTO.lecture_Idx }</td>
				<td>강의명</td> <td>${ lectureBoardDTO.lecture_Name }</td>
			</tr>
			<tr>
				<td>교수ID</td> <td>${ lectureBoardDTO.prof_Id }</td>
				<td>강의 코드</td> <td>${ lectureBoardDTO.lecture_Code }</td>
			</tr>
            <tr>
                <td>강의 시작일</td> <td>${ lectureBoardDTO.lecture_Start_Date }</td> 
                <td>강의 종료일</td> <td>${ lectureBoardDTO.lecture_End_Date }</td>
            </tr>
            <tr>
                <td>전공ID</td>
                <td colspan="3">${ lectureBoardDTO.major_Id }</td>
            </tr>
            <tr>
            <td colspan="4" align="center">
		            <button type="button" onclick="location.href='/admin_lectureboard/lectureedit.do?lecture_Idx=${ lectureBoardDTO.lecture_Idx }';">
		                수정하기
		            </button>
		            <button type="button" onclick="deletePost('${ lectureBoardDTO.lecture_Idx }');">
		                삭제하기
		            </button>
		            <button type="button" onclick="location.href='/admin_lectureboard/lecturelist.do';">
		                목록 바로가기
		            </button>
		        </td>
			</tr>
		</table>
	</body>
</html>