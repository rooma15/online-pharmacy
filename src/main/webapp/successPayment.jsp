<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set scope="session" value="${locale}" var="loc"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="pagecontent"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-cache">
    <link href="css/style.css" type="text/css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <script src="js/script.js" type="text/javascript"></script>
    <title>success payment</title>
</head>
<body>
    <c:import url="WEB-INF/jsp/header.jsp"></c:import>
    <h1 class="text-center"><fmt:message key="payment.success"/> </h1>
</body>
</html>
