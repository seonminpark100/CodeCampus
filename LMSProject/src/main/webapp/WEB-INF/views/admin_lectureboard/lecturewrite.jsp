<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>강의 등록</title>
		<style>
            /* Reset / Basic Styles */
            body { 
                font-family: Arial, sans-serif; 
                margin: 0; /* 기본 마진 제거 */
                padding: 20px; /* 전체 페이지 패딩 */
                background-color: #f4f4f4; 
                display: flex; 
                flex-direction: column; 
                align-items: center; 
                min-height: 90vh; /* 뷰포트 높이의 최소 90% */
                box-sizing: border-box; /* 패딩이 너비에 포함되도록 */
            }

            /* Page Title */
            h2 { 
                color: #333; 
                text-align: center; 
                margin-bottom: 30px; 
            }

            /* Container for the form */
            .form-section { 
                background-color: #fff; 
                padding: 30px; 
                border-radius: 8px; 
                box-shadow: 0 2px 10px rgba(0,0,0,0.1); 
                max-width: 1000px; /* 강의 등록 폼의 너비를 적절히 조정 (필요에 따라 1200px 등으로 변경 가능) */
                width: 100%; 
                margin: 20px auto; /* 상하 여백, 좌우 자동 (중앙 정렬) */
                box-sizing: border-box; 
            }

            /* Table Layout for Form */
            table {
                width: 100%; /* 테이블 너비 100%로 설정하여 부모 요소(form-section)의 너비를 따르도록 함 */
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
                width: 120px; /* 라벨 컬럼 너비 고정 */
                width: 20%;
            }
            td {
                background-color: #fff; /* 데이터 셀 배경색 */
                width: 80%;
            }
            /* Input specific width overrides for elements inside td (table cells) */
            td input[type="text"],
            td input[type="date"],
            td select {
                width: calc(100% - 22px); /* 기본적으로 100% 너비에서 패딩/보더 제외 */
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 1em;
            }
            /* 특정 필드의 너비만 다르게 설정하고 싶을 때 */
            td input[type="text"][name="prof_Id"],
            td input[type="date"],
            td input[type="text"][name="major_Id"] { 
                width: 150px; 
            }
            td textarea[name="lecture_Code"] {
                width: calc(100% - 22px); 
                height: 100px; /* textarea의 높이 */
            }


            /* Button Group Styling */
            .button-group {
                display: flex;
                justify-content: center; /* 버튼들을 중앙으로 정렬 */
                gap: 10px; /* 버튼들 사이 간격 */
                margin-top: 20px;
            }

            /* All Buttons Base Style */
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

            /* Hover Style */
            .button-group button:hover, .button-group a.button:hover {
                background-color: #0056b3;
            }

            /* Specific Button Colors */
            /* Submit Button (등록) */
            .button-group button[type="submit"] {
                background-color: #28a745; /* 초록색 */
            }
            .button-group button[type="submit"]:hover {
                background-color: #218838;
            }

            /* Reset Button (초기화) */
            .button-group button[type="reset"] {
                background-color: #6c757d; /* 회색 */
            }
            .button-group button[type="reset"]:hover {
                background-color: #5a6268;
            }

            /* Back to List Button (목록 바로가기) */
            .button-group button.button-cancel { 
                background-color: #17a2b8; /* 청록색 */
            }
            .button-group button.button-cancel:hover {
                background-color: #138496;
            }
            /* 관리자 페이지로 돌아가는 버튼 (필요시 추가) */
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
			if(frm.lecture_Name.value == ''){
				alert('강의명을 입력하세요.');
				frm.lecture_Name.focus();
				return false;
			}
			if(frm.prof_Id.value == ''){
				alert('교수ID를 입력하세요.');
				frm.prof_Id.focus();
				return false;
			}
			if(frm.lecture_Start_Date.value == ''){
				alert('강의 시작일을 입력하세요.');
				frm.lecture_Start_Date.focus();
				return false;
			}
			if(frm.lecture_End_Date.value == ''){
				alert('강의 종료일을 입력하세요.');
				frm.lecture_End_Date.focus();
				return false;
			}
			if(frm.lecture_Code.value == ''){
				alert('강의 코드를 입력하세요.');
				frm.lecture_Code.focus();
				return false;
			}
			if(frm.major_Id.value == ''){
				alert('전공ID를 입력하세요.');
				frm.major_Id.focus();
				return false;
			}
            return true; 
		}
		</script>
	</head>
	<body>
        <h2>강의 등록</h2>
        <div class="form-section"> <form name="writeFrm" method="post"
                action="/admin_lectureboard/lecturewrite.do" onsubmit="return validateForm(this);">
            <table border="1"> <tr>
                    <th>강의명</th> <td>
                        <input type="text" name="lecture_Name" /> </td>
                </tr>
                <tr>
                    <th>교수ID</th> <td>
                        <input type="text" name="prof_Id" /> </td>
                </tr>
                <tr>
                    <th>강의 시작일</th> <td>
                        <input type="date" name="lecture_Start_Date" /> </td>
                </tr>
                <tr>
                    <th>강의 종료일</th> <td>
                        <input type="date" name="lecture_End_Date" /> </td>
                </tr>
                <tr>
                    <th>강의 코드</th> <td>
                        <textarea name="lecture_Code" rows="5"></textarea> </td>
                </tr>
                <tr>
                    <th>전공ID</th> <td>
                        <input type="text" name="major_Id" /> </td>
                </tr>
                <tr>
                    <td colspan="2" class="button-group"> <button type="submit">등록</button>
                        <button type="reset">초기화</button>
                        <button type="button" class="button-cancel" onclick="location.href='/admin_lectureboard/lecturelist.do';">
                            목록 바로가기
                        </button>
                    </td>
                </tr>
            </table>    
            </form>
        </div> 
      </body>
</html>