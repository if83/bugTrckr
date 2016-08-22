<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left">Users</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li class="active">Users</li>
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

<div class="margin-top-30 row">
    <div class="col-sm-2 col-sm-offset-1">
        <a href="<spring:url value='/user/add/' />" class="abtn-u-white"><i class="fa fa-plus icon-bg-u"></i> Add
            user</a>
    </div>
</div>

<div class="margin-top-30">
    <div class="row">
        <div class="col-md-12">

            <table class="table table-hover">
                <thead>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>E-mail</th>
                    <th>Role</th>
                    <th>Project Title</th>
                    <th class="hidden-xs">Description</th>
                    <th>Actions</th>
                </tr>
                </thead>

                <tbody>


                <tr>
                    <form action="/users/searchByName" method="POST" class="form-inline">
                        <td>
                            <div class="input-group">
                                <input name="firstName" type="text" class="form-control form-text"
                                       placeholder="First name"/>
                                <span class="input-group-btn">
                            <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"
                                                                                aria-hidden="true"></span></button>
                        </span>
                            </div>
                        </td>
                        <td>
                            <div class="input-group">
                                <input name="lastName" type="text" class="form-control form-text"
                                       placeholder="Last name"/>
                                <span class="input-group-btn">
                            <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"
                                                                                aria-hidden="true"></span></button>
                        </span>
                            </div>
                        </td>
                    </form>

                    <td>
                        <form action="/users/searchByEmail" method="POST" class="form-inline">
                            <div class="input-group">
                                <input name="email" type="text" class="form-control form-text" required="required"
                                       placeholder="Email"/>
                                <span class="input-group-btn">
                            <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"
                                                                                aria-hidden="true"></span></button>
                        </span>
                            </div>
                        </form>
                    </td>
                    <td>
                        <form action="/users/searchByRole" modelAttribute="roles" method="POST" class="form-inline">
                            <div class="input-group">
                                <select name="role" type="text" class="form-control" id="roleInput">
                                    <c:forEach var="role" items="${roles}">
                                        <option><c:out value="${role}"/></option>
                                    </c:forEach>
                                </select>
                                <span class="input-group-btn">
                            <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"
                                                                                aria-hidden="true"></span></button>
                        </span>
                            </div>
                        </form>
                    </td>
                    <td></td>
                    <td class="hidden-xs"></td>
                    <td></td>
                </tr>



                <c:if test="${isControllerPagable}">
                    <c:forEach var="user" items="${userList.content}">
                        <tr>
                            <td>
                                <a class="viewLink" href="<spring:url value='/user/${user.id}/view'/>">
                                    <c:out value="${user.firstName}"/>
                                </a>
                            </td>
                            <td><c:out value="${user.lastName}"/></td>
                            <td><c:out value="${user.email}"/></td>
                            <td><c:out value="${user.role}"/></td>
                            <td><c:out value="${fn:substring(user.project.title, 0, 40)}"/></td>
                            <td class="hidden-xs"><c:out value="${fn:substring(user.description, 0, 20)}"/></td>
                            <td>
                                <div class="actionButtons">
                                    <a href="<spring:url value='/user/${user.id}/edit' />"><i
                                            class="fa fa-edit icon-table-u"></i></a> &nbsp
                                    <a href="<spring:url value='/user/${user.id}/remove' />"><i
                                            class="fa fa-remove icon-table-u"></i></a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>


                <c:if test="${!isControllerPagable}">
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td>
                                <a class="viewLink" href="<spring:url value='/user/${user.id}/view'/>">
                                    <c:out value="${user.firstName}"/>
                                </a>
                            </td>
                            <td><c:out value="${user.lastName}"/></td>
                            <td><c:out value="${user.email}"/></td>
                            <td><c:out value="${user.role}"/></td>
                            <td><c:out value="${fn:substring(user.project.title, 0, 40)}"/></td>
                            <td class="hidden-xs"><c:out value="${fn:substring(user.description, 0, 20)}"/></td>
                            <td>
                                <div class="actionButtons">
                                    <a href="<spring:url value='/user/${user.id}/edit' />"><i
                                            class="fa fa-edit icon-table-u"></i></a> &nbsp
                                    <a href="<spring:url value='/user/${user.id}/remove' />"><i
                                            class="fa fa-remove icon-table-u"></i></a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>

                </tbody>
            </table>
        </div>
    </div>

    <c:if test="${isControllerPagable}">
        <div class="row">
            <nav aria-label="Page navigation" id="pagerID">
                <div class="text-center">
                    <ul class="pagination">
                        <li>
                            <a href="<spring:url value='/users?page=0'/>" aria-label="Start">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>

                        <c:forEach var="apage" begin="0"  end="${userList.totalPages - 1}">
                            <li>
                                <a href="<spring:url value='/users?page=${apage}'/>"> <c:out value="${apage + 1}" /></a>
                            </li>
                                </c:forEach>
                        <li>
                            <a href="<spring:url value='/users?page=${userList.totalPages - 1}'/>" aria-label="End">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </c:if>

</div>