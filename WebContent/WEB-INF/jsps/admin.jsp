<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="static/css/main.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Administration</title>
	</head>
	<body>
		<h3>Administration</h3>
		
		<h4>User List</h4>
		
		<table class="formtable">
			<tr>
				<td>Username</td>
				<td>Email</td>
				<td>Role</td>
				<td>Enabled</td>
			</tr>
			<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.username}</td>
					<td>${user.email}</td>
					<td>${user.authority}</td>
					<td>${user.enabled}</td>
				</tr>
			</c:forEach>
		</table>
		
	</body>
</html>