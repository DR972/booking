<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Select the page number</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <div>
        <c:choose>
            <c:when test="${pageNumber == 0}">
                <c:set var="finish" value="${pageNumber+2}"/>
            </c:when>
            <c:otherwise>
                <c:set var="finish" value="${pageNumber+1}"/>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${pageNumber == countPages-1}">
                <c:set var="start" value="${pageNumber-2}"/>
            </c:when>
             <c:otherwise>
                <c:set var="start" value="${pageNumber-1}"/>
            </c:otherwise>
        </c:choose>

        <c:if test = "${start < 0}">
            <c:set var="start" value="0"/>
        </c:if>
        <c:if test = "${finish > countPages-1}">
            <c:set var="finish" value="${countPages-1}"/>
        </c:if>

        <c:forEach var="p" begin="${start}" end="${finish}" step = "1">
            <form action="${action}" method="post">
                <span>
                    <c:choose>
                        <c:when test="${pageNumber == p}">
                            <button type="submit" id="btn"/>...
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="action" value="${action}"/>
                            <input type="hidden" name="rows" value="${rows}"/>
                            <input type="hidden" name="pageNumber" value="${p}"/>
                            <button type="submit" style="margin-left: 10px;"/>${p+1}
                        </c:otherwise>
                    </c:choose>
                </span>
            </form>
        </c:forEach>
    </div>
</body>
</html>