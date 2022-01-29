<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/localization/message" var="msg"/>

<fmt:message bundle="${msg}" key="page.registration" var="registration"/>
<fmt:message bundle="${msg}" key="message.registration" var="message"/>
<fmt:message bundle="${msg}" key="form.username" var="username"/>
<fmt:message bundle="${msg}" key="form.password" var="password"/>
<fmt:message bundle="${msg}" key="form.passwordConfirm" var="passwordConfirm"/>
<fmt:message bundle="${msg}" key="form.lastName" var="lastName"/>
<fmt:message bundle="${msg}" key="form.firstName" var="firstName"/>
<fmt:message bundle="${msg}" key="form.email" var="email"/>
<fmt:message bundle="${msg}" key="button.register" var="register"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="utf-8">
    <title>${registration}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
    <jsp:include page="menu.jsp"></jsp:include>
    <div id="content">
        <h2>${registration}</h2>
        <h3>${message}</h3>
        <c:if test="${errorValidate != null}">
            <h3 style="color: red;"><fmt:message bundle="${msg}" key="${errorValidate}"/></h3>
        </c:if>
        <form method="POST" action="registration" >
            <input type="hidden" name="action" value="registration"/>
            <input type="text" name="username" placeholder="${username}" required pattern="[A-Za-z][A-Za-z0-9.\-]{4,20}"/><br/><br/>
            <input type="password" name="password" placeholder="${password}" required pattern="[0-9]{5,}"/><br/><br/>
            <input type="password" name="passwordConfirm" placeholder="${passwordConfirm}" required pattern="[0-9]{5,}"/><br/><br/>
            <input type="text" name="lastname" placeholder="${lastName}" required pattern="[A-Za-zА-Яа-яЁё]{2,20}"/><br/><br/>
            <input type="text" name="firstname" placeholder="${firstName}" required pattern="[A-Za-zА-Яа-яЁё]{2,20}"/><br/><br/>
            <input type="email" name="email" placeholder="${email}" required/><br/>
            <input type="hidden" name="language" value="${language}"/><br/>
            <button type="submit">${register}</button>
        </form>
    </div>
</body>
</html>