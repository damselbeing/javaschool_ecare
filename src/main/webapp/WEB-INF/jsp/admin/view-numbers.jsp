<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>New Contract</title>
    <jsp:include page="../head.jsp"></jsp:include>
</head>
<body>
<header>
    <jsp:include page="header.jsp"></jsp:include>
</header>
    <div class="container">
        <h1>New contract</h1>
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
                <br>
                <div>
                    <span class="h6">Contract: </span>no contract found</span>
                </div>
                <div>
                    <span class="h6">Tariff: </span>no tariff found</span>
                </div>
            </div>



            <div class="col-3">
                <div class="h4">Please choose one number:</div>
                <form:form>
                    <c:forEach items="${numbers}" var="number">


                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="number" value="${number}" id="number_${number}">
                            <label class="form-check-label" for="number_${number}">
                                    ${number}
                            </label>

                        </div>

                    </c:forEach>
                    <br>
<%--                    <button class="btn btn-primary btn-sm"--%>
<%--                            formaction="/admin/updateTariff/${client.idClient}/${client.contract.idContract}"--%>
<%--                            type="submit">--%>
<%--                        Save--%>
<%--                    </button>--%>

                </form:form>
            </div>




        </div>
    </div>
</body>
</html>
