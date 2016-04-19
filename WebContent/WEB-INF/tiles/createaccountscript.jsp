<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
			alert("<fmt:message key='UnmatchedPasswords.user.password'/>")
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
