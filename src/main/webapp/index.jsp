<%@ page import="project.entities.User" %>
<%@ page import="project.manager.Config" %>
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
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    if (user != null) {
        request.getRequestDispatcher(Config.getInstance().getProperty(Config.MAIN)).forward(request, response);
    }
%>
<form action="" method="POST" autocomplete="off">
    <input type="hidden" name="command" value="login"/>
    Username: <input type="text" name="username"/> <br>
    Password: <input type="password" name="password"/> <br>
    <c:if test="${sessionScope.registered == false}">
        <p style="color: red; font-size: 14px">Incorrect password</p>
    </c:if>
    <input type="submit" value = "Log in" class="button"/>
</form>
</body>
</html>
