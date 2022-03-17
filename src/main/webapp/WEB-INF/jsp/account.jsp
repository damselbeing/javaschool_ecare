<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Account</title>
    <jsp:include page="head.jsp"></jsp:include>
</head>
<body>
    <div class="container">
        <h1>My account</h1>
        <br>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-4">
                <div class="h4">Personal information:</div>
                <div>
                    <span class="h6">Name: </span><span>${client.name} ${client.lastName}</span>
                </div>
                <div>
                    <span class="h6">Passport: </span><span>${client.passport}</span>
                </div>
                <div>
                    <span class="h6">Address: </span><span>${client.address}</span>
                </div>
                <div>
                    <span class="h6">Email: </span><span>${client.email}</span>
                </div>
                <div>
                    <span class="h6">Contract: </span><span>${client.contract.number}</span>
                </div>
                <br>
                <form:form>
                    <c:if test="${client.contract.blockedByClient == false && client.contract.blockedByAdmin == false}">
                    <button class="btn btn-primary btn-sm"
                            formaction="/blockContract/${client.idClient}/${client.contract.idContract}"
                            type="submit">
                        Block contract
                    </button>
                    </c:if>
                    <c:if test="${client.contract.blockedByClient == true && client.contract.blockedByAdmin == false}">
                        <button class="btn btn-primary btn-sm"
                                formaction="/unblockContract/${client.idClient}/${client.contract.idContract}"
                                type="submit">
                            Unblock contract
                        </button>
                    </c:if>
                    <c:if test="${client.contract.blockedByAdmin == true}">
                        <button class="btn btn-primary btn-sm disabled"
                                formaction="/unblockContract/${client.idClient}/${client.contract.idContract}"
                                type="submit">
                            Unblock contract
                        </button>
                    </c:if>
                </form:form>
            </div>




            <div class="col-3">
                <div class="h4">Actual tariffs:</div>
                <form:form>
                    <c:if test="${client.contract.blockedByClient == true || client.contract.blockedByAdmin == true}">
                <fieldset disabled>
                <c:forEach items="${tariffs}" var="tariff">
                    <c:if test="${client.contract.tariff.idTariff == tariff.idTariff}">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="tariffUpdated" value="${tariff.idTariff}" id="tariffUpdated_${tariff.idTariff}" checked>
                        <label class="form-check-label" for="tariffUpdated_${tariff.idTariff}">${tariff.name}</label>
                    </div>
                    </c:if>
                    <c:if test="${client.contract.tariff.idTariff != tariff.idTariff}">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="tariffUpdated" value="${tariff.idTariff}" id="tariffUpdated_${tariff.idTariff}">
                            <label class="form-check-label" for="tariffUpdated_${tariff.idTariff}">${tariff.name}</label>
                        </div>
                    </c:if>
                </c:forEach>
                    <br>
                    <button class="btn btn-primary btn-sm"
                            formaction="/updateTariff/${client.idClient}/${client.contract.idContract}"
                            type="submit">
                        Update tariff
                    </button>
                    </fieldset>
                    </c:if>
                    <c:if test="${client.contract.blockedByClient == false && client.contract.blockedByAdmin == false}">

                            <c:forEach items="${tariffs}" var="tariff">
                                <c:if test="${client.contract.tariff.idTariff == tariff.idTariff}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="tariffUpdated" value="${tariff.idTariff}" id="tariffUpdated_${tariff.idTariff}" checked>
                                        <label class="form-check-label" for="tariffUpdated_${tariff.idTariff}">${tariff.name}</label>
                                    </div>
                                </c:if>
                                <c:if test="${client.contract.tariff.idTariff != tariff.idTariff}">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="tariffUpdated" value="${tariff.idTariff}" id="tariffUpdated_${tariff.idTariff}">
                                        <label class="form-check-label" for="tariffUpdated_${tariff.idTariff}">${tariff.name}</label>
                                    </div>
                                </c:if>
                            </c:forEach>
                            <br>
                            <button class="btn btn-primary btn-sm"
                                    formaction="/updateTariff/${client.idClient}/${client.contract.idContract}"
                                    type="submit">
                                Update tariff
                            </button>

                    </c:if>
                </form:form>
            </div>



            <div class="col-5">
                <div class="h4">Available options:</div>
                <c:if test="${(client.contract.tariff.options.size() == 0)}">
                    <div class="h6">No option(s) found</div>
                </c:if>
                <form:form>
                <c:if test="${client.contract.blockedByClient == true || client.contract.blockedByAdmin == true}">
                <fieldset disabled>
                <c:forEach items="${client.contract.tariff.options}" var="tariffOption">
                    <c:if test="${(client.contract.contractOptions
                                                    .stream()
                                                    .filter(contractOption -> contractOption.idOption == tariffOption.idOption)
                                                    .count() > 0)}">
                        <div class="form-check">
                            <input name="optionsUpdated" class="form-check-input" type="checkbox" value="${tariffOption.idOption}" id="option_${tariffOption.idOption}" checked>
                            <label class="form-check-label" for="option_${tariffOption.idOption}">
                                    ${tariffOption.name}
                            </label>
                            <small class="text-muted">Additional options: <c:forEach items="${tariffOption.additionalOptions}" var="optionAdd">
                                ${optionAdd.name} </c:forEach></small>
                        </div>
                    </c:if>
                    <c:if test="${(client.contract.contractOptions
                                                    .stream()
                                                    .filter(contractOption -> contractOption.idOption == tariffOption.idOption)
                                                    .count() == 0)}">
                        <div class="form-check">
                            <input name="optionsUpdated" class="form-check-input" type="checkbox" value="${tariffOption.idOption}" id="option_${tariffOption.idOption}">
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
                <br>

                <button class="btn btn-primary btn-sm"
                        formaction="/updateOptions/${client.idClient}/${client.contract.idContract}"
                        type="submit">
                    Save options
                </button>
                </fieldset>
                </c:if>
                    <c:if test="${client.contract.blockedByClient == false && client.contract.blockedByAdmin == false}">

                            <c:forEach items="${client.contract.tariff.options}" var="tariffOption">
                                <c:if test="${(client.contract.contractOptions
                                                    .stream()
                                                    .filter(contractOption -> contractOption.idOption == tariffOption.idOption)
                                                    .count() > 0)}">
                                    <div class="form-check">
                                        <input name="optionsUpdated" class="form-check-input" type="checkbox" value="${tariffOption.idOption}" id="option_${tariffOption.idOption}" checked>
                                        <label class="form-check-label" for="option_${tariffOption.idOption}">
                                                ${tariffOption.name}
                                        </label>
                                        <small class="text-muted">Additional options: <c:forEach items="${tariffOption.additionalOptions}" var="optionAdd">
                                            ${optionAdd.name} </c:forEach></small>
                                    </div>
                                </c:if>
                                <c:if test="${(client.contract.contractOptions
                                                    .stream()
                                                    .filter(contractOption -> contractOption.idOption == tariffOption.idOption)
                                                    .count() == 0)}">
                                    <div class="form-check">
                                        <input name="optionsUpdated" class="form-check-input" type="checkbox" value="${tariffOption.idOption}" id="option_${tariffOption.idOption}">
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
                            <br>

                            <button class="btn btn-primary btn-sm"
                                    formaction="/updateOptions/${client.idClient}/${client.contract.idContract}"
                                    type="submit">
                                Save options
                            </button>
                    </c:if>
                </form:form>
            </div>




        </div>
    </div>
</body>
</html>
