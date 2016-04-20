<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<sf:form action="${pageContext.request.contextPath}/docreateoffer" method="post" commandName="offer">
	<sf:input type="hidden" name="id" path="id"/>
	<table class="formtable">
		<tr>
			<c:choose>
				<c:when test="${edit}"><td class="label">Edit Offer:</td></c:when>
				<c:otherwise><td class="label">Enter Offer:</td></c:otherwise>
			</c:choose>
			<td><sf:textarea class="control" path="text" name="text" rows="10" cols="10"></sf:textarea>
			<sf:errors path="text" cssClass="error"></sf:errors></td>
		</tr>
		<tr>
			<td class="label"></td>
			<td><input class="control" name="submit" value="Submit" type="submit"></td>
			<c:if test="${offer.id != 0}">
				<td><input class="delete control" name="delete" id="delete" value="Delete this Offer" type="submit"></td>
			</c:if>	
		</tr>
	</table>
</sf:form>