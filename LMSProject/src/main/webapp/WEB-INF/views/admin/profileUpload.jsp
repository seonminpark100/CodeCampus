<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%-- c:if, c:out 등을 위해 추가 --%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>프로필 이미지 업로드</title> <%-- 제목을 프로필 업로드에 맞게 변경 --%>
        <style>

            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f4f4f4;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 80vh;
            }
            .upload-container {
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0 0 0 / 10%);
                max-width: 500px;
                width: 100%;
                text-align: center;
            }
            h2 {
                color: #333;
                margin-bottom: 25px;
            }
            .form-group {
                margin-bottom: 20px;
            }
            .form-group label {
                display: block;
                margin-bottom: 10px;
                font-weight: bold;
                color: #555;
            }
            .form-group input[type="file"] {
                width: calc(100% - 22px);
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
                background-color: #f8f8f8;
                cursor: pointer;
            }
            .form-group input[type="file"]::-webkit-file-upload-button {
                background-color: #007bff;
                color: white;
                padding: 8px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                margin-right: 10px;
                transition: background-color 0.3s ease;
            }
            .form-group input[type="file"]::-webkit-file-upload-button:hover {
                background-color: #0056b3;
            }
            button[type="submit"] {
                background-color: #28a745; /* Success green */
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
            button[type="submit"]:hover {
                background-color: #218838;
            }
            .message {
                background-color: #d4edda;
                color: #155724;
                border: 1px solid #c3e6cb;
                padding: 10px;
                margin-top: 20px;
                border-radius: 4px;
                text-align: center;
            }
            .error-message {
                background-color: #f8d7da;
                color: #721c24;
                border: 1px solid #f5c6cb;
                padding: 10px;
                margin-top: 20px;
                border-radius: 4px;
                text-align: center;
            }
        </style>
        <script>
            function validateProfileForm(form) { // 함수명 변경 (프로필 업로드 전용임을 명확히)
                const fileInput = form.ofile; // name="ofile"로 변경되므로 여기도 ofile로 수정
                if (fileInput.value === "") {
                    alert("업로드할 파일을 선택해주세요.");
                    return false;
                }

                const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
                if (fileInput.files.length > 0) {
                    const file = fileInput.files[0];
                    if (file.size > MAX_FILE_SIZE) {
                        alert("파일 크기가 너무 큽니다. 5MB 이하의 파일을 선택해주세요.");
                        return false;
                    }
                    const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i; // 프로필이므로 이미지에 한정
                    if (!allowedExtensions.exec(file.name)) {
                        alert('허용되지 않는 이미지 확장자입니다. (허용: jpg, jpeg, png, gif)');
                        fileInput.value = ''; // 선택된 파일 초기화
                        return false;
                    }
                }
                return true; // 모든 검증 통과 시
            }
        </script>
    </head>
    <body>
        <div class="upload-container">
            <h2>프로필 이미지 업로드</h2>

        
            <c:if test="${not empty message}">
                <p class="message">${message}</p>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <p class="error-message">${errorMessage}</p>
            </c:if>

            <form name="profileForm" method="post" enctype="multipart/form-data" 
                action="<c:url value='/file/uploadProfileProcess.do'/>" onsubmit="return validateProfileForm(this)">
                
    
                <input type="hidden" name="userId" value="${userId}" /> 
                
                <div class="form-group">
                    <label for="ofile">프로필 이미지 선택:</label>
                    <input type="file" id="ofile" name="ofile" accept="image/jpg, image/png, image/gif" /><br/>
                </div>
                
                <button type="submit">프로필 이미지 변경</button>
            </form>
        </div>
    </body>
</html>