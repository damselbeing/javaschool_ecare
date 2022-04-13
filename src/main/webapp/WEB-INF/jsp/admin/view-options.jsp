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
        <br>
        <div class="container">
            <div class="row row-cols-3">
                <div class="col">
                    <div>Here can be a new option...</div>

                    <div class="col-6">
                        <form:form method="post" modelAttribute="newOption" action="/admin/addOption">

                            <div class="form-group">
                                <input name="name" class="form-control form-control-sm" type="text" placeholder="Option name" id="formID1">
                                <label class="form-label" for="formID1"></label>
                            </div>
                            <div class="form-group">
                                <input name="price" class="form-control form-control-sm" type="number" min="0" placeholder="Option price" id="formID2">
                                <label class="form-label" for="formID2"></label>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary btn-sm" type="submit" disabled>Add option</button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
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
                                <c:if test="${(option.contracts.size() == 0)}">
                                <a class="btn btn-outline-primary"
                                        href="/admin/optionProfile/${option.idOption}">
                                    Edit option
                                </a>
                                </c:if>
                                <c:if test="${(option.contracts.size() > 0)}">
                                    <div class="btn btn-outline-danger">
                                        No editing!
                                    </div>
                                </c:if>
                            </form:form>

                            <br>
                        </div>
                    </c:forEach>




                    <div class="col">
                        <div>Here can be a new option...</div>

                        <div class="col-6">
                            <form:form method="post" modelAttribute="newOption" action="/admin/addOption">

                                <div class="form-group">
                                    <input name="name" class="form-control form-control-sm" type="text" placeholder="Option name" id="formID1">
                                    <label class="form-label" for="formID1"></label>
                                </div>
                                <div class="form-group">
                                    <input name="price" class="form-control form-control-sm" type="number" min="0" placeholder="Option price" id="formID2">
                                    <label class="form-label" for="formID2"></label>
                                </div>
                                <div class="form-group">
                                    <button class="btn btn-primary btn-sm" type="submit" disabled>Add option</button>
                                </div>
                            </form:form>
                        </div>
                    </div>





                </div>

            </div>
        </c:if>

    </div>

</body>
</html>