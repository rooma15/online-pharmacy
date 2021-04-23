<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set scope="session" value="${locale}" var="loc"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="pagecontent"/>
<div class="header container-fluid">
    <div class="container">
        <div class="row header-block align-items-center">
            <c:if test="${not empty user}">
            <div class="col-2 justify-content-center">
                </c:if>
                <c:if test="${empty user}">
                <div class="col-5 justify-content-center">
                    </c:if>

                    <div class="logo">
                        <a href="../../index.jsp">Pharmacy</a>
                    </div>
                </div>
                <c:if test="${not empty user}">
                    <div class="col-2">
                        <a href="/Controller?action=show_order_history" class="header-buttons"><fmt:message
                                key="header.history"/></a>
                    </div>
                </c:if>
                <c:if test="${user.role eq 'doctor'}">
                    <div class="col">
                        <a href="/Controller?action=show_doctor_page" class="header-buttons"><fmt:message
                                key="header.doctor"/> </a>
                    </div>
                </c:if>

                <c:if test="${user.role eq 'pharmacist'}">
                    <div class="col">
                        <a href="../../admin/index.jsp" class="header-buttons"><fmt:message key="header.admin"/> </a>
                    </div>
                </c:if>
                <div class="col justify-content-center">
                    <c:if test="${empty user}">
                        <a href="../../login.jsp" class="header-buttons"><fmt:message key="header.signIn"/> </a>
                    </c:if>
                    <c:if test="${not empty user}">
                        <a href="${pageContext.request.contextPath}/Controller?action=log_out"
                           class="header-buttons"><fmt:message
                                key="header.signOut"/> </a>
                    </c:if>
                </div>
                <div class="col-2 justify-content-center">
                    <a href="/Controller?action=open_cart" class="header-buttons"><fmt:message key="header.cart"/> </a>
                </div>
                <div class="col-2 justify-content-center">
                    <a href="/Controller?action=change_locale&locale=en_US">EN</a>
                    <span> | </span>
                    <a href="/Controller?action=change_locale&locale=ru_RU">RU</a>
                    <span> | </span>
                    <a href="/Controller?action=change_locale&locale=fr_FR">FR</a>
                </div>
            </div>
        </div>
        </div>
    </div>