<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>대학교 eCampus</title>
<<<<<<< HEAD
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
=======
		
		<script>
		function validateForm(writeFrm){

			if(writeFrm.board_title.value==''){
				alert("강의명을 입력해주세요.");
				writeFrm.board_title.focus();
				return false;
			}
			if(writeFrm.board_content.value==''){
				alert("강의내용을 입력해주세요.");
				writeFrm.board_content.focus();
				return false;
			}
			/* if(writeFrm.ofile.value==''){
				alert("첨부파일은 필수 입력입니다.");
				writeFrm.ofile.focus();
				return false;
			} */
>>>>>>> origin/master
		}
		</script>
	</head>
	<body>
<<<<<<< HEAD
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
=======
	 <%@ include file = "../sidebars.jsp" %>
	 <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
		<h2>강의 업로드</h2>
		<form name="writeFrm" method="post" enctype="multipart/form-data"
			action="./lectureUploadProc.do?lectureCode=${ lectureCode }" onsubmit="return validateForm(this);">
		<input type="hidden" name="user_id" value="${ profDTO.user_id }">
		<table class="table table-hover" border="1" width="90%">
		    <tr>
		        <td>교수명</td>
		        <td>
		            <input type="text" name="user_name" style="width:150px;" value="${ profDTO.user_name }" readonly="readonly"/>
		        </td>
		    </tr>
		    <tr>
		        <td>강의코드</td>
		        <td>
		            <input type="text" name="lecture_code" style="width:90%;" value="${ profDTO.lecture_code }" readonly="readonly"/>
		        </td>
		    </tr>
		    <tr>
		        <td>강의명</td>
		        <td>
		            <input type="text" name="lecture_name" style="width:90%;" value="${ profDTO.lecture_name }" readonly="readonly"/>
		        </td>
		    </tr>
		    <tr>
		        <td>카테고리</td>
		        <td>
		           <select name="category">
		           		<option value="L">강의
		           		<option value="N">공지사항
		           		<option value="C">커뮤니티
		           		<option value="Q">QnA
		           </select>
		        </td>
		    </tr>
		    <tr>
		        <td>제목</td>
		        <td><input type="text" name="board_title" style="width: 90%"></td>
		    </tr>
		    <tr>
		        <td>내용</td>
		        <td>
		        	<textarea name="board_content" style="width:90%;height:100px;"></textarea>
		        </td>
		    </tr>
		    <tr>
		        <td>첨부파일</td>
		        <td><input type="file" name="ofile" accept=".mov, .jpg, .png, .mp4" multiple> <br/> <span style="font-size: 10pt; color: grey;">* mov, png, jpg, mp4 확장자 업로드 가능</span></td>
		        
>>>>>>> origin/master
		    </tr>
		    
		    <tr>
		        <td colspan="2" align="center">
<<<<<<< HEAD
		            <button type="submit">작성 완료</button>
		            <button type="reset">RESET</button>
		            <button type="button" onclick="location.href='lectureboard.do';">
=======
		            <button class="btn btn-dark" type="submit">작성 완료</button>
		            <button class="btn btn-dark" type="reset">RESET</button>
		            <button class="btn btn-dark" type="button" onclick="location.href='lectureList.do?lectureCode=${lectureCode}';">
>>>>>>> origin/master
		                목록 바로가기
		            </button>
		        </td>
		    </tr>
		    
		</table>    
		</form>
<<<<<<< HEAD
=======
		</main>
>>>>>>> origin/master
	</body>
</html>