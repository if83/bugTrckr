<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left"> Add User </h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li><a href="<spring:url value='/users'/>">Users</a></li>
                <li class="active"> Add User </li>
            </ol>
        </div>
    </div>
</div>


<div class="margin-top-30 row">
    <div class="col-sm-8  col-md-offset-2">
        <div class="row">

            <form:form id="userForm" commandName="userCommand" action="/user/add" modelAttribute="user" method="POST">
                <div class="col-sm-12">
                    <div class="col-sm-6 pull-left row">
                        <spring:bind path="email">
                            <div class="form-group">
                                <label for="emailInput">E-mail</label>
                                <form:input path="email" type="email" cssClass="form-control" id="emailInput"
                                            placeholder="E-mail"/>
                                <form:errors path="email" cssClass="control-label"/>
                            </div>
                        </spring:bind>
                    </div>
                    <div class="col-sm-6 pull-right row">
                        <spring:bind path="firstName">
                            <div class="form-group">
                                <label for="firstNameInput">First name</label>
                                <form:input path="firstName" type="text" cssClass="form-control" id="firstNameInput"
                                            placeholder="First name"/>
                                <form:errors path="firstName" cssClass="control-label"/>
                            </div>
                        </spring:bind>
                    </div>
                </div>

                <div class="col-sm-12">
                    <div class="col-sm-6 pull-left row">
                        <spring:bind path="lastName">
                            <div class="form-group">
                                <label for="lastNameInput">Last name</label>
                                <form:input path="lastName" type="text" cssClass="form-control" id="lastNameInput"
                                            placeholder="Last name"/>
                                <form:errors path="lastName" cssClass="control-label"/>
                            </div>
                        </spring:bind>
                    </div>
                    <div class="col-sm-6 pull-right row password">
                        <spring:bind path="password">
                            <div class="form-group">
                                <label for="passwordInput">Password</label>
                                <form:input path="password" type="password" cssClass="form-control" id="passwordInput"
                                            placeholder="Password"/>
                                <form:errors path="password" cssClass="control-label"/>
                            </div>
                        </spring:bind>
                    </div>
                </div>

                <div class="col-sm-12">
                    <div class="col-sm-6 pull-left row password">
                        <spring:bind path="confirmPassword">
                            <div class="form-group">
                                <label for="confirmPasswordInput">Confirm password</label>
                                <form:input path="confirmPassword" type="password" cssClass="form-control"
                                            id="confirmPasswordInput"
                                            placeholder="Confirm password"/>
                                <form:errors path="confirmPassword" cssClass="control-label"/>
                            </div>
                        </spring:bind>
                    </div>

                    <div class="col-sm-6 pull-right row password">
                        <label class="margin-bottom-20 margin-top-30">
                            <input type="checkbox" id="chooseProject"/>&nbsp&nbspSelect Project and Role for User
                        </label>
                    </div>
                </div>
                <div class="col-sm-12">
                    <div class="col-sm-6 pull-left row">
                        <spring:bind path="project">
                            <div class="form-group" id="project" hidden>
                                <label for="roleInput">Project</label>
                                <form:select path="project" type="text" cssClass="form-control selectpicker" id="projectInput"
                                             data-live-search="true">
                                    <form:option cssClass="projectOption" value="0" label="Project is not selected"/>
                                    <c:forEach var="project" items="${projects}">
                                        <form:option cssClass="projectOption" value="${project}" label="${project.getTitle()}"/>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="role" cssClass="control-label"/>
                                <p class="project-info small">*if Project is not selected, role User will be assigned</p>
                            </div>
                        </spring:bind>
                    </div>

                    <div class="col-sm-6 pull-right row">
                        <spring:bind path="role">
                            <div class="form-group" id="role" hidden>
                                <label for="roleInput">Role</label>
                                <form:select path="role" type="text" cssClass="form-control selectpicker" id="roleInput">
                                    <c:forEach var="role" items="${roles}">
                                        <form:option cssClass="roleOption${role.toString()}" value="${role}" label="${role.toString()}"/>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="role" cssClass="control-label"/>
                            </div>
                        </spring:bind>
                    </div>
                </div>

                <div class="col-sm-12">
                    <label for="editor1">Description</label>
                </div>
                <div class="col-sm-12">
                    <form:textarea path="description" cols="100" id="editor1" rows="10"></form:textarea>
                </div>

                <form:hidden path="id"/>

                <div class="col-sm-12">
                    <button id="cancelBtn" class="margin-top-30 btn btn-default col-sm-offset-9">Cancel</button>
                    <input type="submit" value="Create" id="confirmForm" class="margin-top-30 btn btn-default pull-right"/>
                </div>
            </form:form>
        </div>
    </div>
</div>
