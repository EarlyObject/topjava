<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

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
    От даты: <label for="datepicker"><input type="text" placeholder="YYYY-MM-DD" id="datepicker"
                                            name="dateFrom"/></label><br/>
    До даты: <label for="datepicker2"><input type="text" placeholder="YYYY-MM-DD" id="datepicker2"
                                             name="dateTo"/></label><br/><br/>

    <label for="timeFrom">От времени:</label>
    <input type="text" placeholder="hh:mm" id="timeFrom" name="timeFrom"><br/>

    <label for="timeTo">До времени:</label>
    <input type="text" placeholder="hh:mm" id="timeTo" name="timeTo"><br/>

    <input type="hidden" name="userId" value="${userId}">
    <input type="hidden" name="action" value="filter">
    <br/>
    <button type="submit" action="filter">Отфильтровать</button>
    <br><br>
</form>
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