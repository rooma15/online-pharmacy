<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set scope="session" value="${locale}" var="loc"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link href="../css/style.css" rel="stylesheet">
    <meta http-equiv="Cache-Control" content="no-cache">
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <title>edit users</title>
</head>
<body>
<c:import url="../WEB-INF/jsp/header.jsp"></c:import>

<div class="container-fluid mt-5">
    <div class="row justify-content-center">
        <div class="col-6">
            <h2><fmt:message key="admin.edit.header"/> </h2>
            <p>${error}</p>
            <form action="/Controller?action=admin_register_user" method="post" class="add-user-form">
                <label class="login-form-label"><fmt:message key="register.name"/> </label><br>
                <input type="text" class="mb-5" name="name" required><br>
                <label class="login-form-label"><fmt:message key="register.login"/> </label><br>
                <input type="text" class="mb-5" name="login" required><br>
                <label class="login-form-label"><fmt:message key="register.password"/> </label><br>
                <input type="text" class="mb-5" name="password" minlength="8" required><br>
                <label class="login-form-label"><fmt:message key="admin.edit.userType"/></label><br>
                <select name="role" class="mb-5" required>
                    <c:forEach var="role" items="${roles}">
                        <option>${role}</option>
                    </c:forEach>
                </select><br>
                <input type="submit" value="<fmt:message key="admin.edit.addUser"/>">
                <p class="errors">
                    <c:forEach items="${errors}" var="error">
                        ${error}<br>
                    </c:forEach>
                </p>
            </form>
        </div>
        <div class="col-3">
            <h2><fmt:message key="admin.edit.userList"/></h2>
            <div class="user-list">
                <c:forEach var="user" items="${users}">
                    <div class="user-item mb-4">${user.login}(${user.role})
                        <a href="/Controller?action=admin_delete_user&login=${user.login}">
                            <img src="../img/cross.png" width="20" height="20">
                        </a><br>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
</body>
</html>
