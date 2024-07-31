<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Список курсов</title>
    <style>
        <%@include file="/WEB-INF/css/conf.css" %>
    </style>

</head>
<body>

<h2>Доступные курсы</h2>

<c:forEach var="task" items="${requestScope.courses}">
    <ul>
        Название: <c:out value="${task.name}"/> <br>

        <form method="get" action="<c:url value='/update_course'/>">
            <input type="number" hidden name="id" value="${task.id}"/>
            <input type="submit" value="Редактировать"/>
        </form>
        <form method="post" action="<c:url value='/delete_course'/>">
            <input type="number" hidden name="id" value="${task.id}"/>
            <input type="submit" name="delete" value="Удалить"/>
        </form>
        <form method="get" action="<c:url value='/update_executor'/>">
            <input type="number" hidden name="id" value="${task.id}"/>
            <input type="submit" name="change_executor" value="Сменить исполнителя"/>
        </form>

    </ul>
    <hr/>

</c:forEach>

<a href="<c:url value="/showalltasks"/>">
    <img src="<%=request.getContextPath()%>/images/showAllTasks.png" alt="Logo" width="120" height="60"></a>
</a>

<h2>Создание новой задачи</h2>
<form method="post" action="<c:url value='/add_task'/>">

    <label>Название <input type="text" name="title" required></label><br>
    <label>Описание <input type="text" name="description" required></label><br>
    <label>Срок выполнения <input type="date" name="deadline_date" required></label><br>
    <label>Исполнитель <input type="text" name="username" required></label><br>
    <label>Выполнена <input type="checkbox" name="done" ></label><br>

    <input type="submit" value="Ok" name="Ok"><br>
</form>

<h2>Получить JSON задачи по ID</h2>
<form method="get" action="<c:url value='/get_task'/>">

    <label>ID задачи <input type="number" name="id"></label><br>

    <input type="submit" value="Получить данные задачи" name="Ok"><br>
</form>

<br><br>


<a href="<c:url value="/logout"/>">
    <img src="<%=request.getContextPath()%>/images/logout-button.png" alt="Logo" width="120" height="60"></a>

</body>
</html>