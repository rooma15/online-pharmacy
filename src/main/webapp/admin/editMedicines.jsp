<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set scope="session" value="${locale}" var="loc"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title> medicine page</title>
    <link href="../css/style.css" rel="stylesheet">
    <meta http-equiv="Cache-Control" content="no-cache">
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <script src="../js/script.js" type="text/javascript"></script>
</head>
<body>
<c:import url="../WEB-INF/jsp/header.jsp"></c:import>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-4">
            <a href="/Controller?action=admin_show_all_medicines">
                <div class="medicine-choice" id="delete_medicine">
                    <fmt:message key="admin.medicine.deleteMed"/>
                </div>
            </a>
        </div>
        <div class="col-2">
            <a href="/Controller?action=admin_show_create_medicine_page">
                <div class="medicine-choice">
                    <fmt:message key="admin.medicine.createMed"/>
                </div>
            </a>
        </div>
    </div>
    <div class="row justify-content-center mt-5">
        <div class="col-4 edit-medicine-type" id="create-medicine">
            <p>${error}</p>
            <input type="hidden" value="${choice}" id="choice">
            <form action="${pageContext.request.contextPath}/Controller?action=admin_add_medicine" method="post"
                  class="create-medicine-form" enctype="multipart/form-data">
                <label class="login-form-label"> <fmt:message key="admin.medicine.name"/></label><br>
                <input type="text" class="mb-3" name="name" required><br>
                <label class="login-form-label"> <fmt:message key="admin.medicine.dose"/></label><br>
                <input type="text" class="mb-3" name="dose" required><br>
                <label class="login-form-label"> <fmt:message key="medPage.description"/></label><br>
                <textarea type="text" class="mb-3" name="description" required></textarea><br>
                <label class="login-form-label"><fmt:message key="medPage.indicationsForUse"/></label><br>
                <textarea type="text" class="mb-3" name="indicationsForUse" required></textarea><br>
                <span><fmt:message key="medicine.recipe"/> : </span>
                <input type="checkbox" class="mb-3" name="prescriptionDrug"><br>
                <label class="login-form-label"> <fmt:message key="medPage.contraindications"/></label><br>
                <textarea type="text" class="mb-3" name="contraindications" required></textarea><br>
                <label class="login-form-label"><fmt:message key="medPage.sideEffects"/></label><br>
                <textarea type="text" class="mb-3" name="sideEffects" required></textarea><br>
                <label class="login-form-label"> <fmt:message key="admin.medicine.consistency"/></label><br>
                <select name="consistency" class="mb-3" required>
                    <c:forEach var="elem" items="${consistencies}">
                        <option>${elem}</option>
                    </c:forEach>
                </select><br>
                <label class="login-form-label"> <fmt:message key="medPage.composition"/></label><br>
                <textarea type="text" name="composition" class="mb-3" required></textarea><br>
                <label class="login-form-label"> <fmt:message key="medPage.price"/></label><br>
                <input type="text" name="price" class="mb-3" required><br>
                <label class="login-form-label"> <fmt:message key="admin.medicine.category"/></label><br>
                <select name="category" class="mb-3" required>
                    <c:forEach var="elem" items="${categories}">
                        <option>${elem}</option>
                    </c:forEach>
                </select><br>
                <input type="file" name="file" />
                <input type="submit" value="<fmt:message key="admin.medicine.addMed"/>"><br>
            </form>
        </div>
        <div class="all-medicines-list col-6" id="update-delete-medicine">
            <c:forEach var="medicine" items="${medicines}">
                <div class="row justify-content-center mb-3 border-bottom">
                    <div class="col-10">
                        <div class="admin-medicine-item">
                                ${medicine.name}<br>
                                ${medicine.consistency}<br>
                                ${medicine.dose}<br>
                        </div>
                    </div>
                    <div class="col">
                        <div class="icons">
                            <a href="/Controller?action=admin_delete_medicine&id=${medicine.id}">
                                <img src="../img/cross.png" width="20" height="20">
                            </a>
                            <a href="/Controller?action=admin_show_update_medicine_page&id=${medicine.id}">
                                <img src="../img/pencil.png" width="20" height="20">
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="col-4 edit-medicine-type" id="update-medicine">
            <p>${error}</p>
            <form action="${pageContext.request.contextPath}/Controller?action=admin_update_medicine&id=${medicine.id}"
                  method="post" class="update-medicine-form" enctype="multipart/form-data">
                <label class="login-form-label"> <fmt:message key="admin.medicine.name"/></label><br>
                <input type="text" class="mb-3" name="name" value="${medicine.name}" required><br>
                <label class="login-form-label"> <fmt:message key="admin.medicine.dose"/></label><br>
                <input type="text" class="mb-3" name="dose" value="${medicine.dose}" required><br>
                <label class="login-form-label"> <fmt:message key="medPage.description"/></label><br>
                <textarea type="text" class="mb-3" name="description" required>${medicine.description}</textarea><br>
                <label class="login-form-label"><fmt:message key="medPage.indicationsForUse"/></label><br>
                <textarea type="text" class="mb-3" name="indicationsForUse"
                          required>${medicine.indicationsForUse}</textarea><br>
                <span><fmt:message key="medicine.recipe"/> : </span>
                <input type="checkbox" class="mb-3" name="prescriptionDrug"><br>
                <label class="login-form-label"><fmt:message key="medPage.contraindications"/></label><br>
                <textarea type="text" class="mb-3" name="contraindications"
                          required>${medicine.contraindications}</textarea><br>
                <label class="login-form-label"><fmt:message key="medPage.sideEffects"/></label><br>
                <textarea type="text" class="mb-3" name="sideEffects" required>${medicine.sideEffects}</textarea><br>
                <label class="login-form-label"> <fmt:message key="admin.medicine.consistency"/></label><br>
                <select name="consistency" class="mb-3" required>
                    <c:forEach var="elem" items="${consistencies}">
                        <option>${elem}</option>
                    </c:forEach>
                </select><br>
                <label class="login-form-label"> <fmt:message key="medPage.composition"/></label><br>
                <textarea type="text" name="composition" class="mb-3" required>${medicine.composition}</textarea><br>
                <label class="login-form-label"> <fmt:message key="medPage.price"/></label><br>
                <input type="text" name="price" class="mb-3" value="${medicine.price}" required><br>
                <label class="login-form-label"> <fmt:message key="admin.medicine.category"/></label><br>
                <select name="category" class="mb-3" required>
                    <c:forEach var="elem" items="${categories}">
                        <option>${elem}</option>
                    </c:forEach>
                </select><br>
                <input type="file" name="file" />
                <input type="submit" value="<fmt:message key="admin.medicine.update"/>"><br>
            </form>
        </div>
    </div>
</div>
</body>
</html>
