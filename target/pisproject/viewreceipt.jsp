<%@ page import="project.entities.enums.CheckStatus" %>
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
    <table id = "viewCheck">
        <caption>Viewing receipt #${requestScope.check.id}, ${requestScope.check.status.name()}</caption>
        <tr><th>Id</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Cost</th>
            <th></th></tr>
    <c:forEach var="p" items="${requestScope.products}">
        <tr>
            <td>${p.product.id}</td>
            <td>${p.product.name}</td>
            <td>${p.product.price} hrn</td>
            <td>${p.quantity}</td>
            <td>${p.cost} hrn</td>
            <td><a href = "${pageContext.request.contextPath}?command=checkEditProductPage&checkid=${requestScope.check.id}&pid=${p.product.id}" class = "intTabElem">Edit</a ></td>
        </tr>
    </c:forEach>
        <tr>
            <td class = "bottom-left"></td>
            <td><a href = "${pageContext.request.contextPath}?command=checkAddProductPage&checkid=${requestScope.check.id}" class = "intTabElem">Add product</a></td>
            <td>
                <c:if test="${requestScope.check.status == CheckStatus.OPENED}">
                    <a href = "${pageContext.request.contextPath}?command=checkClose&checkid=${requestScope.check.id}" class = "intTabElem">Close receipt</a>
                </c:if>
                <c:if test="${requestScope.check.status == CheckStatus.CLOSED}">
                    <a href = "${pageContext.request.contextPath}?command=checkRefund&checkid=${requestScope.check.id}" class = "intTabElem">Refund receipt</a>
                </c:if>
            </td>
            <td>Total:</td>
            <td>${requestScope.check.cost} hrn</td>
            <td class = "bottom-right"><a href = "${pageContext.request.contextPath}?command=checkDelete&checkid=${requestScope.check.id}" class = "intTabElem">Delete receipt</a></td>
        </tr>
    </table>
</main>
</body>
</html>
