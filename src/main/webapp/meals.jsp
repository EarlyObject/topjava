<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="color" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<body bgcolor="#F0F8FF">


<title>Подсчет калорий</title>


<body>

<h3>Калории</h3>
<table>
    <tr>
        <th>Дата / Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>

    <c:forEach var="meal" items="${meals}">

        <tr style="${meal.getCalories() >900 ? "color: red" :  "color: green" }">
            <td>${meal.getDateTime() }</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories() }</td>
        </tr>

    </c:forEach>

</table>
</body>
