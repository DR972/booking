<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title>Room booking form</title>
</head>
<body>
    <jsp:include page="menuUser.jsp"></jsp:include>
	<div align="center" style="padding: 70px;">
		<h2><fmt:message bundle="${msg}" key="page.book"/></h2>
        <form action="bookingDetails" method="POST">
        <c:if test="${errorValidate != null}">
             <h3 style="color: red;"><fmt:message bundle="${msg}" key="${errorValidate}"/></h3>
        </c:if>
            <table border="0" style="width: 20%">
                <tr>
                    <td><fmt:message bundle="${msg}" key="book.arrival"/>: </td>
                    <td><input type="date" name="arrival" required style="width:173px"></input></td>
                </tr>
                <tr>
                    <td><fmt:message bundle="${msg}" key="book.days"/>: </td>
                    <td><input type="number" name="days" min="1" max="31" required style="width:170px"></input><td><br/>
                </tr>
                <tr>
                    <td><fmt:message bundle="${msg}" key="book.persons"/>: </td>
                    <td><input type="number" name="persons" min="1" max="10" required style="width:170px"></input></td>
                </tr>
                <tr>
                    <td><fmt:message bundle="${msg}" key="book.food"/>: </td>
                    <td><select id="food" name="food" style="width:178px">
                        <c:forEach items="${foodDao.getAll(0,0)}" var="food">
                            <option value="${food.type}"><fmt:message bundle="${msg}" key="db.${food.type}"/>,
                            <fmt:message bundle="${msg}" key="db.price"/>=<ctg:money value="${food.price}"/></option>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <td><fmt:message bundle="${msg}" key="book.service"/>: </td>
                    <td><select id="service" name="service" style="width:178px">
                        <c:forEach items="${servicesDao.getAll(0,0)}" var="service">
                            <option value="${service.type}"><fmt:message bundle="${msg}" key="db.${service.type}"/>,
                            <fmt:message bundle="${msg}" key="db.price"/>=<ctg:money value="${service.price}"/></option>
                        </c:forEach>
                    </select></td>
                </tr>
            </table><br/>
            <input type="hidden" name="action" value="bookingDetails"/>
            <button type="submit"><fmt:message bundle="${msg}" key="button.search"/></button>
        </form>
	</div>
</body>
</html>