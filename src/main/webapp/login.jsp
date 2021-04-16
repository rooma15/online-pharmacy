<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set scope="session" value="${locale}" var="loc"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <link href="css/style.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="js/script.js" type="text/javascript"></script>
    <title>login page</title>
</head>
<body>
<c:import url="WEB-INF/jsp/header.jsp"></c:import>
<div class="container" style="text-align: center;">
    <h2><fmt:message key="login.log"/> </h2>
    <div class="row justify-content-center">
        <div class="col-5 login-content">
            <form class="form" action="${pageContext.request.contextPath}/Controller?action=login" method="post">
                <label class="login-form-label"><fmt:message key="login.login"/></label><br>
                <input class="login-input" type="text" name="login" required><br>
                <label class="login-form-label"><fmt:message key="login.password"/></label><br>
                <input class="login-input" type="password" name="password" minlength="8" required><br>
                <input class="login-input login-button" type="submit" value="<fmt:message key="login.logInButton"/>"><br>
                <a href="register.jsp" class="register-href"><fmt:message key="login.reg"/></a><br>
                <p class="errors"><c:out value="${error}"></c:out></p>
            </form>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
</body>
</html>
