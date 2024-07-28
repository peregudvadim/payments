<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Account Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<c:import url="/WEB-INF/view/menu.jsp"/>
<div class="container mt-5">
    <h2>Account Details</h2>
    <div class="card mb-3">
        <div class="card-body">
            <h5 class="card-title">Account Status: <span class="badge badge-info">${account.status}</span></h5>
            <p class="card-text"><strong>Name:</strong> ${account.name}</p>
            <p class="card-text"><strong>Email:</strong> ${account.email}</p>
            <p class="card-text"><strong>Phone:</strong> ${account.phone}</p>
            <p class="card-text"><strong>Balance:</strong> $${account.balance}</p>
        </div>
    </div>
    <c:if test="${not empty message}">
        <div class="alert alert-info" role="alert">
               ${message}
        </div>
    </c:if>
    <c:if test="${not (account.status eq 'BLOCKED')}">
        <div class="row">
            <div class="col">
                <!-- Форма для перехода на страницу с кредитными картами -->
                <form action="viewCreditCards" method="get" style="display: inline;">
                    <button type="submit" class="btn btn-primary">View Credit Cards</button>
                </form>
            </div>
            <div class="col text-right">
                <!-- Форма для блокировки аккаунта -->
                <form action="blockAccount" method="post" style="display: inline;">
                    <button type="submit" class="btn btn-danger">Block Account</button>
                </form>
            </div>
        </div>

    <br><br><br><br><br>
    <!-- Add Money Form -->
    <h3>Rebalance</h3>
    <form action="addMoney" method="post" class="mb-3">
        <div class="form-group">
            <label for="amount">Amount</label>
            <input type="number" step="0.01" class="form-control" id="amount" name="amount" placeholder="Enter amount">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    </c:if>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

