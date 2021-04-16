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
    <title>admin module</title>
</head>
<body>
<c:import url="../WEB-INF/jsp/header.jsp"></c:import>
<div class="container pt-5">
    <div class="row justify-content-center mt-5 mb-5">
        <div class="col-2">
            <a href="../admin/editMedicines.jsp">
                <div class="admin-button">
                    <fmt:message key="admin.index.medicines"/>
                </div>
            </a>
        </div>
    </div>
    <div class="row justify-content-center mb-5">
        <div class="col-2">
            <a href="../Controller?action=admin_edit_user_page">
                <div class="admin-button">
                    <fmt:message key="admin.index.users"/>
                </div>
            </a>
        </div>
    </div>
</div>
</body>
</html>
