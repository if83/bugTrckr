<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left"> Update User </h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li><a href="<spring:url value='/users'/>">Users</a></li>
                <li class="active"> Update User </li>
            </ol>
        </div>
    </div>
</div>

<div class="margin-top-30 row">
    <div class="col-sm-8  col-sm-offset-2">
        <div class="row">

            <form id="userForm" action="/user/${user.id}/edit" method="POST">
                <div class="col-sm-12">
                    <div class="col-sm-6 pull-left row">
                        <div class="form-group">
                            <label for="firstNameInput">First name</label>
                            <input name="firstName" type="text" class="form-control" id="firstNameInput"
                                   value="${user.firstName}"/>
                        </div>
                    </div>

                    <div class="col-sm-6 pull-right row">
                        <div class="form-group">
                            <label for="lastNameInput">Last name</label>
                            <input name="lastName" type="text" class="form-control" id="lastNameInput" value="${user.lastName}"/>
                        </div>
                    </div>
                </div>

                <div class="col-sm-12">
                    <div class="col-sm-6 pull-left row">
                        <div class="form-group">
                            <label for="emailInput">E-mail</label>
                            <input name="email" type="email" class="form-control" id="emailInput" value="${user.email}"/>
                            <div class="help-block">
                                <p class="error-msg has-error"><strong>${msg}</strong></p>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 form-group row pull-right">
                        <label class="margin-bottom-20 margin-top-30">
                            <input type="checkbox" id="chooseProject"/>&nbsp&nbspChange Project and Role for User
                        </label>
                    </div>
                </div>

                <div class="col-sm-12">
                    <div class="col-sm-6 pull-left row">
                        <div class="form-group" id="project" hidden>
                            <label for="projectInput">Project</label>
                            <select name="projectId" class="form-control selectpicker" id="projectInput"
                                    data-live-search="true">
                                <c:choose>
                                    <c:when test="${user.project eq null}">
                                        <option class="projectOption" value="0">Project is not selected</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option class="projectOption" value="${user.project.id}">${user.project.title}</option>
                                        <option class="projectOption" value="0">Project is not selected</option>
                                    </c:otherwise>
                                </c:choose>
                                <c:forEach var="project" items="${projects}">
                                    <c:if test="${user.project.id != project.id}">
                                        <option class="projectOption" value="${project.id}">${project.title}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <p class="project-info small">*if Project is not selected, role User will be assigned</p>
                            <p id="projectDefault" hidden>${user.project.id}</p>
                        </div>
                    </div>
                    <div class="col-sm-6 pull-right row">
                        <div class="form-group" id="role" hidden>
                            <label for="roleInput">Role</label>
                            <select name="role" class="form-control selectpicker" id="roleInput">
                                <option class="roleOptionFirst" value="${user.role}">${user.role.toString()}</option>
                                <c:forEach var="role" items="${roles}">
                                    <option class="roleOption${role}" value="${role}">${role.toString()}</option>
                                </c:forEach>
                            </select>
                            <p id="roleDefault" hidden>${user.role}</p>
                        </div>
                    </div>
                </div>

                <div class="col-sm-12">
                    <label for="editor1">Description</label>
                </div>
                <div class="col-sm-12">
                    <textarea name="description" cols="100" id="editor1" rows="10">
                        <c:out value="${user.description}"/>
                    </textarea>
                </div>

                <div class="col-sm-12">
                    <button id="cancelBtn" class="margin-top-30 btn btn-default col-sm-offset-9 row">Cancel</button>
                    <input type="submit" id="confirmForm" value="Update" class="margin-top-30 btn btn-default pull-right"/>
                </div>
            </form>
        </div>
    </div>
</div>