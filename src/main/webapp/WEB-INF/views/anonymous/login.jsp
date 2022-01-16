<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="page.logIn" var="logIn"/>
<fmt:message bundle="${msg}" key="message.logIn" var="message"/>
<fmt:message bundle="${msg}" key="form.username" var="username"/>
<fmt:message bundle="${msg}" key="form.password" var="password"/>
<fmt:message bundle="${msg}" key="button.logon" var="logon"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="utf-8">
    <title>${logIn}</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
    <jsp:include page="menu.jsp"></jsp:include>
    <div id="content">
        <h2>${message}</h2>
        <c:if test="${loginError != null}">
            <h3 style="color: red;"><fmt:message bundle="${msg}" key="${loginError}"/></h3>
        </c:if>
        <c:if test="${messageActive != null}">
            <h3 style="color: red;"><fmt:message bundle="${msg}" key="${messageActive}"/></h3>
        </c:if>

        <form method="POST" action="login">
            <input type="hidden" name="action" value="login"/>
            <input type="hidden" name="redirectId" value="${param.redirectId}"/>
            <input type="text" name="username" placeholder="${username}" autofocus="true" required pattern="[A-Za-z][A-Za-z0-9.\-]{4,20}"/>
            <input type="password" name="password" placeholder="${password}" autofocus="true" required pattern="[0-9]{5,}"/>
            <button type="submit">${logon}</button>
        </form>
    </div>
</body>
</html>
