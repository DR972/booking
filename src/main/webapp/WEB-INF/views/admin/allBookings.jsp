<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>All bookings</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
    <jsp:include page="adminMenu.jsp"></jsp:include>
    <div id="content" align="center">
        <h3>Все бронирования</h3>
        <c:if test = "${countPages>1}">
            <div align = "right">
                <a>Количество строк на странице:</a>
                <c:forEach var="r" begin="5" end="20" step="5">
                    <form action="allBookings" method="post">
                        <span>
                            <input type="hidden" name="page" value="0"/>
                            <input type="hidden" name="rows" value="${r}"/>
                            <button type="submit" id="btn" style="color: #D72020;"/> ${r}
                        </span>
                    </form>
                </c:forEach>
            </div>
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
                    <td>${booking.number}</td>
                    <td>${booking.room.roomNumber}</td>
                    <td align = "left">
                        <form action="allBookings" method="POST">
                            <select id ="room" name ="roomNumber" required>
                                <option value="">- Выбрать номер -</option>
                                <c:forEach items="${availableRooms[booking.number]}" var="r">
                                    <option value = "${r.roomNumber}">№${r.roomNumber}, ${r.type}-${r.sleeps}, <ctg:money value="${r.price}"/></option>
                                </c:forEach>
                                <input type="hidden" name="action" value="allBookings"/>
                                <input type="hidden" name="page" value="${page}"/>
                                <input type="hidden" name="rows" value="${rows}"/>
                                <input type="hidden" name="bookingNumber" value="${booking.number}"/>
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
                    <td>${booking.food.type}</td>
                    <td>${booking.services.type}</td>
                    <td><ctg:money value="${booking.amount}"/></td>
                    <td>${booking.user.username}</td>
                    <td>${booking.status}</td>
                    <td>
                        <form action="allBookings" method="POST">
                            <select id ="status" name ="status" required>
                                <option value="">- Изменить -</option>
                                <c:forEach items="${status}" var="st">
                                    <option value = "${st.status}">${st.status}</option>
                                </c:forEach>
                                <input type="hidden" name="action" value="allBookings"/>
                                <input type="hidden" name="page" value="${page}"/>
                                <input type="hidden" name="rows" value="${rows}"/>
                                <input type="hidden" name="bookingNumber" value="${booking.number}"/>
                                <input type="hidden" name="changeStatus" value="changeStatus"/>
                                <button type="submit">Подтвердить</button>
                            </select>
                        </form>
                    </td>
                    <td>
                        <form action="allBookings" method="POST">
                            <input type="hidden" name="action" value="allBookings"/>
                            <input type="hidden" name="page" value="${page}"/>
                            <input type="hidden" name="rows" value="${rows}"/>
                            <input type="hidden" name="bookingNumber" value="${booking.number}"/>
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
                    <form action="allBookings" method="post">
                        <span>
                            <c:choose>
                                <c:when test="${page == p}">
                                     <button type="submit" id="btn"/>...
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="action" value="allBookings"/>
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