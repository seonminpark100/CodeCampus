<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>회원수정</title>
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
            h2 { color: #333; text-align: center; margin-bottom: 30px; }
            form { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); max-width: 600px; margin: 20px auto; box-sizing: border-box; }
            table { width: 100%; border-collapse: collapse; margin-top: 20px; }
            table, th, td { border: 1px solid #ddd; }
            th, td { padding: 12px 15px; text-align: left; }
            th { background-color: #f2f2f2; color: #555; font-weight: bold; width: 30%; }
            input[type="text"], input[type="date"], input[type="password"], select {
                width: calc(100% - 22px); /* 패딩 고려 */
                padding: 10px;
                margin-top: 5px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }
            input[type="radio"] { margin-right: 5px; }
            label { margin-right: 15px; }
            input[type="submit"] {
                background-color: #007bff;
                color: white;
                padding: 12px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 1em;
                margin-top: 20px;
                width: 100%;
                transition: background-color 0.3s ease;
            }
            input[type="submit"]:hover { background-color: #0056b3; }
            small { color: #777; font-size: 0.85em; }
        </style>
    </head>
    <body>
        <h2>회원수정</h2>
        <form action="/admin/accountedit/edit.do" method="post">
            <table border="1">
                <tr>
                    <th>아이디 (수정불가)</th>
                    <td><input type="text" name="userId" value="${dto.userId }" readonly /></td>
                </tr>
                <tr>
                    <th>패스워드</th>
                    <td>
                        <input type="password" name="userPw" placeholder="새 비밀번호를 입력하세요 (비워두면 변경 안 됨)" />
                        <br><small>비밀번호를 변경하려면 여기에 새 비밀번호를 입력하세요.</small>
                    </td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td><input type="text" name="userName" value="${dto.userName }" required /></td>
                </tr>
                <tr>
                    <th>생년월일</th>
                    <td><input type="date" name="userBirthdate" value="${dto.userBirthdate }" required /></td>
                </tr>
                <tr>
                    <th>성별</th>
                    <td>
                        <input type="radio" id="genderM" name="userGender" value="남성"
                               <c:if test="${dto.userGender eq '남성'}">checked</c:if>>
                        <label for="genderM">남성</label>
                        
                        <input type="radio" id="genderF" name="userGender" value="여성"
                               <c:if test="${dto.userGender eq '여성'}">checked</c:if>>
                        <label for="genderF">여성</label>
                       
                    </td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td><input type="text" name="userEmail" value="${dto.userEmail}" required /></td>
                </tr>
                <tr>
                    <th>연락처</th>
                    <td><input type="text" name="userPhonenum" value="${dto.userPhonenum}" required /></td> 
                </tr>
                <tr>
                    <th>주소</th>
                    <td><input type="text" name="userAddr" value="${dto.userAddr}" required /></td>
                </tr>
                <tr>
                    <th>권한</th>
                    <td>
                        <select name="authority" required>
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
                        <select name="enable" required>
                            <option value="1" ${dto.enable == 1 ? 'selected' : ''}>활성</option>
                            <option value="0" ${dto.enable == 0 ? 'selected' : ''}>비활성</option>
                        </select>
                    </td>
                </tr>
            </table>
            <input type="submit" value="수정 완료" />
        </form>
    </body>
</html>