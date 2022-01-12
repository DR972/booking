<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="page.mainPage" var="mainPage"/>
<fmt:message bundle="${msg}" key="page.book" var="book"/>
<fmt:message bundle="${msg}" key="page.prices" var="prices"/>
<fmt:message bundle="${msg}" key="page.adminPage" var="adminPage"/>
<fmt:message bundle="${msg}" key="page.userPage" var="userPage"/>
<fmt:message bundle="${msg}" key="page.photo" var="photo"/>
<fmt:message bundle="${msg}" key="page.contacts" var="contacts"/>
<fmt:message bundle="${msg}" key="page.logIn" var="logIn"/>
<fmt:message bundle="${msg}" key="page.registration" var="registration"/>
<fmt:message bundle="${msg}" key="page.logOut" var="logOut"/>

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
			    <td><a href="/">${mainPage}</a></td>
			    <td><a href="/user/bookingDetails">${book}</a></td>
			    <td><a href="/anonymous/price">${prices}</a></td>
			    <c:if test = "${pageContext.request.isUserInRole('ADMIN')}">
                    <td><a href="/admin/admin">${adminPage}</a></td>
                </c:if>
                <c:if test = "${pageContext.request.isUserInRole('USER')}">
                    <td><a href="/user/user">${userPage}</a></td>
                </c:if>
			    <td><a href="#">${photo}</a></td>
			    <td><a href="#">${contacts}</a></td>
			    <c:if test = "${loggedUser.id == null}">
                    <td><a href="/anonymous/login">${logIn}</a></td>
                    <td><a href="/anonymous/registration">${registration}</a></td>
                </c:if>
			    <c:if test = "${loggedUser.id != null}">
                    <td><a href="/anonymous/logout">${logOut}</a></td>
                </c:if>
                <td><a href="?language=en">en</a><a href="?language=ru">ru</a></td>
			    <td><a style="color:red">${loggedUser.id}</a></td>
		    </tr>
	    </table>
</body>
</html>