<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Account</title>
    <jsp:include page="head.jsp"></jsp:include>
</head>
<body>
    <div class="container">
        <h1>My account</h1>
        <br>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-4">
                <div class="h4">Personal information:</div>
                <div>
                    <span class="h6">Name: </span><span>${client.name} ${client.lastName}</span>
                </div>
                <div>
                    <span class="h6">Passport: </span><span>${client.passport}</span>
                </div>
                <div>
                    <span class="h6">Address: </span><span>${client.address}</span>
                </div>
                <div>
                    <span class="h6">Email: </span><span>${client.name}</span>
                </div>
                <div>
                    <span class="h6">Contract: </span><span>${client.contract.number}</span>
                </div>
            </div>
            <div class="col-4">
                <div class="h4">Actual tariffs:</div>
            </div>
            <div class="col-4">
                <div class="h4">Available options:</div>
            </div>
        </div>
    </div>
</body>
</html>
