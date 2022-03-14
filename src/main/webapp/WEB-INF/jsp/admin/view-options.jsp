<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>View Options</title>
    <jsp:include page="../head.jsp"></jsp:include>
</head>
<body>
    <header>
        <jsp:include page="header.jsp"></jsp:include>
    </header>
    <div class="container">
        <h1>Options' list</h1>
        <br>
    </div>
    <div class="container">
    <c:if test="${options.size() == 0}">
        <h3>Found no option(s)</h3>
    </c:if>
    </div>

    <div class="container">

        <c:if test="${options.size() > 0}">

            <div class="container">

                <div class="row row-cols-3">
                    <c:forEach items="${options}" var="option">
                        <div class="col">


                            <div class="fw-bold">Option name: ${option.name}</div>
                            <div>Option price: ${option.price}</div>
                            <div>Additional options: <c:forEach items="${option.additionalOptions}" var="optionAdd">
                                    ${optionAdd.name} </c:forEach>
                            </div>
                            <div>Conflicting options: <c:forEach items="${option.conflictingOptions}" var="optionConf">
                                ${optionConf.name} </c:forEach>
                            </div>
                                    <form:form>

                                <a class="btn btn-outline-primary"
                                        href="/admin/updateOption/${option.idOption}">
                                    Edit option
                                </a>
                                    </form:form>

                            <br>
                        </div>
                    </c:forEach>
                        <div class="col">
                            <div>Here can be a new option...</div>

                                <button class="btn btn-outline-primary"
                                        formaction="/welcome"
                                        type="submit">
                                    Add option
                                </button>

                        </div>

                </div>

            </div>
        </c:if>

    </div>

</body>
</html>