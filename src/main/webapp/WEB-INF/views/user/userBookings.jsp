<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<%@ page import="java.time.LocalDate" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>All user bookings</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
     ${userBookings}
    <jsp:include page="menuUser.jsp"></jsp:include>
    <div id="content" align="center">
        <h3><br/><fmt:message bundle="${msg}" key="page.userBookings"/></h3>
        <table style="width: 98%;" align="center">
            <thead>
                <th><fmt:message bundle="${msg}" key="book.room.type"/></th>
                <th><fmt:message bundle="${msg}" key="book.persons"/></th>
                <th><fmt:message bundle="${msg}" key="book.arrival"/></th>
                <th><fmt:message bundle="${msg}" key="book.departure"/></th>
                <th><fmt:message bundle="${msg}" key="book.days"/></th>
                <th><fmt:message bundle="${msg}" key="book.food"/></th>
                <th><fmt:message bundle="${msg}" key="book.service"/></th>
                <th><fmt:message bundle="${msg}" key="book.amount"/></th>
                <th><fmt:message bundle="${msg}" key="button.cancel"/></th>
            </thead>
            <c:forEach items="${userBooking}" var="booking">
                <div>
                    <tr>
                        <td><fmt:message bundle="${msg}" key="db.${booking.room.type}"/></td>
                        <td>${booking.persons}</td>
                        <td>${booking.arrival}</td>
                        <td>${booking.departure}</td>
                        <td>${booking.days}</td>
                        <td><fmt:message bundle="${msg}" key="db.${booking.food.type}"/></td>
                        <td><fmt:message bundle="${msg}" key="db.${booking.services.type}"/></td>
                        <td><ctg:money value="${booking.amount}"/></td>
                        <td>
                            <c:if test = "${LocalDate.now().isBefore(booking.arrival)}">
                                <form action="userBookings" method="POST">
                                    <input type="hidden" name="bookingNumber" value="${booking.number}"/>
                                    <input type="hidden" name="delete" value="delete"/>
                                    <button type="submit"><fmt:message bundle="${msg}" key="button.cancel"/></button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </div>
            </c:forEach>
        </table>
    </div>
</body>
</html>