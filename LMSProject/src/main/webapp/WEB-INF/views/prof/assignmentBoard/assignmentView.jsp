<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>대학교 eCampus</title>
		<script>
		function deletePost(assignment_idx,lectureCode){
			var confirmed = confirm("정말로 삭제하겠습니까?");
			if(confirmed){
				var form = document.writeFrm;
				form.method = "post";
				form.action = "assignmentDelete.do";
				form.submit();
			}
		}
		</script>
	</head>
	<body>
	<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
	        <div class="pt-3">
	           <h2>과제 자세히 보기</h2>
	           <form name="writeFrm">
					<input type="hidden" name="assignment_idx" value="${profDTO.assignment_idx }" />
					<input type="hidden" name="lectureCode" value="${ lectureCode }" />
				</form>
				<table class="table table-hover" border="1" width="90%">
				    <colgroup>
				        <col width="15%"/> <col width="35%"/>
				        <col width="15%"/> <col width="*"/>
				    </colgroup>	
				    <!-- 게시글 정보 -->
				    <tr>
				        <td>번호</td> <td>${ profDTO.assignment_idx }</td>
				    </tr>
				    <tr>
				        <td>등록일</td> <td>${ profDTO.uploaded_date }</td>
				        <td>마감일</td> <td>${ profDTO.deadline }</td>
				    </tr>
				    <tr>
				        <td>제목</td>
				        <td colspan="3">${ profDTO.assignment_title }</td>
				    </tr>
				    <tr>
				        <td>내용</td>
				        <td colspan="3" height="100">
				        	${ profDTO.assignment_content }	        	
				        </td>
				    </tr>
				    <!-- 하단 메뉴(버튼) -->
				    <tr>
				        <td colspan="4" align="center">
				            <button class="btn btn-dark" type="button" onclick="location.href='./assignmentEdit.do?lectureCode=${lectureCode}&assignment_idx=${ profDTO.assignment_idx }';">
				                수정하기
				            </button>
				            <button class="btn btn-dark" type="button" onclick="deletePost(${ profDTO.assignment_idx }, '${ lectureCode }');">
				                삭제하기
				            </button>
				            <button class="btn btn-dark" type="button" onclick="location.href='./assignmentList.do?lectureCode=${lectureCode}';">
				                목록 바로가기
				            </button>
				        </td>
				    </tr>
				</table>
	           
	        </div>
        </main>
	</body>
</html>