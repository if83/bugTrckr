<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-3 col-sm-offset-1">
            <h4 class="pull-left">Projects  <a href="<spring:url value='/projects/add/' />" class="abtn-u-white"><i class="fa fa-plus icon-bg-u"></i> Add project</a></h4>
        </div>
        <div class="col-sm-12">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li class="active">Projects</li>
            </ol>
        </div>
    </div>
</div>

<div class="margin-top-30 col-sm-8 col-sm-offset-2">
    <table class="table table-hover">
        <thead>
        <tr>
            <th class="text-center">Project Name</th>
            <th class="text-center">Free to view</th>
            <th class="text-center">Free to comment</th>
            <th class="text-center">Free to add Issue</th>
            <th class="text-center">Edit</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="project" items="${listOfProjects}">
            <tr>
                <td class="text-center">
                    <a href="<spring:url value='/projects/project${project.id}'/>">
                        <c:out value="${project.title}"/></a>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${project.guestView == true}">
                            <i class="glyphicon glyphicon-ok"/>
                        </c:when>
                        <c:otherwise>
                            <i class="glyphicon glyphicon-remove"/>
                        </c:otherwise>
                    </c:choose>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${project.guestAddComment == true}">
                            <i class="glyphicon glyphicon-ok"/>
                        </c:when>
                        <c:otherwise>
                            <i class="glyphicon glyphicon-remove"/>
                        </c:otherwise>
                    </c:choose>
                </td class="text-center">
                <td class="text-center">
                    <c:choose>
                        <c:when test="${project.guestCreateIssues == true}">
                            <i class="glyphicon glyphicon-ok"/>
                        </c:when>
                        <c:otherwise>
                            <i class="glyphicon glyphicon-remove"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <a href="<spring:url value='/projects/edit/project${project.id}' />" ><i class="fa fa-edit icon-table-u"></i></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
