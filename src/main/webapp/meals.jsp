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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
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
        <tr style="${meal.excess ? "color: red" :  "color: green"}">
            <td><fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedEmpDate"/>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedEmpDate}"/></td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&mealId=${meal.id}">Изменить</a></td>
            <td><a href="meals?action=delete&mealId=${meal.id}">Удалить</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=insert">Добавить</a></p>
<hr>

<footer id="main-footer">
    <p>
        Приложение стажировки <a href="https://github.com/JavaOPs/topjava" target="_blank">Spring 5/JPA Enterprise
        (Topjava)</a>
    </p>
</footer>
</body>

</html>