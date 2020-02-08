<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="color" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link type="text/css"
          href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>

    <title>Добавить блюдо</title>
</head>


<header id="main-header">

    <a id="user-profile-link" href="users.jsp">User профиль</a>
    <a id="meals-link" href="meals.jsp">Моя еда</a>

</header>

<body bgcolor="#F0F8FF">
<script>
    $(function() {
        $('input[name=dob]').datepicker();
    });
</script>

<form method="POST" action='CrudServlet' name="frmAddMeal">
    Date : <input type="date" name="date"
                     value="<c:out value="${meal.dateTime}" />" /> <br />
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br />

    <input
        type="submit" value="Submit" />
</form>

</body>
</html>
