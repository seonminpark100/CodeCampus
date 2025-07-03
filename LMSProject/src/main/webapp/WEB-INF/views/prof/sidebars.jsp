<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<s:authorize access="isAuthenticated()">
	<s:authentication property="principal" var="principal"/>
</s:authorize>

	<!-- CDN 방식: Bootstrap, jQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>
	

	<div class="login">
		<c:choose>
		    <c:when test="${empty principal}">
		        <ul class="navbar-nav">
		            <li class="nav-item"><a class="nav-link" href="/myLogin.do">로그인</a></li>
		        </ul>
		    </c:when>
		    <c:otherwise>
			        <ul class="navbar-nav">
		        	<s:authorize access="isAuthenticated()"><s:authentication property="name"/>님 반갑습니다.</s:authorize>
		            <li class="nav-item"><a class="nav-link" href="#">마이페이지</a></li>
		            <li class="nav-item"><a class="nav-link" href="/myLogout.do">로그아웃</a></li>
		        </ul>
		    </c:otherwise>
	    </c:choose>
	</div>
	<nav class="navbar navbar-light bg-success border-bottom-0">
        <!-- Navbar toggle button (hamburger icon) -->
        <button class="navbar-toggler d-block d-md-none"
                type="button" data-bs-toggle="offcanvas"
                data-bs-target="#sidebar"
                aria-controls="sidebar">
            <span class="navbar-toggler-icon"></span>
        </button>
    </nav>

    <div class="offcanvas offcanvas-start 
                bg-success d-md-block" 
        tabindex="-1" id="sidebar"
        aria-labelledby="sidebarLabel">
        <div class="offcanvas-header">
            <h5 class="offcanvas-title text-light"
                id="sidebarLabel">GFG Sidebar</h5>
            <button type="button"
                    class="btn-close text-reset"
                    data-bs-dismiss="offcanvas"
                    aria-label="Close">
            </button>
        </div>
        <!-- <div class="offcanvas-body">
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link active text-light"
                    href="#">
                    Home
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-light"
                    href="#">
                    About Us
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-light"
                    href="#">
                    Contact Us
                    </a>
                </li>
            </ul>
        </div> -->
    </div>

    <div class="container-fluid">
        <div class="row">
            <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-none d-md-block bg-light sidebar">
                <div class="position-sticky bg-success vh-100">
                    <div class="pt-3">
                        <h6 class="sidebar-heading d-flex 
                                justify-content-between 
                                align-items-center px-3 
                                mt-4 mb-1 text-muted">
                            <span class="text-light">
                                <a class="nav-link active 
                                        text-light" 
                                href="#">
                                    강의실
                                </a>
                            </span>
                        </h6>
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link active 
                                        text-light" 
                                href="/prof/studentList.do?lectureId=${lectureId}">
                                    수강생 목록
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link text-light"
                                href="#">
                                    과제
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link text-light"
                                href="#">
                                    강의
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link text-light"
                                href="#">
                                    출석
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link text-light"
                                href="#">
                                    공지사항
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
           