<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Все номера</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
    <jsp:include page="adminMenu.jsp"></jsp:include>
    <div id="content">
        <h3>Все номера</h3>
        <h4>При изменении цены меняется цена у всех номеров этого типа аналогичной вместимости</h4>
        <table>
            <thead>
                <th>Номер номера</th>
                <th>Тип номера</th>
                <th>Вместимость</th>
                <th>Цена</th>
                <th>Изменить цену</th>
                <th>Удалить</th>
            </thead>
            <form action="allRooms" method="POST">
                <c:forEach items="${allRooms}" var="room">
                    <tr>
                        <td>${room.id}</td>
                        <td>${room.type}</td>
                        <td>${room.sleeps}</td>
                        <td><ctg:money value="${room.price}"/></td>
                        <td>
                            <form action="allRooms" method="POST">
                                <input type="number" step="1" name="price" value="${room.price}"/>
                                <input type="hidden" name="roomNumber" value="${room.id}"/>
                                <input type="hidden" name="changePrice" value="changePrice"/>
                                <button type="submit">Изменить</button>
                            </form>
                        </td>
                        <td>
                            <form action="allRooms" method="POST">
                                <input type="hidden" name="action" value="allRooms"/>
                                <input type="hidden" name="roomNumber" value="${room.id}"/>
                                <input type="hidden" name="delete" value="delete"/>
                                <button type="submit">Удалить</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </form>
        </table>
    </div>
</body>
</html>