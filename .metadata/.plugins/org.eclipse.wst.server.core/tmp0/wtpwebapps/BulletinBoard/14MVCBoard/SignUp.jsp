<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>자유게시판</title>
		
		<script>
		
		function validateForm(form) {  // 폼 내용 검증
		    if (form.name.value == "") {
		        alert("이름을 입력하세요.");
		        form.name.focus();
		        return false;
		    }
		    if (form.id.value == "") {
		        alert("아이디를 입력하세요.");
		        form.id.focus();
		        return false;
		    }
		    if (form.pass.value == "") {
		        alert("비밀번호를 입력하세요.");
		        form.pass.focus();
		        return false;
		    }
		    if (form.idCheckbtn.value != "checked") {
		        alert("아이디 중복확인을 해주세요.");
		        return false;
		    }
		    
		    return true; 
		}
		
		function idCheck(form){
			if(form.id.value == ""){
				alert("아이디를 입력해주세요");
				form.id.focus();
				return false;
			}
			var url = "<%=request.getContextPath()%>/mvcboard/idcheck.do?id=" + form.id.value;
		    window.open(url, "_blank_1", "width=500,height=300, toolbar=no,menubar=no,resizable=no,scrollbars=yes");
		}
		
		</script>
	</head>
	<body>
	  <jsp:include page="../Common/Link.jsp"></jsp:include>
	  
		<div class= "container">
			<form name="writeFrm" method="post" action="" onsubmit="return validateForm(this);">
		    	이름: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="name"/> <br/>
		    	아이디: &nbsp;&nbsp;&nbsp;<input type="text" name="id"/> 
		    	<!-- 중복체크하기 -->
		    	<input type="button" value="중복 체크" onclick="return idCheck(this.form)" />
				<input type="hidden" name="idCheckbtn" value="unchecked">
		    	
		    	<br/>
		    	비밀번호: <input type="password" name="pass"/> <br/><br/>
	            <button type="submit">회원가입</button>
	            <button type="reset">다시입력</button>
	            
			</form>
		</div>
		
		
      <jsp:include page="../Common/Bottom.jsp"></jsp:include>
	</body>
</html>