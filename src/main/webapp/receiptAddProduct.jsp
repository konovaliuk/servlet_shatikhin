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
        <input type="hidden" name="command" value="checkAddProduct"/>
        <input type="hidden" name="checkid" value="${requestScope.checkid}"/>
        Select product: <input list="prods" name="prod">
        <datalist id="prods">
            <c:forEach var="p" items="${requestScope.productList}">
                <option value="${p.id}-${p.name}">
            </c:forEach>
        </datalist><br>
        Select quantity: <input type="number" name = "qty" min="0.01" step = "0.01" value = "0"/>
        <input type="submit" value = "Add to receipt" class="button"/>
    </form><br>
    <button onclick="location.href='${pageContext.request.contextPath}?command=viewCheck&checkid=${requestScope.checkid}'" type="button">Back to viewing</button>
</main>
</body>
</html>
