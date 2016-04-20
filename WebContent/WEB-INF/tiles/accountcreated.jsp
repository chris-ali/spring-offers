<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<h4>Account successfully created with the following details:</h4>
<table class="formtable">
	<tr>
		<td>User Name: </td><td>${user.username}</td>
	</tr> 
	<tr>
		<td>Name: </td><td>${user.name}</td>
	</tr>
	<tr>
		<td>Email: </td><td>${user.email}</td>
	</tr> 
</table>
<p><a href="${pageContext.request.contextPath}/login">Click here to login</a></p>