<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<sf:form id="details" action="${pageContext.request.contextPath}/docreateaccount" method="post" commandName="user">
	<table class="formtable">
		<tr>
			<td class="label">Username:</td>
			<td><sf:input class="control" path="username" name="username" type="text" />
			<div class="error"><sf:errors path="username" /></div></td>
		</tr>
		<tr>
			<td class="label">Name:</td>
			<td><sf:input class="control" path="name" name="name" type="text" />
			<div class="error"><sf:errors path="name" /></div></td>
		</tr>
		<tr>
			<td class="label">Email:</td>
			<td><sf:input class="control" path="email" name="email" type="text" />
			<div class="error"><sf:errors path="email" /></div></td>
		</tr>
		<tr>
			<td class="label">Password:</td>
			<td><sf:input class="control" id="password" path="password" name="password" type="password" />
			<div class="error"><sf:errors path="password" /></div></td>
		</tr>
		<tr>
			<td class="label">Repeat Password:</td>
			<td><input class="control" id="repeat" name="repeat" type="password"></input>
			<div id="matchpass"></div></td>
		</tr>
		<tr>
			<td class="label"></td>
			<td><input class="control" value="Create Account" type="submit"></td>
		</tr>
	</table>
</sf:form>