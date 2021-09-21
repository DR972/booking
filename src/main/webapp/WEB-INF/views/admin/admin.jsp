<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Home Page</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
    <jsp:include page="adminMenu.jsp"></jsp:include>
    <div id="content" align="center">
        <h3>Привет, ${loggedUser.username}<h3>
    </div>
</body>
</html>