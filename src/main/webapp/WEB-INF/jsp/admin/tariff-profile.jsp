<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Tariff Profile</title>
    <jsp:include page="../head.jsp"></jsp:include>
</head>
<body>
    <header>
        <jsp:include page="header.jsp"></jsp:include>
    </header>
    <div class="container">
        <h1>Tariff profile</h1>
        <br>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-4">
                <div class="h5">Tariff name: ${tariff.name}</div>
                <form:form>
                    <button class="btn btn-outline-primary"
                            formaction="/admin/changeName/${tariff.idTariff}"
                            type="submit" disabled>
                        Change name
                    </button>
                </form:form>
                <div class="h5">Tariff price: ${tariff.price}</div>
                <form:form>
                    <button class="btn btn-outline-primary"
                            formaction="/admin/changePrice/${tariff.idTariff}"
                            type="submit" disabled>
                        Change price
                    </button>
                </form:form>
            </div>

            <div class="col-8">
                <div class="h5">Available options for tariff: ${tariff.name}</div>
                <form:form>
                <c:forEach items="${optionsTotal}" var="optionOfTotal">
                        <c:if test="${
                            tariff.options
                                .stream()
                                .filter(optionOfTariff -> optionOfTariff.idOption == optionOfTotal.idOption)
                                .count() > 0
                        }">
                            <div class="form-check">
                                <input name="options" class="form-check-input" type="checkbox" value="${optionOfTotal.idOption}" id="option_${optionOfTotal.idOption}" checked>
                                <label class="form-check-label" for="option_${optionOfTotal.idOption}">
                                        ${optionOfTotal.name}
                                </label>
                            </div>
                            <div class="container">
                                <div class="row">
                                    <div class="col">
                                        <small class="text-muted">Additional options: <c:forEach items="${optionOfTotal.additionalOptions}" var="option">
                                            ${option.name} </c:forEach></small>
                                    </div>
                                    <div class="col">
                                        <small class="text-muted">Conflicting options: <c:forEach items="${optionOfTotal.conflictingOptions}" var="option">
                                            ${option.name} </c:forEach></small>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    <c:if test="${
                            tariff.options
                                .stream()
                                .filter(optionOfTariff -> optionOfTariff.idOption == optionOfTotal.idOption)
                                .count() == 0
                        }">
                            <div class="form-check">
                                <input name="options" class="form-check-input" type="checkbox" value="${optionOfTotal.idOption}" id="option_${optionOfTotal.idOption}">
                                <label class="form-check-label" for="option_${optionOfTotal.idOption}">
                                        ${optionOfTotal.name}
                                </label>
                            </div>
                            <div class="container">
                                <div class="row">
                                    <div class="col">
                                        <small class="text-muted">Additional options: <c:forEach items="${optionOfTotal.additionalOptions}" var="option">
                                            ${option.name} </c:forEach></small>
                                    </div>
                                    <div class="col">
                                        <small class="text-muted">Conflicting options: <c:forEach items="${optionOfTotal.conflictingOptions}" var="option">
                                            ${option.name} </c:forEach></small>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                </c:forEach>
                <c:if test="${error != null}">
                    <div class="row alert alert-danger hidden">${error}</div>
                </c:if>
                <button class="btn btn-outline-primary"
                        formaction="/admin/updateTariff/${tariff.idTariff}"
                        type="submit">
                    Save
                </button>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>