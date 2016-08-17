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

<div class="margin-top-30 row">
    <div class="col-sm-2 col-sm-offset-1">
        <a href="<spring:url value='/user/add/' />" class="abtn-u-white"><i class="fa fa-plus icon-bg-u"></i> Add
            user</a>
    </div>
</div>

<div class="margin-top-30">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>E-mail</th>
            <th>Role</th>
            <th>Description</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>

        <c:forEach var="user" items="${userList}">
            <tr>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.role}"/></td>
                <td><c:out value="${fn:substring(user.description, 0, 20)}"/></td>
                <td>
                    <div class="actionButtons">
                    <a href="<spring:url value='/user/${user.id}/view' />" ><i class="fa fa-eye icon-table-u"></i></a>  &nbsp
                    <a href="<spring:url value='/user/${user.id}/edit' />" ><i class="fa fa-edit icon-table-u"></i></a>  &nbsp
                    <a href="<spring:url value='/user/${user.id}/remove' />" ><i class="fa fa-remove icon-table-u"></i></a>
                    </div>

                </td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>