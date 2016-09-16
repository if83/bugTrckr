<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-12">
            <h2 class="col-sm-offset-1 pull-left">${project.title}
                <sec:authorize access="hasRole('ADMIN') or hasRole('PROJECT_MANAGER')">
                    <small><a class="viewLink" href="<spring:url value='/projects/${project.id}/edit'/>">[edit]</a></small>
                </sec:authorize>
            </h2>
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/' />">Home</a></li>
                <li><a href="<spring:url value='/projects'/>">Projects</a></li>
                <li class="active">${project.title} </li>
            </ol>
        </div>
    </div>
</div>

<c:if test="${not empty msg}">
    <div class="row">
        <div class="col-sm-4 col-sm-offset-8">
            <div class="alert alert-${alert} alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <strong>${msg}</strong>
            </div>
        </div>
    </div>
</c:if>

<div class="container">
    <div class="row project-info text-left">
        <div class="col-sm-12">
            <h3>Project description:</h3>
            ${project.description}
        </div>
    </div>

    <div class="row">
        <%--Releases in project--%>
        <div class="col-sm-4">
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
                <%--add button--%>
                <div class="col-sm-5">
                    <a href="<spring:url value='/project/${project.id}/release/add'/>"
                       class="btn btn-default pull-right"><i class="fa fa-plus"></i> Add release</a>
                </div>
            </div>
            <%--table of releases--%>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>Version</th>
                    <th>Status</th>
                    <sec:authorize access="hasRole('ADMIN') or hasRole('PROJECT_MANAGER')">
                        <th class="text-center">Actions</th>
                    </sec:authorize>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="rel" items="${releaseList.content}">
                    <tr>
                        <td>
                            <a class="viewLink"
                               href="<spring:url value='/project/${project.id}/release/${rel.id}'/>">${rel.version}
                            </a>
                        </td>
                        <td>${rel.releaseStatus}</td>
                        <sec:authorize access="hasRole('ADMIN') or hasRole('PROJECT_MANAGER')">
                            <td class="text-center">
                                <a href="<spring:url value='/project/${project.id}/release/${rel.id}/edit' />"><i
                                        class="fa fa-edit fa-lg icon-table-u"></i></a>&nbsp&nbsp
                                <a href="<spring:url value='/project/${project.id}/release/${rel.id}/remove' />"><i
                                        class="fa fa-trash fa-lg icon-table-u"></i></a>
                            </td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${releaseList.getTotalPages()> 1}">
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

        <div class="col-sm-7 col-sm-offset-1">
            <h3>Users</h3>
            <div class="row users-panel">
                <div class="col-sm-11">
                    <%--Search form for users in project--%>
                    <form class="form-inline" action="/projects/project/${project.id}/users_search" method="POST">
                        <select class="form-control form-inline search-by-name" type="text" name="searchedParam">
                            <option value="First Name">First Name</option>
                            <option value="Last Name">Last Name</option>
                        </select>
                        <select class="form-control form-inline search-by-role" type="text" name="role">
                            <option value="">Any Role</option>
                            <option value="${PM}">Project Manager</option>
                            <option value="${DEV}">Developer</option>
                            <option value="${QA}">QA</option>
                        </select>
                        <div class="input-group form-inline search-by-string">
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
                <%--add user button--%>
                <div class="col-sm-1">
                    <sec:authorize access="hasRole('ADMIN') or hasRole('PROJECT_MANAGER')">
                        <a href="<spring:url value='/projects/project/${project.id}/usersWithoutProject'/>"
                           class="btn btn-default pull-right"><i class="fa fa-plus"></i> Add release</a>
                    </sec:authorize>
                </div>
            </div>
            <%--Table of users in project--%>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>User name</th>
                    <th>Role</th>
                    <sec:authorize access="hasRole('ADMIN') or hasRole('PROJECT_MANAGER')">
                        <th class="text-center">Actions</th>
                    </sec:authorize>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${usersList.content}">
                    <tr>
                        <td>
                            <a class="viewLink" href="<spring:url value='/user/${user.id}/view' />">${user.firstName}
                                    ${user.lastName}
                            </a>
                        </td>
                        <td>${user.role.toString()}</td>
                        <sec:authorize access="hasRole('ADMIN') or hasRole('PROJECT_MANAGER')">
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${!user.role.isProjectManager()}">
                                        <!--changing user role-->
                                        <a data-toggle="modal" data-target="#changingRole${user.id}${project.id}">
                                            <i class="fa fa-edit fa-lg icon-table-u"></i></a> &nbsp&nbsp

                                        <!-- Modal for confirmation of changing of user role -->
                                        <div class="modal fade" id="changingRole${user.id}${project.id}" tabindex="-1"
                                             role="dialog" aria-labelledby="myModalLabel">
                                            <div class="modal-dialog" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                        <h4 class="modal-title pull-left">
                                                            Change of role
                                                        </h4>
                                                    </div>
                                                    <div class="modal-body"><p>Confirm the change
                                                        of ${user.firstName} ${user.lastName} role</p>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button class="btn btn-default" data-dismiss="modal">
                                                            Cancel
                                                        </button>
                                                        <a href="<spring:url
                                                        value='/projects/project/${project.id}/usersWithoutProject/
                                                        ${user.id}/changeRole'/>"
                                                           class="btn btn-u">Confirm
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <!--Removing user from project -->
                                        <a data-toggle="modal" data-target="#removingOfUser${user.id}${project.id}">
                                            <i class="fa fa-trash fa-lg icon-table-u"></i></a>

                                        <!-- Modal for confirmation of removing user from project-->
                                        <div class="modal fade" id="removingOfUser${user.id}${project.id}" tabindex="-1"
                                             role="dialog" aria-labelledby="myModalLabel">
                                            <div class="modal-dialog" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                        <h4 class="modal-title pull-left">Removal user from project</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <p>Confirm the removal of ${user.firstName} ${user.lastName}
                                                        from ${project.title}</p>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button class="btn btn-default" data-dismiss="modal">
                                                            Cancel
                                                        </button>
                                                        <a href="<spring:url
                                                        value='/projects/project/${project.id}/removeUser/${user.id}'/>"
                                                           class="btn btn-u">Remove
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:when>
                                </c:choose>
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
                                <a href="<spring:url value='/projects/project/${project.id}?project_page=0'/>"
                                   aria-label="Start">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach var="page" begin="0" end="${usersList.getTotalPages() - 1}">
                                <li>
                                    <a href="<spring:url
                                    value='/projects/project/${project.id}?project_page=${page}'/>">${page + 1}</a>
                                </li>
                            </c:forEach>
                            <li>
                                <a href="<spring:url
                                value='/projects/project/${project.id}?project_page=${usersList.getTotalPages() - 1}'/>"
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