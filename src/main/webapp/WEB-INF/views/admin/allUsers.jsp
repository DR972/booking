<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Все пользователи</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
    <jsp:include page="adminMenu.jsp"></jsp:include>
    <div id="content" align="center">
        <h3>Все пользователи</h3>
        <h4 style="color: red;">${errorDeleteUser}</h4>
        <c:set var="action" scope="request" value="allUsers"/>
        <c:if test = "${countPages>1}">
            <jsp:include page="selectNumberRows.jsp"></jsp:include>
        </c:if>

        <table style="width: 98%;" align="center">
            <thead>
                <th>Ник</th>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Активация</th>
                <th>Почта</th>
                <th>Код активации</th>
                <th>Блокировка</th>
                <th>Изменить блокировку</th>
                <th>Роли</th>
                <th>Добавить/удалить роль ADMIN</th>
                <th>Удалить</th>
            </thead>
            <c:forEach items="${allUsers}" var="user">
                <tr align = "center">
                    <td>${user.id}</td>
                    <td>${user.lastname}</td>
                    <td>${user.firstname}</td>
                    <td>${user.active}</td>
                    <td>${user.email}</td>
                    <td>${user.activationCode}</td>
                    <td>${user.banned}</td>
                    <td>
                        <c:if test = "${user.id != 'ADMIN'}">
                            <form action="allUsers" method="post">
                                <input type="hidden" name="rows" value="${rows}"/>
                                <input type="hidden" name="pageNumber" value="${pageNumber}"/>
                                <input type="hidden" name="username" value="${user.id}"/>
                                <input type="hidden" name="banned" value="${user.banned}"/>
                                <input type="hidden" name="changeAccountLock" value="changeAccountLock"/>
                                <button type="submit">Изменить</button>
                            </form>
                        </c:if>
                    </td>
                    <td>
                        <c:forEach items="${user.roles}" var="role">${role}; </c:forEach>
                    </td>
                    <td>
                        <c:if test = "${user.id != 'ADMIN'}">
                            <form action="allUsers" method="post">
                                <input type="hidden" name="rows" value="${rows}"/>
                                <input type="hidden" name="pageNumber" value="${pageNumber}"/>
                                <input type="hidden" name="username" value="${user.id}"/>
                                <input type="hidden" name="changeRoleList" value="changeRoleList"/>
                                <button type="submit">Изменить</button>
                            </form>
                        </c:if>
                    </td>
                    <td>
                        <c:if test = "${user.id != 'ADMIN'}">
                            <form action="allUsers" method="post">
                                <input type="hidden" name="rows" value="${rows}"/>
                                <input type="hidden" name="pageNumber" value="${pageNumber}"/>
                                <input type="hidden" name="username" value="${user.id}"/>
                                <input type="hidden" name="delete" value="delete"/>
                                <button type="submit">Удалить</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table><br/>
        <c:if test = "${countPages>1}">
            <jsp:include page="selectPageNumber.jsp"></jsp:include>
        </c:if>
    </div>
</body>
</html>