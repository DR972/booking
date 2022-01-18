<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="page.confirmation" var="confirmation"/>
<fmt:message bundle="${msg}" key="message.thanks" var="message"/>
<fmt:message bundle="${msg}" key="book.client" var="client"/>
<fmt:message bundle="${msg}" key="book.arrival" var="arrival"/>
<fmt:message bundle="${msg}" key="book.departure" var="departure"/>
<fmt:message bundle="${msg}" key="book.room.type" var="room"/>
<fmt:message bundle="${msg}" key="book.persons" var="persons"/>
<fmt:message bundle="${msg}" key="book.food" var="food"/>
<fmt:message bundle="${msg}" key="book.service" var="service"/>
<fmt:message bundle="${msg}" key="book.amount" var="amount"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title>${confirmation}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <jsp:include page="menuUser.jsp"></jsp:include>

	<div id="content" align="center">
        <h2>${message}</h2>
        <h3>${client}: ${booking.user.id}</h3>
        <h3>${arrival}: ${booking.arrival}</h3>
        <h3>${departure}: ${booking.departure}</h3>
        <h3>${room}: <fmt:message bundle="${msg}" key="db.${booking.room.type}"/></h3>
        <h3>${persons}: ${booking.persons}</h3>
        <h3>${food}: <fmt:message bundle="${msg}" key="db.${booking.food.id}"/></h3>
        <h3>${service}: <fmt:message bundle="${msg}" key="db.${booking.services.id}"/></h3>
        <h3>${amount}: <ctg:money value="${booking.amount}"/></h3>
	</div>
</body>
</html>