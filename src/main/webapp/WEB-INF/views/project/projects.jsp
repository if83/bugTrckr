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

<c:if test="${not empty msg}">
    <div class="row">
        <div class="col-sm-4 col-sm-offset-8">
            <div class="alert alert-${css} alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <strong>${msg}</strong>
            </div>
        </div>
    </div>
</c:if>

<%--Button for adding project--%>
<sec:authorize access="hasRole('ADMIN')">
    <div class="row margin-top-20">
        <div class="col-sm-2 col-sm-offset-2">
            <a href="<spring:url value='/projects/add/' />" class="btn btn-primary btn-u pull-left">
                <i class="fa fa-plus icon-bg-u"></i>Add Project</a>
        </div>
    </div>
</sec:authorize>

<%--Searching projects by title --%>
<div class="row margin-top-20">
    <div class="col-sm-3 col-sm-offset-2">
        <form action="/search" method="POST">
            <div class="input-group">
                <input name="title" type="text" class="form-control form-text" placeholder="Search By Project's Title"/>
            <span class="input-group-btn"><button type="submit" class="btn btn-default">
                <span class="glyphicon glyphicon-search" aria-hidden="true"></span></button></span>
            </div>
        </form>
    </div>
</div>

<%--Table of projects--%>
<div class="row">
    <div class="margin-top-20 col-sm-8 col-sm-offset-2">
        <table class="table table-hover table-bordered">
            <thead>
            <tr>
                <th class="text-center">Project Title</th>
                <th class="text-center">Free to view</th>
                <th class="text-center">Free to comment</th>
                <th class="text-center">Free to add Issue</th>
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
                <tr>
                    <td class="text-center">
                        <a class="viewLink" href="<spring:url value='projects/project/${project.id}'/>">
                            <c:out value="${project.title}"/></a>
                    </td>
                    <td class="text-center">
                        <c:choose>
                            <c:when test="${project.guestView}">
                                <i class="glyphicon glyphicon-ok"/>
                            </c:when>
                            <c:otherwise>
                                <i class="glyphicon glyphicon-remove"/>
                            </c:otherwise>
                        </c:choose>
                    <td class="text-center">
                        <c:choose>
                            <c:when test="${project.guestAddComment}">
                                <i class="glyphicon glyphicon-ok"/>
                            </c:when>
                            <c:otherwise>
                                <i class="glyphicon glyphicon-remove"/>
                            </c:otherwise>
                        </c:choose>
                    </td class="text-center">
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
                    <sec:authorize access="hasRole('PROJECT_MANAGER')">
                        <c:choose>
                            <c:when test="${project == loggedUser.getProject()}">
                                <td class="text-center">
                                    <a href="<spring:url value='/projects/${project.id}/edit' />">
                                        <i class="fa fa-edit fa-lg icon-table-u"></i></a>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td class="text-center">
                                    <i class="fa fa-lock icon-table-u"></i>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ADMIN')">
                        <td class="text-center">
                            <a href="<spring:url value='/projects/${project.id}/edit' />">
                                <i class="fa fa-edit fa-lg icon-table-u"></i></a>
                        </td>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ADMIN')">
                        <td class="text-center">
                            <a data-toggle="modal" data-target="#removeModal${project.id}">
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
                                    <h4 class="modal-title pull-left">Removal</h4>
                                </div>
                                <div class="modal-body">
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