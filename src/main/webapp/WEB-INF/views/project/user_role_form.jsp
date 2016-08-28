<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left"> Select User Role </h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li><a href="<spring:url value='/projects'/>">Projects</a></li>
                <li><a href="<spring:url value='/projects/project/${project.id}'/>">${project.title}</a></li>
                <li><a href="<spring:url value='/projects/project/${project.id}/usersWithoutProject'/>">Available Users
                </a></li>
                <li class="active"> User Role </li>
            </ol>
        </div>
    </div>
</div>

<c:if test="${not empty msg}">
    <div class="row">
        <div class="col-sm-4 col-sm-offset-8">
            <div class="alert alert-${alert} alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert"
                        aria-label="Close"><span aria-hidden="true">×</span>
                </button>
                <strong>${msg}</strong>
            </div>
        </div>
    </div>
</c:if>

<form action="/projects/project/${project.id}/usersWithoutProject/${user.id}/role"  method="POST">
    <div class="col-sm-offset-3 col-sm-6 text-center">
        <label>Role</label>
        <select name="role" type="text" cssClass="form-control" id="userRoleInput">
            <option value="0" label=" Select a role"/>
            <option value="1" label="Poject Manager"/>
            <option value="2" label="Developer"/>
            <option value="3" label="Quality Assurance"/>
        </select>
        <div class="col-sm-12">
            <input type="submit" value="Submit" class="margin-top-30 btn-u pull-right"/>
        </div>
    </div>
</form>