<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left">Issue</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li class="active">Issue</li>
            </ol>
        </div>
    </div>
</div>

<div class="margin-top-30 row">
    <div class="col-sm-2 col-sm-offset-1">
        <a href="<spring:url value='/issue/add/' />" class="btn btn-primary btn-u"><i class="fa fa-plus icon-bg-u"></i> Add issue</a>
    </div>
</div>
<div class=class="margin-top-30">
    <table class="table table-hover">

        <thead>
        <tr>
            <th>Issue name</th>
            <th>Type</th>
            <th>Priority</th>
            <th>Status</th>
            <th>ProjectRelease</th>
            <th>Assigned User</th>
            <th>Create time</th>
            <th>Finish time</th>
            <th>Last updated</th>
            <th>Estimate time</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="issue" items="${listOfIssues}">
            <tr>
                <td><c:out value="${issue.title}"/></td>
                <td><c:out value="${issue.type}"/></td>
                <td><c:out value="${issue.priority}"/></td>
                <td><c:out value="${issue.status}"/></td>
                <td>
                    <a class="viewLink" href="<spring:url value='/projects/project/${issue.projectRelease.project.id}'/>">
                            ${issue.projectRelease.project.title}
                    </a>
                </td>
                <td>
                    <a class="viewLink" href="<spring:url value='/user/${issue.assignee.id}/view'/>">
                            ${issue.assignee.firstName} ${issue.assignee.lastName}
                    </a>
                </td>
                <td><c:out value="${issue.createTime}"/></td>
                <td><c:out value="${issue.dueDate}"/></td>
                <td><c:out value="${issue.lastUpdateDate}"/></td>
                <td><c:out value="${issue.estimateTime}"/></td>
                <td>
                    <div class="actionButtons">
                        <a href="<spring:url value='/issue/${issue.id}/edit' />"><i class="fa fa-edit icon-table-u"></i></a>
                        &nbsp
                        <a href="<spring:url value='/issue/${issue.id}/remove' />"><i
                                class="fa fa-remove icon-table-u"></i></a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="row">
    <div class="col-sm-12">
        <h2 class=""><a href="<spring:url value='/worklog'/>">Worklog</a></h2>
    </div>
</div>

<div class="row">
    <div class="col-sm-12">
        <h2 class=""><a href="<spring:url value='/label'/>">Label</a></h2>
    </div>
</div>