<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <form method="get" action="meals">
        От даты: <label for="datepicker"><input type="text" id="datepicker" name="dateFrom" action="filter"/> </label><br/>
        До даты: <label for="datepicker2"><input type="text" id="datepicker2" name="dateTo" action="filter"/> </label><br/>
        От времени:
        <input list="browsers" type="text" name="timeFrom" action="filter">
        <datalist id="browsers">
            <option value="01:00">
            <option value="02:00">
            <option value="03:00">
            <option value="04:00">
            <option value="05:00">
            <option value="06:00">
            <option value="07:00">
            <option value="08:00">
            <option value="09:00">
            <option value="10:00">
            <option value="11:00">
            <option value="12:00">
            <option value="13:00">
            <option value="14:00">
            <option value="15:00">
            <option value="16:00">
            <option value="17:00">
            <option value="18:00">
            <option value="19:00">
            <option value="20:00">
            <option value="21:00">
            <option value="22:00">
            <option value="23:00">
        </datalist>
        <br/>

        До времени:
        <input list="browsers2" type="text" name="timeTo" action="filter">
        <datalist id="browsers2">
            <option value="01:00">
            <option value="02:00">
            <option value="03:00">
            <option value="04:00">
            <option value="05:00">
            <option value="06:00">
            <option value="07:00">
            <option value="08:00">
            <option value="09:00">
            <option value="10:00">
            <option value="11:00">
            <option value="12:00">
            <option value="13:00">
            <option value="14:00">
            <option value="15:00">
            <option value="16:00">
            <option value="17:00">
            <option value="18:00">
            <option value="19:00">
            <option value="20:00">
            <option value="21:00">
            <option value="22:00">
            <option value="23:00">
            <option value="24:00">
        </datalist>
        <br/>
        <input type="hidden" name="userId" value="${userId}">
        <input type="hidden" name="action" value="filter">


        <button type="submit">Отфильтровать</button>


        <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>userID</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>${meal.userId}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>