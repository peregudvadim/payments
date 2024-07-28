<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <style>
        .menu-container {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 10px;
            background-color: #343a40;
            padding: 10px;
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
        }

        .menu-items {
            display: flex;
            align-items: center;
            gap: 10px;
            flex: 1;
        }

        .menu-item {
            color: white;
            text-align: center;
            padding: 10px;
            background-color: #495057;
            border-radius: 4px;
            text-decoration: none;
        }

        .menu-item:hover {
            background-color: #6c757d;
        }

        .local-container {
            display: flex;
            gap: 0;
        }

        .local-container .menu-item {
            margin: 0;
        }

        .user-info {
            color: white;
            text-align: center;
            padding: 10px;
            background-color: #495057;
            border-radius: 4px;
            margin-left: 10px;
        }

        .user-info div {
            margin-bottom: 5px;
        }

        .message-container {
            color: white;
            text-align: center;
            background-color: #495057;
            padding: 10px;
            border-radius: 4px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        body {
            padding-top: 100px;
        }

        .menu-right {
            display: flex;
            gap: 10px;
        }
    </style>
</head>

<body>
<div class="menu-container">
    <div class="menu-items">
        <c:if test="${account.name eq null}">
            <div class="local-container">
                <a href="changeLocale?lang=ru" class="menu-item"><spring:message code="local.ru"/></a>
                <a href="changeLocale?lang=en" class="menu-item"><spring:message code="local.en"/></a>
            </div>
        </c:if>

        <c:if test="${not(account.name eq null)}">
            <div class="user-info">
                    ${account.name}
            </div>
        </c:if>

        <c:if test="${not(account.email eq null)}">
            <div class="user-info">
                    ${account.email}
            </div>
        </c:if>

        <c:if test="${not(account.name eq null)}">
            <div class="user-info">
                <a href="signOut"><spring:message code="menu.signout"/></a>
            </div>
        </c:if>

        <c:if test="${not (param.message eq null)}">
            <div class="message-container">
                <div>
                    <strong><spring:message code="menu.message"/></strong> <spring:message code="${param.message}"/>
                </div>
            </div>
        </c:if>
    </div>

    <div class="menu-right">
        <a href="warn" class="menu-item"><spring:message code="menu.warn"/></a>
        <a href="error" class="menu-item"><spring:message code="menu.error"/></a>
    </div>
</div>
</body>
</html>
