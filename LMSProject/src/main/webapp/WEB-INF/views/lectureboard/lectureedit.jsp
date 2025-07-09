<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>강의 정보 수정</title>
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
                max-width: 600px; /* 폼 너비 조정 */
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
            .form-group textarea { /* textarea도 스타일 적용 */
                width: calc(100% - 22px); /* 패딩 및 보더 고려 */
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 1em; /* 폰트 크기 통일 */
            }

            /* 버튼 그룹 스타일 */
            .button-group {
                display: flex;
                justify-content: center; /* 버튼들을 중앙으로 정렬 */
                gap: 10px; /* 버튼들 사이 간격 */
                margin-top: 20px;
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

            /* 특정 버튼 색상 변경 (초기화, 목록) */
            .button-group button[type="reset"] {
                background-color: #6c757d; /* 회색 */
            }
            .button-group button[type="reset"]:hover {
                background-color: #5a6268;
            }
            .button-group a.button-cancel { /* '목록 바로가기' 버튼 */
                background-color: #17a2b8; /* 청록색 */
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
                width: 120px; /* 라벨 컬럼 너비 고정 (필요시 조정) */
            }
            td {
                background-color: #fff; /* 데이터 셀 배경색 하얗게 */
            }
        </style>
		<script type="text/javascript">
		function validateForm(form) { 
		    // 강의명 (lectureName) 입력 확인
		    if (form.lectureName.value == "") {
		        alert("강의명을 입력하세요.");
		        form.lectureName.focus();
		        return false;
		    }
		    // 교수ID (profId) 입력 확인
		    if (form.profId.value == "") {
		        alert("교수ID를 입력하세요.");
		        form.profId.focus();
		        return false;
		    }
		    // 강의 코드 (lectureCode) 입력 확인
		    if (form.lectureCode.value == "") {
		        alert("강의 코드를 입력하세요.");
		        form.lectureCode.focus();
		        return false;
		    }
            // 강의 시작일 (lectureStartDate) 입력 확인
            if (form.lectureStartDate.value == "") {
                alert("강의 시작일을 입력하세요.");
                form.lectureStartDate.focus();
                return false;
            }
            // 강의 종료일 (lectureEndDate) 입력 확인
            if (form.lectureEndDate.value == "") {
                alert("강의 종료일을 입력하세요.");
                form.lectureEndDate.focus();
                return false;
            }
            // 전공ID (majorId) 입력 확인
            if (form.majorId.value == "") {
                alert("전공ID를 입력하세요.");
                form.majorId.focus();
                return false;
            }
		}
		</script>
	</head>
	<body>
		<h2>강의 정보 수정(Mybatis)</h2> 
		<form name="writeFrm" method="post"
			action="/lectureboard/lectureedit.do" onsubmit="return validateForm(this);"> 
		<input type="hidden" name="lectureIdx" value="${lectureBoardDTO.lectureIdx }" /> 
		<table border="1" width="90%">
		    <tr>
		        <td>강의 번호</td> 
                <td>${lectureBoardDTO.lectureIdx }</td> 
		    </tr>
            <tr>
                <td>강의명</td>
                <td>
                    <input type="text" name="lectureName" style="width:90%;" 
                        value="${lectureBoardDTO.lectureName }"/> 
                </td>
            </tr>
		    <tr>
		        <td>교수ID</td> 
		        <td>
		            <input type="text" name="profId" style="width:150px;" 
		            	value="${lectureBoardDTO.profId }" /> 
		        </td>
		    </tr>
            <tr>
                <td>강의 시작일</td>
                <td>
                    <input type="date" name="lectureStartDate" style="width:150px;"
                        value="${lectureBoardDTO.lectureStartDate }" /> 
                </td>
            </tr>
            <tr>
                <td>강의 종료일</td> 
                <td>
                    <input type="date" name="lectureEndDate" style="width:150px;"
                        value="${lectureBoardDTO.lectureEndDate }" /> 
                </td>
            </tr>
		    <tr>
		        <td>강의 코드</td> 
		        <td>
		            <textarea name="lectureCode" style="width:90%;
		            	height:100px;">${lectureBoardDTO.lectureCode }</textarea> 
		        </td>
		    </tr>
            <tr>
                <td>전공ID</td> 
                <td>
                    <input type="text" name="majorId" style="width:150px;"
                        value="${lectureBoardDTO.majorId }" /> 
                </td>
            </tr>

		    <tr>
		        <td colspan="2" align="center">
		            <button type="submit">수정 완료</button> 
		            <button type="reset">초기화</button> 
		            <button type="button" onclick="location.href='/lectureboard/lecturelist.do';"> 
		                목록 바로가기
		            </button>
		        </td>
		    </tr>
		</table>    
		</form>
	</body>
</html>