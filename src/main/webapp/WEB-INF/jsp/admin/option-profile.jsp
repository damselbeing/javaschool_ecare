<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Option Profile</title>
    <jsp:include page="../head.jsp"></jsp:include>
</head>
<body>
    <header>
        <jsp:include page="header.jsp"></jsp:include>
    </header>
    <div class="container">
        <h1>Option profile</h1>
        <br>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-4">
                <div class="h5">Option name: ${option.name}</div>
                <form:form>
                    <button class="btn btn-outline-primary"
                            formaction="/admin/changeName/${option.idOption}"
                            type="submit" disabled>
                        Change name
                    </button>
                </form:form>
                <div class="h5">Option price: ${option.price}</div>
                <form:form>
                    <button class="btn btn-outline-primary"
                            formaction="/admin/changePrice/${option.idOption}"
                            type="submit" disabled>
                        Change price
                    </button>
                </form:form>
            </div>

            <form:form class="container col-8">
                <div class="row">
                    <div class="col">
                <div class="h5">Additional options for option: ${option.name}</div>

                <c:forEach items="${optionsTotal}" var="optionOfTotal">
                    <c:if test="${
                                (option.additionalOptions
                                        .stream()
                                        .filter(option -> option.idOption == optionOfTotal.idOption)
                                        .count() > 0)
                                && (optionOfTotal.idOption != option.idOption)}">
                            <div class="form-check">
                                <input name="optionsAdditional" class="form-check-input" type="checkbox" value="${optionOfTotal.idOption}" id="option_${optionOfTotal.idOption}" checked>
                                <label class="form-check-label" for="option_${optionOfTotal.idOption}">
                                        ${optionOfTotal.name}
                                </label>
                            </div>
                        </c:if>
                        <c:if test="${
                                (option.additionalOptions
                                        .stream()
                                        .filter(option -> option.idOption == optionOfTotal.idOption)
                                        .count() == 0)
                                && (optionOfTotal.idOption != option.idOption)}">
                            <div class="form-check">
                                <input name="optionsAdditional" class="form-check-input" type="checkbox" value="${optionOfTotal.idOption}" id="option_${optionOfTotal.idOption}">
                                <label class="form-check-label" for="option_${optionOfTotal.idOption}">
                                        ${optionOfTotal.name}
                                </label>
                            </div>
                        </c:if>
                    <c:if test="${optionOfTotal.idOption == option.idOption}">
                        <div class="form-check">
                            <input name="optionsAdditional" class="form-check-input" type="checkbox" value="${optionOfTotal.idOption}" id="option_${optionOfTotal.idOption}" disabled>
                            <label class="form-check-label" for="option_${optionOfTotal.idOption}">
                                    ${optionOfTotal.name}
                            </label>
                        </div>
                    </c:if>
                </c:forEach>

                    </div>

                        <div class="col">
                <div class="h5">Conflicting options for option: ${option.name}</div>

                    <c:forEach items="${optionsTotal}" var="optionOfTotal">
                        <c:if test="${
                                (option.conflictingOptions
                                        .stream()
                                        .filter(option -> option.idOption == optionOfTotal.idOption)
                                        .count() > 0)
                                && (optionOfTotal.idOption != option.idOption)}">
                            <div class="form-check">
                                <input name="optionsConflicting" class="form-check-input" type="checkbox" value="${optionOfTotal.idOption}" id="option_${optionOfTotal.idOption}" checked>
                                <label class="form-check-label" for="option_${optionOfTotal.idOption}">
                                        ${optionOfTotal.name}
                                </label>
                            </div>
                        </c:if>
                        <c:if test="${
                                (option.conflictingOptions
                                        .stream()
                                        .filter(option -> option.idOption == optionOfTotal.idOption)
                                        .count() == 0)
                                && (optionOfTotal.idOption != option.idOption)}">
                            <div class="form-check">
                                <input name="optionsConflicting" class="form-check-input" type="checkbox" value="${optionOfTotal.idOption}" id="option_${optionOfTotal.idOption}">
                                <label class="form-check-label" for="option_${optionOfTotal.idOption}">
                                        ${optionOfTotal.name}
                                </label>
                            </div>
                        </c:if>
                        <c:if test="${optionOfTotal.idOption == option.idOption}">
                            <div class="form-check">
                                <input name="optionsConflicting" class="form-check-input" type="checkbox" value="${optionOfTotal.idOption}" id="option_${optionOfTotal.idOption}" disabled>
                                <label class="form-check-label" for="option_${optionOfTotal.idOption}">
                                        ${optionOfTotal.name}
                                </label>
                            </div>
                        </c:if>
                    </c:forEach>

                        </div>
                    
                </div>

                <c:if test="${error != null}">
                <div class="row alert alert-danger hidden">${error}</div>
                </c:if>

                <div class="row alert alert-secondary">Please note that after any option changes some tariffs may need editing as well! Check tariffs list in the end!</div>

<%--                <c:if test="${error == null}">--%>
<%--                    <div class="row alert alert-secondary">Please note that in case of any option changes--%>
<%--                        some tariffs must be edited as well!</div>--%>
<%--                </c:if>--%>
                <button class="btn btn-outline-primary"
                        formaction="/admin/updateOption/${option.idOption}"
                        type="submit">
                    Save
                </button>


                </form:form>
        </div>
    </div>
</body>
</html>