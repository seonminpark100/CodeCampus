<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>대학교 eCampus</title>
		<script>
		function validateForm(writeFrm){

			if(writeFrm.score.value==''){
				alert("점수를 입력해주세요.");
				writeFrm.score.focus();
				return false;
			}
			if(writeFrm.assignment_comment.value==''){
				alert("학생을 위한 과제 피드백을 입력해주세요.");
				writeFrm.assignment_comment.focus();
				return false;
			}
		}
		</script>
	</head>
	<body>
	<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
	        <div class="pt-3">
	           <h2>과제 자세히 보기</h2>
	           <form name="writeFrm" method="post" 
	           	action="./submittedAssignmentGetSocreProc.do?lectureCode=${lectureCode}&assignment_submit_idx=${ profDTO.assignment_submit_idx }"
	           	onsubmit="return validateForm(this);">
					<input type="hidden" name="assignment_idx" value="${profDTO.assignment_idx }" />
					<input type="hidden" name="assignment_sfile" value="${profDTO.assignment_sfile }" />
					<input type="hidden" name="assignment_ofile" value="${profDTO.assignment_ofile }" />
					<input type="hidden" name="assignment_submit_idx" value="${profDTO.assignment_submit_idx }" />
					<input type="hidden" name="lectureCode" value="${ lectureCode }" />
				
				<table class="table table-hover" border="1" width="90%">
				    <colgroup>
				        <col width="15%"/> <col width="35%"/>
				        <col width="15%"/> <col width="*"/>
				    </colgroup>	
				    <!-- 게시글 정보 -->
				    <tr>
				        <td>학생 학번</td> <td>${ profDTO.user_id }</td>
				        <td>학생 이름</td> <td>${ profDTO.user_name }</td>
				    </tr>
				    <tr>
				        <td>제출일</td> <td>${ profDTO.submitted_date }</td>
				        <td>마감일</td> <td>${ profDTO.deadline }</td>
				    </tr>
				    <tr>
				        <td>제목</td> <td>${ profDTO.assignment_title }</td>
				    </tr>
				    <tr>
				        <td>내용</td>
				        <td colspan="3" height="100">
				        	${ profDTO.assignment_content_s }	        	
				        </td>
				    </tr>
				    <tr>
				        <td>첨부파일</td>  
				         <td><a href="/assignmentFileDownload.do/${ profDTO.assignment_sfile }">
							    ${ profDTO.assignment_ofile }
							</a></td> 
				    </tr>
				    <tr>
				        <td>점수</td> <td> <input type="number" name="score" value="${profDTO.score}"> </td>
				    </tr>
				    <tr>
				        <td>피드백</td><td> 
				        <textarea name="assignment_comment" style="width:90%;height:100px;">${profDTO.assignment_comment}</textarea>
				        </td>
				    </tr>
				    <!-- 하단 메뉴(버튼) -->
				    <tr>
				        <td colspan="4" align="center">
				            <button class="btn btn-dark" type="submit">
				                채점하기
				            </button>
				            <button class="btn btn-dark" type="button" onclick="location.href='./submittedAssignmentList.do?lectureCode=${lectureCode}';">
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