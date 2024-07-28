<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Стили для отображения сообщений */
        .alert-error {
            border: 1px solid #dc3545; /* Красная граница для ошибок */
            background-color: #f8d7da; /* Светлый красный фон */
            color: #721c24; /* Тёмно-красный текст */
        }
        .form-inline .form-control {
            margin-right: 10px; /* Отступы для полей формы */
        }
        .log-container {
            white-space: pre-wrap; /* Сохранение пробелов и переносов строк */
            font-family: monospace; /* Моноширинный шрифт для логов */
        }
        .log-entry {
            border-bottom: 1px solid #ddd;
            padding: 5px 0;
        }
        .list-group-item {
            border: 1px solid #ddd;
            margin-bottom: 5px;
        }
        .form-section {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        .form-section section {
            width: 48%;
        }
    </style>
</head>
<body>
<c:import url="/WEB-INF/view/admin-menu.jsp"/>
<div class="container">
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-error" role="alert">
                ${message}
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-info" role="alert">
                ${error}
        </div>
    </c:if>

    <div class="form-section">
        <section>
            <h2>Get User Account</h2>
            <form action="${pageContext.request.contextPath}/admin-info" method="post" class="form-inline">
                <div class="form-group mb-2">
                    <label for="phone" class="sr-only">Phone Number</label>
                    <input type="text" class="form-control" id="phone" name="phone" placeholder="Enter user phone">
                </div>
                <button type="submit" class="btn btn-primary mb-2">Get Account</button>
            </form>
        </section>
        <section>
            <h2>Get User Payments</h2>
            <form action="${pageContext.request.contextPath}/get-payments" method="post" class="form-inline">
                <div class="form-group mb-2">
                    <label for="phone" class="sr-only">Phone Number</label>
                    <input type="text" class="form-control" name="phone" placeholder="Enter user phone">
                </div>
                <button type="submit" class="btn btn-primary mb-2">Get Payments</button>
            </form>
        </section>
    </div>

    <c:if test="${not empty userAccount}">
        <section>
            <h2>User Account Details</h2>
            <p><strong>Name:</strong> ${userAccount.client.name}</p>
            <p><strong>Account Status:</strong>
            <form action="${pageContext.request.contextPath}/update-account-status" method="post" class="form-inline">
                <select name="accountStatus" class="form-control">
                    <option value="active" ${userAccount.status == 'ACTIVE' ? 'selected' : ''}>Active</option>
                    <option value="blocked" ${userAccount.status == 'BLOCKED' ? 'selected' : ''}>Blocked</option>
                </select>
                <input type="hidden" name="phone" value="${userAccount.client.phone}">
                <input type="hidden" name="accountId" value="${userAccount.accountId}">
                <button type="submit" class="btn btn-primary ml-2">Update Status</button>
            </form>
            </p>
            <h3>Linked Cards</h3>
            <ul class="list-group">
                <c:forEach items="${userAccount.creditCards}" var="card">
                    <li class="list-group-item">
                        <p><strong>Card Number:</strong> ${card.cardNumber}</p>
                        <p><strong>Card Status:</strong>
                        <form action="${pageContext.request.contextPath}/update-card-status" method="post" class="form-inline">
                            <select name="cardStatus" class="form-control">
                                <option value="active" ${card.status == 'ACTIVE' ? 'selected' : ''}>Active</option>
                                <option value="blocked" ${card.status == 'BLOCKED' ? 'selected' : ''}>Blocked</option>
                            </select>
                            <input type="hidden" name="cardId" value="${card.cardId}">
                            <input type="hidden" name="phone" value="${userAccount.client.phone}">
                            <input type="hidden" name="cardNumber" value="${card.cardNumber}">
                            <button type="submit" class="btn btn-primary ml-2">Update Status</button>
                        </form>
                        </p>
                    </li>
                </c:forEach>
            </ul>
        </section>
    </c:if>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
