<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
        <div class="col-sm-5 text-left">
            <div class="release-name">
                Release: ${release.version}
                <sec:authorize access="@releaseSecurityService.hasPermissionToEditRelease(${release.id})">
                    <small>
                        <a class="viewLink"
                           href="<spring:url value='/project/${release.project.id}/release/${release.id}/edit'/>">[edit]</a>
                    </small>
                </sec:authorize>
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
        <%--Issues in release--%>
        <div class="release-issues col-sm-7">
            <h3>Issues</h3>
            <div class="row issues-panel">
                <div class="col-sm-9">
                    <%--search panel--%>
                    <form action="/project/${release.project.id}/release/${release.id}/issuesSearch" method="POST"
                          class="form-inline">
                        <div class="input-group">
                            <input type="text" class="form-control" name="searchedString"
                                   placeholder="Search...">
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="submit">
                                    <span class="glyphicon glyphicon-search"></span>
                                </button>
                            </span>
                        </div>
                    </form>
                </div>
                <%--add issue button--%>
                <div class="col-sm-3">
                    <a href="<spring:url value='/issue/add'/>"
                       class="btn btn-default pull-right"><i class="fa fa-plus"></i> Add issue</a>
                </div>
            </div>
            <%--issues table--%>
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
                <c:forEach var="issue" items="${issueList.content}">
                    <tr>
                        <input name="issueId" type="hidden" value="${issue.id}"/>
                        <td>${issue.title}</td>
                        <td>
                            <select class="statuses-dropdown selectpicker" data-width="150px">
                                <option selected="selected" value="${issue.status}">${issue.status}</option>
                            </select>
                        </td>
                        <td>
                            <form:select class="users-dropdown selectpicker" data-live-search="true" data-width="200px" path="users">
                                <form:option selected="selected" label="${issue.assignee.firstName} ${issue.assignee.lastName}"
                                             value="${issue.assignee.id}"/>
                                <form:options items="${users}" itemLabel="firstName" itemValue="id"/>
                            </form:select>
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
            <c:if test="${issueList.getTotalPages()> 1}">
                <nav aria-label="Page navigation" id="pagerID">
                    <div class="text-center">
                        <ul class="pagination">
                            <li>
                                <a href="<spring:url value='/project/${release.project.id}/release/${release.id}?page=0'/>"
                                   aria-label="Start">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach var="page" begin="0" end="${issueList.getTotalPages() - 1}">
                                <li>
                                    <a href="<spring:url value='/project/${release.project.id}/release/${release.id}?page=${page}'/>">${page + 1}</a>
                                </li>
                            </c:forEach>
                            <li>
                                <a href="<spring:url value='/project/${release.project.id}/release/${release.id}?page=${issueList.getTotalPages() - 1}'/>"
                                   aria-label="End"><span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </c:if>
        </div>
    </div>
</div>

