<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.unibl.etf.ip.model.dto.Message"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="messageBean"
	class="org.unibl.etf.ip.model.beans.MessageBean" scope="session" />
<jsp:useBean id="adminBean"
	class="org.unibl.etf.ip.model.beans.AdminBean" scope="session"></jsp:useBean>
<!DOCTYPE html>
<%
	List<Message> messages = new ArrayList<>();
	if (adminBean.isLoggedIn()) {
		if (request.getParameter("read") == null) {
			messages = messageBean.getAll();
		} else if ("true".equals(request.getParameter("read"))) {
			messages = messageBean.getAllByStatus(true);
		} else if ("false".equals(request.getParameter("read"))) {
			messages = messageBean.getAllByStatus(false);
		}
	} else {
		response.sendRedirect("index.jsp");
	}
%>
<html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Poruke</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="css/styles.css" rel="stylesheet" />
</head>
<body>
	<jsp:include page="WEB-INF/menu.jsp"></jsp:include>
	<div class="container mt-3">
		<h3>Poruke</h3>
		<button type="button" class="btn btn-primary" onclick="location.href='messages.jsp?read=false'">Nepročitane</button>
  		<button type="button" class="btn btn-primary" onclick="location.href='messages.jsp?read=true'">Pročitane</button>
  		<button type="button" class="btn btn-primary" onclick="location.href='messages.jsp'">Sve</button>
		<div class="table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Naslov</th>
						<th>Sadržaj</th>
						<th>Pročitana</th>
						<th>Akcija</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (Message message : messages) {
					%>
					<tr>
						<td><%=message.getTitle()%></td>
						<td><%=message.getContent()%></td>
						<td><input class="form-check-input" type="checkbox"
							<%=message.isRead() ? "checked" : ""%> disabled /></td>
						<td class="action-cell">
							<button type="button" class="btn btn-primary"
								onclick=<%="location.href=\"response.jsp?id=" + message.getId() + "\""%>>Pregledajte</button>
						</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>