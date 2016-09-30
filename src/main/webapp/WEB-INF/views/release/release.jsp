<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-12">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/' />">Home</a></li>
                <li><a href="<spring:url value='/projects'/>">Projects</a></li>
                <li><a href="<spring:url value='/projects/project/${release.project.id}'/>">${release.project.title}</a>
                </li>
                <li class="active">release ${release.version}</li>
            </ol>
        </div>
    </div>
</div>

<div class="container">

    <div class="row">
        <%--Release description--%>
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1 text-left">
                <div class="release-name">
                    Release: ${release.version}
                    <sec:authorize access="@releaseSecurityService.hasPermissionToEditRelease('${release.id}')">
                        <small>
                            <a class="viewLink"
                               href="<spring:url value='/project/${release.project.id}/release/${release.id}/edit'/>">[edit]</a>
                        </small>
                    </sec:authorize>
                </div>
                <div class="project-name">
                    Project:
                    <a class="viewLink"
                       href="<spring:url value='/projects/project/${release.project.id}'/>">${release.project.title}</a>
                </div>
                <div class="status-name">Status: ${release.releaseStatus.toString()}</div>
                <div class="release-description">Release description:</div>
                <p>${release.description}</p>
            </div>
        </div>
        <%--Issues in release--%>
        <div class="row">
            <div class="release-issues col-sm-10 col-sm-offset-1">
                <h3>Issues</h3>
                <div class="row issues-panel">
                    <div class="col-sm-7">
                        <%--search panel--%>
                        <form action="/project/${release.project.id}/release/${release.id}/issuesSearch" method="POST"
                              class="form-inline">
                            <div class="input-group">
                                <input type="text" class="form-control" name="searchedString"
                                       placeholder="Search...">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="submit">
                                    <span class="glyphicon glyphicon-search"></span>
                                </button>
                            </span>
                            </div>
                        </form>
                    </div>
                    <%--add issue button--%>
                    <div class="col-sm-5">
                    <sec:authorize access="@releaseSecurityService.hasPermissionToEditRelease('${release.id}')">
                        <a href="<spring:url value='/issue/add'/>"
                           class="btn btn-default pull-right"><i class="fa fa-plus"></i> Add issue</a>
                    </sec:authorize>
                    </div>
                </div>
                <%--issues table--%>
                    <table class="table-issues table table-hover table-bordered text-center">
                    <thead>
                    <tr>
                        <th class="text-center">Title</th>
                        <th class="text-center">Status</th>
                        <th class="text-center">Assigne to</th>
                        <sec:authorize access="@releaseSecurityService.hasPermissionToEditRelease('${release.id}')">
                            <th class="text-center">Actions</th>
                        </sec:authorize>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="issue" items="${issueList.content}">
                        <tr>
                            <input name="issueId" type="hidden" value="${issue.id}"/>
                            <input name="issueTitle" type="hidden" value="${issue.title}"/>
                            <td>
                                <sec:authorize access="@issueSecurityService.hasPermissionToEditIssue('${issue.id}')">
                                    <a class="viewLink" href="<spring:url value='/issue/${issue.id}'/>">${issue.title}</a>
                                </sec:authorize>
                                <sec:authorize access="!@issueSecurityService.hasPermissionToEditIssue('${issue.id}')">
                                    ${issue.title}
                                </sec:authorize>
                            </td>
                            <td>
                                <sec:authorize access="@issueSecurityService.hasPermissionToEditIssue('${issue.id}')">
                                    <select class="statuses-dropdown selectpicker" data-width="100%">
                                        <option selected="selected"
                                                value="${issue.status}">${issue.status.toString()}</option>
                                    </select>
                                </sec:authorize>
                                <sec:authorize access="!@issueSecurityService.hasPermissionToEditIssue('${issue.id}')">
                                    ${issue.status.toString()}
                                </sec:authorize>
                            </td>
                            <td>
                                <sec:authorize access="@issueSecurityService.hasPermissionToEditIssue('${issue.id}')">
                                    <select class="users-dropdown selectpicker" data-live-search="true" data-width="100%">
                                        <option selected="selected"
                                                value="${issue.assignee.id}">${issue.assignee.firstName} ${issue.assignee.lastName}</option>
                                        <c:forEach var="user" items="${users}">
                                            <option value="${user.id}">${user.firstName} ${user.lastName}</option>
                                        </c:forEach>
                                    </select>
                                </sec:authorize>
                                <sec:authorize access="!@issueSecurityService.hasPermissionToEditIssue('${issue.id}')">
                                    ${issue.assignee.firstName} ${issue.assignee.lastName}
                                </sec:authorize>
                            </td>
                            <sec:authorize access="@releaseSecurityService.hasPermissionToEditRelease('${release.id}')">
                                <td>
                                    <a href="<spring:url value='/issue/${issue.id}/edit'/>"><i
                                            class="fa fa-edit fa-lg icon-table-u"></i></a>
                                    <a href="" data-toggle="modal" data-target="#removeIssueBtn-${issue.id}"><i
                                            class="fa fa-trash fa-lg icon-table-u"></i></a>
                                    <!-- Modal for confirmation remove issue from release-->
                                    <div class="modal fade" id="removeIssueBtn-${issue.id}" tabindex="-1"
                                         role="dialog" aria-labelledby="myModalLabel">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                    <h4 class="modal-title pull-left">Remove Issue from release</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <p>Confirm remove of ${issue.title}
                                                        from ${release.version}</p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button class="btn btn-default" data-dismiss="modal">
                                                        Cancel
                                                    </button>
                                                    <a href="<spring:url
                                                            value='/issue/${issue.id}/remove'/>"
                                                       class="btn btn-u">Remove
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </sec:authorize>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <%--Pagination of issue-table--%>
                <c:if test="${issueList.getTotalPages() gt 1}">
                    <nav aria-label="Page navigation" id="pagerID">
                        <div class="text-center">
                            <ul class="pagination">
                                <li>
                                    <a href="<spring:url value='/project/${release.project.id}/release/${release.id}?page=0'/>"
                                       aria-label="Start">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <c:forEach var="page" begin="0" end="${issueList.getTotalPages() - 1}">
                                    <li>
                                        <a href="<spring:url value='/project/${release.project.id}/release/${release.id}?page=${page}'/>">${page + 1}</a>
                                    </li>
                                </c:forEach>
                                <li>
                                    <a href="<spring:url value='/project/${release.project.id}/release/${release.id}?page=${issueList.getTotalPages() - 1}'/>"
                                       aria-label="End"><span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>
</div>

<!-- Popup for notifying of changing issue-->
<div class="modal fade" id="modalChangeIssue" tabindex="-1" data-backdrop="false"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-body text-center">
            </div>
        </div>
    </div>
</div>