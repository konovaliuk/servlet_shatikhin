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
</head>
<body>
<jsp:include page="header.jsp"/>
<% Boolean access = (Boolean) request.getAttribute("access");
    if (access == null || !access) {
        response.sendRedirect("/");
    }%>
<main>
    <table class = "genericTable">
        <caption>Users</caption>
        <tr><th>Id</th>
            <th>Username</th>
            <th>Full name</th>
            <th>Role</th></tr>
        <c:forEach var="u" items="${requestScope.userList}">
        <tr>
            <td>${u.id}</td>
            <td>${u.username}</td>
            <td>${u.full_name}</td>
            <td>${u.role.name}</td>
        </tr>
        </c:forEach>
    </table>
    <button onclick="location.href='${pageContext.request.contextPath}?command=createUserPage'" type="button">Create new user</button>
</main>
</body>
</html>
