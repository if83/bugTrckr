<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left">Users</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li class="active">Users</li>
            </ol>
        </div>
    </div>
</div>

<div class="margin-top-30 row">
    <div class="col-sm-1 col-sm-offset-1">
        <sec:authorize access="@userSecurityService.hasPermissionToUserManagement()">
            <a href="<spring:url value='/user/add/' />" class="btn btn-default"><i class="fa fa-plus"></i>
                Add user</a>
        </sec:authorize>
    </div>
    <div class="col-sm-1">
        <button class="btn btn-default" type="button" data-toggle="collapse" data-target="#collapseSearch"
                aria-expanded="false" aria-controls="collapseSearch">
            <span class="glyphicon glyphicon-search" aria-hidden="true"></span> Search
        </button>
    </div>
</div>

<div class="collapse" id="collapseSearch">
    <div class="row">
        <div class="col md-8 col-md-offset-4">
            <form action="/users/search" method="POST" class="form-inline">
                <div class="row">
                    <div class="col-md-4">
                        <div class="input-group">
                            <input name="firstName" type="text" class="form-control form-text"
                                   placeholder="First name"/>
                            <span class="input-group-btn">
                                        <button type="submit" class="btn btn-default">
                                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                        </button>
                                </span>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="input-group" id="inputLastNameIDFormSearch">
                            <input name="lastName" type="text" class="form-control form-text"
                                   placeholder="Last name"/>
                            <span class="input-group-btn">
                                    <button type="submit" class="btn btn-default">
                                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                    </button>
                                </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <div class="input-group">
                            <input name="email" type="text" class="form-control form-text"
                                   placeholder="Email"/>
                            <span class="input-group-btn">
                            <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"
                                                                                aria-hidden="true"></span></button>
                        </span>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="input-group">
                            <select name="role" type="text" class="form-control" id="roleInput">
                                <c:forEach var="role" items="${roles}">
                                    <option value="${role}">${role.toString()}</option>
                                </c:forEach>
                            </select>
                            <span class="input-group-btn">
                                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"
                                                                                    aria-hidden="true"></span></button>
                            </span>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="margin-top-30">
    <div class="row">
        <div class="col-sm-10 col-sm-offset-1">
            <table class="table table-striped table-hover table-bordered text-center">
                <thead>
                <tr>
                    <th class="text-center">User name</th>
                    <th class="text-center">E-mail</th>
                    <th class="text-center">Role</th>
                    <th class="text-center">Project Title</th>
                    <th class="text-center">Is deleted</th>
                    <th class="text-center">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${userList.content}">
                    <tr>
                        <td>
                            <a class="viewLink" href="<spring:url value='/user/${user.id}/view'/>">
                                    ${user.firstName} ${user.lastName}
                            </a>
                        </td>
                        <td>${user.email}</td>
                        <td>${user.role.toString()}</td>
                        <td>
                            <a class="viewLink" href="<spring:url value='/projects/project/${user.project.id}'/>">
                                    ${user.project.title}
                            </a>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${user.isDeleted()}">
                                    <input type="checkbox" name="isDeletedChbx" checked="checked" disabled/>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" name="isDeletedChbx" disabled/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <div class="actionButtons">
                                <sec:authorize access="@userSecurityService.hasPermissionToUserManagement()">
                                    <c:choose>
                                        <c:when test="${not user.isDeleted()}">
                                            <a href="<spring:url value='/user/${user.id}/edit' />"><i
                                                    class="fa fa-edit fa-lg icon-table-u"></i></a>
                                            <a href="<spring:url value='/user/${user.id}/remove' />"
                                               class="btn removeUserBtn">
                                                <i class="fa fa-trash fa-lg icon-table-u"></i>
                                                <p class="removeUserNotification hidden">Confirm remove user ${user.getFullName()}</p>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fa fa-edit fa-lg disabled-icon"></i>
                                            <a href="<spring:url value='/user/${user.id}/restore' />"><i
                                                    class="fa fa-external-link-square icon-table-u"></i></a>
                                        </c:otherwise>
                                    </c:choose>
                                </sec:authorize>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%--pagination of releses-table--%>
            <c:if test="${userList.getTotalPages() gt 1}">
                <nav aria-label="Page navigation" id="pagerID">
                    <div class="text-center">
                        <ul class="pagination">
                            <li>
                                <a href="<spring:url value='/users?page=0'/>"
                                   aria-label="Start">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach var="page" begin="0" end="${userList.getTotalPages() - 1}">
                                <li>
                                    <a href="<spring:url value='/users?page=${page}'/>">${page + 1}</a>
                                </li>
                            </c:forEach>
                            <li>
                                <a href="<spring:url value='/users?page=${userList.totalPages - 1}'/>"
                                   aria-label="End">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </c:if>
        </div>
    </div>
</div>

<!-- Popup for notifying of changing users-->
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

<!-- Modal for confirmation remove user-->
<div class="modal fade" id="removingOfUser" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title pull-left">Remove user</h4>
            </div>
            <div class="modal-body text-center"></div>
            <div class="modal-footer">
                <button class="btn btn-default"  data-dismiss="modal">Cancel</button>
                <a href="" class="btn btn-u confirmUserRelease">Remove</a>
            </div>
        </div>
    </div>
</div>