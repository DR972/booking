<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="page.mainPage" var="mainPage"/>
<fmt:message bundle="${msg}" key="message.welcome" var="welcome"/>

<!DOCTYPE HTML>
<html lang="${language}">
<head>
    <title>${mainPage}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <jsp:include page="/WEB-INF/views/anonymous/menu.jsp"></jsp:include>
    <div id="content">
        <h3>${welcome}</h3>
        <h3>${message} ${loggedUser.id}</h3>
    </div>
</body>
</html>