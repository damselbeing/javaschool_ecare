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
    </div>
    <div class="container">
            <table class="table">
                <tbody>
                    <tr>
                        <td>Contract number</td>
                        <td>${contract.number}</td>
                        <td>
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
                        </td>
                    </tr>
                    <tr>
                        <td>Tariff name</td>
                        <td>tariff1</td>
                        <td>
                            <button class="btn btn-outline-primary"
                                    formaction="/admin/changeTariff/${contract.idContract}"
                                    type="submit">
                                Change tariff
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
    </div>
</body>
</html>