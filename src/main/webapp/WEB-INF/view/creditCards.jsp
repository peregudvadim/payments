<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Credit Cards</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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
    <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert">
                ${message}
        </div>
    </c:if>

    <h2>Account Balance: <fmt:formatNumber value="${account.balance}" type="currency" currencySymbol="$"/></h2>

    <a href="addCreditCard" class="btn btn-success mb-3">Add Credit Card</a>

    <c:forEach var="creditCard" items="${account.creditCards}">
        <div class="card mb-3">
            <div class="card-body">
                <h5 class="card-title">
                    Card Status:
                    <c:choose>
                        <c:when test="${creditCard.status eq 'ACTIVE'}">
                            <span class="badge badge-primary">${creditCard.status}</span>
                        </c:when>
                        <c:when test="${creditCard.status eq 'BLOCKED'}">
                            <span class="badge badge-danger">${creditCard.status}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-secondary">${creditCard.status}</span>
                        </c:otherwise>
                    </c:choose>
                </h5>
                <div class="row">
                    <div class="col">
                        <strong>Number:</strong> ${creditCard.cardNumber.substring(0, 4)}********${creditCard.cardNumber.substring(12)}<br>
                        <strong>Expiry Date:</strong> <fmt:formatDate value="${creditCard.expirationDate}" pattern="yyyy-MM-dd"/><br>
                        <strong>Balance:</strong> <fmt:formatNumber value="${creditCard.balance}" type="currency" currencySymbol="$"/>
                    </div>
                    <c:if test="${not (creditCard.status eq 'BLOCKED')}">
                        <div class="col text-right">
                            <form action="addMoneyToCard" method="post" class="form-inline">
                                <input type="hidden" name="cardId" value="${creditCard.cardId}">
                                    <div class="form-group">
                                    <label for="amount-${creditCard.cardId}" class="sr-only">Sum</label>
                                    <input type="number" min="0.01" step="0.01" class="form-control mr-2" id="sum-${creditCard.cardId}" name="amount" placeholder="amount" required>
                                    <button type="submit" class="btn btn-primary">Add Money</button>
                                </div>
                            </form>
                        </div>
                    </c:if>
                </div>
                <c:if test="${not (creditCard.status eq 'BLOCKED')}">
                    <form action="processPayment" method="post">
                        <input type="hidden" name="creditCardId" value="${creditCard.cardId}">
                            <div class="form-group">
                            <label for="amount-${creditCard.cardId}">Amount</label>
                            <input type="number" min="0.01" step="0.01" class="form-control" id="amount-${creditCard.cardId}" name="amount" required>
                        </div>
                        <div class="form-group">
                            <label for="account-${creditCard.cardId}">Account Number</label>
                            <input type="text" class="form-control" id="account-${creditCard.cardId}" name="account" required>
                        </div>
                        <div class="form-group">
                            <label for="cvv-${creditCard.cardId}">CVV</label>
                            <input type="text" class="form-control" id="cvv-${creditCard.cardId}" name="cvv" pattern="[0-9]{3}" required>
                        </div>
                        <div class="row">
                            <div class="col text-left">
                                <!-- Button to submit payment -->
                                <button type="submit" class="btn btn-primary">Submit Payment</button>
                            </div>
                        </div>
                    </form>
                    <div class="row">
                        <div class="col text-right">
                            <!-- Form to block the card -->
                            <form action="blockCard" method="post" style="display: inline;">
                                <input type="hidden" name="cardId" value="${creditCard.cardId}">
                                <button type="submit" class="btn btn-danger">Block Card</button>
                            </form>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </c:forEach>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
