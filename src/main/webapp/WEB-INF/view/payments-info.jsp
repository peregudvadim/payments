<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Payments</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <style>
    .log-container {
      white-space: pre-wrap;
      font-weight: bold;
    }
    .log-entry {
      border-bottom: 1px solid #ddd;
      padding: 2.5px 0;
    }
  </style>
</head>
<body>
<c:import url="/WEB-INF/view/admin-menu.jsp"/>
<div class="container mt-4">
  <section>
    <c:if test="${not empty payments}">
      <div class="log-container">
        <div class="log-entry">
          Name: ${payments[0].name} <br>
          Email: ${payments[0].email} <br>
          Phone: ${payments[0].phone} <br>
        </div>
        <table class="table table-striped mt-4">
          <thead>
          <tr>
            <th>Amount</th>
            <th>Payment Date</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="payment" items="${payments}">
            <tr>
              <td>${payment.amount}</td>
              <td>${payment.paymentDate}</td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </c:if>

    <c:if test="${empty payments}">
      <div class="alert alert-info" role="alert">
        Платежи не найдены.
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
