<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="project.entities.User" %><%--
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
<main>
    <div id = "errorsmg">
    <img src="images/error.jpg" alt="error msg" style="height:500px;"><br>
    <br><span style = "color:red; font-size:125%;">Error.<br>${requestScope.message}</span>
    </div>
</main>
</body>
</html>
