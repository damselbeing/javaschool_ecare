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

            <div class="col-4">
                <div class="h4">Actual tariffs:</div>
                <form:form>
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
                        Save tariff
                    </button>
                </form:form>
            </div>

            <div class="col-4">
                <div class="h4">Available options:</div>
            </div>

        </div>
    </div>
</body>
</html>
