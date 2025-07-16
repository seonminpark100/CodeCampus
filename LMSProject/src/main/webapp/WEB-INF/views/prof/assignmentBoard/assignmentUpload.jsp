<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>대학교 eCampus</title>
		<script>
		function validateForm(writeFrm){
			if(writeFrm.assignment_title.value==''){
				alert("제목을 입력해주세요.");
				writeFrm.assignment_title.focus();
				return false;
			}
			if(writeFrm.deadline.value==''){
				alert("마감일을 지정해주세요.");
				writeFrm.deadline.focus();
				return false;
			}
			if(writeFrm.assignment_content.value==''){
				alert("내용을 입력해주세요.");
				writeFrm.assignment_content.focus();
				return false;
			}
		}
		</script>
	</head>
	<body>
		<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
			<div class="pt-3">
	           <h2>과제 등록</h2>
	            
	            <form name="writeFrm" method="post" action="./assignmentUpload.do" onsubmit="return validateForm(this);">
				<input type="hidden" name="lectureCode" value="${ lectureCode }">
				<table border="1" width="90%">
				    <tr>
				        <td>제목</td>
				        <td>
				            <input type="text" name="assignment_title" style="width:90%;" />
				        </td>
				    </tr>
				    <tr>
				        <td>마감일</td>
				        <td>
				            <input type="date" name="deadline" style="width:150px;" />
				        </td>
				    </tr>
				    <tr>
				        <td>내용</td>
				        <td>
				            <textarea name="assignment_content" style="width:90%;height:100px;"></textarea>
				        </td>
				    </tr>
				    <tr>
				        <td colspan="2" align="center">
				            <button type="submit">작성 완료</button>
				            <button type="reset">RESET</button>
				            <button type="button" onclick="location.href='./assignmentList.do?lectureCode=${lectureCode}';">
				                목록 바로가기
				            </button>
				        </td>
				    </tr>
				</table>    
			</form>
	        </div>
   	 	</main>
	</body>
</html>