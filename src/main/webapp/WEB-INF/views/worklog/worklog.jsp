<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
</div>

<div class="col-sm-6">
    <%--worklog aggregated data--%>
    <div class="col-sm-10 pull-right">
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
                <td>${parsedDueDate}</td>
            </tr>
            </tbody>
        </table>

        <%--worklog form--%>
        <div class="margin-top-30">
            <sec:authorize access="hasRole('${permissionToUseWorkLogForm}')">
                <form:form action="${action}" modelAttribute="workLog" method="POST">
                    <div class="col-sm-8">
                        <spring:bind path="startDate">
                            <div class="margin-top-10">
                                <label for="startDate">Start date</label>
                                <form:input path="startDate" type="text" class="form-control" id="startDate"
                                            value="${startDate}" placeholder="yyyy-MM-dd"/>
                                <form:errors path="startDate" class="control-label"/>
                            </div>
                        </spring:bind>

                        <spring:bind path="endDate">
                            <div class="margin-top-20">
                                <label for="endDate">End date</label>
                                <form:input path="endDate" type="text" class="form-control" id="endDate"
                                            value="${endDate}" placeholder="yyyy-MM-dd"/>
                                <form:errors path="endDate" class="control-label"/>
                            </div>
                        </spring:bind>

                        <spring:bind path="amountOfTime">
                            <div class="margin-top-20">
                                <label for="amountOfTime">Amount (1-8 hrs per day range)</label>
                                    <%--may specify user or project workday duration instead--%>
                                <form:input path="amountOfTime" type="text" class="form-control" id="amountOfTime"
                                            placeholder="Type your amount here" value="${worklog.amountOfTime}"/>
                                <form:errors path="amountOfTime" class="control-label"/>
                            </div>
                        </spring:bind>
                    </div>
                    <form:hidden path="user"/>
                    <form:hidden path="issue"/>
                    <form:hidden path="id"/>
                    <div class="col-sm-10 col-sm-offset-1">
                        <input type="submit" value="Submit" class="margin-top-30 btn-u pull-right"/>
                    </div>
                </form:form>
            </sec:authorize>
            <c:choose>
                <c:when test="${permissionToUseWorkLogForm == null}">
                    <h4 class="margin-top-30 text-center ">You have no permissions to add worklog</h4>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>


<%--detailed data--%>
<%--TODO: prokhorenkovkv worklog pagination in users profile--%>
<%--TODO: prokhorenkovkv datepicker--%>
<%--TODO: prokhorenkovkv add frontend validation--%>
<div class="col-sm-6">
    <div class="margin-top-30 col-sm-10 pull-left">
        <table class="table table-hover">
            <thead>
            <tr>
                <th class="text-center">User</th>
                <th class="text-center">Work period</th>
                <th class="text-center">Logged</th>
                <th><%--Edit--%></th>
                <th><%--Remove--%></th>

            </tr>
            </thead>
            <tbody>
            <c:forEach var="workLogIterator" items="${workLogsOfCurrentIssueByAllUsers.content}">
                <tr class="text-center">
                    <td>
                        <a href="<spring:url value='/user/${workLogIterator.user.id}/view'/>">
                                ${workLogIterator.user.firstName} ${workLogIterator.user.lastName}</a>
                    </td>
                    <td>${workLogIterator.startDate} - ${workLogIterator.endDate}</td>
                    <td>${workLogIterator.amountOfTime} hrs</td>
                    <c:choose>
                        <c:when test="${currentUser == workLogIterator.user}">
                            <td>
                                <a href="<spring:url value='/issue/${workLogIterator.issue.id}/worklog/${workLogIterator.id}/edit' />"><i
                                        class="fa fa-edit icon-table-u"></i></a>
                            </td>
                            <td>
                                <a href="<spring:url value='/issue/${workLogIterator.issue.id}/worklog/${workLogIterator.id}/remove' />"><i
                                        class="fa fa-remove icon-table-u"></i></a>
                            </td>
                            <c:set var="permissionToCreate" value="${currentUser.role}"/>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <i class="fa fa-lock icon-table-u"></i>
                            </td>
                            <td></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <c:if test="${workLogsOfCurrentIssueByAllUsers.getTotalPages()> 1}">
        <div class="row col-sm-offset-4 col-sm-4">
            <nav aria-label="Page navigation" id="pagerID">
                <div class="text-center">
                    <c:choose>
                        <c:when test="${stage == 'new'}">
                            <c:set var="paginationLink" value="../${issue.id}/worklog"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="paginationLink" value="edit"/>
                        </c:otherwise>
                    </c:choose>
                    <ul class="pagination">
                        <li>
                            <a href="<spring:url value='${paginationLink}?page=0'/>" aria-label="Start">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <c:forEach var="page" begin="0"
                                   end="${workLogsOfCurrentIssueByAllUsers.getTotalPages() - 1}">
                            <li>
                                <a href="<spring:url value='${paginationLink}?page=${page}'/>">${page + 1}</a>
                            </li>
                        </c:forEach>
                        <li>
                            <a href="<spring:url value='${paginationLink}?page=${workLogsOfCurrentIssueByAllUsers.getTotalPages() - 1}'/>"
                               aria-label="End">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </c:if>
</div>