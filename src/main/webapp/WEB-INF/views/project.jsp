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
                <li class="active">Projects</li>
            </ol>
        </div>
    </div>
</div>

<div class="margin-top-30 row">
    <div class="col-sm-2 col-sm-offset-1">
        <a href="<spring:url value='' />" class="abtn-u-white"><i class="fa fa-plus icon-bg-u"></i> Add project</a>
    </div>
</div>

<div class=class="margin-top-30">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Project Name</th>
            <th>Description</th>
            <th>Free to view</th>
            <th>Free to comment</th>
            <th>Free to add Issue</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="project" items="${listOfProjects}">
            <tr>
                <td>
                    <a href="<spring:url value='/project/${project.id}/usersOnProject'/>">
                        <c:out value="${project.title}"/></a>
                </td>
                <td>
                    <c:out value="${project.description}"/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${project.guestView == true}">
                            <i class="glyphicon glyphicon-ok"/>
                        </c:when>
                        <c:otherwise>
                            <i class="glyphicon glyphicon-remove"/>
                        </c:otherwise>
                    </c:choose>
                <td>
                    <c:choose>
                        <c:when test="${project.guestAddComment == true}">
                            <i class="glyphicon glyphicon-ok"/>
                        </c:when>
                        <c:otherwise>
                            <i class="glyphicon glyphicon-remove"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${project.guestCreateIssues == true}">
                            <i class="glyphicon glyphicon-ok"/>
                        </c:when>
                        <c:otherwise>
                            <i class="glyphicon glyphicon-remove"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
