<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%--breadcrumbs--%>
<div class="breadcrumbs">
    <div class="row">
        <div class="row">
            <div class="col-sm-2 col-sm-offset-1">
                <h1 class="pull-left">${issue.title} worklog </h1>
            </div>
            <div class="col-sm-8">
                <ol class="pull-right breadcrumb">
                    <li><a href="<spring:url value='/'/>">Home</a></li>
                    <li><a href="<spring:url value='/issue'/>">Issue</a></li>
                    <li class="active"> Worklog</li>
                </ol>
            </div>
        </div>
    </div>

    <%--worklog aggregated data--%>
    <div class="col-sm-4 col-sm-offset-1">
        <table class="table margin-top-30">
            <thead>
            <tr>
                <th class="text-center">Time left</th>
                <th class="text-center">Total spent time</th>
                <th class="text-center">Estimated time</th>
                <th class="text-center">Due date</th>
            </tr>
            </thead>
            <tbody>
            <tr class="text-center">
                <c:set var="leftTime" value="${issue.estimateTime - totalSpentTimeByAllUsers}"/>
                <td>${leftTime} hrs</td>
                <td>${totalSpentTimeByAllUsers} hrs</td>
                <td>${issue.estimateTime} hrs</td>
                <td>${issue.dueDate}</td>
            </tr>
            </tbody>
        </table>

        <%--worklog form--%>
        <div class="margin-top-30">
            <form:form action="${action}" modelAttribute="workLog" method="POST">
            <div class="col-sm-8 col-lg-offset-2">
                <spring:bind path="startTime">
                    <div class="form-group ${status.error ? 'has-error' : ''} margin-bottom-30">
                        <label for="startTime">Your workday date</label>
                        <form:input path="startTime" type="text" class="form-control" id="startTime"
                                    value="${date}"/>
                        <form:errors path="startTime" class="control-label"/>
                    </div>
                </spring:bind>

                <spring:bind path="amountOfTime">
                    <div class="form-group ${status.error ? 'has-error' : ''} margin-bottom-30">
                        <label for="amountOfTime">Your daily amount (1-8 hrs range)</label> <%--may specify user or project workday duration instead--%>
                        <form:input path="amountOfTime" type="text" class="form-control" id="amountOfTime"
                                    placeholder="Type your amount here" value="${worklog.amountOfTime}"/>
                        <form:errors path="amountOfTime" class="control-label"/>
                    </div>
                </spring:bind>
                <form:hidden path="user" value="${issue.assignee.id}"/>
                <form:hidden path="issue" value="${issue.id}"/>
                <form:hidden path="id" value="${id}"/>

                <div class="col-sm-10 col-sm-offset-1">
                    <input type="submit" value="Submit" class="margin-top-30 btn-u pull-right"/>
                </div>
                </form:form>
            </div>
        </div>
    </div>

    <%--detailed data--%>
    <div class="col-sm-6">
        <div class="margin-top-30">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-center">User</th>
                    <th class="text-center">Work date</th>
                    <th class="text-center">Logged</th>
                    <th><%--Edit--%></th>
                    <th><%--Remove--%></th>
                </tr>
                </thead>
                <tbody>
                <%--TODO: pagination--%>
                <c:forEach var="workLogIterator" items="${workLogsOfCurrentIssueByAllUsers}">
                    <tr class="text-center">
                        <td><a href="<spring:url value='/user/${workLogIterator.user.id}/view'/>">
                                ${workLogIterator.user.firstName} ${workLogIterator.user.lastName}
                        </a></td>
                        <td><c:out value="${workLogIterator.startTime}"/></td>
                        <td><c:out value="${workLogIterator.amountOfTime} hrs"/></td>
                        <td>
                            <a href="<spring:url value='/issue/${workLogIterator.issue.id}/worklog/${workLogIterator.id}/edit' />"><i
                                    class="fa fa-edit icon-table-u"></i></a>
                        </td>
                        <td>
                            <a href="<spring:url value='/issue/${workLogIterator.issue.id}/worklog/${workLogIterator.id}/remove' />"><i
                                    class="fa fa-remove icon-table-u"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <%--<c:if test="${isControllerPagable}">--%>
    <div class="row">
        <nav aria-label="Page navigation" id="pagerID">
            <div class="text-center">
                <ul class="pagination">
                    <li>
                        <a href="<spring:url value='/users?page=0'/>" aria-label="Start">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>

                    <c:forEach var="apage" begin="0" end="${userList.totalPages - 1}">
                        <li>
                            <a href="<spring:url value='/users?page=${apage}'/>"> <c:out value="${apage + 1}"/></a>
                        </li>
                    </c:forEach>
                    <li>
                        <a href="<spring:url value='/users?page=${userList.totalPages - 1}'/>" aria-label="End">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
    <%--</c:if>--%>
</div>