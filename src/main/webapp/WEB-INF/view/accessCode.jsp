<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Проверка кода доступа</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <style>
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
            <h2 class="text-center"><spring:message code="access.verification"/></h2>
            <p class="text-center"><spring:message code="access.code"/> (${accessCode}) <spring:message code="access.code2"/>  ${phone}</p>
            <c:if test="${not empty message}">
                <div class="alert alert-info" role="alert">
                        ${message}
                </div>
            </c:if>
            <spring:message code="access.enter" var="placeholderCode"/>
            <form action="verifyCode" method="post">
                <div class="form-group">
                    <label for="code"><spring:message code="access.code3"/> </label>
                    <input type="hidden" name="accessCode" value="${accessCode}">
                    <input type="text" class="form-control" id="code" name="code" placeholder=${placeholderCode}>
                </div>
                <button type="submit" class="btn btn-primary btn-block"><spring:message code="access.submit"/> </button>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
