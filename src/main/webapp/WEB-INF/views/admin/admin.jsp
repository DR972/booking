<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Страница администратора</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <jsp:include page="adminMenu.jsp"></jsp:include>
    <div id="content" align="center">
        <h3>Привет, ${loggedUser.id}<h3>
    </div>
</body>
</html>