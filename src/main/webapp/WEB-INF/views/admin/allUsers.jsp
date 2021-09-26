<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
    <jsp:include page="adminMenu.jsp"></jsp:include>
        <c:if test = "${empty page}">
            <c:set var="page" value="0"/>
        </c:if>
        <c:if test = "${empty rows}">
            <c:set var="rows" value="10"/>
        </c:if>
        <c:set var="countPages" value="${userDao.countUsersPages(rows)}"/>

    <div id="content" align="center">
        <h3>Все пользователи</h3>
        <h4 style="color: red;">${errorDeleteUser}</h4>

        <c:if test = "${countPages>1}">
            <div align = "right">
                <a>Количество строк на странице:</a>
                <c:forEach var="r" begin="5" end="20" step="5">
                    <form action="allUsers" method="post">
                        <span>
                            <input type="hidden" name="action" value="allUsers"/>
                            <input type="hidden" name="page" value="0"/>
                            <input type="hidden" name="rows" value="${r}"/>
                            <button type="submit" id="btn" style="color: #D72020;"/> ${r}
                        </span>
                    </form>
                </c:forEach>
            </div>
        </c:if>

        <table style="width: 98%;" align="center">
            <thead>
                <th>Ник</th>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Активация</th>
                <th>Почта</th>
                <th>Код активации</th>
                <th>Роли</th>
                <th>Удалить</th>
            </thead>
            <c:forEach items="${userDao.getAll(page, rows)}" var="user">
                <tr align = "center">
                    <td>${user.username}</td>
                    <td>${user.lastname}</td>
                    <td>${user.firstname}</td>
                    <td>${user.active}</td>
                    <td>${user.email}</td>
                    <td>${user.activationCode}</td>
                    <td>
                        <c:forEach items="${user.roles}" var="role">${role}; </c:forEach>
                    </td>
                    <td>
                        <form action="allUsers" method="post">
                            <input type="hidden" name="action" value="allUsers"/>
                            <input type="hidden" name="rows" value="${rows}"/>
                            <input type="hidden" name="page" value="${page}"/>
                            <input type="hidden" name="userId" value="${user.username}"/>
                            <input type="hidden" name="delete" value="delete"/>
                            <button type="submit">Удалить</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table><br/>
        <c:if test = "${countPages>1}">
            <div>
                <c:forEach var="p" begin="0" end="${countPages-1}">
                    <form action="allUsers" method="post">
                        <span>
                            <c:choose>
                                <c:when test="${page == p}">
                                     <button type="submit" id="btn"/>...
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="action" value="allUsers"/>
                                    <input type="hidden" name="rows" value="${rows}"/>
                                    <input type="hidden" name="page" value="${p}"/>
                                    <button type="submit" style="margin-left: 10px;"/>${p+1}
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </form>
                </c:forEach>
            </div>
        </c:if>
    </div>
</body>
</html>