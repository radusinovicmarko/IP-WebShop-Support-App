<%@page import="javax.websocket.SendResult"%>
<%@page import="org.unibl.etf.ip.model.dto.Message"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp" %>
<jsp:useBean id="messageService"
	class="org.unibl.etf.ip.service.MessageService" scope="session" />
<jsp:useBean id="adminService"
	class="org.unibl.etf.ip.service.AdminService" scope="session"></jsp:useBean>
<!DOCTYPE html>
<%
	Message message = new Message();
	if (adminService.isLoggedIn()) {
		if (request.getParameter("id") != null) {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				message = messageService.getById(id);
				if (!message.isRead())
					messageService.read(id, message);
			} catch (Exception ex) {
			response.sendRedirect("messages.jsp");
		}
	} else if (request.getParameter("submit") != null) {
		String email = request.getParameter("email");
		String title = request.getParameter("title");
		String responseText = request.getParameter("response");
		messageService.sendResponse(email, title, responseText);
		response.sendRedirect("messages.jsp");
	} else
		response.sendRedirect("index.jsp");
} else {
	response.sendRedirect("index.jsp");
}
%>
<html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Odgovor</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="css/styles.css" rel="stylesheet" />
</head>
<body>
	<jsp:include page="WEB-INF/menu.jsp"></jsp:include>
	<div class="form container mt-3">
		<h3>Odgovor na poruku</h3>
		<form action="response.jsp" method="post">
			<div class="mb-3 mt-3">
				<label class="form-label">Naslov:</label>
				<input type="text" class="form-control"
					value="<%=message.getTitle()%>" placeholder="Naslov" name="title"
					readonly />
			</div>
			<div class="mb-3 mt-3">
				<label class="form-label">Sadržaj:</label>
				<textarea readonly class="form-control" name="content"><%=message.getContent()%></textarea>
			</div>
			<div class="mb-3 mt-3">
				<label class="form-label">E-mail:</label>
				<textarea readonly class="form-control" name="email"><%= message.getUserEmail() %></textarea>
			</div>
			<div class="mb-3 mt-3">
				<label class="form-label">Odgovor:</label>
				<textarea required class="form-control" rows="6" name="response"></textarea>
			</div>
			<div class="mb-3 mt-3">
				<input type="submit" class="btn btn-primary" value="Odgovorite"
					name="submit" readonly />
			</div>
		</form>
	</div>
</body>
</html>