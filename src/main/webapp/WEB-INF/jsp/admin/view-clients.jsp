<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>View Clients</title>
    <jsp:include page="../head.jsp"></jsp:include>
</head>
<body>
    <header>
        <jsp:include page="header.jsp"></jsp:include>
    </header>
    <div class="container">
        <form class="form-inline" method="get" action="/admin/searchClientByContract">
            <label>
                <input type="text" name="contractNumber" class="form-control" placeholder="Enter client's contract">
            </label>
            <button type="submit" class="btn btn-primary">Search client</button>
        </form>
    </div>
    <c:if test="${clients.size() == 0}">
        <h3>Clients are not found</h3>
    </c:if>
    <c:if test="${clients.size() > 0}">
    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Last Name</th>
            <th>Birth Date</th>
            <th>Passport</th>
            <th>Address</th>
            <th>Email</th>
            <th>Contract</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clients}" var="client">
            <tr>
                <td>${client.name}</td>
                <td>${client.lastName}</td>
                <td>${client.birthDate}</td>
                <td>${client.passport}</td>
                <td>${client.address}</td>
                <td>${client.email}</td>
                <td>${client.contract.number}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:if>
</body>
</html>