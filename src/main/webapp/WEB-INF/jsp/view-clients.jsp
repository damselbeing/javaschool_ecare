<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>View Clients</title>
    <jsp:include page="head.jsp"></jsp:include>
</head>
<body>
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
            <td>${client.contract}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>