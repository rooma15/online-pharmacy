<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set scope="session" value="${locale}" var="loc"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-cache">
    <link href="../css/style.css" type="text/css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <script src="../js/script.js" type="text/javascript"></script>
    <title>doctor page</title>
</head>
<body>
<c:import url="../WEB-INF/jsp/header.jsp"></c:import>

<div class="container mt-5 prescription-blog">
    <h2><fmt:message key="doctor.notAccepted"/> </h2>
    <c:forEach var="notAccepted" items="${notAccepted}">
        <div class="row mb-4">
            <div class="col-6 user-item">
                    ${notAccepted.medicineName} ${notAccepted.medicineConsistency} ${notAccepted.medicineDose}<br>
                    ${notAccepted.userLogin} ${notAccepted.userName}<br>
            </div>
            <div class="col-1 cross-doctor">
                <a href="${pageContext.request.contextPath}/Controller?action=doctor_edit_prescriptions&method=delete&id=${notAccepted.id}">
                    <img src="../img/cross.png" height="50px" width="50px">
                </a>
            </div>
            <div class="col-1">
                <a href="${pageContext.request.contextPath}/Controller?action=doctor_edit_prescriptions&method=accept&id=${notAccepted.id}">
                    <img src="../img/yes.png" height="50px" width="50px">
                </a>
            </div>
        </div>
    </c:forEach>
</div>
<div class="container prescription-blog">
    <h2><fmt:message key="doctor.accepted"/> </h2>
    <c:forEach var="accepted" items="${accepted}">
        <div class="row mb-4">
            <div class="col-6 user-item">
                <input type="hidden" name="id" value="${accepted.id}">
                    ${accepted.medicineName} ${accepted.medicineConsistency} ${accepted.medicineDose}<br>
                    ${accepted.userLogin} ${accepted.userName}<br>
            </div>
            <div class="col-1 cross-doctor">
                <a href="${pageContext.request.contextPath}/Controller?action=doctor_edit_prescriptions&method=delete&id=${accepted.id}">
                    <img src="../img/cross.png" height="50px" width="50px">
                </a>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
