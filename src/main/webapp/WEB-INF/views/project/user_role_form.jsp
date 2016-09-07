<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left">User Role</h1>
        </div>
        <div class="col-sm-9">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li><a href="<spring:url value='/projects'/>">Projects</a></li>
                <li><a href="<spring:url value='/projects/project/${project.id}'/>">${project.title}</a></li>
                <li><a href="<spring:url value='/projects/project/${project.id}/usersWithoutProject'/>">Available Users
                </a></li>
                <li class="active">User Role</li>
            </ol>
        </div>
    </div>
</div>

<div class="col-sm-offset-4 col-sm-4 text-center">
    <form action="/projects/project/${project.id}/usersWithoutProject/${user.id}/role" method="POST">
        <h3 class="text-center">Select Role for User</h3><br/>
        <select class="form-control" name="role" type="text">
            <option class="text-center" value="${DEV}">Developer</option>
            <option class="text-center" value="${QA}">Quality Assurance</option>
        </select>
        <input type="submit" value="Submit" class="margin-top-30 btn-u pull-right"/>
    </form>
</div>