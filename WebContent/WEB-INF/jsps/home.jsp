<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Home</title>
	</head>
	<body>
		<sec:authorize access="!isAuthenticated()">
			<p><a href="${pageContext.request.contextPath}/login">Login</a></p>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<p><a href="<c:url value='/j_spring_security_logout' />">Logout</a></p>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<p><a href="<c:url value='/admin' />">Admin Page</a></p>
		</sec:authorize>
		
		
		<p><a href="${pageContext.request.contextPath}/createaccount">Create an account</a></p>
		
		<p><a href="${pageContext.request.contextPath}/offers">View current offers</a></p>
		<p><a href="${pageContext.request.contextPath}/createoffer">Create an offer</a></p>
	</body>
</html>