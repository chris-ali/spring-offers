<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<sf:form action="${pageContext.request.contextPath}/docreateoffer"
	method="post" commandName="offer">
	<table class="formtable">
		<tr>
			<td class="label">Offer:</td>
			<td><sf:textarea class="control" path="text" name="text" rows="20" cols="15"></sf:textarea><br>
			<sf:errors path="text" cssClass="error"></sf:errors></td>
		</tr>
		<tr>
			<td class="label"></td>
			<td><input class="control" value="Submit" type="submit"></td>
		</tr>
	</table>
</sf:form>