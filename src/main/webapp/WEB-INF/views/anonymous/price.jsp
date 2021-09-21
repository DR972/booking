<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<%@ page import="by.rozmysl.bookingServlet.dao.hotel.FoodDaoImp, by.rozmysl.bookingServlet.dao.hotel.AdditionalServicesDaoImp" %>
<%@ page import="by.rozmysl.bookingServlet.dao.hotel.RoomDaoImp" %>
<jsp:useBean id="roomDao" class="by.rozmysl.bookingServlet.dao.hotel.RoomDaoImp" scope="request" />
<jsp:useBean id="fooDao" class="by.rozmysl.bookingServlet.dao.hotel.FoodDaoImp" scope="request" />
<jsp:useBean id="servicesDao" class="by.rozmysl.bookingServlet.dao.hotel.AdditionalServicesDaoImp" scope="request" />
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="utf-8">
    <title>Price</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css" media="screen">
</head>

<body>
    <jsp:include page="menu.jsp"></jsp:include>
    <div id="content">
        <h3><fmt:message bundle="${msg}" key="page.prices"/></h3>
        <h3><fmt:message bundle="${msg}" key="book.room.type"/></h3>
        <table>
            <thead>
                <th><fmt:message bundle="${msg}" key="book.room.type"/></th>
                <th><fmt:message bundle="${msg}" key="book.sleeps"/></th>
                <th><fmt:message bundle="${msg}" key="book.price"/></th>
            </thead>
            <c:forEach items="${roomDao.findAllRoomsByTypesAndSleeps()}" var="room">
                <tr>
                    <td><fmt:message bundle="${msg}" key="db.${room.type}"/></td>
                    <td>${room.sleeps}</td>
                    <td><ctg:money value="${room.price}"/></td>
                </tr>
            </c:forEach>
        </table>
        <h3><fmt:message bundle="${msg}" key="book.food"/></h3>
        <table>
            <thead>
                <th><fmt:message bundle="${msg}" key="book.food"/></th>
                <th><fmt:message bundle="${msg}" key="book.price"/></th>
            </thead>
            <c:forEach items="${fooDao.getAll(0,0)}" begin="1" var="food">
                <tr>
                    <td><fmt:message bundle="${msg}" key="db.${food.type}"/></td>
                    <td><ctg:money value="${food.price}"/></td>
                </tr>
            </c:forEach>
        </table>
        <h3><fmt:message bundle="${msg}" key="book.service"/></h3>
        <table class="style" id="content">
            <thead>
                <th><fmt:message bundle="${msg}" key="book.service"/></th>
                <th><fmt:message bundle="${msg}" key="book.price"/></th>
            </thead>
            <c:forEach items="${servicesDao.getAll(0,0)}" begin="1" var="service">
                <tr>
                    <td><fmt:message bundle="${msg}" key="db.${service.type}"/></td>
                    <td><ctg:money value="${service.price}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>