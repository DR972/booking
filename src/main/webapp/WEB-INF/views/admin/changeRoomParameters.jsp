<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Изменение параметров номера</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
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
            <c:forEach items="${allRooms}" var="room">
                <tr>
                    <td>${room.id}</td>
                    <td>${room.type}</td>
                    <td>${room.sleeps}</td>
                    <td>
                        <form action="changeRoomParameters" method="POST">
                            <select id ="r" name ="parameter" required>
                                <option value="">- Выбрать тип -</option>
                                <c:forEach items="${allRoomsByTypesAndSleeps}" var="r">
                                    <c:if test = "${!(room.type==r.type && room.sleeps==r.sleeps)}">
                                        <option value = "${r.id}">тип=${r.type}, мест=${r.sleeps},
                                        цена=<ctg:money value="${r.price}"/></option>
                                    </c:if>
                                </c:forEach>
                                <input type="hidden" name="roomNumber" value="${room.id}"/>
                                <input type="hidden" name="changeParameters" value="changeParameters"/>
                                <button type="submit">Изменить</button>
                            </select>
                        </form>
                    </td>
                    <td><ctg:money value="${room.price}"/></td>
                    <td>
                        <form action="changeRoomParameters" method="post">
                            <input type="number" step="1" name="price" value="${room.price}"/>
                            <input type="hidden" name="type" value="${room.type}"/>
                            <input type="hidden" name="sleeps" value="${room.sleeps}"/>
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