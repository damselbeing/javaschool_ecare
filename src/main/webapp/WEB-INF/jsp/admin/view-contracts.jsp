<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>View Contracts</title>
    <jsp:include page="../head.jsp"></jsp:include>
</head>
<body>
    <header>
        <jsp:include page="header.jsp"></jsp:include>
    </header>
    <div class="container">
        <form class="form-inline" method="get" action="/admin/searchClientByPassport">
            <label>
                <input type="text" name="passportNumber" class="form-control" placeholder="Enter client's passport">
            </label>
            <button type="submit" class="btn btn-primary">Sign contract</button>
        </form>
    </div>
    <c:if test="${contracts.size() == 0}">
        <h3>Contracts are not found</h3>
    </c:if>
    <c:if test="${contracts.size() > 0}">
    <table class="table">
        <thead>
        <tr>
            <th>Number</th>
            <th>Blocked By Client</th>
            <th>Blocked By Admin</th>
            <th>Client</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${contracts}" var="contract">
            <tr>
                <td>${contract.number}</td>
                <td>${contract.blockedByClient}</td>
                <td>${contract.blockedByAdmin}</td>
                <td>${contract.client}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:if>
</body>
</html>