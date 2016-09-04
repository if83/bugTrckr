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
                <li><a href="<spring:url value='/project/${release.project.id}'/>">${release.project.title}</a></li>
                <li class="active">release ${release.version}</li>
            </ol>
        </div>
    </div>
</div>

<div class="container">

    <div class="row">
        <div class="col-sm-6 text-left">
            <div class="release-name">
                Release: ${release.version}
                <small>
                    <a class="viewLink"
                       href="<spring:url value='/project/${release.project.id}/release/${release.id}/edit'/>">[edit]</a>
                </small>
            </div>
            <div class="project-name">
                Project:
                <a class="viewLink"
                   href="<spring:url value='/projects/project/${release.project.id}'/>">${release.project.title}</a>
            </div>
            <div class="status-name">Status: ${release.releaseStatus}</div>
            <div class="release-description">Release description:</div>
            <p>${release.description}</p>
        </div>
        <div class="release-issues col-sm-6">
            <a href="<spring:url value='/issue/add' />" class="btn btn-primary btn-u"><i
                    class="fa fa-plus icon-bg-u"></i> Add issue</a>
            <table class="table-issues table table-hover table-bordered">
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Status</th>
                    <th>Assigne to</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="issue" items="${issues}">
                    <tr>
                        <input name="issueId" type="hidden" value="${issue.id}"/>
                        <td>${issue.title}</td>
                        <td>
                            <div class="dropdown statuses-dropdown">
                                <button class="statuses-label" type="button" data-toggle="dropdown" aria-haspopup="true"
                                        aria-expanded="false">
                                        ${issue.status}
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="statuses-label">
                                    <c:forEach var="status" items="${issueStatuses}">
                                        <li value='${status}'>
                                            <a href="#">${status}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </td>
                        <td>
                            <div class="dropdown users-dropdown">
                                <button class="users-label" type="button" data-toggle="dropdown" aria-haspopup="true"
                                        aria-expanded="false">
                                        ${issue.assignee.firstName}
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="users-label">
                                    <c:forEach var="user" items="${users}">
                                        <li value='${user.id}'>
                                            <a href="#">${user.firstName}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </td>
                        <td>
                            <a href="<spring:url value='/issue/${issue.id}/edit'/>"><i
                                    class="fa fa-edit fa-lg icon-table-u"></i></a>
                            <a href="<spring:url value='/issue/${issue.id}/remove'/>"><i
                                    class="fa fa-trash fa-lg icon-table-u"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

