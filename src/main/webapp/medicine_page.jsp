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
    <title>medicine</title>
</head>
<body>
<c:import url="WEB-INF/jsp/header.jsp"></c:import>

<div class="container">
    <div class="row">
        <div class="col-2">
            <img src="img/${medicine.path}">
        </div>
        <div class="col-6 mt-4">
            <span class="medicine-page-name">${medicine.name} ${medicine.dose} <fmt:message key="medPage.dose"/> </span><br>
            <span class="medicine-page-consistency">${medicine.consistency}</span><br>
        </div>
        <div class="col-4">
            <span class="price-text"><fmt:message key="medPage.price"/> : </span>
            <span class="price">${medicine.price}</span>
            <form name="medicine-page-form" class="medicine-page-form">
                <input type="hidden" value="${medicine.id}" name="id">
                <span class="medicine-page-text mr-2"><fmt:message key="medPage.amount"/> : </span><input type="number" value="1" name="amount"
                                                                                min="1"><br>
                <c:if test="${prescription eq 'not needed'}">
                    <input type="submit" value="<fmt:message key="medPage.buy"/>" class="buy-button-medicine-page">
                </c:if>
                <c:if test="${prescription eq 'needed'}">
                    <div class="buy-button-medicine-page-disabled"><fmt:message key="medPage.recipeNeeded"/></div>
                    <c:if test="${empty user}">
                        <div class="error-alert">
                            <span><fmt:message key="medPage.needToSignIn"/></span>
                        </div>
                    </c:if>
                    <c:if test="${not empty user}">
                        <a href="/Controller?action=create_prescription&medicineId=${medicine.id}&login=${user.login}">
                            <div class="buy-button-medicine-page"><fmt:message key="medPage.orderRecipe"/></div>
                        </a>
                    </c:if>
                    <c:out value="${error}"></c:out>
                </c:if>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h2><fmt:message key="medPage.description"/></h2><br>
            <p class="medicine-page-text">${medicine.description}</p>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h2><fmt:message key="medPage.indicationsForUse"/></h2><br>
            <p class="medicine-page-text">${medicine.indicationsForUse}</p>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h2><fmt:message key="medPage.contraindications"/></h2><br>
            <p class="medicine-page-text">${medicine.contraindications}</p>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h2><fmt:message key="medPage.sideEffects"/></h2><br>
            <p class="medicine-page-text">${medicine.sideEffects}</p>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h2><fmt:message key="medPage.composition"/></h2><br>
            <p class="medicine-page-text">${medicine.composition}</p>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>
</body>
</html>
