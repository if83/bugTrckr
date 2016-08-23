<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-3 col-sm-offset-1">
            <h4 class="pull-left">Projects <a href="<spring:url value='/projects/add/'/>" class="abtn-u-white"><i
                    class="fa fa-plus icon-bg-u"></i> Add project</a></h4>
        </div>
        <div class="col-sm-12">
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
                <button type="button" class="close" data-dismiss="alert"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <strong>${msg}</strong>
            </div>
        </div>
    </div>
</c:if>

<div class=" col-sm-8 col-sm-offset-2">
    <form action="/projects/searchByTitle" method="POST" class="form-inline">
        <td>
            <div class="input-group">
                <input name="title" type="text" class="form-control form-text"
                       placeholder="Search By Project Title"/><span class="input-group-btn">
                                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"
                                                                                    aria-hidden="true"></span>
                                </button></span>
            </div>
    </form>

</div>

<div class="margin-top-30">
    <div class="margin-top-30 col-sm-8 col-sm-offset-2">
        <table class="table table-hover">
            <thead>
            <tr>
                <th class="text-center">Project Title</th>
                <th class="text-center">Free to view</th>
                <th class="text-center">Free to comment</th>
                <th class="text-center">Free to add Issue</th>
                <th class="text-center">Edit</th>
                <th class="text-center">Delete</th>
            </tr>
            </thead>
            <tbody>

                <c:if test="${isControllerPagable}">
                    <c:forEach var="project" items="${listOfProjects.content}">
                        <tr>
                            <td class="text-center">
                                <a href="<spring:url value='/projects/project/${project.id}'/>">
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
                                <a href="<spring:url value='/projects/${project.id}/edit' />"><i class="fa fa-edit icon-table-u"></i></a>
                            </td>
                            <td class="text-center">
                                <a href="<spring:url value='/projects/${project.id}/remove' />" ><i class="fa fa-remove icon-table-u"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>

                <c:if test="${!isControllerPagable}">
                    <c:forEach var="project" items="${listOfProjects}">
                        <tr>
                            <td class="text-center">
                                <a href="<spring:url value='/projects/project/${project.id}'/>">
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
                                <a href="<spring:url value='/projects/${project.id}/edit' />"><i class="fa fa-edit icon-table-u"></i></a>
                            </td>
                            <td class="text-center">
                                <a href="<spring:url value='/projects/${project.id}/remove' />" ><i class="fa fa-remove icon-table-u"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
    </div>
    <c:if test="${isControllerPagable && totalPagesCount> 1}">
        <div class="row col-sm-offset-4 col-sm-4">
            <nav aria-label="Page navigation" id="pagerID">
                <div class="text-center">
                    <ul class="pagination">
                        <li>
                            <a href="<spring:url value='/projects?page=0'/>" aria-label="Start">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>

                        <c:forEach var="apage" begin="0"  end="${totalPagesCount - 1}">
                            <li>
                                <a href="<spring:url value='/projects?page=${apage}'/>">${apage + 1}</a>
                            </li>
                        </c:forEach>
                        <li>
                            <a href="<spring:url value='/projects?page=${totalPagesCount - 1}'/>" aria-label="End">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </c:if>
</div>
