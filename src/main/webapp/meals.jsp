<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="color" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<link rel="stylesheet" href="jquery.datetimepicker.min.css">
<script src="jquery.js"></script>
<script src="jquery.datetimepicker.full.js"></script>

<!DOCTYPE html>
<html>
<head>
    <title>Подсчет калорий</title>
    <style type="text/css">
        #main-header {
            text-align: left;
            background-color: dimgrey;
            color: white;
            padding: 5px;
        }

        #user-profile-link {
            text-align: right;
        }

        #main-footer {
            text-align: left;
            background-color: white;
            color: black;
            padding: 5px;
        }
    </style>

</head>

<body bgcolor="#F0F8FF">
<header id="main-header">
    <h2>Моя еда</h2>
    <a id="user-profile-link" href="users.jsp">User профиль</a>
</header>

<br>
<br>

<table>
    <thead>
    <tr>
        <th>Дата / Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="meal" items="${meals}">
        <tr style="${meal.isExcess() ? "color: red" :  "color: green"}">
            <td><fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedEmpDate"/>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedEmpDate}"/></td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
            <td><a href="meals?action=edit&mealId=${meal.getId()}">Update</a></td>
            <td><a href="meals?action=delete&mealId=${meal.getId()}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=insert">Add Meal</a></p>
<hr>

<form method="POST" action="meals" name="editForm">

    <script>
        $(function () {
            $("#datetime").datetimepicker(
            );
        });
    </script>

    Дата: <input id="datetime" name="date" type="text"
                 value="<fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"/><fmt:formatDate pattern="yyyy/MM/dd HH:mm" value="${parsedDate}"/>"/>
    <br/>
    Описание: <input type="text" name="description"
                     value="<c:out value="${meal.getDescription()}" />"/>
    <br/>
    Калории: <input type="text" name="calories"
                    value="<c:out  value="${meal.getCalories()}"/>"/>
    <br/>

    <input type="submit" value="Submit"/>
</form>

<footer id="main-footer">
    <p>
        Приложение стажировки <a href="https://github.com/JavaOPs/topjava" target="_blank">Spring 5/JPA Enterprise
        (Topjava)</a>
    </p>
</footer>
</body>

</html>