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
                        <div>Tariff name: ${tariff.name}</div>
                        <div>Tariff price: ${tariff.price}</div>
                        <div>Tariff options: <c:forEach items="${tariff.options}" var="option">
                                ${option.name} </c:forEach></div>
                        <form:form>
                        <button class="btn btn-outline-primary"
                                formaction="/welcome"
                                type="submit">
                            Archive tariff
                        </button>
                        </form:form>
                    </div>
                </c:forEach>
                <div class="col">
                    <div>Here can be a new tariff...</div>
                    <form:form>
                        <button class="btn btn-outline-primary"
                                formaction="/welcome"
                                type="submit">
                            Add tariff
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </c:if>
    </div>
</body>
</html>