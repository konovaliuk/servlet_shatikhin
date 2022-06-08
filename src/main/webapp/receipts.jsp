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
    <button onclick="location.href='${pageContext.request.contextPath}?command=createCheck'" type="button">Open new</button><br>
    <c:forEach var="check" items="${requestScope.checks}">
        <a href = "${pageContext.request.contextPath}?command=viewCheck&checkid=${check.id}" class="check">
            <ul>
                <li>CheckId = ${check.id}</li>
                <li>Date: ${check.timestamp}</li>
                <li>Status: ${check.status.name()}</li>
                <li>Cost: ${check.cost} hrn</li>
                <li>Cashier: ${check.cashier.username}</li>
            </ul>
        </a><br>
    </c:forEach>
</main>
</body>
</html>
