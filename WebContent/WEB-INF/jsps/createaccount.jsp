<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/jquery.js"></script>
		
		<script type="text/javascript">
		
			function onLoad() {
				$("#password").keyup(checkMatchPasswords);
				$("#repeat").keyup(checkMatchPasswords);
				
				$("#details").submit(canSubmit);
			}
			
			function canSubmit() {
				var password = $("#password").val();
				var repeat = $("#repeat").val();
				
				if (password != repeat){
					alert("Passwords do not match")
					return false;
				} else {
					return true;
				}
				
			}
			
			function checkMatchPasswords() {
				var password = $("#password").val();
				var repeat = $("#repeat").val();
				
				if(password.length < 3 || repeat.length < 3) {
					return;
				}
				
				if (password == repeat) {
					$("#matchpass").text("<fmt:message key='MatchedPasswords.user.password'/>");
					$("#matchpass").addClass("valid");
					$("#matchpass").removeClass("error");
				} else {
					$("#matchpass").text("<fmt:message key='UnmatchedPasswords.user.password'/>");
					$("#matchpass").addClass("error");
					$("#matchpass").removeClass("valid");
				}
			}
			
			$(document).ready(onLoad);
		
		</script>
		
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Create Account</title>
	</head>
	<body>
		<sf:form id="details" action="${pageContext.request.contextPath}/docreateaccount" method="post" commandName="user">
			<table class="formtable">
				<tr>
					<td class="label">Username:</td>
					<td><sf:input class="control" path="username" name="username" type="text" />
					<div class="error"><sf:errors path="username" /></div></td>
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
	</body>
</html>