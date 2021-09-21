<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title>Confirmation of the booking</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <jsp:include page="menuUser.jsp"></jsp:include>
	<div id="content" align="center">
        <h2><fmt:message bundle="${msg}" key="message.thanks"/></h2>
        <h3><fmt:message bundle="${msg}" key="book.client"/>: ${pageContext.request.userPrincipal.name}</h3>
        <h3><fmt:message bundle="${msg}" key="book.arrival"/>: ${booking.arrival}</h3>
        <h3><fmt:message bundle="${msg}" key="book.departure"/>: ${booking.departure}</h3>
        <h3><fmt:message bundle="${msg}" key="book.room.type"/>: <fmt:message bundle="${msg}" key="db.${booking.room.type}"/></h3>
        <h3><fmt:message bundle="${msg}" key="book.persons"/>: ${booking.persons}</h3>
        <h3><fmt:message bundle="${msg}" key="book.food"/>: <fmt:message bundle="${msg}" key="db.${booking.food.type}"/></h3>
        <h3><fmt:message bundle="${msg}" key="book.service"/>: <fmt:message bundle="${msg}" key="db.${booking.services.type}"/></h3>
        <h3><fmt:message bundle="${msg}" key="book.amount"/>: <ctg:money value="${booking.amount}"/></h3>
	</div>
</body>
</html>