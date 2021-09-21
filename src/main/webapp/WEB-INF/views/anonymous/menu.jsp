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
    <title>Menu</title>
    <link rel="stylesheet" href="/css/menu.css">
</head>

<body>
    <div class="menu" id="menu">
	    <table style="width: 100%;">
		    <tr align = "center">
			    <td><a href="/"><fmt:message bundle="${msg}" key="page.mainPage"/></a></td>
			    <td><a href="/user/bookingDetails"><fmt:message bundle="${msg}" key="page.book"/></a></td>
			    <td><a href="/anonymous/price"><fmt:message bundle="${msg}" key="page.prices"/></a></td>
			    <c:if test = "${pageContext.request.isUserInRole('ADMIN')}">
                    <td><a href="/admin/admin"><fmt:message bundle="${msg}" key="page.adminPage"/></a></td>
                </c:if>
                <c:if test = "${pageContext.request.isUserInRole('USER')}">
                    <td><a href="/user/user"><fmt:message bundle="${msg}" key="page.userPage"/></a></td>
                </c:if>
			    <td><a href="#"><fmt:message bundle="${msg}" key="page.photo"/></a></td>
			    <td><a href="#"><fmt:message bundle="${msg}" key="page.contacts"/></a></td>
			    <c:if test = "${loggedUser.username == null}">
                    <td><a href="/anonymous/login"><fmt:message bundle="${msg}" key="page.logIn"/></a></td>
                    <td><a href="/anonymous/registration"><fmt:message bundle="${msg}" key="page.registration"/></a></td>
                </c:if>
			    <c:if test = "${loggedUser.username != null}">
                    <td><a href="/anonymous/logout"><fmt:message bundle="${msg}" key="page.logOut"/></a></td>
                </c:if>
                <td><a href="?language=en">en</a><a href="?language=ru">ru</a></td>
			    <td><a style="color:red">${loggedUser.username}</a></td>
		    </tr>
	    </table>
</body>
</html>