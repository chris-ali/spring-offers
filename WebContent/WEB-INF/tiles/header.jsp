<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<a class="title" href="<c:url value='/' />">Offers</a>

<sec:authorize access="!isAuthenticated()">
	<a class="createaccount" href="${pageContext.request.contextPath}/createaccount">Create an account</a>
	<a class="login" href="${pageContext.request.contextPath}/login">Login</a>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
	<a class="login" href="<c:url value='/j_spring_security_logout' />">Logout</a>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<a class="admin" href="<c:url value='/admin' />">Admin Page</a>
</sec:authorize>