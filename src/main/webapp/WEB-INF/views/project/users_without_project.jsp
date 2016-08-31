<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-3 col-sm-offset-1">
            <h1 class="pull-left">Users without projects</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li><a href="<spring:url value='/projects'/>">Projects</a></li>
                <li><a href="<spring:url value='/projects/project/${project.id}'/>">${project.title}</a></li>
                <li><a href="<spring:url value='/projects/project/${project.id}/usersWithoutProject'/>">
                    Available Users</a></li>
            </ol>
        </div>
    </div>
</div>

<c:if test="${not empty msg}">
    <div class="row">
        <div class="col-sm-4 col-sm-offset-8">
            <div class="alert alert-${css} alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <strong>${msg}</strong>
            </div>
        </div>
    </div>
</c:if>

<div class="margin-top-30 row">
    <div class="col-sm-10 col-sm-offset-2">
        <form action="/projects/project/${project.id}/usersWithoutProject" method="POST" class="form-inline">
            <div class="input-group">
                <input name="email" type="text" class="form-control form-text" placeholder="E-mail"/>
                <input name="role" type="hidden" type="text" value="${roles[4]}"/>
                <span class="input-group-btn">
                        <button type="submit" class="btn btn-default">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        </button>
                    </span>
            </div>
        </form>
    </div>
</div>

<div class="margin-top-30">
    <div class="row">
        <div class="col-sm-8 col-sm-offset-2">
            <table class="table table-hover table-bordered">
                <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>E-mail</th>
                        <th class="text-center">Add to Project</th>
                        <th class="text-center">User Info</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td><c:out value="${user.firstName}"/></td>
                            <td><c:out value="${user.lastName}"/></td>
                            <td><c:out value="${user.email}"/></td>
                            <td class="text-center">
                                <a href="<spring:url value='/projects/project/${project.id}/usersWithoutProject/
                                ${user.id}/role'/>" class="btn btn-primary btn-u">ADD</a>
                            </td>
                            <td class="text-center">
                                <a href="<spring:url value='/user/${user.id}/view'/>"
                               class="btn btn-primary btn-u">INFO</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>