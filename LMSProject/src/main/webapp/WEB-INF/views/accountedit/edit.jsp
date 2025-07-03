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
                    <td><input type="text" name="userId" value="${dto.userId }" readonly /></td>
                </tr>
                <tr>
                    <th>패스워드</th>
                    <td><input type="text" name="userPw" value="${dto.userPw }" /></td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td><input type="text" name="userName" value="${dto.userName }" /></td>
                </tr>
                <tr>
                    <th>생일</th>
                    <td><input type="date" name="userBirthdate" value="${dto.userBirthdate }" /></td>
                </tr>
                <%-- 추가: 성별, 이메일, 연락처, 주소, 권한, 학과, 상태 등 모든 필드 추가 --%>
                <tr>  <th>성별</th>
                    <td>
                        <input type="radio" id="genderM" name="userGender" value="M"
                               <c:if test="${dto.userGender eq 'M'}">checked</c:if>>
                        <label for="genderM">남</label>
                        
                        <input type="radio" id="genderF" name="userGender" value="F"
                               <c:if test="${dto.userGender eq 'F'}">checked</c:if>>
                        <label for="genderF">여</label>
                        
                        <%-- '선택 안 함' 또는 '빈 값'을 위한 숨겨진 라디오 버튼. 필요에 따라 유지하거나 제거할 수 있습니다. --%>
                        <input type="radio" id="genderNone" name="userGender" value="" style="display:none;"
                               <c:if test="${dto.userGender eq null || dto.userGender eq ''}">checked</c:if>>
                    </td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td><input type="text" name="userEmail" value="${dto.userEmail}" /></td>
                </tr>
                <tr>
                    <th>연락처</th>
                    <td><input type="text" name="userPhoneNum" value="${dto.userPhoneNum}" /></td>
                </tr>
                <tr>
                    <th>주소</th>
                    <td><input type="text" name="userAddr" value="${dto.userAddr}" /></td>
                </tr>
                <tr>
                    <th>권한</th>
                    <td>
                        <select name="authority">
                            <option value="ROLE_USER" ${dto.authority == 'ROLE_USER' ? 'selected' : ''}>사용자</option>
                            <option value="ROLE_PROF" ${dto.authority == 'ROLE_PROF' ? 'selected' : ''}>교수</option>
                            <option value="ROLE_ADMIN" ${dto.authority == 'ROLE_ADMIN' ? 'selected' : ''}>관리자</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>학과</th>
                    <td><input type="text" name="majorId" value="${dto.majorId}" /></td>
                </tr>
                <tr>
                    <th>상태 (활성/비활성)</th>
                    <td>
                        <select name="enable">
                            <option value="1" ${dto.enable == 1 ? 'selected' : ''}>활성</option>
                            <option value="0" ${dto.enable == 0 ? 'selected' : ''}>비활성</option>
                        </select>
                    </td>
                </tr>
            </table>
            <input type="submit" value="전송하기" />
        </form>
    </body>
</html>