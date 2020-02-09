<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="color" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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

<body bgcolor="#F0F8FF">
<%--<script>
    $(function() {
        $('input[name=dob]').datepicker();
    });
</script>--%>


<form method="POST" action="CrudServlet" name="frmAddMeal">
    Date : <input type="datetime-local" name="date"
                     value="<c:out value="${meal.date}" />" /> <br />
    Description : <input type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
    Calories : <input type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br />

    <input
        type="submit" value="Submit" />
</form>



</body>
</html>
