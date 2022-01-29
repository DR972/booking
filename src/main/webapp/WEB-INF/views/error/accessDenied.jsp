<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="page.accessDenied" var="accessDenied"/>
<fmt:message bundle="${msg}" key="message.accessDenied" var="message"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${accessDenied}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/anonymous/menu.jsp"></jsp:include>
    <div id="content">
        <h3>${loggedUser.id}</h3>
        <h3 style="color:red;">${message}</h3>
    </div>
</body>
</html>