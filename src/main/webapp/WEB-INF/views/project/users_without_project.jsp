<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-3 col-sm-offset-1">
            <c:choose>
                <c:when test="${action eq 'addPM'}">
                    <h1 class="pull-left">Available Users</h1>
                </c:when>
                <c:otherwise>
                    <h1 class="pull-left">Users Without Projects</h1>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li><a href="<spring:url value='/projects'/>">Projects</a></li>
                <li><a href="<spring:url value='/projects/project/${project.id}'/>">${project.title}</a></li>
                <li><a href="<spring:url value='/projects/project/${project.id}/usersWithoutProject'/>">
                    Available Users</a></li>
            </ol>
        </div>
    </div>
</div>

<div class="row margin-top-20">
    <div class="form-inline col-sm-8 col-sm-offset-2">
        <c:choose>
            <c:when test="${action eq 'addPM'}">
                <form action="/projects/project/${project.id}/addProjectManager/usersWithoutProject/search" method="POST">
            </c:when>
            <c:otherwise>
                <form action="/projects/project/${project.id}/usersWithoutProject/search" method="POST">
            </c:otherwise>
        </c:choose>
            <div class="input-group form-inline col-xs-8">
                <select class="selectpicker " data-width="25%" type="text" name="searchedParam">
                    <option value="">Option</option>
                    <option value="Email">E-mail</option>
                    <option value="First Name">First Name</option>
                    <option value="Last Name">Last Name</option>
                </select>
                <span class="input-group-btn"><input type="text" class="form-control" name="searchedString"
                                                     placeholder="Search...">
                <button class="btn btn-default" type="submit">
                    <span class="glyphicon glyphicon-search"></span></button></span>
            </div>
        </form>
    </div>
</div>

<div class="margin-top-20">
    <div class="row">
        <div class="col-sm-8 col-sm-offset-2">
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>E-mail</th>
                    <th class="text-center">Add to Project</th>
                    <th class="text-center">User Info</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${userList.content}">
                    <tr>
                        <td><c:out value="${user.firstName}"/></td>
                        <td><c:out value="${user.lastName}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${action eq 'addPM'}">
                                <!--Add PM to project -->
                                <a data-toggle="modal" data-target="#AddingPM${user.id}${project.id}"
                                   class="btn btn-default">ADD PM</a>

                                <!-- Modal for adding of PM to project-->
                                <div class="modal fade" id="AddingPM${user.id}${project.id}" tabindex="-1"
                                     role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                                <h5 class="modal-title pull-left">Adding Project Manager </h5>
                                            </div>
                                            <div class="modal-body">
                                                <p class="text-center">Confirm adding <strong>${user.firstName}
                                                    ${user.lastName}</strong> as Project Manager of
                                                    <strong>${project.title}</strong></p>
                                            </div>
                                            <div class="modal-footer">
                                                <button class="btn btn-default" data-dismiss="modal">
                                                    Cancel
                                                </button>
                                                <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject/${user.id}'/>"
                                                   class="btn btn-u">Confirm
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a type="button" class="btn btn-default" data-toggle="modal"
                                       data-target="#modalForm${project.id}${user.id}"
                                       href="<spring:url value='/projects/project/
                                       ${project.id}/usersWithoutProject/${user.id}/selectRole'/>"
                                       class="btn btn-default">ADD
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="text-center">
                            <a href="<spring:url value='/user/${user.id}/view'/>"
                               class="btn btn-default">INFO</a>
                        </td>
                    </tr>
                    <%--Modal form for selecting user role--%>
                    <form action="/projects/project/${project.id}/usersWithoutProject/${user.id}/selectRole"
                          method="post">
                        <div class="modal fade" id="modalForm${project.id}${user.id}" tabindex="-1" role="dialog"
                             aria-labelledby="myModalLabel">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                        <h4 class="modal-title text-center" id="myModalLabel">Select Role for User</h4>
                                    </div>
                                    <div class="modal-body">
                                        <select class="form-control" name="role" type="text">
                                            <option class="text-center" value="${DEV}">Developer</option>
                                            <option class="text-center" value="${QA}">Quality Assurance</option>
                                        </select>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close
                                        </button>
                                        <input type="submit" class="btn btn-u" value="Submit"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${userList.getTotalPages()> 1}">
            <div class="row col-sm-offset-4 col-sm-4">
                <nav aria-label="Page navigation" id="pagerID">
                    <div class="text-center">
                        <ul class="pagination">
                            <li>
                                <c:choose>
                                    <c:when test="${action eq 'addPM'}">
                                        <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject?page=0'/>" aria-label="Start">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<spring:url value='/projects/project/${project.id}/usersWithoutProject?page=0'/>" aria-label="Start">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                            <c:forEach var="page" begin="0" end="${userList.getTotalPages() - 1}">
                                <c:choose>
                                    <c:when test="${action eq 'addPM'}">
                                        <li>
                                            <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject?page=${page}'/>">${page + 1}</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li>
                                            <a href="<spring:url value='/projects/project/${project.id}/usersWithoutProject?page=${page}'/>">${page + 1}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <li>
                                <c:choose>
                                    <c:when test="${action eq 'addPM'}">
                                        <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject?page=${userList.getTotalPages() - 1}'/>"
                                           aria-label="End"><span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<spring:url value='/projects/project/${project.id}/usersWithoutProject?page=${userList.getTotalPages() - 1}'/>"
                                           aria-label="End"><span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
        </c:if>
    </div>
</div>