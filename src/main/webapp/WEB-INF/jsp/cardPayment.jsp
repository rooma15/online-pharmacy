<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set scope="session" value="${locale}" var="loc"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-cache">
    <link href="../../css/style.css" type="text/css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <script src="../../js/script.js" type="text/javascript"></script>
    <title>Card payment</title>
</head>
<body>
<c:import url="header.jsp"/>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-auto justify-content-center">
                <form action="/Controller?action=make_order" method="post" class="card-payment-form">
                    <input type="hidden" value="card" name="method">
                    <label>
                        <span><fmt:message key="payment.cardNumber"/> </span><br>
                        <input type="text" name="cardNumber" required minlength="19" maxlength="19" oninput="cardNumberFormatting()">
                    </label>
                    <br>
                    <label>
                        <span><fmt:message key="payment.name"/> </span><br>
                        <input type="text" name="name" required>
                    </label>
                    <br>
                    <label>
                        <span><fmt:message key="payment.cvv"/> </span><br>
                        <input type="password" name="cvv" required minlength="3" maxlength="3">
                    </label>
                    <br>
                    <input type="submit" value="<fmt:message key="cart.order.byCard"/>"><br>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
