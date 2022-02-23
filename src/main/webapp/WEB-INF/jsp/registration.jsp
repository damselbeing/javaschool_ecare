<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Registration</title>
    <jsp:include page="head.jsp"></jsp:include>
</head>
<body>
<form:form method="post" modelAttribute="client" action="/registration">
    <div class="input-group mb-3">
        <form:label path="name">Name</form:label>
        <form:input type="text" required="name" class="form-control" path="name"/>
        <form:errors path="name" cssClass="error"/>
    </div>
    <div class="input-group mb-3">
        <form:label path="lastName">Last Name</form:label>
        <form:input type="text" required="lastName" class="form-control" path="lastName"/>
        <form:errors path="lastName" cssClass="error"/>
    </div>
    <div class="input-group mb-3">
        <form:label path="birthDate">Birth Date</form:label>
        <form:input type="date" required="date" class="form-control" path="birthDate"/>
        <form:errors path="birthDate" cssClass="error"/>
    </div>
    <div class="input-group mb-3">
        <form:label path="passport">Passport</form:label>
        <form:input type="text" required="passport" class="form-control" path="passport"/>
        <form:errors path="passport" cssClass="error"/>
    </div>
    <div class="input-group mb-3">
        <form:label path="address">Address</form:label>
        <form:input type="text" required="address" class="form-control" path="address"/>
        <form:errors path="address" cssClass="error"/>
    </div>
    <div class="input-group mb-3">
        <form:label path="email">Email</form:label>
        <form:input type="text" required="email" class="form-control" path="email"/>
        <form:errors path="email" cssClass="error"/>
    </div>
    <div class="input-group mb-3">
        <form:label path="password">Password</form:label>
        <form:input type="password" required="password" class="form-control" path="password"
                    pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                    title="Password must contain at least one digit, one lowercase letter,
                    one uppercase one and length must be not less than 8 symbols"/>
        <form:errors path="password" cssClass="error"/>
    </div>
    <button type="submit" class="btn btn-primary">Sign Up</button>
</form:form>
</body>
</html>
