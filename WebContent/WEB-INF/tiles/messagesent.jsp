<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<h4>Sent with the following details:</h4>
<table class="formtable">
	<tr>
		<td>From: </td><td>${message.email}</td>
	</tr> 
	<tr>
		<td>To: </td><td>${message.username}</td>
	</tr> 
	<tr>
		<td>Subject: </td><td>${message.subject}</td>
	</tr>
	<tr>
		<td>Message: </td><td>${message.content}</td>
	</tr> 
</table>
<p><a href="${pageContext.request.contextPath}">Go back home</a></p>