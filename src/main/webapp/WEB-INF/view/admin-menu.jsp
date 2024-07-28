<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Menu</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
    <style>
        .menu-container {
            display: flex;
            align-items: center;
            justify-content: space-between;
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
            padding-top: 100px; /* Убедитесь, что это значение соответствует высоте меню */
        }

        .menu-right {
            display: flex;
            align-items: center;
            gap: 10px;
        }
    </style>
</head>
<body>
<div class="menu-container">
    <div class="menu-items">
        <c:if test="${not empty admin.name}">
            <div class="user-info">
                    ${admin.name}
            </div>
        </c:if>

        <c:if test="${not empty admin.role}">
            <div class="user-info">
                    ${admin.role}
            </div>
        </c:if>

        <c:if test="${not empty admin.name}">
            <a href="signOut" class="menu-item"><spring:message code="menu.signout"/></a>
        </c:if>

        <c:if test="${not empty param.message}">
            <div class="message-container">
                <div>
                    <strong><spring:message code="menu.message"/></strong> <spring:message code="${param.message}"/>
                </div>
            </div>
        </c:if>
    </div>

    <c:if test="${not empty admin.name}">
        <div class="menu-right">
            <a href="get-logs" class="menu-item"><spring:message code="admin.menu.getlogs"/></a>
        </div>
    </c:if>
</div>
</body>
</html>
