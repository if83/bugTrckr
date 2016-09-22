<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-3 col-sm-offset-1">
            <h1 class="pull-left">Projects</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li class="active">Projects</li>
            </ol>
        </div>
    </div>
</div>

<%--Table of projects--%>
<div class="row">
    <div class="margin-top-20">
        <%--Searching projects by title --%>
        <form action="/projects/search" method="POST" class="col-sm-3 col-sm-offset-1">
            <div class="input-group">
                <input name="title" type="text" class="form-control form-text" placeholder="Search By Project's Title"/>
            <span class="input-group-btn"><button type="submit" class="btn btn-default">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span></button></span>
            </div>
        </form>
        <%--Button for adding project--%>
        <sec:authorize access="hasRole('ADMIN')">
            <div class="col-sm-2 ">
                <a href="<spring:url value='/projects/add/' />" class="btn btn-default">
                    </i>Add Project</a>
            </div>
        </sec:authorize>
    </div>
    <div class="margin-top-20 col-sm-10 col-sm-offset-1">
        <table class="table table-hover table-bordered">
            <thead>
            <tr>
                <th class="text-center">Project Title</th>
                <sec:authorize access="isAuthenticated()">
                    <th class="text-center">Free to view</th>
                </sec:authorize>
                <th class="text-center">Free to comment</th>
                <th class="text-center">Free to add Issue</th>
                <th class="text-center">Users in Project</th>
                <th class="text-center">Number of Releases</th>
                <th class="text-center">Number of Issues</th>
                <sec:authorize access="hasRole('ADMIN') or hasRole('PROJECT_MANAGER')">
                    <th class="text-center">Edit</th>
                </sec:authorize>
                <sec:authorize access="hasRole('ADMIN')">
                    <th class="text-center">Delete</th>
                </sec:authorize>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="project" items="${listOfProjects.content}">
                <sec:authorize access="isAnonymous()">
                    <c:if test="${project.getGuestView()}">
                        <tr>
                            <td class="text-center">
                                <a class="viewLink" href="<spring:url value='/projects/project/${project.id}'/>">
                                    <c:out value="${project.title}"/></a>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${project.guestAddComment}">
                                        <i class="glyphicon glyphicon-ok"/>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="glyphicon glyphicon-remove"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${project.guestCreateIssues}">
                                        <i class="glyphicon glyphicon-ok"/>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="glyphicon glyphicon-remove"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${!project.getUsers().isEmpty()}">
                                        <c:out value="${project.getUsers().size()}"/>
                                    </c:when>
                                    <c:otherwise>
                                        None
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${!project.getProjectReleases().isEmpty()}">
                                        <c:out value="${project.getProjectReleases().size()}"/>
                                    </c:when>
                                    <c:otherwise>
                                        None
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${!project.getIssues().isEmpty()}">
                                        <c:out value="${project.getIssues().size()}"/>
                                    </c:when>
                                    <c:otherwise>
                                        None
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:if>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <tr>
                        <td class="text-center">
                            <a class="viewLink" href="<spring:url value='/projects/project/${project.id}'/>">
                                <c:out value="${project.title}"/></a>
                        </td>
                        <sec:authorize access="isAuthenticated()">
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${project.guestView}">
                                        <i class="glyphicon glyphicon-ok"/>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="glyphicon glyphicon-remove"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </sec:authorize>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${project.guestAddComment}">
                                    <i class="glyphicon glyphicon-ok"/>
                                </c:when>
                                <c:otherwise>
                                    <i class="glyphicon glyphicon-remove"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${project.guestCreateIssues}">
                                    <i class="glyphicon glyphicon-ok"/>
                                </c:when>
                                <c:otherwise>
                                    <i class="glyphicon glyphicon-remove"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${!project.getUsers().isEmpty()}">
                                    <c:out value="${project.getUsers().size()}"/>
                                </c:when>
                                <c:otherwise>
                                    None
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${!project.getProjectReleases().isEmpty()}">
                                    <c:out value="${project.getProjectReleases().size()}"/>
                                </c:when>
                                <c:otherwise>
                                    None
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${!project.getIssues().isEmpty()}">
                                    <c:out value="${project.getIssues().size()}"/>
                                </c:when>
                                <c:otherwise>
                                    None
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <%--Access to editing of PM's project--%>
                        <sec:authorize access="@projectSecurityService.hasPermissionToProjectManagement(${project.id})">
                            <td class="text-center">
                                <a href="<spring:url value='/projects/${project.id}/edit' />">
                                    <i class="fa fa-edit fa-lg icon-table-u"></i></a>
                            </td>
                        </sec:authorize>
                        <sec:authorize access="!@projectSecurityService.hasPermissionToProjectManagement(${project.id})">
                            <td class="text-center">
                                <i class="fa fa-lock fa-lg icon-table-u"></i>
                            </td>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ADMIN')">
                            <td class="text-center">
                                <a data-toggle="modal" href data-target="#removeModal${project.id}">
                                    <i class="fa fa-trash fa-lg icon-table-u"></i></a>
                            </td>
                        </sec:authorize>
                        <!-- Modal confirmation for removing project-->
                        <div class="modal fade" id="removeModal${project.id}" tabindex="-1" role="dialog">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close"><span aria-hidden="true">&times;</span>
                                        </button>
                                        <h5 class="modal-title pull-left">Removal</h5>
                                    </div>
                                    <div class="modal-body text-center">
                                        Confirm removal of ${project.title}
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-default" data-dismiss="modal">Cancel</button>
                                        <a href="<spring:url value='/projects/${project.id}/remove' />"
                                           class="btn btn-u">Confirm</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </tr>
                </sec:authorize>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <c:if test="${listOfProjects.getTotalPages()> 1}">
        <div class="row col-sm-offset-4 col-sm-4">
            <nav aria-label="Page navigation" id="pagerID">
                <div class="text-center">
                    <ul class="pagination">
                        <li>
                            <a href="<spring:url value='/projects?page=0'/>" aria-label="Start">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                            <c:forEach var="page" begin="0"  end="${listOfProjects.getTotalPages() - 1}">
                                <li>
                                    <a href="<spring:url value='/projects?page=${page}'/>">${page + 1}</a>
                                </li>
                            </c:forEach>
                        <li>
                            <a href="<spring:url value='/projects?page=${listOfProjects.getTotalPages() - 1}'/>" aria-label="End">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </c:if>
</div>

<!-- Popup for notifying of changing project-->
<p hidden id="message">${msg}</p>
<div class="modal fade" id="modalChanges" tabindex="-1" data-backdrop="false"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-body text-center">
            </div>
        </div>
    </div>
</div>