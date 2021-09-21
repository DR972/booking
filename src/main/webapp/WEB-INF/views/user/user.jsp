<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<!DOCTYPE HTML>
<html lang="${language}">
<head>
    <title>User page</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
    <jsp:include page="menuUser.jsp"></jsp:include>
    <div id="content">
        <h3><fmt:message bundle="${msg}" key="page.userPage"/></h3>
        <h3><fmt:message bundle="${msg}" key="message.welcome"/> ${loggedUser.username}</h3>
    </div>
</body>
</html>