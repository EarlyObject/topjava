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
    <title>Добавить блюдо</title>
</head>

<header id="main-header">
    <a id="user-profile-link" href="users.jsp">User профиль</a>
    <a id="meals-link" href="meals.jsp">Моя еда</a>
</header>
<hr>

<body>
<form method="POST" action="CrudServlet" name="frmAddMeal">

    <script>
        $( function() {
        $("#datetime").datetimepicker(
        );
        });
    </script>

    Дата: <input type="text" id="datetime" name="date"/> <br />
    Описание: <input type="text" name="description"
        value="<c:out value="${meal.getDescription()}" />" /> <br />
    Калории: <input type="text" name="calories"
        value="<c:out  value="${meal.getCalories()}"/>" /><br />

    <input
        type="submit" value="Submit" />
</form>
</body>
</html>
