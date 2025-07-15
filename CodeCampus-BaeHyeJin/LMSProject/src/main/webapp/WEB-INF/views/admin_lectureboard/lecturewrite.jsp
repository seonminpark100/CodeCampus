<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>강의 등록</title>
		<script>
		let validateForm = (frm) => {
			if(frm.lectureName.value == ''){
				alert('강의명을 입력하세요.');
				frm.lectureName.focus();
				return false;
			}
			if(frm.profId.value == ''){
				alert('교수ID를 입력하세요.');
				frm.profId.focus();
				return false;
			}
			if(frm.lectureStartDate.value == ''){
				alert('강의 시작일을 입력하세요.');
				frm.lectureStartDate.focus();
				return false;
			}
			if(frm.lectureEndDate.value == ''){
				alert('강의 종료일을 입력하세요.');
				frm.lectureEndDate.focus();
				return false;
			}
			if(frm.lectureCode.value == ''){
				alert('강의 코드를 입력하세요.');
				frm.lectureCode.focus();
				return false;
			}
			if(frm.majorId.value == ''){
				alert('전공ID를 입력하세요.');
				frm.majorId.focus();
				return false;
			}
            return true; // 모든 유효성 검사 통과 시 true 반환
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
		            <input type="text" name="lectureName" style="width:90%;" />
		        </td>
			</tr>
			<tr>
				<td>교수ID</td>
				<td>
		            <input type="text" name="profId" style="width:150px;" />
		        </td>
			</tr>
            <tr>
            	<td>강의 시작일</td>
			<td>
		            <input type="date" name="lectureStartDate" style="width:150px;" />
			</td>
			</tr>
            <tr>
            	<td>강의 종료일</td>
			<td>
		            <input type="date" name="lectureEndDate" style="width:150px;" />
		        </td>
			</tr>
			<tr>
				<td>강의 코드</td>
			<td>
		            <textarea name="lectureCode" style="width:90%;height:100px;"></textarea>
		        </td>
			</tr>
            <tr>
            	<td>전공ID</td>
			<td>
		            <input type="text" name="majorId" style="width:150px;" />
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