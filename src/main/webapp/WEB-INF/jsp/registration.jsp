<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Registration</title>
    <jsp:include page="head.jsp"></jsp:include>
</head>
<body>
    <div class="container">
        <h4 class="display-4">Registration</h4>
        <br>
    </div>
    <div class="container">
<div class="container">
    <div class="row">


        <div class="col-5">
            <form:form method="post" modelAttribute="client" action="/registration">
                <div class="form-group">
                    <form:label path="name">Name</form:label>
                    <form:input type="text" required="name" class="form-control" path="name"/>
<%--                    <form:errors path="name" cssClass="error"/>--%>
                </div>
                <div class="form-group">
                    <form:label path="lastName">Last Name</form:label>
                    <form:input type="text" required="lastName" class="form-control" path="lastName"/>
<%--                    <form:errors path="lastName" cssClass="error"/>--%>
                </div>
                <div class="form-group">
                    <form:label path="birthDate">Birth Date</form:label>
                    <form:input type="date" required="date" min="1900-01-01" class="form-control" path="birthDate"/>
<%--                    <form:errors path="birthDate" cssClass="error"/>--%>
                </div>
                <div class="form-group">
                    <form:label path="passport">Passport</form:label>
                    <form:input type="text" required="passport" class="form-control" path="passport"/>
<%--                    <form:errors path="passport" cssClass="error"/>--%>
                </div>
                <div class="form-group">
                    <form:label path="address">Address</form:label>
                    <form:input type="text" required="address" class="form-control" path="address"/>
<%--                    <form:errors path="address" cssClass="error"/>--%>
                </div>
                <div class="form-group">
                    <form:label path="email">Email</form:label>
                    <form:input type="text" required="text" class="form-control" path="email"
                    pattern="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
                    title="username@domain.name"/>
<%--                    <form:errors path="email" cssClass="error"/>--%>
                </div>
                <div class="form-group">
                    <form:label path="password">Password</form:label>
                    <form:input type="password" required="password" class="form-control" path="password"
                                pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                title="Password must contain at least one digit, one lowercase letter,
                                one uppercase letter and password length must be not less than 8 symbols"/>
<%--                    <form:errors path="password" cssClass="error"/>--%>
                </div>
                <div class="form-group">
                    <form:label path="name">Confirm password</form:label>
                    <form:input type="password" required="confirm" class="form-control" path="passwordConfirm"/>
<%--                    <form:errors path="passwordConfirm" cssClass="error"/>--%>
                </div>
                <br>
                <c:if test="${error != null}">
                    <div class="row alert alert-danger hidden">${error}</div>
                </c:if>

                <button type="submit" class="btn btn-primary">Sign Up</button>
            </form:form>
        </div>

        <div class="col-7">
            <img src="/res/images/login.jpg" alt="img">
        </div>

    </div>
</div>
</body>
</html>
