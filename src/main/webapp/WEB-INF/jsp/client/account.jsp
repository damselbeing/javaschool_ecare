<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Account</title>
    <jsp:include page="../head.jsp"></jsp:include>
</head>
<body>
<header>
    <jsp:include page="header.jsp"></jsp:include>
</header>
<div class="container">
    <h1>My account</h1>
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
            <br>
            <div>
                <strong>Contract number: </strong><span>${client.contract.number}</span>
                <c:if test="${client.contract == null}">
                        <span>
                            no contract found
                        </span>
                </c:if>
            </div>
            <div>
                <strong>Contract tariff: </strong><span>${client.contract.tariff.name}</span>
                <c:if test="${client.contract.tariff.archived == true}">
                    <div>
                        <small class="text-muted">This tariff is archived</small>
                    </div>
                </c:if>
                <c:if test="${client.contract.tariff == null}">
                        <span>
                            no tariff found
                        </span>
                </c:if>
            </div>

            <form:form>
                <c:if test="${client.contract.blockedByClient == false && client.contract.blockedByAdmin == false}">
                    <button class="btn btn-outline-primary"
                            formaction="/client/blockContract/${client.idClient}/${client.contract.idContract}"
                            type="submit">
                        Block contract
                    </button>
                </c:if>
                <c:if test="${client.contract.blockedByClient == true && client.contract.blockedByAdmin == false}">
                    <button class="btn btn-outline-primary"
                            formaction="/client/unblockContract/${client.idClient}/${client.contract.idContract}"
                            type="submit">
                        Unblock contract
                    </button>
                </c:if>
                <c:if test="${client.contract.blockedByAdmin == true}">
                    <button class="btn btn-outline-danger disabled"
                            formaction="/client/unblockContract/${client.idClient}/${client.contract.idContract}"
                            type="submit">
                        Blocked by admin
                    </button>
                </c:if>
            </form:form>
        </div>


        <div class="col-3">
            <div class="h4">Actual tariffs:</div>
            <c:choose>
                <c:when test="${(
                client.contract == null ||
                tariffs.isEmpty() ||
                (tariffs.size() == tariffs
                            .stream()
                            .filter(tariff -> tariff.archived == true)
                            .count())
                            )}">
                    <%--                    <div class="container">--%>
                    <div class="h6">No tariff(s) found</div>
                    <%--                    </div>--%>
                </c:when>
                <c:otherwise>
                    <form:form>
                        <c:if test="${client.contract.blockedByClient == true || client.contract.blockedByAdmin == true}">
                            <fieldset disabled>
                                <c:forEach items="${tariffs}" var="tariff">
                                    <c:if test="${tariff.archived == false && client.contract.tariff.idTariff == tariff.idTariff}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="tariffUpdated"
                                                   value="${tariff.idTariff}" id="tariffUpdated_${tariff.idTariff}"
                                                   checked>
                                            <label class="form-check-label"
                                                   for="tariffUpdated_${tariff.idTariff}">${tariff.name}
                                                <div class="small">Price: ${tariff.price} EUR</div>
                                            </label>
                                        </div>
                                    </c:if>
                                    <c:if test="${tariff.archived == false && client.contract.tariff.idTariff != tariff.idTariff}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="tariffUpdated"
                                                   value="${tariff.idTariff}" id="tariffUpdated_${tariff.idTariff}">
                                            <label class="form-check-label"
                                                   for="tariffUpdated_${tariff.idTariff}">${tariff.name}
                                                <div class="small">Price: ${tariff.price} EUR</div>
                                            </label>
                                        </div>
                                    </c:if>
                                </c:forEach>

                                <button class="btn btn-outline-primary"
                                        formaction="/client/updateTariff/${client.idClient}/${client.contract.idContract}"
                                        type="submit">
                                    Update tariff
                                </button>
                            </fieldset>
                        </c:if>
                        <c:if test="${client.contract.blockedByClient == false && client.contract.blockedByAdmin == false}">

<%--                            <c:forEach items="${tariffs}" var="tariff">--%>
<%--                                <c:if test="${tariff.archived == false && client.contract.tariff.idTariff == tariff.idTariff}">--%>
<%--                                    <div class="form-check">--%>
<%--                                        <input class="form-check-input" type="radio" name="tariffUpdated"--%>
<%--                                               value="${tariff.idTariff}" id="tariffUpdated_${tariff.idTariff}" checked>--%>
<%--                                        <label class="form-check-label"--%>
<%--                                               for="tariffUpdated_${tariff.idTariff}">${tariff.name}--%>
<%--                                            <div class="small">Price: ${tariff.price} EUR</div>--%>
<%--                                        </label>--%>
<%--                                    </div>--%>
<%--                                </c:if>--%>
<%--                                <c:if test="${tariff.archived == false && client.contract.tariff.idTariff != tariff.idTariff}">--%>
<%--                                    <div class="form-check">--%>
<%--                                        <input class="form-check-input" type="radio" name="tariffUpdated"--%>
<%--                                               value="${tariff.idTariff}" id="tariffUpdated_${tariff.idTariff}">--%>
<%--                                        <label class="form-check-label"--%>
<%--                                               for="tariffUpdated_${tariff.idTariff}">${tariff.name}--%>
<%--                                            <div class="small">Price: ${tariff.price} EUR</div>--%>
<%--                                        </label>--%>
<%--                                    </div>--%>
<%--                                </c:if>--%>
<%--                            </c:forEach>--%>









                            <c:forEach items="${tariffs}" var="tariff">

                                <c:if test="${tariff.archived == false && client.contract.tariff.idTariff == tariff.idTariff}">
                                    <c:if test="${tariff.markedForUpdate == true}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="tariffUpdated"
                                                   value="${tariff.idTariff}" id="tariffUpdated_${tariff.idTariff}" checked disabled>
                                            <label class="form-check-label"
                                                   for="tariffUpdated_${tariff.idTariff}">${tariff.name}
                                                <div class="small">Price: ${tariff.price} EUR</div>
                                                <div class="small">Tariff must be edited!</div>
                                            </label>
                                        </div>
                                    </c:if>
                                    <c:if test="${tariff.markedForUpdate == false}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="tariffUpdated"
                                                   value="${tariff.idTariff}" id="tariffUpdated_${tariff.idTariff}" checked>
                                            <label class="form-check-label"
                                                   for="tariffUpdated_${tariff.idTariff}">${tariff.name}
                                                <div class="small">Price: ${tariff.price} EUR</div>
                                            </label>
                                        </div>
                                    </c:if>
                                </c:if>
                                <c:if test="${tariff.archived == false && client.contract.tariff.idTariff != tariff.idTariff}">
                                    <c:if test="${tariff.markedForUpdate == true}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="tariffUpdated"
                                                   value="${tariff.idTariff}" id="tariffUpdated_${tariff.idTariff}" disabled>
                                            <label class="form-check-label"
                                                   for="tariffUpdated_${tariff.idTariff}">${tariff.name}
                                                <div class="small">Price: ${tariff.price} EUR</div>
                                                <div class="small">Tariff must be edited!</div>
                                            </label>
                                        </div>
                                    </c:if>
                                    <c:if test="${tariff.markedForUpdate == false}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="tariffUpdated"
                                                   value="${tariff.idTariff}" id="tariffUpdated_${tariff.idTariff}">
                                            <label class="form-check-label"
                                                   for="tariffUpdated_${tariff.idTariff}">${tariff.name}
                                                <div class="small">Price: ${tariff.price} EUR</div>
                                            </label>
                                        </div>
                                    </c:if>
                                </c:if>
                            </c:forEach>










                            <button class="btn btn-outline-primary"
                                    formaction="/client/updateTariff/${client.idClient}/${client.contract.idContract}"
                                    type="submit">
                                Update tariff
                            </button>

                        </c:if>
                    </form:form>
                </c:otherwise>
            </c:choose>
        </div>


        <div class="col-5">
            <div class="h4">Contract tariff options:</div>
            <c:choose>
                <c:when test="${(
            client.contract.tariff.options.size() == 0 ||
            client.contract.tariff.options == null ||
            client.contract.tariff.markedForUpdate == true ||
            client.contract.tariff.archived == true ||
            (tariffs.size() == tariffs
                            .stream()
                            .filter(tariff -> tariff.archived == true)
                            .count())
                            )}">
                    <%--                    <div class="container">--%>
                    <div class="h6">No option(s) found</div>
                    <%--                    </div>--%>
                </c:when>
                <c:otherwise>
                    <%--            <c:if test="${(client.contract.tariff.options.size() > 0)}">--%>
                    <form:form>
                        <c:if test="${client.contract.blockedByClient == true || client.contract.blockedByAdmin == true}">
                            <fieldset disabled>
                                <c:forEach items="${client.contract.tariff.options}" var="tariffOption">
                                    <c:if test="${(client.contract.contractOptions
                                                    .stream()
                                                    .filter(contractOption -> contractOption.idOption == tariffOption.idOption)
                                                    .count() > 0)}">
                                        <div class="form-check">
                                            <input name="optionsUpdated" class="form-check-input" type="checkbox"
                                                   value="${tariffOption.idOption}" id="option_${tariffOption.idOption}"
                                                   checked>
                                            <label class="form-check-label" for="option_${tariffOption.idOption}">
                                                    ${tariffOption.name}
                                                <small class="text-muted">Additional options: <c:forEach
                                                        items="${tariffOption.additionalOptions}" var="optionAdd">
                                                    ${optionAdd.name} </c:forEach></small>
                                                <div class="small">Price: ${tariffOption.price} EUR</div>
                                            </label>
                                        </div>
                                    </c:if>
                                    <c:if test="${(client.contract.contractOptions
                                                    .stream()
                                                    .filter(contractOption -> contractOption.idOption == tariffOption.idOption)
                                                    .count() == 0)}">
                                        <div class="form-check">
                                            <input name="optionsUpdated" class="form-check-input" type="checkbox"
                                                   value="${tariffOption.idOption}"
                                                   id="option_${tariffOption.idOption}">
                                            <label class="form-check-label" for="option_${tariffOption.idOption}">
                                                    ${tariffOption.name}
                                                <small class="text-muted">Additional options: <c:forEach
                                                        items="${tariffOption.additionalOptions}" var="optionAdd">
                                                    ${optionAdd.name} </c:forEach></small>
                                                <div class="small">Price: ${tariffOption.price} EUR</div>
                                            </label>
                                        </div>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${error != null}">
                                    <div class="row alert alert-danger hidden">${error}</div>
                                </c:if>


                                <button class="btn btn-outline-primary"
                                        formaction="/client/updateOptions/${client.idClient}/${client.contract.idContract}"
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
                                        <input name="optionsUpdated" class="form-check-input" type="checkbox"
                                               value="${tariffOption.idOption}" id="option_${tariffOption.idOption}"
                                               checked>
                                        <label class="form-check-label" for="option_${tariffOption.idOption}">
                                                ${tariffOption.name}
                                            <small class="text-muted">Additional options: <c:forEach
                                                    items="${tariffOption.additionalOptions}" var="optionAdd">
                                                ${optionAdd.name} </c:forEach></small>
                                            <div class="small">Price: ${tariffOption.price} EUR</div>
                                        </label>
                                    </div>
                                </c:if>
                                <c:if test="${(client.contract.contractOptions
                                                    .stream()
                                                    .filter(contractOption -> contractOption.idOption == tariffOption.idOption)
                                                    .count() == 0)}">
                                    <div class="form-check">
                                        <input name="optionsUpdated" class="form-check-input" type="checkbox"
                                               value="${tariffOption.idOption}" id="option_${tariffOption.idOption}">
                                        <label class="form-check-label" for="option_${tariffOption.idOption}">
                                                ${tariffOption.name}
                                            <small class="text-muted">Additional options: <c:forEach
                                                    items="${tariffOption.additionalOptions}" var="optionAdd">
                                                ${optionAdd.name} </c:forEach></small>
                                            <div class="small">Price: ${tariffOption.price} EUR</div>
                                        </label>
                                    </div>
                                </c:if>
                            </c:forEach>
                            <c:if test="${error != null}">
                                <div class="row alert alert-danger hidden">${error}</div>
                            </c:if>


                            <button class="btn btn-outline-primary"
                                    formaction="/client/updateOptions/${client.idClient}/${client.contract.idContract}"
                                    type="submit">
                                Save options
                            </button>
                        </c:if>
                    </form:form>

                </c:otherwise>
            </c:choose>
        </div>

    </div>
</div>
</body>
</html>
