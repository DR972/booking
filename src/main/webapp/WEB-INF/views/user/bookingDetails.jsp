<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="page.book" var="book"/>
<fmt:message bundle="${msg}" key="book.arrival" var="arrival"/>
<fmt:message bundle="${msg}" key="book.days" var="days"/>
<fmt:message bundle="${msg}" key="book.persons" var="persons"/>
<fmt:message bundle="${msg}" key="book.food" var="food"/>
<fmt:message bundle="${msg}" key="book.service" var="service"/>
<fmt:message bundle="${msg}" key="button.search" var="search"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title>Room booking form</title>
</head>
<body>
    <jsp:include page="menuUser.jsp"></jsp:include>
	<div align="center" style="padding: 70px;">
		<h2>${book}</h2>
        <form action="bookingDetails" method="POST">
        <c:if test="${errorValidate != null}">
             <h3 style="color: red;"><fmt:message bundle="${msg}" key="${errorValidate}"/></h3>
        </c:if>
            <table border="0" style="width: 20%">
                <tr>
                    <td>${arrival}: </td>
                    <td><input type="date" name="arrival" required pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])" style="width:173px"/></td>
                </tr>
                <tr>
                    <td>${days}: </td>
                    <td><input type="number" name="days" min="1" max="31" required style="width:170px"/><td><br/>
                </tr>
                <tr>
                    <td>${persons}: </td>
                    <td><input type="number" name="persons" min="1" max="10" required style="width:170px"/></td>
                </tr>
                <tr>
                    <td>${food}: </td>
                    <td><select id="food" name="food" style="width:178px">
                        <c:forEach items="${allFood}" var="food">
                            <option value="${food.id}"><fmt:message bundle="${msg}" key="db.${food.id}"/>,
                            <fmt:message bundle="${msg}" key="db.price"/>=<ctg:money value="${food.price}"/></option>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <td>${service}: </td>
                    <td><select id="service" name="service" style="width:178px">
                        <c:forEach items="${allServices}" var="service">
                            <option value="${service.id}"><fmt:message bundle="${msg}" key="db.${service.id}"/>,
                            <fmt:message bundle="${msg}" key="db.price"/>=<ctg:money value="${service.price}"/></option>
                        </c:forEach>
                    </select></td>
                </tr>
            </table><br/>
            <input type="hidden" name="action" value="bookingDetails"/>
            <button type="submit">${search}</button>
        </form>
	</div>
</body>
</html>