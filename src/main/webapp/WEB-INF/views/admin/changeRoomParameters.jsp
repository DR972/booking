<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Change room parameters</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
    <jsp:include page="adminMenu.jsp"></jsp:include>
    <div id="content" align="center">
        <h3>Изменение параметров номера</h3>
        <h4>При изменении цены меняется цена у всех номеров этого типа аналогичной вместимости</h4>
        <table style="width: 98%;" align="center">
            <thead>
                <th>Номер комнаты</th>
                <th>Тип комнаты</th>
                <th>Кол-во мест</th>
                <th>Изменить параметры</th>
                <th>Цена</th>
                <th>Изменить цену</th>
            </thead>
            <c:forEach items="${roomDao.getAll(page, rows)}" var="room">
                <tr>
                    <td>${room.roomNumber}</td>
                    <td>${room.type}</td>
                    <td>${room.sleeps}</td>
                    <td>
                        <form action="changeRoomParameters" method="POST">
                            <select id ="r" name ="parameter" required>
                                <option value="">- Выбрать тип -</option>
                                <c:forEach items="${roomDao.findAllRoomsByTypesAndSleeps()}" var="r">
                                    <c:if test = "${!(room.type==r.type && room.sleeps==r.sleeps)}">
                                        <option value = "${r.roomNumber}">тип=${r.type}, мест=${r.sleeps},
                                        цена=<ctg:money value="${r.price}"/></option>
                                    </c:if>
                                </c:forEach>
                                <input type="hidden" name="action" value="changeRoomParameters"/>
                                <input type="hidden" name="roomId" value="${room.roomNumber}"/>
                                <input type="hidden" name="changeParameters" value="changeParameters"/>
                                <button type="submit">Изменить</button>
                            </select>
                        </form>
                    </td>
                    <td><ctg:money value="${room.price}"/></td>
                    <td>
                        <form action="changeRoomParameters" method="post">
                            <input type="hidden" name="action" value="changeRoomParameters"/>
                            <input type="number" step="1" name="price" value="${room.price}"></input>
                            <input type="hidden" name="roomId" value="${room.roomNumber}"/>
                            <input type="hidden" name="changePrice" value="changePrice"/>
                            <button type="submit">Изменить</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>