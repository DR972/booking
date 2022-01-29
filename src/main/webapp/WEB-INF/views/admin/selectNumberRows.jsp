<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Select the Number of Rows</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div align = "right">
        <a>Количество строк на странице:</a>
        <c:forEach var="r" begin="5" end="20" step="5">
            <form action="${action}" method="post">
                <span>
                    <input type="hidden" name="rows" value="${r}"/>
                    <button type="submit" id="btn" style="color: #D72020;"/> ${r}
                </span>
            </form>
        </c:forEach>
    </div>
</body>
</html>