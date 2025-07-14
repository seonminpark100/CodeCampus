<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>OO대학교 eCampus</title>
		<script type="text/javascript">
		function validateForm(form) { 
		    if (form.assignment_title.value == "") {
		        alert("제목을 입력하세요.");
		        form.assignment_title.focus();
		        return false;
		    }
		    if (form.deadline.value == "") {
		        alert("마감일을 입력하세요.");
		        form.deadline.focus();
		        return false;
		    }
		    if (form.assignment_content.value == "") {
		        alert("내용을 입력하세요.");
		        form.assignment_content.focus();
		        return false;
		    }
		}
		</script>
	</head>
	<body>
		<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
	        <div class="pt-3">
	           <h2>과제 수정하기</h2>
	           <form name="writeFrm" method="post" action="./assignmentEdit.do" onsubmit="return validateForm(this);">
	           	<input type="hidden" name="assignment_idx" value="${profDTO.assignment_idx }" />
	           	<input type="hidden" name="lecture_code" value="${profDTO.lecture_code }" />
	           	<input type="hidden" name="uploaded_date" value="${profDTO.uploaded_date }" />
					<table class="table table-hover" border="1" width="90%">
					    <tr>
					        <td>제목</td>
					        <td>
					            <input type="text" name="assignment_title" style="width:90%;" 
					            	value="${profDTO.assignment_title }"/>
					        </td>
					    </tr>
					    <tr>
					        <td>마감일</td>
					        <td>
					        	<input type="date" name="deadline" style="width:150px;" value="${profDTO.deadline }"/>
					        </td>
					    </tr>
					    <tr>
					        <td>내용</td>
					        <td>
					            <textarea name="assignment_content" style="width:90%;
					            	height:100px;">${profDTO.assignment_content }</textarea>
					        </td>
					    </tr>
					    <tr>
					        <td colspan="2" align="center">
					            <button class="btn btn-dark" type="submit">작성 완료</button>
					            <button class="btn btn-dark" type="reset">RESET</button>
					            <button class="btn btn-dark" type="button" onclick="location.href='./assignmentList.do?lectureCode=${profDTO.lecture_code}';">
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