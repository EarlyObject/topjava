<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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



        <c:set var="caloriesValue" value="${meal.getCalories()}"/>
        <c:if test="${caloriesValue > 900}">
            <tr style="color: red">
                <td>${meal.getDateTime() }</td>
                <td>${meal.getDescription()}</td>
                <td>${meal.getCalories() }</td>
            </tr>
        </c:if>

        <c:if test="${caloriesValue < 900}">
            <tr style="color: green">
                <td>${meal.getDateTime() }</td>
                <td>${meal.getDescription()}</td>
                <td>${meal.getCalories() }</td>
            </tr>
        </c:if>

    </c:forEach>

</table>
</body>
