<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<h2>Send Message</h2>

<sf:form method="post" commandName="message">
	
	<input name="_flowExecutionKey" value="${flowExecutionKey}" type="hidden" />
	<input name="_eventId" value="send" type="hidden" />
	
	<table class="formtable">
		<tr>
			<td class="label">Your Name:</td>
			<td><sf:input class="control" path="name" type="text" value="${fromUserName}"/>
			<div class="error"><sf:errors path="name" /></div></td>
		</tr>
		<tr>
			<td class="label">Your Email:</td>
			<td><sf:input class="control" path="email" type="text" value="${fromUserEmail}" />
			<div class="error"><sf:errors path="email" /></div></td>
		</tr>
		<tr>
			<td class="label">Subject:</td>
			<td><sf:input class="control" path="subject" type="text" />
			<div class="error"><sf:errors path="subject" /></div></td>
		</tr>
		<tr>
			<td class="label">Your Message:</td>
			<td><sf:textarea class="control" path="content" type="text" />
			<div class="error"><sf:errors path="content" /></div></td>
		</tr>
		<tr>
			<td class="label"></td>
			<td><input class="control" value="Send" type="submit"></td>
		</tr>
	</table>
</sf:form>