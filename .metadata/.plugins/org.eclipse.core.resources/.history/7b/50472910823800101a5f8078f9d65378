<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>아이디 중복체크</title>
    <script type="text/javascript">
	    function idok(id){
			// 아이디 입력값 적용
			opener.document.writeFrm.id.value = id;
			// 중복 확인 완료 표시
			opener.document.writeFrm.idCheckbtn.value = "checked";
			opener.document.writeFrm.id.readOnly = true;
			self.close();
		}
    </script>
</head>
<body>
    <h3>아이디 중복 확인</h3>
    
    <!-- 아이디 재입력 가능 -->
    <form action="${pageContext.request.contextPath}/mvcboard/idcheck.do" method="get" name="frm">
        아이디:
        <input type="text" name="id" value="${id}" />
        <input type="submit" value="중복 체크" />
    </form>

    <!-- 중복됨 -->
    <c:if test="${result == 1}">
        <script>
            alert("${id}는 이미 사용 중인 아이디입니다.");
        </script>
        <p style="color:red;">${id}는 이미 사용 중인 아이디입니다.</p>
    </c:if>

    <!-- 사용 가능 -->
    <c:if test="${result == -1}">
        <p style="color:green;">${id}는 사용 가능한 아이디입니다.</p>
        <input type="button" value="사용" onclick="idok('${id}')" />
    </c:if>
</body>
</html>
