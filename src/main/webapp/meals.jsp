<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Demo Page</title>
</head>
<body>

<h3>Product List</h3>
<c:set var="total" value="0"></c:set>
<table>

<tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach var="meal" items="${meals }">
        <c:set var="total" value="${meal}"></c:set>
        <tr>
            <td>${meal.getCalories() }</td>
            <td>${meal.getDescription() }</td>
            <td>${meal.getCalories() }</td>
        </tr>
    </c:forEach>

<</table>

</body>
</html>