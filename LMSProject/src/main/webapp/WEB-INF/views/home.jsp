<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메인 화면 - CodeCampus</title>
    <script src="<c:url value='/js/commons.js'/>"></script>
	<link rel="stylesheet" href="/css/style.css">
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 960px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; }
        h1 { text-align: center; color: #333; }
        .welcome-message { text-align: center; margin-top: 30px; font-size: 1.2em; }
        .auth-links { text-align: center; margin-top: 20px; }
        .auth-links a { margin: 0 10px; text-decoration: none; color: #007bff; }
        .auth-links a:hover { text-decoration: underline; }
        .user-info { text-align: center; margin-top: 20px; }
        .user-info span { font-weight: bold; color: #0056b3; }
        .quick-menu { display: flex; justify-content: center; gap: 20px; margin-top: 40px; }
        .quick-menu div { border: 1px solid #ccc; padding: 15px 25px; border-radius: 5px; text-align: center; }
        .quick-menu div a { text-decoration: none; color: #333; font-weight: bold; }
        .quick-menu div a:hover { color: #007bff; }
    </style>
</head>
<body>
   <h2>스프링 부트 프로젝트 - CodeCampus 시작</h2>
		<ul>
			<li><a href="<c:url value='/'/>">루트 (현재 페이지)</a></li>
            <li><a href="<c:url value='/myLogin.do'/>">로그인</a></li>
            <li><a href="<c:url value='/admin'/>">관리자 페이지 (회원 관리)</a></li>
            <li><a href="<c:url value='/admin/users'/>">관리자 페이지 (home.jsp로 이동)</a></li>
            <li><a href="<c:url value='/member/mypage'/>">마이페이지 (로그인 필요)</a></li>
            <li><a href="<c:url value='/denied.do'/>">권한 부족 페이지 테스트</a></li>
		</ul>

        <p style="margin-top: 30px;">
            <a href="<c:url value='/myLogout.do'/>">로그아웃</a> (로그인 상태일 때만 유효)
        </p>
</body>
</html>