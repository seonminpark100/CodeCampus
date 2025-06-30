<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>  
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원수정</title>
	</head>
	<body>
		<h2>회원수정</h2>
		<form action="edit.do" method="post">
			<table border="1">
				<tr>
					<th>아이디(수정불가)</th>
					<td><input type="text" name="id" value="${dto.id }" readonly /></td>
				</tr>
				<tr>
					<th>패스워드</th>
					<td><input type="text" name="pass" value="${dto.pass }" /></td>
				</tr>
				<tr>
					<th>이름</th>
					<td><input type="text" name="name" value="${dto.name }" /></td>
				</tr>
			</table>
			<input type="submit" value="전송하기" />
		</form>
	</body>
</html>