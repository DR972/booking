<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Menu admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">
</head>

<body>
    <div class="menu" id="menu">
	    <table style="width: 100%;">
		    <tr align = "center">
			    <td><a href="${pageContext.request.contextPath}/anonymous/mainPage">Главная</a></td>
			    <td><a href="${pageContext.request.contextPath}/user/bookingDetails">Забронировать номер</a></td>
			    <td><a href="${pageContext.request.contextPath}/admin/allUsers">Все пользователи</a></td>
			    <td><a href="${pageContext.request.contextPath}/admin/allBookings">Все бронирования</a></td>
                <td><a href="${pageContext.request.contextPath}/admin/allRooms">Все номера</a></td>
                <td><a href="${pageContext.request.contextPath}/admin/freeRooms">Свободные номера</a></td>
			    <td><a href="${pageContext.request.contextPath}/admin/changeRoomParameters">Изменить параметры номеров</a></td>
			    <td><a href="${pageContext.request.contextPath}/admin/changeServicesPrice">Изменить цены сервисов</a></td>
                <td><a href="${pageContext.request.contextPath}/admin/addRoom">Добавить номер</a></td>
			    <td><a style="color:red">${loggedUser.id}</a></td>
		    </tr>
	    </table>
    </div>
</body>
</html>