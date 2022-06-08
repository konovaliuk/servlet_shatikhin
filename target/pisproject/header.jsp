<%@ page import="project.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <nav>
        <a href = "${pageContext.request.contextPath}?command=openReceipts">Receipts</a>
        <a href = "${pageContext.request.contextPath}?command=openStorage">Storage</a>
        <a href = "${pageContext.request.contextPath}?command=openAdmin">Admin</a>
        <a href = "${pageContext.request.contextPath}?command=logout">Welcome, ${sessionScope.user.full_name}! Logout</a>
    </nav>
    <%
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/");
        }
    %>
</header>
