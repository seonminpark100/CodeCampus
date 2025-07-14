<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>대학교 eCampus</title>
		
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
		}
		</script>
	</head>
	<body>
	 <%@ include file = "../sidebars.jsp" %>
	 <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
		<h2>강의 업로드</h2>
		 <%-- <c:forEach items="${ myFileLists }" var="row" varStatus="loop">${row.file_idx } <br/> </c:forEach> --%>
		<form name="writeFrm" method="post" enctype="multipart/form-data"
			action="./lectureEditProc.do?lectureCode=${ profDTO.lecture_code }&board_idx=${profDTO.board_idx}" onsubmit="return validateForm(this);">
		<input type="hidden" name="user_id" value="${ profDTO.user_id }">
		<table class="table table-hover" border="1" width="90%">
		    <tr>
		        <td>강의코드</td>
		        <td>
		            <input type="text" name="lecture_code" style="width:90%;" value="${ profDTO.lecture_code }" readonly="readonly"/>
		        </td>
		    </tr>
		    <tr>
		        <td>카테고리</td>
		        <td>
		           <select name="category">
		           		<option value="L">강의
		           		<option value="B">공지사항
		           		<option value="C">커뮤니티
		           </select>
		        </td>
		    </tr>
		    <tr>
		        <td>제목</td>
		        <td><input type="text" name="board_title" value="${profDTO.board_title}" style="width: 90%;"></td>
		    </tr>
		    <tr>
		        <td>내용</td>
		        <td>
		        	<textarea name="board_content" style="width:90%;height:100px;">${ profDTO.board_content}</textarea>
		        </td>
		    </tr>
		    <tr>
		        <td>첨부파일</td> 
		        <td><input type="file" name="ofile"  accept=".mov, .jpg, .png, .mp4" multiple="multiple"/> 
		        <br/> <span style="font-size: 10pt; color: grey;">* mov, png, jpg, mp4 확장자 업로드 가능</span></td>
		    </tr>
		    
		    <tr>
		        <td colspan="2" align="center">
		            <button class="btn btn-dark" type="submit">작성 완료</button>
		            <button class="btn btn-dark" type="reset">RESET</button>
		            <button class="btn btn-dark" type="button" onclick="location.href='lectureList.do?lectureCode=${profDTO.lecture_code}';">
		                목록 바로가기
		            </button>
		        </td>
		    </tr>
		    
		</table>    
		</form>
		</main>
	</body>
</html>