<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>New Contract</title>
    <jsp:include page="../head.jsp"></jsp:include>
</head>
<body>
<header>
    <jsp:include page="header.jsp"></jsp:include>
</header>
    <div class="container">
        <h1>New contract</h1>
        <br>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-4">
                <div class="h4">Personal information:</div>
                <div>
                    <strong>Name: </strong><span>${client.name} ${client.lastName}</span>
                </div>
                <div>
                    <strong>Passport: </strong><span>${client.passport}</span>
                </div>
                <div>
                    <strong>Address: </strong><span>${client.address}</span>
                </div>
                <div>
                    <strong>Email: </strong><span>${client.email}</span>
                </div>

            </div>



            <div class="col-4">
                <div class="h4">Please choose one number:</div>
                <form:form method="post" modelAttribute="number" action="/admin/saveContract/${client.idClient}">
                    <c:forEach items="${telnumbers}" var="telnumber">

                        <div style="display: inline-block; width: 150px" class="form-check">
                            <input class="form-check-input" type="radio" name="number" value="${telnumber}" id="number_${telnumber}" required>
                            <label class="form-check-label" for="number_${telnumber}">
                                    ${telnumber}
                            </label>

                        </div>

                    </c:forEach>
                    <br>
                    <button class="btn btn-outline-primary" type="submit">
                        Add contract
                    </button>
                </form:form>
            </div>




        </div>
    </div>
</body>
</html>
