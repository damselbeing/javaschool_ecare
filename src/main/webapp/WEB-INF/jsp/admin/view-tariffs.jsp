<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
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
        <br>
        <div class="container">

            <div class="row row-cols-3">
        <div class="col">
            <div>Here can be a new tariff...</div>

            <div class="col-6">
                <form:form method="post" modelAttribute="newTariff" action="/admin/addTariff">

                <div class="form-group">
                    <input name="name" class="form-control form-control-sm" type="text" placeholder="Tariff name" id="formID1" required>
                    <label class="form-label" for="formID1"></label>
                </div>
                <div class="form-group">
                    <input name="price" class="form-control form-control-sm" type="number" min="0" placeholder="Tariff price" id="formID2" required>
                    <label class="form-label" for="formID2"></label>
                </div>
                    <c:if test="${error != null}">
                        <div class="row alert alert-danger hidden">${error}</div>
                    </c:if>
                <div class="form-group">
                    <button class="btn btn-primary btn-sm" type="submit">Add tariff</button>
                </div>

            </div>

            </form:form>
        </div>
            </div>
        </div>

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
                                    <c:if test="${tariff.markedForUpdate == true}">
                                        <a class="btn btn-outline-danger"
                                                href="/admin/tariffProfile/${tariff.idTariff}">
                                            Must be edited!
                                        </a>
                                    </c:if>
                                    <c:if test="${tariff.markedForUpdate == false}">
                                        <a class="btn btn-outline-primary"
                                           href="/admin/tariffProfile/${tariff.idTariff}">
                                            Edit tariff
                                        </a>
                                    </c:if>
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

                    <div class="col-6">
                        <form:form method="post" modelAttribute="newTariff" action="/admin/addTariff">

                            <div class="form-group">
                                <input name="name" class="form-control form-control-sm" type="text" placeholder="Tariff name" id="formID1" required>
                                <label class="form-label" for="formID1"></label>
                            </div>
                            <div class="form-group">
                                <input name="price" class="form-control form-control-sm" type="number" min="0" placeholder="Tariff price" id="formID2" required>
                                <label class="form-label" for="formID2"></label>
                            </div>
                            <c:if test="${error != null}">
                                <div class="row alert alert-danger hidden">${error}</div>
                            </c:if>
                            <div class="form-group">
                                <button class="btn btn-primary btn-sm" type="submit">Add tariff</button>
                            </div>
                        </form:form>
                    </div>


                </div>


            </div>

        </div>
    </c:if>

    </div>

</body>
</html>