<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="page.error500" var="pageError"/>
<fmt:message bundle="${msg}" key="error.error" var="error"/>

<!DOCTYPE HTML>
<html lang="${language}">
<head>
    <title>${pageError}</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/anonymous/menu.jsp"></jsp:include>
    <div id="content">
        <h3>${error}</h3>
        <strong>Status Code</strong>: ${code}<br/>
        <strong>Servlet Name</strong>: ${servletName}<br/>
        <strong>Exception Name</strong>: ${name}<br/>
        <strong>Requested URI</strong>: ${uri}<br/>
        <strong>Exception message</strong>: ${message}<br/>
    </div>
</body>
</html>