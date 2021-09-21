<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Menu admin</title>
    <link rel="stylesheet" href="/css/menu.css">
</head>

<body>
    <div class="menu" id="menu">
	    <table style="width: 100%;">
		    <tr align = "center">
			    <td><a href="/">Главная</a></td>
			    <td><a href="/user/bookingDetails">Забронировать номер</a></td>
			    <td><a href="/admin/allUsers">Все пользователи</a></td>
			    <td><a href="/admin/allBookings">Все бронирования</a></td>
                <td><a href="/admin/allRooms">Все номера</a></td>
                <td><a href="/admin/freeRooms">Свободные номера</a></td>
			    <td><a href="/admin/changeRoomParameters">Изменить параметры номеров</a></td>
			    <td><a href="/admin/changeServicesPrice">Изменить цены сервисов</a></td>
                <td><a href="/admin/addRoom">Добавить номер</a></td>
			    <td><a style="color:red">${loggedUser.username}</a></td>
		    </tr>
	    </table>
    </div>
</body>
</html>