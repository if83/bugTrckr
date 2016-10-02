<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-3 col-sm-offset-1">
            <h1 class="pull-left">Available Users</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li><a href="<spring:url value='/projects'/>">Projects</a></li>
                <li><a href="<spring:url value='/projects/project/${project.id}'/>">${project.title}</a></li>
                <li><a>Available Users</a></li>
            </ol>
        </div>
    </div>
</div>

<!-- Nav tabs -->
<c:if test="${action eq 'addPM'}">
    <ul class="nav nav-tabs">
        <li role="presentation"><a href="#allUsers" aria-controls="home" role="tab" data-toggle="tab">All Users</a></li>
        <li role="presentation"><a href="#usersInProject" aria-controls="profile" role="tab" data-toggle="tab">Users
            in ${project.title}</a></li>
    </ul>
</c:if>

<div class="margin-top-20 row">
    <div class="col-sm-8 col-sm-offset-2 tab-content">
        <div>
            <c:choose>
                <c:when test="${action eq 'addPM'}">
                    <form id="search" action="/projects/project/${project.id}/addProjectManager/usersWithoutProject"
                        method="POST">
                </c:when>
                <c:otherwise>
                    <form action="/projects/project/${project.id}/usersWithoutProject/search" method="POST">
                </c:otherwise>
            </c:choose>
                        <div class="input-group form-inline col-sm-5">
                            <select class="selectpicker" id="search" type="text" name="searchedParam">
                                <option value="">Option</option>
                                <option value="Email">E-mail</option>
                                <option value="First Name">First Name</option>
                                <option value="Last Name">Last Name</option>
                            </select>
                            <span class="input-group-btn">
                                <input type="text" class="form-control"
                                name="searchedString" placeholder="Search..."><button class="btn btn-default" type="submit">
                                <span class="glyphicon glyphicon-search"></span></button>
                            </span>
                        </div>
                    </form>
        </div>
        <%--List of all users with role USER--%>
        <div role="tabpanel" class="tab-pane active" id="allUsers">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th class="text-center">Full Name</th>
                    <th class="text-center">E-mail</th>
                    <th class="text-center">Add to Project</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${userList.content}">
                    <tr>
                        <td class="text-center">
                            <a class="viewLink" href="<spring:url value='/user/${user.id}/view'/>"><c:out value="${user.fullName}"/></a>
                        </td>
                        <td class="text-center"><c:out value="${user.email}"/></td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${action eq 'addPM'}">
                                    <!--Add PM to project -->
                                    <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject/${user.id}'/>"
                                       class="btn btn-default addPMBtn">ADD PM
                                        <p class="text-center hidden addPMNotification">Confirm adding <strong>
                                                ${user.fullName}</strong> as Project Manager of
                                            <strong>${project.title}</strong></p>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a type="button" class="btn btn-default userRoleBtn"
                                       href="<spring:url value='/projects/project/${project.id}/usersWithoutProject/${user.id}/selectRole'/>">ADD
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%--Paggination of user list--%>
            <c:if test="${userList.getTotalPages()> 1}">
                <nav aria-label="Page navigation" class="text-center" id="pagerID">
                    <ul class="pagination">
                        <li>
                            <c:choose>
                                <c:when test="${action eq 'addPM'}">
                                    <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject?availableUsers_page=0'/>" aria-label="Start">
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
                                        <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject?availableUsers_page=${page}'/>">${page + 1}</a>
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
                                    <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject?availableUsers_page=${userList.getTotalPages() - 1}'/>"
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
                </nav>
            </c:if>
        </div>
        <%--List of users in current project--%>
        <div role="tabpanel" class="tab-pane" id="usersInProject">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th class="text-center">Full Name</th>
                    <th class="text-center">E-mail</th>
                    <th class="text-center">Add to Project</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${usersInProject.content}">
                    <tr>
                        <td class="text-center">
                            <a class="viewLink" href="<spring:url value='/user/${user.id}/view'/>"><c:out value="${user.fullName}"/></a>
                        </td>
                        <td class="text-center"><c:out value="${user.email}"/></td>
                        <td class="text-center">
                            <!--Add PM to project -->
                            <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject/${user.id}'/>"
                               class="btn btn-default addPMBtn">ADD PM
                                <p class="text-center hidden addPMNotification">Confirm appointment <strong>${user.fullName}
                                </strong> as Project Manager of<strong>${project.title}</strong></p>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%--Paggination of user in project list--%>
            <c:if test="${usersInProject.getTotalPages()> 1}">
                <div class="row col-sm-offset-4 col-sm-4">
                    <nav aria-label="Page navigation" class="text-center" id="pagerID">
                        <ul class="pagination">
                            <li>
                                <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject?usersInProject_page=0'/>" aria-label="Start">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach var="page" begin="0" end="${usersInProject.getTotalPages() - 1}">
                                <li>
                                    <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject?usersInProject_page=${page}'/>">${page + 1}</a>
                                </li>
                            </c:forEach>
                            <li>
                                <a href="<spring:url value='/projects/project/${project.id}/addProjectManager/usersWithoutProject?usersInProject_page=${usersInProject.getTotalPages() - 1}'/>"
                                   aria-label="End"><span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </c:if>
        </div>
    </div>
</div>

<%--Modal form for selecting user role--%>
<form action="" method="post" id="userRoleForm">
    <div class="modal fade" id="userRoleModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title text-center">Select Role for User</h4>
                </div>
                <div class="modal-body">
                    <select class="form-control" name="role" type="text">
                        <option class="text-center" value="${DEV}">Developer</option>
                        <option class="text-center" value="${QA}">Quality Assurance</option>
                    </select>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" data-dismiss="modal">Close</button>
                    <input type="submit" class="btn btn-u" value="Submit"/>
                </div>
            </div>
        </div>
    </div>
</form>

<!-- Modal for adding of PM to project-->
<div class="modal fade" id="addingPM" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title pull-left">Appointment of Project Manager</h4>
            </div>
            <div class="modal-body text-center"></div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a href="" class="btn btn-u addPMBtnConfirm">Confirm</a>
            </div>
        </div>
    </div>
</div>