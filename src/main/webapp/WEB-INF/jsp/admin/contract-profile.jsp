<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Contract Profile</title>
    <jsp:include page="../head.jsp"></jsp:include>
</head>
<body>
    <header>
        <jsp:include page="header.jsp"></jsp:include>
    </header>
    <div class="container">
        <h1>Contract profile</h1>
        <br>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-5">
                <div class="h5">Contract number: ${contract.number}</div>
                <form:form>
                    <c:if test="${contract.blockedByClient == false && contract.blockedByAdmin == false}">
                        <button class="btn btn-outline-primary"
                                formaction="/admin/blockContract/${contract.idContract}"
                                type="submit">
                            Block contract
                        </button>
                    </c:if>
                    <c:if test="${contract.blockedByClient == true || contract.blockedByAdmin == true}">
                        <button class="btn btn-outline-primary"
                                formaction="/admin/unblockContract/${contract.idContract}"
                                type="submit">
                            Unblock contract
                        </button>
                    </c:if>
                </form:form>
                <div class="h5">Tariff name: ${contract.tariff.name}</div>
                <form:form>
                    <button class="btn btn-outline-primary"
                            formaction="/admin/changeTariff/${contract.idContract}"
                            type="submit">
                        Change tariff
                    </button>
                </form:form>
            </div>

            <form:form class="col-7">
                <div class="h5">Available options for tariff: ${contract.tariff.name}</div>
                <c:forEach items="${contract.tariff.options}" var="tariffOption">

                    <c:if test="${(contract.contractOptions
                                                .stream()
                                                .filter(contractOption -> contractOption.idOption == tariffOption.idOption)
                                                .count() > 0)}">
                        <div class="form-check">
                            <input name="options" class="form-check-input" type="checkbox" value="${tariffOption.idOption}" id="option_${tariffOption.idOption}" checked>
                            <label class="form-check-label" for="option_${tariffOption.idOption}">
                                    ${tariffOption.name}
                            </label>
                            <small class="text-muted">Additional options: <c:forEach items="${tariffOption.additionalOptions}" var="optionAdd">
                                ${optionAdd.name} </c:forEach></small>
                        </div>
                    </c:if>

                    <c:if test="${(contract.contractOptions
                                                .stream()
                                                .filter(contractOption -> contractOption.idOption == tariffOption.idOption)
                                                .count() == 0)}">
                        <div class="form-check">
                            <input name="options" class="form-check-input" type="checkbox" value="${tariffOption.idOption}" id="option_${tariffOption.idOption}">
                            <label class="form-check-label" for="option_${tariffOption.idOption}">
                                    ${tariffOption.name}
                            </label>
                            <small class="text-muted">Additional options: <c:forEach items="${tariffOption.additionalOptions}" var="optionAdd">
                                ${optionAdd.name} </c:forEach></small>
                        </div>
                    </c:if>

                </c:forEach>
                <c:if test="${error != null}">
                    <div class="row alert alert-danger hidden">${error}</div>
                </c:if>
                <button class="btn btn-outline-primary"
                        formaction="/admin/updateContract/${contract.idContract}"
                        type="submit">
                    Save
                </button>

            </form:form>
        </div>
    </div>
</body>
</html>