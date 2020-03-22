<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<hr/>
<h2><spring:message code="meal.title"/></h2>
<form method="get" action="meals">
    <input type="hidden" name="action" value="filter">
    <dl>
        <dt><spring:message code="meal.fromDate"/>:</dt>
        <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
    </dl>
    <dl>
        <dt><spring:message code="meal.toDate"/>:</dt>
        <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
    </dl>
    <dl>
        <dt><spring:message code="meal.fromTime"/>:</dt>
        <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
    </dl>
    <dl>
        <dt><spring:message code="meal.toTime"/>:</dt>
        <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
    </dl>
    <button type="submit"><spring:message code="meal.filter"/></button>
</form>
<hr/>

<a href="meals?action=create">
    <button><spring:message code="meal.addMeal"/></button>
</a>


<section>
    <h3><spring:message code="meal.title"/></h3>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="meal.date"/></th>
            <th><spring:message code="meal.description"/></th>
            <th><spring:message code="meal.calories"/></th>
            <%--            <th></th>--%>
            <%--            <th></th>--%>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr data-mealExcess="${meal.excess}">
                <td>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><a href="meals?action=update&id=${meal.id}">
                    <button><spring:message code="meal.update"/></button>
                </a></td>
                <td>
                    <form action="<c:url value='meals/delete/${meal.id}'/>" method="post">
                        <button><spring:message code="meal.delete"/></button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

