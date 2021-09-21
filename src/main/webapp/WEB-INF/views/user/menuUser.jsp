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
    <title>Menu user</title>
    <link rel="stylesheet" href="/css/menu.css">
</head>

<body>
    <div class="menu" id="menu">
	    <table style="width: 100%;">
		    <tr align = "center">
			    <td><a href="/"><fmt:message bundle="${msg}" key="page.mainPage"/></a></td>
			    <td><a href="/user/bookingDetails"><fmt:message bundle="${msg}" key="page.book"/></a></td>
			    <td><a href="/anonymous/price"><fmt:message bundle="${msg}" key="page.prices"/></a></td>
			    <td><a href="/user/user"><fmt:message bundle="${msg}" key="page.userPage"/></a></td>
                <td><a href="/user/userBookings"><fmt:message bundle="${msg}" key="page.userBookings"/></a></td>
			    <td><a href="#"><fmt:message bundle="${msg}" key="page.photo"/></a></td>
			    <td><a href="#"><fmt:message bundle="${msg}" key="page.contacts"/></a></td>
                <td><a href="/anonymous/logout"><fmt:message bundle="${msg}" key="page.logOut"/></a></td>
                <td><a href="?language=en">en</a><a href="?language=ru">ru</a></td>
			    <td><a style="color:red">${loggedUser.username}</a></td>
		    </tr>
	    </table>
    </div>
</body>
</html>