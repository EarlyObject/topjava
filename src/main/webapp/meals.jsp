<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="color" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
<hr>
<section>
    <form>
        <div>
            <label>От даты</label>
            <input type="date" name="Дата" placeholder="01.01.2019">
        </div>
        <label>До даты</label>
        <input type="date" name="до даты" placeholder="31.12.2020">


        <label>От времени</label>
        <select name="время">
            <option value="">01:00</option>
            <option value="">02:00</option>
            <option value="">03:00</option>
            <option value="">04:00</option>
            <option value="">05:00</option>
            <option value="">06:00</option>
            <option value="">07:00</option>
            <option value="">08:00</option>
            <option value="">09:00</option>
            <option value="">10:00</option>
            <option value="">11:00</option>
            <option value="">12:00</option>
            <option value="">13:00</option>
            <option value="">14:00</option>
            <option value="">15:00</option>
            <option value="">16:00</option>
            <option value="">17:00</option>
            <option value="">18:00</option>
            <option value="">19:00</option>
            <option value="">20:00</option>
            <option value="">21:00</option>
            <option value="">22:00</option>
            <option value="">23:00</option>
        </select>
        <label>До времени</label>
        <select name="время">
            <option value="">01:00</option>
            <option value="">02:00</option>
            <option value="">03:00</option>
            <option value="">04:00</option>
            <option value="">05:00</option>
            <option value="">06:00</option>
            <option value="">07:00</option>
            <option value="">08:00</option>
            <option value="">09:00</option>
            <option value="">10:00</option>
            <option value="">11:00</option>
            <option value="">12:00</option>
            <option value="">13:00</option>
            <option value="">14:00</option>
            <option value="">15:00</option>
            <option value="">16:00</option>
            <option value="">17:00</option>
            <option value="">18:00</option>
            <option value="">19:00</option>
            <option value="">20:00</option>
            <option value="">21:00</option>
            <option value="">22:00</option>
            <option value="">23:00</option>
            <option value="">24:00</option>
        </select>


    </form>

</section>


<hr>
<button click="">Отменить</button>
<button click="">Отфильтровать</button>

<br>
<button click="">+Добавить</button>
<br>

<form action="" method="">
    <label>Искать</label>
    <input type="text">
</form>
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
        <tr style="${meal.isExcess()==true ? "color: red" :  "color: green" }">
            <td><fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedEmpDate"/>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedEmpDate}"/></td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
            <td><a href="CrudServlet?action=edit&mealId=${meal.getId()}">Update</a></td>
            <td><a href="CrudServlet?action=delete&mealId=${meal.getId()}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<hr>
<footer id="main-footer">
    <p>
        Приложение стажировки <a href="https://github.com/JavaOPs/topjava" target="_blank">Spring 5/JPA Enterprise
        (Topjava)</a>
    </p>
</footer>
</body>

</html>