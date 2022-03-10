<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>View Profile</title>
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
            <div class="col">
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

    <div class="col">
        <div class="h5">Available options for tariff: ${contract.tariff.name}</div>
        <c:forEach items="${contract.tariff.options}" var="option">
            <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault">
                <label class="form-check-label" for="flexSwitchCheckDefault">${option.name}</label>
            </div>
        </c:forEach>
    </div>
        </div>
    </div>
</body>
</html>