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
                        <form:form>
                            <c:if test="${tariff.archived == false}">
                        <div class="fw-bold">Tariff name: ${tariff.name}</div>
                        <div>Tariff price: ${tariff.price}</div>
                        <div>Tariff options: <c:forEach items="${tariff.options}" var="option">
                                ${option.name} </c:forEach>
                        </div>
                            <button class="btn btn-outline-primary"
                                    formaction="/admin/archiveTariff/${tariff.idTariff}"
                                    type="submit">
                                Archive tariff
                            </button>
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
                        </form:form>
                        <br>
                    </div>
                </c:forEach>
                <div class="col">
                    <div>Here can be a new tariff...</div>

                        <button class="btn btn-outline-primary"
                                formaction="/welcome"
                                type="submit">
                            Add tariff
                        </button>

                </div>

            </div>

        </div>
    </c:if>

    </div>

</body>
</html>