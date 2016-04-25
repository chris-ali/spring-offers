<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="messages">
	
</div>

<script type="text/javascript">

	var timer;
	
	function showReply(i) {
		stopTimer();
		$("#replyform" + i).toggle();
	}
	
	function sendMessage(i, name, email) {
		var text = $("#replyarea" + i).val();
		
		$.ajax({
			"type": 'POST',
			"url": "<c:url value='/sendmessage' />",
			"data": JSON.stringify({"text": text, "name": name, "email": email, "target": i}),
			"success": success,
			"error": error,
			contentType: "application/json",
			dataType: "json"
		});
	}
	
	function success(data) {
		startTimer();
		$("#replyform" + data.target).toggle();
		$("#alertspan" + data.target).text("Message Sent");
	}
	
	function error(data) {
		startTimer();
		$("#replyform" + data.target).toggle();
	}

	function showMessages(data) {
		
		$("#messages").html("");
		
		for (var i=0; i<data.messages.length; i++) {
			var message = data.messages[i];
			
			var messageDiv = document.createElement("div");
			messageDiv.setAttribute("class","message");
			
			var subjectSpan = document.createElement("span");
			subjectSpan.setAttribute("class","subject");
			subjectSpan.appendChild(document.createTextNode(message.subject));
			
			var bodySpan = document.createElement("span");
			bodySpan.setAttribute("class","messageBody");
			bodySpan.appendChild(document.createTextNode(message.content));
			
			var nameSpan = document.createElement("span");
			nameSpan.setAttribute("class","name");
			nameSpan.appendChild(document.createTextNode(message.name + " (" ));
			
				var link = document.createElement("a");
				link.setAttribute("class", "replylink");
				link.setAttribute("href", "#");
				link.setAttribute("onclick", "showReply(" + i + ")");
				link.appendChild(document.createTextNode(message.email));
			
			nameSpan.appendChild(link);	
			nameSpan.appendChild(document.createTextNode(")"));
			
			var alertSpan = document.createElement("span");
			alertSpan.setAttribute("class", "alertspan");
			alertSpan.setAttribute("id", "alertspan" + i);	
			
			var replyForm = document.createElement("form");
			replyForm.setAttribute("id","replyform" + i);
			replyForm.setAttribute("class","replyform");

			var replyArea = document.createElement("textarea");
			replyArea.setAttribute("id","replyarea" + i);
			replyArea.setAttribute("class","replyarea");
			
			var replyButton = document.createElement("input");
			replyButton.setAttribute("class","replybutton");
			replyButton.setAttribute("type", "button");
			replyButton.setAttribute("value", "Reply");
			replyButton.onclick = function(j, name, email) {
				return function () {
					sendMessage(j, name, email);
				}
			}(i, message.name, message.email);
			
			replyForm.appendChild(replyArea);
			replyForm.appendChild(replyButton);
			
			messageDiv.appendChild(subjectSpan);
			messageDiv.appendChild(bodySpan);
			messageDiv.appendChild(nameSpan);
			messageDiv.appendChild(alertSpan);
			messageDiv.appendChild(replyForm);
			
			$("#messages").append(messageDiv);
		}
	}
	
	function updatePage() {
		$.getJSON("<c:url value="/getmessages" />", showMessages);
	}
	
	function onLoad() {
		updatePage();
		startTimer();
	}
	
	function startTimer() {
		timer = window.setInterval(updatePage, 5000);
	}
	
	function stopTimer() {
		window.clearInterval(timer);
	}
	
	$(document).ready(onLoad);
	
</script>