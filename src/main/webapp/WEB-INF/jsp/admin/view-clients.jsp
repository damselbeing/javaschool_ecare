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
        <h1>Clients' list</h1>

    </div>
    <div class="container">
    <c:if test="${clients.size() == 0}">
        <br>
        <h3>Found no client(s)</h3>
    </c:if>
    </div>
    <div class="container">
    <c:if test="${clients.size() > 0}">
        <form class="form-inline" method="get" action="/admin/clients">
            <label>
                <input type="text" name="contractNumber" class="form-control" placeholder="Enter client's contract" required>
            </label>
            <button type="submit" class="btn btn-primary">Search client</button>
            <c:if test="${error != null}">
                <div class="container">
                <div class="row alert alert-danger hidden">${error}</div>
                </div>
            </c:if>
        </form>
        <table class="table">
            <thead>
            <tr>
                <th>Name</th>
                <th>Birth Date</th>
                <th>Passport</th>
                <th>Address</th>
                <th>Email</th>
                <th>Contract</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${clients}" var="client">
                <c:if test="${client.role != 'ADMIN'}">
                <tr>
                    <td>${client.name} ${client.lastName}</td>
                    <td>${client.birthDate}</td>
                    <td>${client.passport}</td>
                    <td>${client.address}</td>
                    <td>${client.email}</td>
                    <td>
                        <c:if test="${client.contract.number != null}">
                            <a href="/admin/contractProfile/${client.idClient}">
                                    ${client.contract.number}
                            </a>
                        </c:if>
                        <c:if test="${client.contract.number == null}">
                            <a href="/admin/addContract/${client.idClient}">Add contract</a>
                        </c:if>
                    </td>
                </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    </div>
</body>
</html>