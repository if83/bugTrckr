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

<div class="container">

    <div class="project-info text-left">
        <h3>Project description:</h3>
        ${project.description}
    </div>

    <div class="release-info text-left">
        <h3>Release description:</h3>
        ${release.description}
    </div>

    <div class="row">

        <div class="col-md-5">
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
                            <a href="<spring:url value='/project/${project.id}/release/${rel.id}'/>">${rel.version}</a>
                        </td>
                        <td>${rel.releaseStatus}</td>
                        <td class="text-center">
                            <a href="<spring:url value='' />"><i
                                    class="fa fa-edit icon-table-u"></i></a> &nbsp&nbsp
                            <a href="<spring:url value='' />"><i
                                    class="fa fa-remove icon-table-u"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="col-md-6 col-md-offset-1">
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
                            <a href="<spring:url value='/user/${users.id}/view' />">${users.firstName} ${users.lastName}</a>
                        </td>
                        <td>${users.role}</td>
                        <td class="text-center">
                            <a href="<spring:url value='/user/${users.id}/edit' />"><i
                                    class="fa fa-edit icon-table-u"></i></a> &nbsp&nbsp
                            <a href="<spring:url value='/user/${users.id}/remove' />"><i
                                    class="fa fa-remove icon-table-u"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>
