<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<%@ page import="by.rozmysl.bookingServlet.dao.hotel.RoomDaoImp" %>
<jsp:useBean id="roomDao" class="by.rozmysl.bookingServlet.dao.hotel.RoomDaoImp" scope="request" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Free rooms</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css" media="screen">
</head>

<body>
    <jsp:include page="adminMenu.jsp"></jsp:include>
    <div align="center" style="padding: 70px;">
        <h3>Свободные номера</h3>
    	<h3 style="color: red;">${dateError}</h3>
        <form action="freeRooms" method="POST">
            <table border="0" style="width: 30%">
                <tr>
                    <td>С: </td>
                    <td align = "center"> ${from}
                        <td align = "center"><input type="date" name="from" required></input></td>
                    </td>
                </tr>
                <tr>
                    <td>по: </td>
                    <td align = "center"> ${to}
                        <td align = "center"><input type="date" name="to" required></input></td>
                    </td>
                </tr>
            </table><br/>
            <input type="hidden" name="action" value="freeRooms"/>
            <input type="hidden" name="from" value="${from}"/>
            <input type="hidden" name="to" value="${to}"/>
            <button type="submit">Искать</button>
        </form>
    </div>
    <c:if test = "${from != null && to != null}">
        <div id="content" style="padding: 0;">
            <table align="center">
                <thead>
                    <th>Номер номера</th>
                    <th>Тип номера</th>
                    <th>Вместимость</th>
                    <th>Цена</th>
                </thead>
                <form action="freeRooms" method="POST">
                    <c:forEach items="${roomDao.findAllFreeRoomsBetweenTwoDates(from, to)}" var="room">
                        <tr>
                            <td>${room.roomNumber}</td>
                            <td>${room.type}</td>
                            <td>${room.sleeps}</td>
                            <td><ctg:money value="${room.price}"/></td>
                        </tr>
                    </c:forEach>
                </form>
            </table>
        </div>
    </c:if>
</body>
</html>