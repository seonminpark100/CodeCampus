<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 20px;
        background-color: #f4f4f4;
        display: flex; /* Flexbox를 사용하여 컨텐츠 중앙 정렬 */
        flex-direction: column; /* 세로 방향으로 정렬 */
        align-items: center; /* 가로 중앙 정렬 */
        min-height: 90vh; /* 뷰포트 높이의 최소 90% */
    }


    .container {
        background-color: #fff;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        max-width: 900px; /* 테이블이 넓을 수 있으므로 max-width 증가 */
        width: 100%; /* 너비 100% 사용 (max-width 내에서) */
        margin: 20px auto; /* 상하 여백, 좌우 자동 (중앙 정렬) */
        box-sizing: border-box; /* 패딩이 너비에 포함되도록 */
    }

    h2 {
        color: #333;
        text-align: center;
        margin-bottom: 30px;
    }

    /* 검색 폼 스타일 */
    .search-form-group {
        margin-bottom: 20px;
        display: flex; /* 요소를 한 줄에 정렬 */
        gap: 10px; /* 요소들 간의 간격 */
        justify-content: center; /* 가로 중앙 정렬 */
        align-items: center; /* 세로 중앙 정렬 */
    }
    .search-form-group select,
    .search-form-group input[type="text"] {
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
        font-size: 1em;
        flex-grow: 1; /* 가용 공간을 차지하도록 */
        max-width: 200px; /* 최대 너비 제한 */
    }
    .search-form-group input[type="submit"] {
        background-color: #007bff;
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 1em;
        transition: background-color 0.3s ease;
    }
    .search-form-group input[type="submit"]:hover {
        background-color: #0056b3;
    }

    /* 테이블 스타일 */
    table {
        width: 100%;
        border-collapse: collapse; /* 테이블 셀 간의 여백 제거 */
        margin-top: 20px;
    }

    table, th, td {
        border: 1px solid #ddd; /* 모든 경계선 통일 */
    }

    th, td {
        padding: 12px 15px; /* 패딩 증가 */
        text-align: left;
    }

    th {
        background-color: #f2f2f2;
        color: #555;
        font-weight: bold;
    }

    tr:nth-child(even) {
        background-color: #f9f9f9; /* 짝수 행 배경색 */
    }

    tr:hover {
        background-color: #f1f1f1; /* 마우스 오버 시 배경색 */
    }

    /* 버튼 스타일 (toggle-button) */
    .toggle-button {
        padding: 8px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 0.9em;
        transition: background-color 0.3s ease;
        white-space: nowrap; /* 텍스트가 줄바꿈되지 않도록 */
    }

    .toggle-button.active { /* 활성 상태일 때 (비활성으로 변경 버튼) */
        background-color: #dc3545; /* 빨간색 계열 */
        color: white;
    }

    .toggle-button.active:hover {
        background-color: #c82333;
    }

    .toggle-button.inactive { /* 비활성 상태일 때 (활성으로 변경 버튼) */
        background-color: #28a745; /* 초록색 계열 */
        color: white;
    }

    .toggle-button.inactive:hover {
        background-color: #218838;
    }

    /* 수정/삭제 링크 스타일 */
    .action-links a {
        color: #007bff;
        text-decoration: none;
        margin-right: 10px;
        transition: color 0.3s ease;
    }

    .action-links a:hover {
        color: #0056b3;
        text-decoration: underline;
    }

    /* 메시지 스타일 (계정 생성 페이지와 동일하게) */
    .message {
        background-color: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
        padding: 10px;
        margin-bottom: 20px;
        border-radius: 4px;
        text-align: center;
    }
    .back-to-admin-button {
        position: absolute; /* 절대 위치 지정 */
        top: 20px; /* 상단에서 20px */
        left: 20px; /* 왼쪽에서 20px */
        background-color: #007bff; /* 파란색 (admin-sidebar의 btn-blue와 유사) */
        color: white;
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        text-decoration: none; /* 링크 밑줄 제거 */
        font-size: 1em;
        transition: background-color 0.3s ease;
        z-index: 1000; /* 다른 요소 위에 표시 */
    }

    .back-to-admin-button:hover {
        background-color: #0056b3; /* 호버 시 진한 파란색 */
    }
</style>

</head>
<body>
	<a href="<c:url value='/admin'/>" class="back-to-admin-button">관리자 페이지로</a>

    <h2>회원목록</h2>
    <p>회원 목록 크기: ${fn:length(memberList)}</p>

    <form action="/admin/accountedit/list.do" method="get" onsubmit="return validateSearch();">
        <select name="search_Field">
    		<option value="userId">아이디</option>
    		<option value="userName">이름</option>
		</select>
        <input type="text" name="search_Keyword" id="search_Keyword" placeholder="검색어를 입력하세요.">
        <input type="submit" value="검색">
    </form>

    <table border="1">
        <thead>
            <tr>
                <th>아이디</th>
                <th>이름</th>
                <th>성별</th>
                <th>이메일</th>
                <th>연락처</th>
                <th>주소</th>
                <th>생년월일</th>
                <th>가입일</th>
                <th>권한</th>
                <th>학과</th>
                <th>상태</th>
                <th>상태 변경</th>
                <th>수정/삭제</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty memberList}">
                    <tr>
                        <td colspan="13">등록된 회원이 없습니다.</td> <%-- 컬럼 개수에 맞춰 colspan 수정 (12 -> 13) --%>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="dto" items="${memberList}">
                        <tr>
                            <td>${dto.userId}</td>
                            <td>${dto.userName}</td>
                            <td>${dto.userGender}</td>
                            <td>${dto.userEmail}</td>
                            <td>${dto.userPhonenum}</td> 
                            <td>${dto.userAddr}</td>
                            <td>${dto.userBirthdate}</td>
                            <td>${dto.joindate}</td>
                            <td>${dto.authority}</td>
                            <td>${dto.majorId}</td>
                            <td>${dto.enable == 1 ? '활성' : '비활성'}</td>
                            <td>
                                <button type="button"
                                        class="toggle-button ${dto.enable == 1 ? 'active' : 'inactive'}"
                                        onclick="toggleEnable('${dto.userId}', ${dto.enable})">
                                    ${dto.enable == 1 ? '비활성으로 변경' : '활성으로 변경'}
                                </button>
                            </td>
                            <td class="action-links">
                                <a href="/admin/accountedit/edit.do?userId=${dto.userId}">수정</a>
                                <a href="/admin/accountedit/delete.do?userId=${dto.userId}" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a> 
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
    <script>
    function validateSearch() {
        var search_Keyword = document.getElementById("search_Keyword").value;
        if (search_Keyword.trim() === "") {
            alert("검색어를 입력하세요.");
            return false;
        }
        return true;
    }
    function toggleEnable(userId, currentEnable) {
        if (confirm('정말 상태를 변경하시겠습니까?')) {
            var form = document.createElement('form');
            form.setAttribute('method', 'post');
            // ★★★ action 경로 수정: 컨트롤러의 실제 매핑 경로에 맞게 조정해야 합니다.
            // 예시: "/accountedit/toggleEnable.do" 또는 "/admin/toggleEnable.do"
            form.setAttribute('action', '/admin/accountedit/toggleEnable.do'); // 이전 논의를 바탕으로 설정
            var userIdField = document.createElement('input');
            userIdField.setAttribute('type', 'hidden');
            userIdField.setAttribute('name', 'userId');
            userIdField.setAttribute('value', userId);
            form.appendChild(userIdField);

            var newEnableValue = (currentEnable == 1) ? 0 : 1;
            var enableField = document.createElement('input');
            enableField.setAttribute('type', 'hidden');
            enableField.setAttribute('name', 'enable');
            enableField.setAttribute('value', newEnableValue);
            form.appendChild(enableField);

            document.body.appendChild(form);
            form.submit();
        }
    }
</script>
    <script>
        <c:if test="${not empty message}">
            alert('${message}');
        </c:if>
    </script>
</body>
</html>