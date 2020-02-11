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
    <title>Ваше блюдо</title>
</head>
<body>

<h1>Введите блюдо</h1>
<form method="POST" action="meals" name="editForm"><table style="with: 50%"></table>

    <script>
        $(function () {
            $("#datetime").datetimepicker(
            );
        });
    </script>

    Дата: <input id="datetime" name="date" type="text"
                 value="<fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"/><fmt:formatDate pattern="yyyy/MM/dd HH:mm" value="${parsedDate}"/>"/>
    <br/>
    Описание: <input type="text" name="description" value="${meal.description}"/>
    <br/>
    Калории: <input type="text" name="calories" value="${meal.calories}"/>
    <br/>

    <input type="hidden" name="mealId" value="${meal.id}">

    <input type="submit" value="Submit"/>
</form>

</body>
</html>