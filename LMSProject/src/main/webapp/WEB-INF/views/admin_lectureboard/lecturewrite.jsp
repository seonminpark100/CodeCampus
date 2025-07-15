<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>강의 등록</title>
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
		<form name="writeFrm" method="post"
			action="/admin_lectureboard/lecturewrite.do" onsubmit="return validateForm(this);">
		<table border="1" width="90%">
		<tr>
				<td>강의명</td>
				<td>
		            <input type="text" name="lecture_Name" style="width:90%;" />
		        </td>
			</tr>
			<tr>
				<td>교수ID</td>
				<td>
		            <input type="text" name="prof_Id" style="width:150px;" />
		        </td>
			</tr>
            <tr>
            	<td>강의 시작일</td>
			<td>
		            <input type="date" name="lecture_Start_Date" style="width:150px;" />
			</td>
			</tr>
            <tr>
            	<td>강의 종료일</td>
			<td>
		            <input type="date" name="lecture_End_Date" style="width:150px;" />
		        </td>
			</tr>
			<tr>
				<td>강의 코드</td>
			<td>
		            <textarea name="lecture_Code" style="width:90%;height:100px;"></textarea>
		        </td>
			</tr>
            <tr>
            	<td>전공ID</td>
			<td>
		            <input type="text" name="major_Id" style="width:150px;" />
		        </td>
			</tr>
			<tr>
				<td colspan="2" align="center">	
		            <button type="submit">등록</button>
		            <button type="reset">초기화</button>
		            <button type="button" onclick="location.href='/admin_lectureboard/lecturelist.do';">
		                목록 바로가기
		            </button>
		        </td>
			</tr>
		</table>
		</form>
	</body>
</html>