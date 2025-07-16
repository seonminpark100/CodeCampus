<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<s:authorize access="isAuthenticated()">
	<s:authentication property="principal" var="principal"/>
</s:authorize>

<link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>

<style>
	.login {float: right;}
	html, body {
    height: 100%;
	}
	
	#layoutSidenav {
	    display: flex;
	    min-height: 100vh;
	}
	
	#layoutSidenav_nav {
	    flex-shrink: 0;
	}
	
	.sb-sidenav {
	    height: 100%;
	    min-height: 100vh;
	}
	
</style>
    <link href="../css/styles.css" rel="stylesheet" />
    
 	<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
        <!-- Navbar Brand-->
        <a class="navbar-brand ps-3" href="/prof/submain.do?lectureCode=${lectureCode}">OO대학교 eCampus</a>

	<div class="login">
		<c:choose>
		    <c:when test="${empty principal}">
		        <ul class="navbar-nav">
		            <li class="nav-item"><a class="nav-link" href="/myLogin.do">로그인</a></li>
		        </ul>
		    </c:when>
		    <c:otherwise>
		    <s:authorize access="isAuthenticated()"><s:authentication property="name"/>님 반갑습니다.</s:authorize>
		    	<button class="btn btn-dark" type="button" onclick="location.href='/prof/mypage.do' ">마이페이지</button>
		    	<button class="btn btn-dark" type="button" onclick="location.href='/myLogout.do' ">로그아웃</button>
		    	<button class="btn btn-dark" type="button" onclick="location.href='/prof/index.do' ">내 강의실</button>
		    </c:otherwise>
	    </c:choose>
	</div>
    </nav>
    <div id="layoutSidenav">
        <div id="layoutSidenav_nav">
            <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                <div class="sb-sidenav-menu">
                    <div class="nav">
                        <div class="sb-sidenav-menu-heading">Core</div>
                        <a class="nav-link" href="/prof/studentList.do?lectureCode=${lectureCode}">
                            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            수강생 목록
                        </a>
                        <a class="nav-link" href="/prof/lectureList.do?lectureCode=${lectureCode}">
                            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            강의
                        </a>
                        <div class="sb-sidenav-menu-heading">Interface</div>
                        <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                            <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                            과제
                            <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                        </a>
                        <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                            <nav class="sb-sidenav-menu-nested nav">
                                <a class="nav-link" href="/prof/assignmentList.do?lectureCode=${lectureCode}">과제 등록</a>
                                <a class="nav-link" href="/prof/submittedAssignmentList.do?lectureCode=${lectureCode}">과제 채점</a>
                            </nav>
                        </div>
                        
                        <div class="sb-sidenav-menu-heading">Addons</div>
                        <a class="nav-link" href="/prof/absentboard.do?lectureCode=${lectureCode}">
                            <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                            출석
                        </a>
                    </div>
                </div>
                <div class="sb-sidenav-footer">
                    <div class="small">Code Campus</div>
                    LMS
                </div>
            </nav>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
