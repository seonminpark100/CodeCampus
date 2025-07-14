<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>파일첨부형 게시판</title>
		<script type="text/javascript">
		function validateForm(form) {  // 폼 내용 검증
		    if (form.name.value == "") {
		        alert("작성자를 입력하세요.");
		        form.name.focus();
		        return false;
		    }
		    if (form.title.value == "") {
		        alert("제목을 입력하세요.");
		        form.title.focus();
		        return false;
		    }
		    if (form.content.value == "") {
		        alert("내용을 입력하세요.");
		        form.content.focus();
		        return false;
		    }
		}
		</script>
	</head>
	<body>
	<jsp:include page="../Common/Link.jsp"></jsp:include>
	<div class='container'>
		<h2>파일 첨부형 게시판 - 수정하기(Edit)</h2>
		<form name="fileFrm" method="post" enctype="multipart/form-data" 
			action="../mvcboard/edit.do" onsubmit="return validateForm(this)">
			 <input type="hidden" name="idx" value="${ dto.idx }"/> 
			 <input type="hidden" name="prevOfile" value="${ dto.ofile }"/> 
		     <input type="hidden" name="prevSfile" value="${ dto.sfile }"/> 
		     
		     검증된 패스워드 #()
		     
			 <table border="1" width="90%">
		        <tr>
		            <td>작성자</td>
		            <td>
		                <input type="text" name="name" style="width: 150px;" 
		                	value="${ dto.name }"/>
		            </td>
		        </tr>
		        <tr>
		            <td>제목</td>
		            <td>
		                <input type="text" name="title" style="width: 90%;" 
		                	value="${ dto.title }"/>
		            </td>
		        </tr>
		        <tr>
		            <td>내용</td>
		            <td>
		            	<!-- 태그 사이에 공백이 있으면 안됨 한줄로 작성하기. -->
		                <textarea name="content" style="width: 90%; height: 100px;">${ dto.content }</textarea>
		            </td>
		        </tr>
		        <tr>
		            <td>첨부파일</td>
		            <td>
		            	<input type="file" name="ofile" />
		            </td>
		        </tr>
		        <tr>
		            <td colspan="2" align="center">
		                <button type="submit">작성 완료</button>
		                <button type="reset">RESET</button>
		                <button type="button" onclick="location.href='../mvcboard/list.do';">
		                    목록 보기</button>
		            </td>
		        </tr>
	   	 	</table>
		</form>
		</div>
		<jsp:include page="../Common/Bottom.jsp"></jsp:include>
	</body>
</html>