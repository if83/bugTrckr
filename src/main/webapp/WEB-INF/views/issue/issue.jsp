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
    <sec:authorize access="isAuthenticated()">
        <a href="<spring:url value='/issue/add/' />" class="btn btn-default pull-left">
           Add Issue</a>
    </sec:authorize>
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

<div class="margin-top-30 row">
    <div class="col-sm-12">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs">
            <li role="presentation" class="active">
                <a href="#allIssuesTab" role="tab" data-toggle="tab">Issues</a>
            </li>
            <li role="presentation">
                <a href="#personalIssuesTab" role="tab" data-toggle="tab">My Issues</a>
            </li>
        </ul>
    </div>
</div>

<div class="tab-content">
    <div role="tabpanel" class="tab-pane fade in active" id="allIssuesTab">
        <div class="margin-top-30">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th class="text-center">Issue name</th>
                    <th class="text-center">Type</th>
                    <th class="text-center">Priority</th>
                    <th class="text-center">Status</th>
                    <th class="text-center">Project</th>
                    <th class="text-center">Release version</th>
                    <th class="text-center">Assigned User</th>
                    <th class="text-center">Estimate time, hrs</th>
                    <sec:authorize access="isAuthenticated()">
                    <th class="text-center">Actions</th>
                    </sec:authorize>
                </tr>

                </thead>
                <c:forEach var="issue" items="${listOfIssues.content}">
                    <sec:authorize access="@issueSecurityService.hasPermissionToViewIssue(${issue.id})">
                        <tr>
                            <td class="text-center">

                                    <a class="viewLink"
                                <sec:authorize access="isAuthenticated()">
                                       href="<spring:url value='issue/${issue.id}'/>"
                                </sec:authorize> >
                                            ${issue.title}
                                    </a>

                            </td>
                            <td class="text-center"><c:out value="${issue.type}"/></td>
                            <td class="text-center"><c:out value="${issue.priority}"/></td>
                            <td class="text-center"><c:out value="${issue.status}"/></td>
                            <td class="text-center">
                                <a class="viewLink"
                                   href="<spring:url value='projects/project/${issue.project.id}'/>">
                                        ${issue.project.title}
                                </a>
                            </td>
                            <td class="text-center">
                                <a class="viewLink"
                                   href="<spring:url value='/project/${issue.projectRelease.project.id}/release/${issue.projectRelease.id}'/>">
                                        ${issue.projectRelease.version}
                                </a>
                            </td>

                            <td class="text-center">
                                <a class="viewLink" href="<spring:url value='/user/${issue.assignee.id}/view'/>">
                                        ${issue.assignee.firstName} ${issue.assignee.lastName}
                                </a>
                            </td>
                            <td class="text-center"><c:out value="${issue.estimateTime}"/></td>
                            <td class="text-center">
                                <div class="actionButtons">
                                    <sec:authorize access="hasAnyRole('ADMIN', 'PROJECT_MANAGER','DEVELOPER', 'QA')">
                                        <a href="<spring:url value='/issue/${issue.id}/worklog' />"><i
                                                class="fa fa-hourglass-half icon-table-u"></i></a>
                                    </sec:authorize>
                                    <sec:authorize access="@issueSecurityService.hasPermissionToEditIssue(${issue.id})">
                                                <a href="<spring:url value='/issue/${issue.id}/edit' />"><i
                                                        class="fa fa-edit icon-table-u"></i></a>
                                    </sec:authorize>
                                    <sec:authorize access="@issueSecurityService.hasPermissionToRemoveIssue(${issue.id})">
                                        <a data-toggle="modal" data-target="#removeModal${issue.id}">
                                            <i class="fa fa-trash fa-lg icon-table-u"></i></a>
                                    </sec:authorize>

                                    <!-- Modal confirmation for removing issue-->
                                    <div class="modal fade" id="removeModal${issue.id}" tabindex="-1" role="dialog">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close"><span aria-hidden="true">&times;</span>
                                                    </button>
                                                    <h4 class="modal-title pull-left">Removal</h4>
                                                </div>
                                                <div class="modal-body">
                                                    Please conform the deleting of <strong>${issue.title}</strong> ?
                                                </div>
                                                <div class="modal-footer">
                                                    <button class="btn btn-default" data-dismiss="modal">Cancel</button>
                                                    <a href="<spring:url value='/issue/${issue.id}/remove' />"
                                                       class="btn btn-u">Confirm</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </sec:authorize>
                </c:forEach>
            </table>
        </div>
        <c:if test="${listOfIssues.getTotalPages()> 1}">
            <div class="row col-sm-offset-4 col-sm-4">
                <nav aria-label="Page navigation" id="pagerID">
                    <div class="text-center">
                        <ul class="pagination">
                            <li>
                                <a href="<spring:url value='/issue?page=0'/>" aria-label="Start">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach var="page" begin="0" end="${listOfIssues.getTotalPages() - 1}">
                                <li>
                                    <a href="<spring:url value='/issue?page=${page}'/>">${page + 1}</a>
                                </li>
                            </c:forEach>
                            <li>
                                <a href="<spring:url value='/issue?page=${listOfIssues.getTotalPages() - 1}'/>"
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
    <div role="tabpanel" class="tab-pane fade" id="personalIssuesTab">
        <div class="margin-top-30">
            <table class="table table-hover table-striped">

                <thead>
                <tr>
                    <th class="text-center">Issue name</th>
                    <th class="text-center">Type</th>
                    <th class="text-center">Priority</th>
                    <th class="text-center">Status</th>
                    <th class="text-center">Project</th>
                    <th class="text-center">Release version</th>
                    <th class="text-center">Assigned User</th>
                    <th class="text-center">Estimate time, hrs</th>
                    <th class="text-center">Actions</th>
                </tr>
                </thead>
                <sec:authorize access="hasAnyRole('DEVELOPER', 'QA', 'PROJECT_MANAGER')">
                    <c:forEach var="userIssue" items="${userIssues}">

                        <tr>
                            <td class="text-center">
                                <a class="viewLink"
                                   href="<spring:url value='issue/${userIssue.id}'/>">
                                        ${userIssue.title}
                                </a>
                            </td>
                            <td class="text-center"><c:out value="${userIssue.type}"/></td>
                            <td class="text-center"><c:out value="${userIssue.priority}"/></td>
                            <td class="text-center"><c:out value="${userIssue.status}"/></td>
                            <td class="text-center">
                                <a class="viewLink"
                                   href="<spring:url value='projects/project/${userIssue.project.id}'/>">
                                        ${userIssue.project.title}
                                </a>
                            </td>
                            <td class="text-center">
                                <a class="viewLink"
                                   href="<spring:url value='/project/
                                   ${userIssue.projectRelease.project.id}/release/${userIssue.projectRelease.id}'/>">
                                        ${userIssue.projectRelease.version}
                                </a>
                            </td>

                            <td class="text-center">
                                <a class="viewLink" href="<spring:url value='/user/${userIssue.assignee.id}/view'/>">
                                        ${userIssue.assignee.firstName} ${userIssue.assignee.lastName}
                                </a>
                            </td>
                            <td class="text-center"><c:out value="${userIssue.estimateTime}"/></td>
                            <td class="text-center">
                                <div class="actionButtons">
                                    <sec:authorize access="hasAnyRole('ADMIN', 'PROJECT_MANAGER','DEVELOPER', 'QA')">
                                        <a href="<spring:url value='/issue/${userIssue.id}/worklog' />"><i
                                                class="fa fa-hourglass-half icon-table-u"></i></a>
                                        &nbsp
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ADMIN', 'PROJECT_MANAGER')
                                    or hasAnyRole('DEVELOPER', 'QA') and ${userIssue.editAbility}">
                                        <a href="<spring:url value='/issue/${userIssue.id}/edit' />"><i
                                                class="fa fa-edit icon-table-u"></i></a>
                                        &nbsp
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ADMIN', 'PROJECT_MANAGER','DEVELOPER', 'QA')">
                                        <a data-toggle="modal" data-target="#removeModalNew${userIssue.id}">
                                            <i class="fa fa-trash fa-lg icon-table-u"></i></a>
                                    </sec:authorize>

                                    <!-- Modal confirmation for removing issue-->
                                    <div class="modal fade" id="removeModalNew${userIssue.id}" tabindex="-1" role="dialog">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close"><span aria-hidden="true">&times;</span>
                                I                    </button>
                                                    <h4 class="modal-title pull-left">Removal</h4>
                                                </div>
                                                <div class="modal-body">
                                                    Please conform the deleting of <b>${userIssue.title}</b> ?
                                                </div>
                                                <div class="modal-footer">
                                                    <button class="btn btn-default" data-dismiss="modal">Cancel</button>
                                                    <a href="<spring:url value='/issue/${userIssue.id}/remove' />"
                                                       class="btn btn-u">Confirm</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </sec:authorize>
            </table>
        </div>
    </div>
</div>
