<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>대학교 eCampus</title>
		<script>
		function validateForm(writeFrm){

			if(writeFrm.LECTURE_START_DATE.value==''){
				alert("강의 시작일을 입력해주세요.");
				writeFrm.LECTURE_START_DATE.focus();
				return false;
			}
			if(writeFrm.LECTURE_END_DATE.value==''){
				alert("강의 종료일을 입력해주세요.");
				writeFrm.LECTURE_END_DATE.focus();
				return false;
			}
		}
		</script>
	</head>
	<body>
		<h2>게시판 작성(Mybatis)</h2>
		<form name="writeFrm" method="post"
			action="./lectureUpload.do" onsubmit="return validateForm(this);">
		<table border="1" width="90%">
		    <tr>
		        <td>강의명</td>
		        <td>
		            <input type="text" name="LECTURE_NAME" style="width:150px;" value="" readonly="readonly"/>
		        </td>
		    </tr>
		    <tr>
		        <td>교수명</td>
		        <td>
		            <input type="text" name="PROF_NAME" style="width:90%;" value="" readonly="readonly"/>
		        </td>
		    </tr>
		    <tr>
		        <td>시작일</td>
		        <td><input type="date" name="LECTURE_START_DATE"> </td>
		    </tr>
		    <tr>
		        <td>종료일</td>
		        <td><input type="date" name="LECTURE_END_DATE"></td>
		    </tr>
		    <tr>
		        <td>첨부파일</td>
		        <td><input type="file"> </td>
		    </tr>
		    
		    <tr>
		        <td colspan="2" align="center">
		            <button type="submit">작성 완료</button>
		            <button type="reset">RESET</button>
		            <button type="button" onclick="location.href='lectureboard.do';">
		                목록 바로가기
		            </button>
		        </td>
		    </tr>
		    
		</table>    
		</form>
	</body>
</html>