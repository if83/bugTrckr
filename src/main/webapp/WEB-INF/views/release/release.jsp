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
        <div class="col-sm-8 text-left">
            <div class="release-name">Release: ${release.version}</div>
            <div class="project-name">Project: <a href="/project/${release.project.id}">${release.project.title}</a></div>
            <div class="status-name">Status: ${release.releaseStatus}</div>
            <div class="release-description">Release description:</div>
            <p>${release.description}</p>
        </div>
        <div class="col-sm-4">
            <table class=" issue-list table table-hover table-bordered">
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
                        <td>
                            <a href="<spring:url value=''/>">${issue.title}</a>
                        </td>
                        <td>${issue.status}</td>
                        <td>${issue.assignee.firstName} ${issue.assignee.lastName} </td>
                        <td>
                            <a href="<spring:url value='' />"><i
                                    class="fa fa-edit icon-table-u"></i></a>
                            <a href="<spring:url value='' />"><i
                                    class="fa fa-remove icon-table-u"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

