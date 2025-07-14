<%@page import="utils.JSFunction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
/* UserId이 없으면 로그인할 수 있도록 로그인 페이지로 이동시켜주기 */
if(session.getAttribute("id") == null){
	JSFunction.alertLocation("로그인 후 이용 가능합니다.", "../mvcboard/login.do", out);
	return; // method의 종료를 의미
}
%>
