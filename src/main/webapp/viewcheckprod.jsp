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
    <table class = "genericTable">
    <caption>Product information</caption>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Weight</th>
        <th>Price</th>
        <th>In stock</th>
    </tr>
    <tr>
        <td>${requestScope.cpdto.product.id}</td>
        <td>${requestScope.cpdto.product.name}</td>
        <td>${requestScope.cpdto.product.weight}</td>
        <td>${requestScope.cpdto.product.price} hrn</td>
        <td>${requestScope.cpdto.product.qty_instock}</td>
    </tr>
    </table>
    <br>
    <form action="${pageContext.request.contextPath}/" method="POST">
        <input type="hidden" name="command" value="editCheckProduct"/>
        <input type="hidden" name="checkid" value="${requestScope.checkid}"/>
        <input type="hidden" name="prodid" value="${requestScope.cpdto.product.id}"/>
        Select quantity: <input type="number" name = "qty" min="0.01" max = "${requestScope.cpdto.product.qty_instock}" step = "0.01" value = "${requestScope.cpdto.quantity}"/>
        <input type="submit" value = "Confirm edit" class="button"/>
    </form>
    <br>
    <button onclick="location.href='${pageContext.request.contextPath}?command=viewCheck&checkid=${requestScope.checkid}'" type="button">Back to viewing</button>
    <button onclick="location.href='${pageContext.request.contextPath}?command=removeProductCheck&checkid=${requestScope.checkid}&prodid=${requestScope.cpdto.product.id}'" type="button">Remove product</button>
</main>
</body>
</html>