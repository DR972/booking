<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add a room</title>
    <link rel="stylesheet" type="text/css" href="/css/addRoom.css">
</head>
<body>
    <jsp:include page="adminMenu.jsp"></jsp:include>
	<div align="center" style="padding: 70px;">
		<h3>Добавить номер</h3>
		<h3 style="color: red;">${errorRoomNumber}</h3><br/>
        <form action="addRoom" method="POST">
            <table border="0" style="width: 20%">
                <tr>
                    <td>Номер комнаты: </td>
                    <td align = "center">${roomNumber}
                        <c:if test = "${roomNumber == null}">
                            <input type="number" name="roomNumber" min="1" required style="width:87px"/>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>Тип: </td>
                    <td align = "center">${type}
                        <c:if test = "${type == null}">
                            <select id="room" name="type" required>
                                <option value="" label="- Выбрать -"/>
                                <c:forEach items="${allTypesRooms}" var="room">
                                    <option value = "${room.type}">${room.type}</option>
                                </c:forEach>
                            </select>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>Кол-во мест: </td>
                    <td align = "center">${sleeps}
                        <c:if test = "${sleeps == null}">
                            <input type="number" name="sleeps" min="1" required style="width:87px"/>
                        </c:if>
                    </td>
                </tr>
                <c:if test = "${type!=null && newRoom == null}">
                    <tr>
                        <td>Цена: </td>
                        <td align = "center"><input type="number" name="price" min="1" required style="width:87px"/></td>
                    </tr>
                </c:if>
            </table><br/>
            <input type="hidden" name="action" value="addRoom"/>
            <input type="hidden" name="roomNumber" value="${roomNumber}"/>
            <input type="hidden" name="type" value="${type}"/>
            <input type="hidden" name="sleeps" value="${sleeps}"/>
            <button type="submit">Сохранить</button>
		</form>
	</div>
</body>
</html>