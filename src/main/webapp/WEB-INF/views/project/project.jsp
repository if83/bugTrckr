<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-12">
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

    <div class="row release-add-button">
        <div class="col-sm-2 col-sm-offset-1">
            <a href="<spring:url value='/project/${project.id}/release/add' />" class="abtn-u-white">
                <i class="fa fa-plus icon-bg-u"></i>Addrelease</a>
        </div>

        <div class="col-sm-2 col-sm-offset-5">
            <a href="<spring:url value='/projects/project/${project.id}/usersWithoutProject' />"
               class="abtn-u-white"><i class="fa fa-plus icon-bg-u"></i>Add Users</a>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-4">
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>Version</th>
                    <th>Status</th>
                    <th class="text-center">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="rel" items="${releases}">
                    <tr>
                        <td>
                            <a class="viewLink"  href="<spring:url value='/project/${project.id}/release/${rel.id}'/>">${rel.version}
                            </a>
                        </td>
                        <td>${rel.releaseStatus}</td>
                        <td class="text-center">
                            <a href="<spring:url value='/project/${project.id}/release/${rel.id}/edit' />"><i
                                    class="fa fa-edit fa-lg icon-table-u"></i></a>&nbsp&nbsp
                            <a href="<spring:url value='/project/${project.id}/release/${rel.id}/remove' />"><i
                                    class="fa fa-trash fa-lg icon-table-u"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="col-sm-7 col-sm-offset-1">
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>User name</th>
                    <th>Role</th>
                    <th class="text-center">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="users" items="${usersList}">
                    <tr>
                        <td>
                            <a class="viewLink" href="<spring:url value='/user/${users.id}/view' />">${users.firstName}
                                    ${users.lastName}</a>
                        </td>
                        <td>${users.role}</td>
                        <td class="text-center">
                            <a href="<spring:url value='/projects/project/${project.id}/usersWithoutProject/
                                ${users.id}/role'/>"><i class="fa fa-edit fa-lg icon-table-u"></i></a> &nbsp&nbsp
                            <a href="<spring:url value='/projects/project/${project.id}/removeUser/${users.id}'/>">
                                <i class="fa fa-trash fa-lg icon-table-u"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>