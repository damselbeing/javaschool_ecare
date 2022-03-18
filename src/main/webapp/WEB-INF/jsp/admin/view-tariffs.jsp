<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>View Tariffs</title>
    <jsp:include page="../head.jsp"></jsp:include>
</head>
<body>
    <header>
        <jsp:include page="header.jsp"></jsp:include>
    </header>
    <div class="container">
        <h1>Tariffs' list</h1>
        <br>
    </div>
    <div class="container">
    <c:if test="${tariffs.size() == 0}">
        <h3>Found no tariff(s)</h3>
    </c:if>
    </div>

    <div class="container">

    <c:if test="${tariffs.size() > 0}">

        <div class="container">

            <div class="row row-cols-3">
                <c:forEach items="${tariffs}" var="tariff">
                    <div class="col">

                            <c:if test="${tariff.archived == false}">
                        <div class="fw-bold">Tariff name: ${tariff.name}</div>
                        <div>Tariff price: ${tariff.price}</div>
                        <div>Tariff options: <c:forEach items="${tariff.options}" var="option">
                                ${option.name} </c:forEach>
                        </div>
                                <form:form>
                            <button class="btn btn-outline-primary"
                                    formaction="/admin/archiveTariff/${tariff.idTariff}"
                                    type="submit">
                                Archive tariff
                            </button>

                            <a class="btn btn-outline-primary"
                                    href="/admin/tariffProfile/${tariff.idTariff}">
                                Edit tariff
                            </a>
                                </form:form>
                            </c:if>
                            <c:if test="${tariff.archived == true}">
                            <div class="text-muted">
                                <div class="fw-bold">Tariff name: ${tariff.name}</div>
                                <div>Tariff price: ${tariff.price}</div>
                                <div>Tariff options: <c:forEach items="${tariff.options}" var="option">
                                    ${option.name} </c:forEach>
                                </div>
                                <div class="text-decoration-underline">
                                    Tariff is marked for deletion
                                </div>
                            </div>
                            </c:if>

                        <br>
                    </div>
                </c:forEach>


                <div class="col">
                    <div>Here can be a new tariff...</div>
                    <form:form method="post" modelAttribute="newTariff" action="/admin/addTariff">
                    <div class="form-group">
                        <form:label path="name">Name</form:label>
                        <form:input type="text" required="name" class="form-control" path="name"/>
                        <form:errors path="name" cssClass="error"/>
                    </div>
                    <div class="form-group">
                        <form:label path="price">Price</form:label>
                        <form:input type="text" required="price" class="form-control" path="price"/>
                        <form:errors path="price" cssClass="error"/>
                    </div>
                    <br>
                    <button type="submit" class="btn btn-outline-primary">Add tariff</button>
                    </form:form>
                </div>

            </div>

        </div>
    </c:if>

    </div>

</body>
</html>