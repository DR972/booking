<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="page.prices" var="prices"/>
<fmt:message bundle="${msg}" key="book.room.type" var="room"/>
<fmt:message bundle="${msg}" key="book.sleeps" var="sleeps"/>
<fmt:message bundle="${msg}" key="book.price" var="price"/>
<fmt:message bundle="${msg}" key="book.food" var="food"/>
<fmt:message bundle="${msg}" key="book.service" var="service"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="utf-8">
    <title>${prices}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" media="screen">
</head>

<body>
    <jsp:include page="menu.jsp"></jsp:include>
    <div id="content">
        <h3>${prices}</h3>
        <h3>${room}</h3>
        <table>
            <thead>
                <th>${room}</th>
                <th>${sleeps}</th>
                <th>${price}</th>
            </thead>
            <c:forEach items="${allRoomsByTypesAndSleeps}" var="room">
                <tr>
                    <td><fmt:message bundle="${msg}" key="db.${room.type}"/></td>
                    <td>${room.sleeps}</td>
                    <td><ctg:money value="${room.price}"/></td>
                </tr>
            </c:forEach>
        </table>
        <h3>${food}</h3>
        <table>
            <thead>
                <th>${food}</th>
                <th>${price}</th>
            </thead>
            <c:forEach items="${allFood}" begin="1" var="food">
                <tr>
                    <td><fmt:message bundle="${msg}" key="db.${food.id}"/></td>
                    <td><ctg:money value="${food.price}"/></td>
                </tr>
            </c:forEach>
        </table>
        <h3>${service}</h3>
        <table class="style" id="content">
            <thead>
                <th>${service}</th>
                <th>${price}</th>
            </thead>
            <c:forEach items="${allServices}" begin="1" var="service">
                <tr>
                    <td><fmt:message bundle="${msg}" key="db.${service.id}"/></td>
                    <td><ctg:money value="${service.price}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>