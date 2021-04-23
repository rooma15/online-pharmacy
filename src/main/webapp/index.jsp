<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="p" uri="pagination" %>
<html>
<c:set scope="session" value="${locale}" var="loc"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="pagecontent"/>
<head>
    <meta http-equiv="Cache-Control" content="no-cache">
    <link href="css/style.css" type="text/css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <script src="js/script.js" type="text/javascript"></script>
    <title>main page</title>
</head>
<body>

<c:import url="WEB-INF/jsp/header.jsp"/>
<p:paginationContent onPage="3" items="${allItems}"/>
<div class="container-fluid main-content">
    <div class="row">
        <div class="col-2 text-left">
            <form action="/Controller?action=filter" method="get" id="filterForm">
                <label class="filter-block-label"><fmt:message key="admin.medicine.category"/></label>
                <div class="filter-block">
                    <input type="checkbox" name="category" value="антибиотики"> <fmt:message
                        key="medicine.antibiotics"/><br><br>
                    <input type="checkbox" name="category" value="витамины"> <fmt:message
                        key="medicine.vitamins"/><br><br>
                    <input type="checkbox" name="category" value="гормональные"> <fmt:message key="medicine.hormonal"/>
                </div>

                <label class="filter-block-label"><fmt:message key="admin.medicine.consistency"/> </label>
                <div class="filter-block">
                    <input type="checkbox" name="consistency" value="мазь"> <fmt:message
                        key="medicine.ointment"/><br><br>
                    <input type="checkbox" name="consistency" value="таблетки"> <fmt:message
                        key="medicine.pills"/><br><br>
                    <input type="checkbox" name="consistency" value="капли"> <fmt:message key="medicine.drops"/>
                </div>
                <label class="filter-block-label"><fmt:message key="medicine.recipe"/> </label>
                <div class="filter-block">
                    <input type="radio" name="isRecipe" value="true"> <fmt:message key="medicine.recipe"/><br><br>
                    <input type="radio" name="isRecipe" value="false"> <fmt:message key="medicine.noRecipe"/>
                </div>
                <input type="submit" value="Фильтр" class="filter-button">
            </form>
        </div>
        <div class="col-7">
            <c:forEach var="medicines" items="${paginatedItems}">
                <div class="row medicine-elem ml-5">
                    <div class="col-3">
                        <a href="${pageContext.request.contextPath}/Controller?action=show_medicine_page&id=${medicines.id}">
                            <img src="img/${medicines.path}" alt="medicine">
                        </a>
                    </div>
                    <div class="col-6">
                        <c:if test="${medicines.prescriptionDrug == false}">
                            <div class="prescription-plug"></div>
                        </c:if>
                        <c:if test="${medicines.prescriptionDrug == true}">
                            <div class="recipe"><fmt:message key="medicine.recipe"/></div>
                        </c:if>
                        <c:out value="${medicines.name}"></c:out><br>
                        <c:out value="${medicines.dose}"></c:out> мг<br>
                        <c:out value="${medicines.category}"></c:out><br>
                        <c:out value="${medicines.consistency}"></c:out>
                    </div>
                    <div class="col-3 pt-3">
                        <span class="price-text"><fmt:message key="index.price"/> </span> <span class="price">
                <c:out value="${medicines.price}"></c:out></span><br>
                        <a href="${pageContext.request.contextPath}/Controller?action=show_medicine_page&id=${medicines.id}">
                            <button class="buy-button"><fmt:message key="index.more"/></button>
                        </a>
                    </div>
                </div>
            </c:forEach>
            <div class="pagination-tab ml-5">
                <p:paginationTab onPage="3" items="${allItems}"/>
            </div>
        </div>
    </div>
</div>

<c:if test="${empty allItems}">
    <h1 class="text-center no-goods"><fmt:message key="index.noGoods"/></h1>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
</body>
</html>
