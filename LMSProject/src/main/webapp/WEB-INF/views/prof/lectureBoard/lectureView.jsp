<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>대학교 eCampus</title>
		
		<script>
		function deletePost(board_idx,lectureCode){
			var confirmed = confirm("정말로 삭제하겠습니까?");
			if(confirmed){
				var form = document.writeFrm;
				form.method = "post";
				form.action = "lectureDelete.do";
				form.submit();
			}
		}
		</script>
	</head>
	<body>
	<%@ include file = "../sidebars.jsp" %>
		<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
	        <div class="pt-3">
	           <h2>강의 자세히 보기 </h2>
	           <form name="writeFrm">
	           <input type="hidden" name="board_idx" value="${result.board_idx }" />
	           <input type="hidden" name="lectureCode" value="${ result.lecture_code }" />
				<table class="table table-hover" border="1" style="width: 90%">
				    <colgroup>
				        <col width="15%"/> <col width="35%"/>
				        <col width="15%"/> <col width="*"/>
				    </colgroup>	
				    <!-- 게시글 정보 -->
				    <tr>
				        <td>번호</td> <td>${ result.board_idx }</td>
				        <td>업로드날짜</td> <td>${ result.board_postdate }</td>
				    </tr>
				    <tr>
				        <td>제목</td>
				        <td colspan="3">${ result.board_title }</td>
				    </tr>
				    <tr>
				        <td>내용</td>
				        <td colspan="3" height="100">
				        	${ result.board_content }	        	
				        </td>
				    </tr>
				    </table>
				<c:choose>
			    <c:when test="${ empty myFileLists }"> 
			        <tr>
			            <td colspan="5" align="center">
			                등록된 첨부파일이 없습니다.
			            </td>
			        </tr>
			    </c:when> 
			    <c:otherwise> 
			        <c:forEach items="${ myFileLists }" var="row" varStatus="loop">    
			        <tr align="center">
			         <tr>
				        <c:if test="${fn:endsWith(row.sfile, '.mov') || fn:endsWith(row.sfile, '.mp4')}">
					        <td colspan="3"> <td><video src="./../uploads/${row.sfile }" controls="controls" width="500"></video></td> <br/><br/>
				        </c:if>
				        <c:if test="${fn:endsWith(row.sfile, '.png') || fn:endsWith(row.sfile, '.jpg')}">
					        <td colspan="3"> <td><img src="./../uploads/${row.sfile }" width="200"></img></td> <br/><br/>
				        </c:if>
				        
				    </tr>
			        </c:forEach>        
			    </c:otherwise>    
			</c:choose>
				    <table class="table table-hover" >
				    <!-- 하단 메뉴(버튼) -->
				    <tr>
				        <td colspan="4" align="center">
				            <button class="btn btn-dark" type="button" onclick="location.href='./lectureEdit.do?lectureCode=${lectureCode}&board_idx=${ result.board_idx }';">
				                수정하기
				            </button>
				            <button class="btn btn-dark" type="button" onclick="deletePost(${ result.board_idx }, '${ lectureCode }');">
				                삭제하기
				            </button>
				            <button class="btn btn-dark" type="button" onclick="location.href='./lectureList.do?lectureCode=${lectureCode}';">
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