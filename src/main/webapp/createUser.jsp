<%@ page import="project.entities.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%--
  Created by IntelliJ IDEA.
  User: EShat
  Date: 03.06.2022
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cash Machine</title>
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/receipts.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<% Boolean access = (Boolean) request.getAttribute("access");
    if (access == null || !access) {
        response.sendRedirect("/");
    }%>
<main>
    <br>
    <form action="${pageContext.request.contextPath}/" method="POST" autocomplete="off">
        <input type="hidden" name="command" value="createUser"/>
        Username: <input type="text" name="username"><br>
        Password: <input type="text" name="password"><br>
        Name: <input type="text" name="fullname"><br>
        Role:
        <c:forEach var="r" items="${requestScope.roles}">
            <input type="radio" name="role" value="${r.id}"> ${r.name}
        </c:forEach>
        <br>
        <input type="submit" value = "Create user" class="button"/>
</main>
</body>
</html>
