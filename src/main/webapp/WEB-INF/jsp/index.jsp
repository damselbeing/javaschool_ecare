<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Main</title>
    <jsp:include page="head.jsp"></jsp:include>
</head>
<body>

<div class="container" style="height: 100vh;">
    <div class="row align-items-center" style="height: 100%">
        <div class="col-4">


            <sec:authorize access="hasRole('ADMIN')">
                <h1 class="display-6">Hello, Admin!</h1>
                <div>
                    <h4><a href="/logout">Log out</a></h4>
                    <br>
                    <h4><a href="/admin/clients">View clients</a></h4>
                    <h4><a href="/admin/tariffs">View tariffs</a></h4>
                    <h4><a href="/admin/options">View options</a></h4>
                </div>
            </sec:authorize>



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
