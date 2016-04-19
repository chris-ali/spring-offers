<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3>Administration</h3>

<h4>User List</h4>

<table class="formtable">
	<tr>
		<td>Username</td>
		<td>Name</td>
		<td>Email</td>
		<td>Role</td>
		<td>Enabled</td>
	</tr>
	<c:forEach var="user" items="${users}">
		<tr>
			<td>${user.username}</td>
			<td>${user.name}</td>
			<td>${user.email}</td>
			<td>${user.authority}</td>
			<td>${user.enabled}</td>
		</tr>
	</c:forEach>
</table>
