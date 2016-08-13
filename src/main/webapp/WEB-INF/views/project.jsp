<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>



<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left">Project</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li class="active">Project</li>
            </ol>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h4>List of projects</h4>
            <div class="table-responsive">
                <table id="mytable" class="table table-bordred table-striped">

                    <thead>
                    <th>Project Name</th>
                    <th>Project Manager</th>
                    <th>Releases </th>
                    <th>Description</th>
                    <th>Free to join</th>
                    <th>Edit</th>
                    <th>Delete</th>
                    </thead>

                    <tbody>
                        <c:forEach items="${projectList}" var="projectList">
                            <tr>
                                <td>${projectList.title}</td>
                                <td>${projectList.projectManager.firstName + " " +projectList.projectManager.secondName}</td>
                                <%--add releases--%>
                                <td></td>
                                <td>${projectList.description}</td>
                                <td>${projects.guestView}</td>
                                <%--add references to edit and delete project--%>
                                <td>edit</td>
                                <td>delete</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
