<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Create Offer</title>
	</head>
	<body>
		<sf:form action="${pageContext.request.contextPath}/docreateoffer"
			method="post" commandName="offer">
			<table class="formtable">
				<tr>
					<td class="label">Name:</td>
					<td><sf:input class="control" path="name" name="name" type="text" /><br>
					<sf:errors path="name" cssClass="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">Email:</td>
					<td><sf:input class="control" path="email" name="email" type="text" /><br>
					<sf:errors path="email" cssClass="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label">Offer:</td>
					<td><sf:textarea class="control" path="text" name="text" rows="10" cols="15"></sf:textarea><br>
					<sf:errors path="text" cssClass="error"></sf:errors></td>
				</tr>
				<tr>
					<td class="label"></td>
					<td><input class="control" value="Submit" type="submit"></td>
				</tr>
			</table>
		</sf:form>
	</body>
</html>