<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/scripts/jquery.js"></script>
		
		<tiles:insertAttribute name="includes" />
		
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><tiles:insertAttribute name="title" /></title>
	</head>
	<body>
		<div class="header">
			<tiles:insertAttribute name="header" />
		</div>
		
		<div class="body">
			<tiles:insertAttribute name="body" />
		</div>
		
		<hr /><div class="footer">
			<tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>