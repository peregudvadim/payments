<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><spring:message code="login.authorization"/></title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .alert-error {
            color: red;
            font-weight: bold;
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
            <h2 class="text-center"><spring:message code="login.welcome"/></h2>
            <p class="text-center"><spring:message code="login.please"/><a href="register"><spring:message
                    code="login.reg"/></a></p>
            <c:if test="${not empty message}">
                <div class="alert alert-info alert-error" role="alert">
                        ${message}
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-error" role="alert">
                        ${error}
                </div>
            </c:if>
            <form action="login" method="post">
                <div class="form-group">
                    <label for="phone"><spring:message code="login.phone.number"/></label>
                    <input type="text" class="form-control" id="phone" name="phone"
                           placeholder="<spring:message code="login.enter.phone"/>">
                </div>
                <div class="form-group">
                    <label for="password"><spring:message code="login.password"/></label>
                    <input type="password" class="form-control" id="password" name="password"
                           placeholder="<spring:message code="login.enter.password"/>">
                </div>
                <button type="submit" class="btn btn-primary btn-block"><spring:message code="login.enter"/></button>
            </form>
            <div class="text-center mt-3">
                <a href="register"><spring:message code="login.registration"/></a>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
