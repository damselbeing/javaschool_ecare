<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>View Profile</title>
    <jsp:include page="../head.jsp"></jsp:include>
</head>
<body>
    <header>
        <jsp:include page="header.jsp"></jsp:include>
    </header>
    <div class="container">
        <h1>Contract Profile</h1>
        <h3>Client's Name ${contract.client.name} ${contract.client.lastName}</h3>
        <h3>Client's Passport ${contract.client.passport}</h3>
        <h3>Client's Contract ${contract.number}</h3>
    </div>
</body>
</html>