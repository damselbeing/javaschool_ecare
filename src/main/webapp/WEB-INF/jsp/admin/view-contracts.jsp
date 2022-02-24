<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>View Contracts</title>
    <jsp:include page="../head.jsp"></jsp:include>
</head>
<body>
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
</body>
</html>