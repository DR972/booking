<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="page.booking" var="pageBooking"/>
<fmt:message bundle="${msg}" key="book.days" var="days"/>
<fmt:message bundle="${msg}" key="book.persons" var="persons"/>
<fmt:message bundle="${msg}" key="book.room.type" var="room"/>
<fmt:message bundle="${msg}" key="book.sleeps" var="sleeps"/>
<fmt:message bundle="${msg}" key="book.price" var="price"/>
<fmt:message bundle="${msg}" key="button.choose" var="choose"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title>${pageBooking}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <jsp:include page="menuUser.jsp"></jsp:include>
	<div id="content" align="center">
        <h2>${pageBooking}</h2>
        <form action="booking" method="POST">
            <h3>${booking.arrival} - ${booking.departure}, ${days}: ${booking.days}, ${persons}: ${booking.persons}</h3><br/>
        </form>
	    <table>
            <thead>
                <th>${room}</th>
                <th>${sleeps}</th>
                <th>${price}</th>
                <th>${choose}</th>
            </thead>
            <c:if test="${noAvailable != null}">
                <h3 style="color: red;"><fmt:message bundle="${msg}" key="${noAvailable}" /></h3><br/>
            </c:if>
            <c:forEach items="${rooms}" var="room">
                <tr>
                    <td><fmt:message bundle="${msg}" key="db.${room.type}"/></td>
                    <td>${room.sleeps}</td>
                    <td><ctg:money value="${room.price}"/></td>
                    <td>
                        <form action="booking" method="POST">
                            <input type="hidden" name="arrival" value= "${booking.arrival}"/>
                            <input type="hidden" name="days" value= "${booking.days}"/>
                            <input type="hidden" name="persons" value= "${booking.persons}"/>
                            <input type="hidden" name="food" value="${booking.food.id}"/>
                            <input type="hidden" name="service" value="${booking.services.id}"/>
                            <input type="hidden" name="roomNumber" value="${room.id}"/>
                            <button type="submit">${choose}</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${missed != null}">
             <br/><h3 style="color: red;"><fmt:message bundle="${msg}" key="${missed}"/></h3><br/>
        </c:if>
	</div>
</body>
</html>