<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Application Logs</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Дополнительные стили для улучшения читаемости логов */
        .log-container {
            white-space: pre-wrap; /* Сохранение пробелов и переносов строк */
            font-weight: bold; /* Жирный шрифт */
        }

        .log-entry {
            border-bottom: 1px solid #ddd;
            padding: 2.5px 0; /* Уменьшение расстояния между строками в 2 раза (с 5px до 2.5px) */
        }
    </style>
</head>
<body>
<c:import url="/WEB-INF/view/admin-menu.jsp"/>
<div class="container mt-4">
    <section>
        <c:if test="${not empty fileContent}">
            <div class="log-container">
                <c:forEach var="line" items="${fileContent}">
                    <div class="log-entry">${line}</div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty fileContent}">
            <div class="alert alert-info" role="alert">
                Logs not found or file is empty.
            </div>
        </c:if>

        <a href="${pageContext.request.contextPath}/admin-info" class="btn btn-primary mt-3">Back to Admin Info</a>
    </section>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
