<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set scope="session" value="${locale}" var="loc"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-cache">
    <link href="css/style.css" type="text/css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <script src="js/script.js" type="text/javascript"></script>
    <title>History page</title>
</head>
<body>
<c:import url="WEB-INF/jsp/header.jsp"></c:import>
<div class="container mt-4">
    <c:forEach var="order" items="${orders}">
        <div class="order">
            <span class="order-text">
                Order number: ${order.key.id}
            </span>
            <span class="order-text float-right">
                Date: ${order.key.orderDate}
            </span>
            <c:forEach var="orderItems" items="${order.value}">
                <div class="row mb-3 order-item justify-content-center">
                    <div class="col-6">
                        <h3>
                            <a href="/Controller?action=show_medicine_page&id=${orderItems.medicineId}" style="color: white">
                                    ${orderItems.name}
                            </a>
                        </h3><br>
                            ${orderItems.dose} <fmt:message key="medPage.dose"/> <br>
                            ${orderItems.consistency}<br>
                    </div>
                    <div class="col-2">
                        <fmt:message key="medPage.amount"/> : ${orderItems.amount}<br>
                        <fmt:message key="cart.summary"/>: ${orderItems.price}
                    </div>
                </div>
            </c:forEach>
            <span class="order-text">
                Total Price: ${order.key.orderPrice}
            </span>
        </div>
    </c:forEach>
    <c:if test="${empty orders}">
        <h1 class="text-center"><fmt:message key="index.noGoods"/></h1>
    </c:if>
</div>
</body>
</html>
