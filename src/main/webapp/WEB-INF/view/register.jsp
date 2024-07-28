<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error {
            color: red;
            font-size: 12px;
            margin-top: 5px;
        }
        .alert {
            padding: .75rem 1.25rem;
            margin-bottom: 1rem;
            border: 1px solid transparent;
            border-radius: .375rem;
        }

        .alert-info {
            color: #0c5460;
            background-color: #d1ecf1;
            border-color: #bee5eb;
        }
    </style>
</head>
<body>
<c:import url="/WEB-INF/view/menu.jsp"/>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h2 class="text-center"><spring:message code="register.registration"/></h2>
            <c:if test="${not empty message}">
                <div class="alert alert-info" role="alert">
                        ${message}
                </div>
            </c:if>
            <spring:message code="register.enter.name" var="placeholderName"/>
            <spring:message code="register.enter.email" var="placeholderEmail"/>
            <spring:message code="register.enter.phone" var="placeholderPhone"/>
            <spring:message code="register.enter.password" var="placeholderPassword"/>
            <form:form method="post" modelAttribute="client" action="registerProcess" class="registration-form">
                <div class="form-group">
                    <label for="name"><spring:message code="register.name"/></label>
                    <form:input path="name" id="name" class="form-control" placeholder="${placeholderName}" />
                    <form:errors path="name" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label for="email"><spring:message code="register.email"/></label>
                    <form:input path="email" id="email" class="form-control" placeholder="${placeholderEmail}" />
                    <form:errors path="email" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label for="phone"><spring:message code="register.phone.number"/></label>
                    <form:input path="phone" id="phone" class="form-control" placeholder="${placeholderPhone}" />
                    <form:errors path="phone" cssClass="error"/>
                </div>
                <div class="form-group">
                    <label for="password"><spring:message code="register.password"/></label>
                    <form:password path="password" id="password" class="form-control" placeholder="${placeholderPassword}" />
                    <form:errors path="password" cssClass="error"/>
                </div>
                <button type="submit" class="btn btn-primary btn-block"><spring:message code="register.signin"/></button>
            </form:form>
            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/"><spring:message code="register.are.you.signin"/></a>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
