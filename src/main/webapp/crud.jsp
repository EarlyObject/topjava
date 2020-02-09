<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="color" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<link rel="stylesheet" href="jquery.datetimepicker.min.css">
<script src="jquery.js"></script>
<script src="jquery.datetimepicker.full.js"></script>
<%--<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src=https://code.jquery.com/jquery-1.12.4.js></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>--%>

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

    Дата: <input type="text" id="datetime" name="date"
        <%--value="<c:out value="${meal.getDateTime()}" />"--%> /> <br />
    Описание: <input type="text" name="description"
        value="<c:out value="${meal.getDescription()}" />" /> <br />
    Калории: <input type="text" name="calories"
        value="<c:out  value="${meal.getCalories()}"/>" /><br />

    <input
        type="submit" value="Submit" />
</form>
</body>
</html>
