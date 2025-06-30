<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<script>
    function validateSearch() {
        var searchKeyword = document.getElementById("searchKeyword").value;
        if (searchKeyword.trim() === "") {
            alert("검색어를 입력하세요.");
            return false; 
        }
        return true; 
    }
</script>
</head>
<body>
    <h2>회원목록</h2>
    <p>회원 목록 크기: ${fn:length(memberList)}</p>

    <form action="list.do" method="get" onsubmit="return validateSearch();">
        <select name="searchField">
            <option value="userid">아이디</option>
            <option value="username">이름</option>
        </select>
        <input type="text" name="searchKeyword" id="searchKeyword" placeholder="검색어를 입력하세요.">
        <input type="submit" value="검색">
    </form>

    <table border="1">
        <thead>
            <tr>
                <th>아이디</th>
                <th>패스워드</th>
                <th>이름</th>
                <th>생일</th>
                <th>가입일</th>
                <th>권한</th>
                <th>수정/삭제</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty memberList}">
                    <tr>
                        <td colspan="7">등록된 회원이 없습니다.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="dto" items="${memberList}">
                        <tr>
                            <td>${dto.userid}</td>
                            <td>${dto.userpw}</td>
                            <td>${dto.username}</td>
                            <td>${dto.userbirthdate}</td>
                            <td>${dto.joindate}</td>
                            <td>${dto.authority}</td>
                            <td>
                                <a href="edit.do?userid=${dto.userid}">수정</a>
                                 <a href="delete.do?userid=${dto.userid}" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>