<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<script type="text/javascript">

	function onDeleteClick(event) {
		var doDelete = confirm("<fmt:message key='Confirm.offer.delete'/>")
		
		if (!doDelete) {event.preventDefault();}
	}
	
	function onReady() {$("#delete").click(onDeleteClick);}
	
	$(document).ready(onReady);

</script>