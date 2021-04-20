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
    <title>cart</title>
</head>
<body>
<c:import url="WEB-INF/jsp/header.jsp"></c:import>
<div class="container mt-4">
    <c:forEach var="items" items="${order_items}">
        <div class="row mb-3 order-item justify-content-center">
            <div class="col-3">
                <a href="/Controller?action=delete_order_item&id=${items.id}" class="delete-order-item-button"><fmt:message key="cart.delete"/> </a>
            </div>
            <div class="col-6">
                <h3>${items.medicineName}</h3><br>
                    ${items.medicineDose} <fmt:message key="medPage.dose"/> <br>
                    ${items.medicineConsistency}<br>
            </div>
            <div class="col-2">
                    <fmt:message key="medPage.amount"/> : ${items.amount}<br>
                <fmt:message key="cart.summary"/>: ${items.price}
            </div>
        </div>
    </c:forEach>
    <c:if test="${not empty order_items and not empty user}">
        <a href="/Controller?action=show_payment_page&method=card" class="order-items-button"><fmt:message key="cart.order.byCard"/></a>
    </c:if>
    <c:if test="${empty order_items}">
        <h1 class="text-center"><fmt:message key="index.noGoods"/> </h1>
    </c:if>
</div>
</body>
</html>
