<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<<<<<<< HEAD
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>OO대학교 eCampus</title>
	</head>
	<body>
	<%@ include file = "sidebars.jsp" %>
	 <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 ">
                <div class="pt-3">
                    <h2 class="text-primary">
                    TypeScript
                    </h2>
                    <p class="text-success">
                    Responsive sidebar menu to 
                    top navbar in Bootstrap 5
                    </p>
                    <p class="text-success">
                        TypeScript is a strict superset
                        of JavaScript, which means anything
                        that is implemented in JavaScript 
                        can be implemented using TypeScript
                        along with the choice of adding 
                        enhanced features. It is an Open Source
                        Object Oriented programming language and
                        strongly typed language. As TS code is 
                        converted to JS code it makes it easier 
                        to integrate into JavaScript projects. 
                        The TypeScript Tutorial provides the 
                        complete guide from beginner to 
                        advanced level.
                    </p>
                </div>
            </main>
    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>
=======
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>OO대학교 eCampus</title>
    </head>
    <body class="sb-nav-fixed">
    <%@ include file = "sidebars.jsp" %>
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Dashboard</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item active">Dashboard</li>
                    </ol>
                    <div class="row">
                        <div class="col-xl-3 col-md-6">
                            <div class="card bg-primary text-white mb-4">
                                <div class="card-body">Primary Card</div>
                                <div class="card-footer d-flex align-items-center justify-content-between">
                                    <a class="small text-white stretched-link" href="#">View Details</a>
                                    <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-3 col-md-6">
                            <div class="card bg-warning text-white mb-4">
                                <div class="card-body">Warning Card</div>
                                <div class="card-footer d-flex align-items-center justify-content-between">
                                    <a class="small text-white stretched-link" href="#">View Details</a>
                                    <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-3 col-md-6">
                            <div class="card bg-success text-white mb-4">
                                <div class="card-body">Success Card</div>
                                <div class="card-footer d-flex align-items-center justify-content-between">
                                    <a class="small text-white stretched-link" href="#">View Details</a>
                                    <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-3 col-md-6">
                            <div class="card bg-danger text-white mb-4">
                                <div class="card-body">Danger Card</div>
                                <div class="card-footer d-flex align-items-center justify-content-between">
                                    <a class="small text-white stretched-link" href="#">View Details</a>
                                    <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xl-6">
                            <div class="card mb-4">
                                <div class="card-header">
                                    <i class="fas fa-chart-area me-1"></i>
                                    커뮤니티
                                </div>
                                <div class="card-body">
                                <c:choose>
						    <c:when test="${ empty lists_c }"> 
						        <tr>
						            <td colspan="5" align="center">
						                등록된 커뮤니티가 없습니다.
						            </td>
						        </tr>
						    </c:when> 
						    <c:otherwise> 
						        <c:forEach items="${ lists_c }" var="row" varStatus="loop">    
						        <tr align="center">
						             <td><a href="#">${ row.board_title }</a><br/></td> 
						        </tr>
						        </c:forEach>        
						    </c:otherwise>    
						</c:choose>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-6">
                            <div class="card mb-4">
                                <div class="card-header">
                                    <i class="fas fa-chart-bar me-1"></i>
                                    공지사항
                                </div>
                                <div class="card-body">
                                <c:choose>
						    <c:when test="${ empty lists }"> 
						        <tr>
						            <td colspan="5" align="center">
						                등록된 게시물이 없습니다^^*
						            </td>
						        </tr>
						    </c:when> 
						    <c:otherwise> 
						        <c:forEach items="${ lists }" var="row" varStatus="loop">    
						        <tr align="center">
						            <td><a href="#"> ${ row.board_title }</a><br/></td> 
						        </tr>
						        </c:forEach>        
						    </c:otherwise>    
						</c:choose>
					</div>
                            </div>
                        </div>
                    </div>
                    <div class="card mb-4">
                        <div class="card-header">
                            <i class="fas fa-table me-1"></i>
                            DataTable Example
                        </div>
                        <div class="card-body">
                            <table id="datatablesSimple">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Position</th>
                                        <th>Office</th>
                                        <th>Age</th>
                                        <th>Start date</th>
                                        <th>Salary</th>
                                    </tr>
                                </thead>
                                
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </main>
            <footer class="py-4 bg-light mt-auto">
                <div class="container-fluid px-4">
                    <div class="d-flex align-items-center justify-content-between small">
                        <div class="text-muted">Copyright &copy; Your Website 2023</div>
                        <div>
                            <a href="#">Privacy Policy</a>
                            &middot;
                            <a href="#">Terms &amp; Conditions</a>
                        </div>
                    </div>
                </div>
            </footer>
        </div>
    </body>
</html>
>>>>>>> origin/master
