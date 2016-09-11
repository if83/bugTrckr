<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left">Issues</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li class="active">Issues</li>
            </ol>
        </div>
    </div>
</div>

<c:if test="${not empty msg}">
    <div class="row">
        <div class="col-sm-4 col-sm-offset-8">
            <div class="alert alert-${css} alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <strong>${msg}</strong>
            </div>
        </div>
    </div>
</c:if>

<div class="col-sm-2 col-sm-offset-1">
    <a href="<spring:url value='/issue/add/' />" class="btn btn-primary btn-u pull-left">
        <i class="fa fa-plus icon-bg-u"></i>Add Issue</a>
</div>

<div class="col-sm-4 col-sm-offset-1">
    <form action="/issue/search" method="POST">
        <div class="input-group">
            <input name="title" type="text" class="form-control form-text" placeholder="Search By Issue's Title"/>
            <span class="input-group-btn"><button type="submit" class="btn btn-default">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span></button></span>
        </div>
    </form>
</div>

<div class=class="margin-top-30">
    <table class="table table-hover">

        <thead>
        <tr>
            <th>Issue name</th>
            <th>Type</th>
            <th>Priority</th>
            <th>Status</th>
            <th>Project</th>
            <th>Release version</th>
            <th>Assigned User</th>
            <th>Create time</th>
            <th>Finish time</th>
            <th>Last updated</th>
            <th>Estimate time, hrs</th>
            <th><%--Actions--%></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="issue" items="${listOfIssues.content}">
            <tr>
                <td>
                    <a class="viewLink"
                       href="<spring:url value='issue/${issue.id}'/>">
                            ${issue.title}
                    </a>
                </td>
                <td><c:out value="${issue.type}"/></td>
                <td><c:out value="${issue.priority}"/></td>
                <td><c:out value="${issue.status}"/></td>
                <td>
                    <a class="viewLink"
                       href="<spring:url value='projects/project/${issue.projectRelease.project.id}'/>">
                            ${issue.projectRelease.project.title}
                    </a>
                </td>
                <td>
                    <a class="viewLink"
                       href="<spring:url value='/project/${issue.projectRelease.project.id}/release/${issue.projectRelease.id}'/>">
                            ${issue.projectRelease.version}
                    </a>
                </td>

                <td>
                    <a class="viewLink" href="<spring:url value='/user/${issue.assignee.id}/view'/>">
                            ${issue.assignee.firstName} ${issue.assignee.lastName}
                    </a>
                </td>
                <td><c:out value="${issue.createTime}"/></td>
                <td><c:out value="${issue.dueDate}"/></td>
                <td><c:out value="${issue.lastUpdateDate}"/></td>
                <td><c:out value="${issue.estimateTime}"/></td>
                <td>
                    <div class="actionButtons">
                        <sec:authorize access="hasAnyRole('ADMIN', 'PROJECT_MANAGER','DEVELOPER', 'QA')">
                        <a href="<spring:url value='/issue/${issue.id}/worklog' />"><i
                                class="fa fa-hourglass-half icon-table-u"></i></a>
                        &nbsp
                        </sec:authorize>
                        <sec:authorize access="hasAnyRole('ADMIN', 'PROJECT_MANAGER') or
                        (hasAnyRole('DEVELOPER', 'QA') and @issueService.findById(${issue.id}).editAbility)">
                        <a href="<spring:url value='/issue/${issue.id}/edit' />"><i
                                class="fa fa-edit icon-table-u"></i></a>
                        &nbsp
                        <a href="<spring:url value='/issue/${issue.id}/remove' />"><i
                                class="fa fa-remove icon-table-u"></i></a>
                        </sec:authorize>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
