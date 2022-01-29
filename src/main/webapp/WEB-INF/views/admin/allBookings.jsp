<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Все бронирования</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="adminMenu.jsp"></jsp:include>
    <div id="content" align="center">
        <h3>Все бронирования</h3>
        <c:set var="action" scope="request" value="allBookings"/>
        <c:if test = "${countPages>1}">
            <jsp:include page="selectNumberRows.jsp"></jsp:include>
        </c:if>

        <table id="content" align="center">
            <thead>
                <th>Номер брони</th>
                <th>Номер комнаты</th>
                <th>Изменить номер</th>
                <th>Тип комнаты</th>
                <th>Кол-во мест</th>
                <th>Кол-во гостей</th>
                <th>Дата заезда</th>
                <th>Дата выезда</th>
                <th>Кол-во дней</th>
                <th>Тип питания</th>
                <th>Доп. услуги</th>
                <th>Сумма</th>
                <th>Клиент</th>
                <th>Статус</th>
                <th>Изменить статус</th>
                <th>Удалить</th>
            </thead>
            <c:forEach items="${allBookings}" var="booking">
                <tr>
                    <td>${booking.id}</td>
                    <td>${booking.room.id}</td>
                    <td align = "left">
                        <form action="allBookings" method="POST">
                            <select id ="room" name ="roomNumber" required>
                                <option value="">- Выбрать номер -</option>
                                <c:forEach items="${availableRooms[booking.id]}" var="r">
                                    <option value = "${r.id}">№${r.id}, ${r.type}-${r.sleeps}, <ctg:money value="${r.price}"/></option>
                                </c:forEach>
                                <input type="hidden" name="rows" value="${rows}"/>
                                <input type="hidden" name="pageNumber" value="${pageNumber}"/>
                                <input type="hidden" name="bookingNumber" value="${booking.id}"/>
                                <input type="hidden" name="changeRoom" value="changeRoom"/>
                                <button type="submit">Подтвердить</button>
                            </select>
                        </form>
                    </td>
                    <td>${booking.room.type}</td>
                    <td>${booking.room.sleeps}</td>
                    <td>${booking.persons}</td>
                    <td>${booking.arrival}</td>
                    <td>${booking.departure}</td>
                    <td>${booking.days}</td>
                    <td>${booking.food.id}</td>
                    <td>${booking.services.id}</td>
                    <td><ctg:money value="${booking.amount}"/></td>
                    <td>${booking.user.id}</td>
                    <td>${booking.status}</td>
                    <td>
                        <form action="allBookings" method="POST">
                            <select id ="status" name ="status" required>
                                <option value="">- Изменить -</option>
                                <c:forEach items="${status}" var="st">
                                    <option value = "${st.status}">${st.status}</option>
                                </c:forEach>
                                <input type="hidden" name="rows" value="${rows}"/>
                                <input type="hidden" name="pageNumber" value="${pageNumber}"/>
                                <input type="hidden" name="bookingNumber" value="${booking.id}"/>
                                <input type="hidden" name="changeStatus" value="changeStatus"/>
                                <button type="submit">Подтвердить</button>
                            </select>
                        </form>
                    </td>
                    <td>
                        <form action="allBookings" method="POST">
                            <input type="hidden" name="rows" value="${rows}"/>
                            <input type="hidden" name="pageNumber" value="${pageNumber}"/>
                            <input type="hidden" name="bookingNumber" value="${booking.id}"/>
                            <input type="hidden" name="delete" value="delete"/>
                            <button type="submit">Удалить</button>
                        </form>
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