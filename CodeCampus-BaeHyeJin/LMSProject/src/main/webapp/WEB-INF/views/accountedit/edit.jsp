<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>회원수정</title>
        <style>
            /* 기존 스타일 유지 (위에서 제안된 스타일 적용) */
            body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
            h2 { color: #333; text-align: center; margin-bottom: 30px; }
            form { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); max-width: 600px; margin: 20px auto; box-sizing: border-box; }
            table { width: 100%; border-collapse: collapse; margin-top: 20px; }
            table, th, td { border: 1px solid #ddd; }
            th, td { padding: 12px 15px; text-align: left; }
            th { background-color: #f2f2f2; color: #555; font-weight: bold; width: 30%; }
            input[type="text"], input[type="date"], input[type="password"], select {
                width: calc(100% - 22px);
                padding: 10px;
                margin-top: 5px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }
            input[type="radio"] { margin-right: 5px; }
            label { margin-right: 15px; }
            .submit-btn {
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
            .submit-btn:hover { background-color: #0056b3; }
            small { color: #777; font-size: 0.85em; }

            /* 추가된 스타일 */
            .profile-img-container {
                display: flex;
                flex-direction: column;
                align-items: center;
                gap: 10px;
                margin-bottom: 15px;
            }
            .profile-img {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                object-fit: cover;
                border: 1px solid #ddd;
            }
            .file-input-wrapper {
                 width: 100%;
                 box-sizing: border-box;
                 display: flex;
                 align-items: center;
                 gap: 10px;
            }
            #ofile {
                flex-grow: 1;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                background-color: #f8f8f8;
                cursor: pointer;
            }
            #uploadProfileBtn {
                background-color: #28a745;
                color: white;
                padding: 8px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            #uploadProfileBtn:hover {
                background-color: #218838;
            }
            .alert-message { /* 메시지 표시를 위한 스타일 */
                color: green;
                font-weight: bold;
                margin-top: 10px;
                text-align: center;
            }
            .error-message {
                color: red;
                font-weight: bold;
                margin-top: 10px;
                text-align: center;
            }
        </style>
        <script>
            function validateEditForm(form) {
                // 회원 정보 수정 폼에 대한 유효성 검사
                if (form.userName.value.trim() === "") {
                    alert("이름을 입력하세요.");
                    form.userName.focus();
                    return false;
                }
                return true;
            }

            // 프로필 파일 업로드 처리 함수 (비동기)
            function uploadProfileImage() {
                const fileInput = document.getElementById('ofile');
                if (fileInput.files.length === 0) {
                    alert("업로드할 프로필 이미지를 선택해주세요.");
                    return;
                }

                const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
                const file = fileInput.files[0];

                if (file.size > MAX_FILE_SIZE) {
                    alert("파일 크기가 너무 큽니다. 5MB 이하의 파일을 선택해주세요.");
                    return;
                }
                const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
                if (!allowedExtensions.exec(file.name)) {
                    alert('허용되지 않는 이미지 확장자입니다. (허용: jpg, jpeg, png, gif)');
                    fileInput.value = ''; // 선택된 파일 초기화
                    return;
                }

                const userId = document.getElementById('userIdHidden').value; 
                const formData = new FormData();
                formData.append('userId', userId);
                formData.append('ofile', file);

                fetch('/file/uploadProfileProcess.do', {
                    method: 'POST',
                    body: formData
                })
                .then(response => {

                    if (response.redirected) {
                        window.location.href = response.url; 
                    } else {
                 
                        alert('프로필 이미지 업로드 처리 중 예상치 못한 오류가 발생했습니다.');
                        console.error('Unexpected response:', response);
                    }
                })
                .catch(error => {
                    console.error('Fetch Error:', error);
                    alert('프로필 이미지 업로드 중 네트워크 오류가 발생했습니다.');
                });
            }

            // 페이지 로드 시 URL 파라미터에서 메시지 가져와 표시
            window.onload = function() {
                const urlParams = new URLSearchParams(window.location.search);
                const message = urlParams.get('message');
                if (message) {
                    const messageDiv = document.createElement('div');
                    messageDiv.textContent = decodeURIComponent(message);
                    messageDiv.classList.add('alert-message'); // 성공 메시지 스타일
                    if (message.includes("실패")) { // 실패 메시지라면
                         messageDiv.classList.remove('alert-message');
                         messageDiv.classList.add('error-message'); // 에러 메시지 스타일
                    }
                    document.body.prepend(messageDiv); // body의 맨 앞에 추가
                    // 메시지 표시 후 URL에서 메시지 파라미터 제거 (선택 사항)
                    setTimeout(() => {
                        const newUrl = window.location.href.split('?')[0];
                        window.history.replaceState({}, document.title, newUrl);
                        messageDiv.remove(); // 메시지 사라지게 함
                    }, 5000); // 5초 후 메시지 사라짐
                }
            };
        </script>
    </head>
    <body>
        <h2>회원수정</h2>
        <%-- 회원 정보 수정 폼 --%>
        <form action="/admin/accountedit/edit.do" method="post" onsubmit="return validateEditForm(this);">
            <input type="hidden" name="userId" value="${dto.userId }" /> <%-- userId를 숨겨진 필드로 전달 --%>
            <input type="hidden" id="userIdHidden" value="${dto.userId }" /> <%-- JS에서 사용하기 위한 별도의 hidden 필드 --%>
            <table border="1">
                <tr>
                    <th>아이디 (수정불가)</th>
                    <td><input type="text" value="${dto.userId }" readonly /></td>
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
            <input type="submit" value="회원 정보 수정 완료" class="submit-btn" />
        </form>

        <hr style="margin: 40px auto; max-width: 600px; border-color: #eee;">

        <%-- 프로필 이미지 업로드/변경 섹션 --%>
        <div style="max-width: 600px; margin: 20px auto; padding: 30px; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
            <h3>프로필 이미지 관리</h3>
            <div class="profile-img-container">
                <c:choose>
                    <c:when test="${not empty dto.savefile}">
                        <%-- WebConfig에서 매핑한 /upload/ 경로 사용 --%>
                        <img id="currentProfileImage" src="/upload/user_profiles/${dto.savefile}" alt="현재 프로필 이미지" class="profile-img">
                        <small>현재 프로필 이미지 (${dto.originalfile})</small>
                    </c:when>
                    <c:otherwise>
                        <img id="currentProfileImage" src="/images/default_profile.png" alt="기본 프로필 이미지" class="profile-img">
                        <small>등록된 프로필 이미지가 없습니다.</small>
                    </c:otherwise>
                </c:choose>
                <div class="file-input-wrapper">
                    <input type="file" id="ofile" name="ofile" accept="image/jpeg, image/png, image/gif" />
                    <button type="button" id="uploadProfileBtn" onclick="uploadProfileImage()">프로필 이미지 업로드</button>
                </div>
                <small>새 프로필 이미지를 선택하세요. (5MB 이하, JPG, PNG, GIF)</small>
            </div>
        </div>
    </body>
</html>