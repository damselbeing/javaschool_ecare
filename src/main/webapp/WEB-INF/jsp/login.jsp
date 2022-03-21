<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Login</title>
    <jsp:include page="head.jsp"></jsp:include>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    <% response.sendRedirect("/"); %>
</sec:authorize>

<div class="container" style="height: 90vh;">
    <div class="row align-items-center" style="height: 100%">
        <div class="col-4">
            <h1 class="display-1">eCare</h1>

            <form method="POST" action="/login">
                <h6 class="display-6"><small>LogIn below, or SignUp <a href="/registration">here</a></small></h6>
                <div class="mb-3">
                    <label for="exampleInputEmail1" class="form-label"></label>
                    <input name="username" type="email" class="form-control" id="exampleInputEmail1"
                           placeholder="email">
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword1" class="form-label"></label>
                    <input name="password" type="password" class="form-control" id="exampleInputPassword1"
                           placeholder="password">
                </div>
                <button type="submit" class="container btn btn-primary">Log in</button>
                <br>
            </form>
        </div>
        <div class="col-1">
        </div>
        <div class="col-7">
            <img src="/res/images/login.jpg" alt="img">
        </div>
    </div>

</div>

</body>
</html>
