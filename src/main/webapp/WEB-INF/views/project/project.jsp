<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-12">
            <h2 class="col-sm-offset-1 pull-left">${project.title}
                <sec:authorize access="@projectSecurityService.hasPermissionToProjectManagement('${project.id}')">
                    <small><a class="viewLink" href="<spring:url value='/projects/${project.id}/edit'/>">[edit]</a>
                    </small>
                </sec:authorize>
            </h2>
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/' />">Home</a></li>
                <li><a href="<spring:url value='/projects'/>">Projects</a></li>
                <li class="active"><a href="<spring:url value='/projects/project/${project.id}'/>">${project.title}</a></li>
            </ol>
        </div>
    </div>
</div>

<div class="container">
    <div class="row project-info text-left">
        <div class="col-sm-12">
            <h3>Project description</h3>
            ${project.description}
        </div>
    </div>

    <div class="text-left margin-bottom-20">
        <div class="row col-sm-12">
            <h3 class="pull-left">Project Manager
                <a class="viewLink"
                        <sec:authorize access="isAuthenticated()">
                            href="<spring:url value='/user/${projectManager.id}/view' />"
                        </sec:authorize>>
                    <c:out value="${projectManager.getFullName()}"/>
                </a>
                <sec:authorize access="hasRole('ADMIN')">
                    <c:choose>
                        <c:when test="${projectManager == null}">
                            <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject'/>"
                               class="btn btn-default"><i class="fa fa-plus"></i> Add PM</a>
                        </c:when>
                        <c:otherwise>
                            <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject'/>"
                               class="btn btn-default"><i class="fa fa-plus"></i> Change PM</a>
                        </c:otherwise>
                    </c:choose>
                </sec:authorize>
            </h3>
        </div>
        <h4>
            Email: ${projectManager.email}
        </h4>
    </div>

    <div class="row">

        <%--Releases in project--%>
        <div class="col-sm-5">
            <h3>Releases</h3>
            <div class="row releases-panel">
                <div class="col-sm-7">
                    <%--search panel--%>
                    <form action="/projects/project/${project.id}/releases/search" method="POST"
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
                <%--add release button--%>
                <div class="col-sm-5">
                    <sec:authorize access="@releaseSecurityService.hasPermissionToAddRelease('${project.id}')">
                        <a href="<spring:url value='/project/${project.id}/release/add'/>"
                           class="btn btn-default pull-right"><i class="fa fa-plus"></i> Add release</a>
                    </sec:authorize>
                </div>
            </div>
            <%--table of releases--%>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th class="text-center">Version</th>
                    <th class="text-center">Status</th>
                    <sec:authorize access="@projectSecurityService.hasPermissionToProjectManagement('${project.id}')">
                        <th class="text-center">Actions</th>
                    </sec:authorize>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="rel" items="${releaseList.content}">
                    <tr>
                        <td class="text-center">
                            <sec:authorize access="@releaseSecurityService.hasPermissionToViewRelease('${rel.id}')">
                                <a class="viewLink"
                                   href="<spring:url value='/project/${project.id}/release/${rel.id}'/>">${rel.version}
                                </a>
                            </sec:authorize>
                            <sec:authorize access="!@releaseSecurityService.hasPermissionToViewRelease('${rel.id}')">
                                ${rel.version}
                            </sec:authorize>
                        </td>
                        <td class="text-center">${rel.releaseStatus.toString()}</td>
                        <sec:authorize access="@projectSecurityService.hasPermissionToProjectManagement('${project.id}')">
                            <td class="text-center">
                                <a class="btn" href="<spring:url value='/project/${project.id}/release/${rel.id}/edit' />"><i
                                        class="fa fa-edit fa-lg icon-table-u"></i></a>
                                <a href="<spring:url value='/project/${project.id}/release/${rel.id}/remove'/>"
                                   class="btn removeReleaseBtn"><i class="fa fa-trash fa-lg icon-table-u"></i>
                                    <p class="removeReleaseNotification hidden">Confirm remove of ${rel.version}
                                        from ${project.title}</p>
                                </a>
                            </td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%--pagination of releses-table--%>
            <c:if test="${releaseList.getTotalPages() gt 1}">
                <nav aria-label="Page navigation" id="pagerID">
                    <div class="text-center">
                        <ul class="pagination">
                            <li>
                                <a href="<spring:url value='/projects/project/${project.id}?release_page=0'/>"
                                   aria-label="Start">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach var="page" begin="0" end="${releaseList.getTotalPages() - 1}">
                                <li>
                                    <a href="<spring:url value='/projects/project/${project.id}?release_page=${page}'/>">${page + 1}</a>
                                </li>
                            </c:forEach>
                            <li>
                                <a href="<spring:url value='/projects/project/${project.id}?release_page=${releaseList.getTotalPages() - 1}'/>"
                                   aria-label="End"><span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </c:if>
        </div>

        <%--Users in projects--%>
        <div class="col-sm-7">
            <h3>Users</h3>
            <div class="row users-panel">
                <div class="col-sm-11">
                    <%--Search form for users in project--%>
                    <form class="form-inline" action="/projects/project/${project.id}/users_search" method="POST">
                        <div class="input-group form-inline col-xs-11">
                            <select class="form-control form-inline search-by-name selectpicker" type="text"
                                    name="searchedParam">
                                <option value="">Option</option>
                                <option value="First Name">First Name</option>
                                <option value="Last Name">Last Name</option>
                            </select>
                            <select class="form-control form-inline search-by-role selectpicker" type="text" name="role">
                                <option value="">Any Role</option>
                                <option value="${DEV}">Developer</option>
                                <option value="${QA}">QA</option>
                            </select>
                            <input type="text" class="form-control searchedString" name="searchedString"
                                   placeholder="Search...">
                            <span class="input-group-btn pull-left">
                                <button class="btn btn-default" type="submit">
                                    <span class="glyphicon glyphicon-search"></span>
                                </button>
                            </span>
                        </div>
                    </form>
                </div>
                <%--add user button--%>
                <div class="col-sm-1">
                    <sec:authorize access="@projectSecurityService.hasPermissionToProjectManagement('${project.id}')">
                        <a href="<spring:url value='/projects/project/${project.id}/usersWithoutProject'/>"
                           class="btn btn-default pull-right"><i class="fa fa-plus"></i> Add User</a>
                    </sec:authorize>
                </div>
            </div>
            <%--Table of users in project--%>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th class="text-center">User name</th>
                    <th class="text-center">Role</th>
                    <sec:authorize access="@projectSecurityService.hasPermissionToProjectManagement('${project.id}')">
                        <th class="text-center">Actions</th>
                    </sec:authorize>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${usersList.content}">
                    <tr>
                        <td class="text-center">
                            <a class="viewLink"
                                    <sec:authorize access="isAuthenticated()">
                                        href="<spring:url value='/user/${user.id}/view' />"
                                    </sec:authorize>>
                                    ${user.firstName} ${user.lastName}
                            </a>
                        </td>
                        <td class="text-center">${user.role.toString()}</td>
                        <sec:authorize access="@projectSecurityService.hasPermissionToProjectManagement('${project.id}')">
                            <td class="text-center">
                                <!--Change of user's role-->
                                <a class="changeRoleBtn btn" href="<spring:url value='/projects/project/
                                    ${project.id}/usersWithoutProject/${user.id}/changeRole'/>">
                                    <i class="glyphicon glyphicon-retweet fa-lg icon-table-u"></i>
                                    <p class="changeRoleBtn hidden"> Confirm the change of <strong>
                                            ${user.fullName}</strong> role</p>
                                </a>

                                <!--Remove user from project -->
                                <a class="removeUserBtn" href="<spring:url value='/projects/project/
                                ${project.id}/removeUser/${user.id}'/>">
                                    <i class="fa fa-trash fa-lg icon-table-u btn"></i>
                                    <p class="removeUserNotification hidden">Confirm the removal of <strong>
                                    ${user.firstName}${user.lastName}</strong> from <strong>${project.title}</strong></p>
                                </a>
                            </td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%--pagination of user table--%>
            <c:if test="${usersList.getTotalPages()> 1}">
                <nav aria-label="Page navigation" id="pagerID">
                    <div class="text-center">
                        <ul class="pagination">
                            <li>
                                <a href="<spring:url value='/projects/project/${project.id}?user_page=0'/>"
                                   aria-label="Start">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach var="page" begin="0" end="${usersList.getTotalPages() - 1}">
                                <li>
                                    <a href="<spring:url
                                    value='/projects/project/${project.id}?user_page=${page}'/>">${page + 1}</a>
                                </li>
                            </c:forEach>
                            <li>
                                <a href="<spring:url
                                value='/projects/project/${project.id}?user_page=${usersList.getTotalPages() - 1}'/>"
                                   aria-label="End"><span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </c:if>
        </div>

        <%--Table of issues in project--%>
        <div class="margin-top-30 col-sm-12">
            <h3 class="margin-bottom-20">Issues</h3>
            <div class="row users-panel">
                <div class="col-sm-4">
                    <%--Search form for issues in project--%>
                    <form action="/projects/project/${project.id}/search_issues" method="POST">
                        <div class="input-group">
                            <input name="searchedString" type="text" class="form-control form-text"
                                   placeholder="Search By Issue's Title"/>
                            <span class="input-group-btn"><button type="submit" class="btn btn-default">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span></button></span>
                        </div>
                    </form>
                </div>
                <%--add issue button--%>
                <div class="col-sm-1">
                    <sec:authorize access="isAuthenticated() or (isAnonymous() and ${project.guestCreateIssues})">
                        <a href="<spring:url value='/issue/add/' />" class="btn btn-default pull-left">
                            <i class="fa fa-plus icon-bg"></i>Add Issue</a>
                    </sec:authorize>
                </div>
            </div>
            <table class="table table-hover table-bordered margin-top-20">
                <thead>
                <tr>
                    <th class="text-center">Issue name</th>
                    <th class="text-center">Type</th>
                    <th class="text-center">Status</th>
                    <th class="text-center">Release version</th>
                    <th class="text-center">Assigned User</th>
                </tr>
                </thead>
                <c:forEach var="issue" items="${listOfIssues.content}">
                    <tr>
                        <td class="text-center">
                            <a class="viewLink"
                                <sec:authorize access="isAuthenticated()">
                                    href="<spring:url value='/issue/${issue.id}'/>"
                                </sec:authorize> >
                                ${issue.title}
                            </a>
                        </td>
                        <td class="text-center"><c:out value="${issue.type}"/></td>
                        <td class="text-center"><c:out value="${issue.status}"/></td>
                        <td class="text-center">
                            <a class="viewLink"
                               href="<spring:url value='/project/${issue.projectRelease.project.id}/release/
                               ${issue.projectRelease.id}'/>">
                                    ${issue.projectRelease.version}
                            </a>
                        </td>
                        <td class="text-center">
                            <sec:authorize access="isAnonymous()">
                                ${issue.assignee.firstName} ${issue.assignee.lastName}
                            </sec:authorize>
                            <sec:authorize access="isAuthenticated()">
                                <a class="viewLink" href="<spring:url value='/user/${issue.assignee.id}/view'/>">
                                        ${issue.assignee.firstName} ${issue.assignee.lastName}
                                </a>
                            </sec:authorize>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${listOfIssues.getTotalPages()> 1}">
                <nav aria-label="Page navigation" id="pagerID">
                    <div class="text-center">
                        <ul class="pagination">
                            <li>
                                <a href="<spring:url value='/projects/project/${project.id}?issue_page=0'/>"
                                   aria-label="Start">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach var="page" begin="0" end="${listOfIssues.getTotalPages() - 1}">
                                <li>
                                    <a href="<spring:url
                                    value='/projects/project/${project.id}?issue_page=${page}'/>">${page + 1}</a>
                                </li>
                            </c:forEach>
                            <li>
                                <a href="<spring:url
                                value='/projects/project/${project.id}?issue_page=${listOfIssues.getTotalPages() - 1}'/>"
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

<!-- Popup for notifying of changing project-->
<p hidden id="message">${msg}</p>
<div class="modal fade" id="modalChanges" tabindex="-1" data-backdrop="false"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-body text-center">
            </div>
        </div>
    </div>
</div>

<!-- Modal for confirmation of changing of user role -->
<div class="modal fade" id="changRoleModal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title pull-left">Change of role</h4>
            </div>
            <div class="modal-body text-center"></div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a href="" class="btn btn-u confirmChangeRole">Confirm</a>
            </div>
        </div>
    </div>
</div>

<!-- Modal for confirmation of removing user from project-->
<div class="modal fade" id="removingOfUser" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title pull-left">Removal user from project</h4>
            </div>
            <div class="modal-body text-center"></div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a href="" class="btn btn-u confirmRemove">Remove</a>
            </div>
        </div>
    </div>
</div>

<!-- Modal for confirmation remove release from project-->
<div class="modal fade" id="removingOfRelease" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title pull-left">Remove release from project</h4>
            </div>
            <div class="modal-body text-center"></div>
            <div class="modal-footer">
                <button class="btn btn-default"  data-dismiss="modal">Cancel</button>
                <a href="" class="btn btn-u confirmRemoveRelease">Remove</a>
            </div>
        </div>
    </div>
</div>