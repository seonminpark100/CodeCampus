<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>파일 업로드</title> <%-- 제목을 더 범용적으로 변경 --%>
		<script>
			function validateForm(form)
			{
				if(form.title.value == "")
				{
					alert("제목을 입력하세요");
					form.title.focus();
					return false;
				}
				if(form.file.value == "") /* ★ ofile -> file로 변경, 또는 ofile 유지 */
				{
					alert("첨부파일은 필수 입력입니다.");
					return false;
				}
                // 파일 크기 제한 (예시: 5MB)
                const MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
                if (form.file.files.length > 0) {
                    if (form.file.files[0].size > MAX_FILE_SIZE) {
                        alert("파일 크기가 너무 큽니다. 5MB 이하의 파일을 선택해주세요.");
                        return false;
                    }
                }
                // 파일 확장자 제한 (예시: 이미지 파일만)
                const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
                if (form.file.files.length > 0 && !allowedExtensions.exec(form.file.value)) {
                    alert('이미지 파일 (jpg, jpeg, png, gif)만 업로드 가능합니다.');
                    form.file.value = ''; // 필드 초기화
                    return false;
                }
			}
            return true; // 모든 검증 통과 시
		</script>
	</head>
	<body>
		<h2>파일 업로드</h2>
		<form name="fileForm" method="post" enctype="multipart/form-data" 
			action="/YOUR_NEW_FILE_UPLOAD_CONTROLLER_PATH/uploadProcess.do" onsubmit="return validateForm(this)">
			제목 <input type="text" name="title" /><br/>
			카테고리(선택사항) :
			<input type="checkbox" name="cate" value="사진" checked />사진
			첨부파일 : <input type="file" name="file" /><br/> <input type="submit" value="전송하기">
		</form>
	</body>
</html>