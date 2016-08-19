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

<div class="margin-top-30 col-sm-6">
    <h3 class="text-center"> Users in ${project.title}</h3>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>E-mail</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="users" items="${usersList}">
            <tr>
                <td><c:out value="${users.firstName}"/></td>
                <td><c:out value="${users.lastName}"/></td>
                <td><c:out value="${users.email}"/></td>
                <td><c:out value="${users.role}"/></td>
                <td>
                    <a href="<spring:url value='/user/${users.id}/view' />"><i class="fa fa-eye icon-table-u"></i></a>
                    <a href="<spring:url value='/user/${users.id}/edit' />"><i class="fa fa-edit icon-table-u"></i></a>
                    <a href="<spring:url value='/user/${users.id}/remove' />"><i class="fa fa-remove icon-table-u"></i></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>