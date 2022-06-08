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
<caption>All products</caption>
<tr><th>Id</th>
    <th>Name</th>
    <th>Weight</th>
    <th>Price</th>
    <th>In Stock</th></tr>
<c:forEach var="p" items="${requestScope.productList}">
    <tr>
        <td>${p.id}</td>
        <td>${p.name}</td>
        <td>${p.weight} kg</td>
        <td>${p.price} hrn</td>
        <td>${p.qty_instock}</td>
    </tr>
</c:forEach>

</table>
</main>
</body>
</html>