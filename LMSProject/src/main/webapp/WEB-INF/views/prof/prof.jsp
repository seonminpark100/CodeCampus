<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>  
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>OO대학교 eCampus</title>
		<style type="text/css">
		.bl-left { position:relative; float:left; width:50%; height:300px; display: flex; justify-content: center; align-items: center; }
		.bl-right{ position:relative; float:right; width:50%; height:300px; display: flex; justify-content: center; align-items: center; }
		</style>
	</head>
<<<<<<< HEAD
	<body>
	<div class="container">
	<%@ include file = "top.jsp" %>
		<!-- 로그인을 통해 '인증'되었다면 이부분이 출력된다.  -->
		<s:authorize access="isAuthenticated()">
			<!-- name속성을 통해 로그인 아이디를 출력한다. -->
			로그인 아이디 : <s:authentication property="name"/><br/>
		</s:authorize>
			로그인 세션 정보: ${user_id}
		<div class="container">
				<div class="bl-left">
				    <span class="bluelight">
				        <h2>강의목록</h2>
				        <c:choose>
					    <c:when test="${ empty lists }"> 
					        <tr>
					            <td colspan="5" align="center">
					                등록된 게시물이 없습니다^^*
					            </td>
					        </tr>
					    </c:when> 
					    <c:otherwise> 
					        <c:forEach items="${ lists }" var="row" varStatus="loop">    
 					        <tr align="center">
					            <a href="./submain.do?lectureId=${row.LECTURE_ID}">${ row.CLASS_1 }</a> <br/>
							    <a href="./view.do?lectureId=${row.LECTURE_ID}">${ row.CLASS_2 }</a> <br/>
							    <a href="./view.do?lectureId=${row.LECTURE_ID}">${ row.CLASS_3 }</a> <br/>
					        </tr>
					        </c:forEach>        
					    </c:otherwise>    
					</c:choose>
				    </span>
				</div>
		       	<div class="bl-right">
		            <span class="bluelight">
		                <h2>Quick Menu(임시메뉴임)</h2>
		                <a href="/prof/lectureboard.do"><button>강의</button></a>
		                <a href="/prof/noticeboard.do"><button>공지사항</button></a>
		                <a href="/prof/absentboard.do"><button>출석부</button></a>
		                <button>메뉴3</button>
		                <button>메뉴4</button>
		                <button>메뉴5</button>
		                <button>메뉴6</button>
		            </span>
		        </div>
			</div>
		</div>
		
=======
	
	<body>
	<div class="container">
	<%@ include file = "top.jsp" %>
		<div class="row">
		  <div class="leftcolumn">
		  
		    <div class="card">
		      <h2>내 강의 목록</h2>
		      <c:choose>
			  	<c:when test="${ empty lists }"> 
			        <tr>
			            <td colspan="5" align="center">
			                등록된 게시물이 없습니다^^*
			            </td>
			        </tr>
			    </c:when> 
			    <c:otherwise> 
			        <c:forEach items="${ lists }" var="row" varStatus="loop">    
			            <p><a href="./submain.do?lectureCode=${row.lecture_code}">${ row.lecture_name }</a></p><br/>
			        </c:forEach>        
			    </c:otherwise>    
			  </c:choose>
		    </div>
		    
		    <div class="card">
		      <h2>공지사항</h2>
		      <p>Some text..</p>
		      <p>Some text..</p>
		      <p>Some text..</p>
		    </div>
		    
		  	</div>
		 </div>
	  </div>
>>>>>>> origin/master
	</body>
</html>