<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/customtags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Изменение цены сервисов</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
    <jsp:include page="adminMenu.jsp"></jsp:include>
    <div id="content">
        <h3>Питание</h3>
        <table>
            <thead>
                <th>Тип питания</th>
                <th>Цена</th>
                <th>Изменить цену</th>
            </thead>
            <c:forEach items="${allFood}" begin="1" var="food">
                <div align="center">
                    <tr>
                        <td>${food.id}</td>
                        <td><ctg:money value="${food.price}"/></td>
                        <td>
                            <form action="changeServicesPrice" method="post">
                                <input type="number" step="0.5" name="foodPrice" value="${food.price}"/>
                                <input type="hidden" name="foodType" value="${food.id}"/>
                                <input type="hidden" name="changeFoodPrice" value="changeFoodPrice"/>
                                <button type="submit">Изменить</button>
                            </form>
                        </td>
                    </tr>
                </div>
            </c:forEach>
        </table>

        <h3>Дополнительные услуги</h3>
        <table>
            <thead>
                <th>Тип услуги</th>
                <th>Цена</th>
                <th>Изменить цену</th>
            </thead>
            <c:forEach items="${allServices}" begin="1" var="service">
                <div align="center">
                    <tr>
                        <td>${service.id}</td>
                        <td><ctg:money value="${service.price}"/></td>
                        <td>
                            <form action="changeServicesPrice" method="post">
                                <input type="number" step="0.5" name="servicePrice" value="${service.price}"/>
                                <input type="hidden" name="serviceType" value="${service.id}"/>
                                <input type="hidden" name="changeServicePrice" value="changeServicePrice"/>
                                <button type="submit">Изменить</button>
                            </form>
                        </td>
                    </tr>
                </div>
            </c:forEach>
        </table>
    </div>
</body>
</html>