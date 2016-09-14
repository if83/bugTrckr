<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<header class="topbar container-fluid">
    <div class="row">
        <div class="col-sm-6">
            <ul class="left-topbar">
                <li>Bug Trckr - An essential tool for a project developer</li>
            </ul>
        </div>
        <div class="col-sm-6 col-md-6">
            <div class="row">
                <ul class="right-topbar">
                    <div class="col-sm-11 col-sm-offset-1">
                        <sec:authorize access="!hasAnyRole('ADMIN','PROJECT_MANAGER', 'DEVELOPER', 'QA', 'USER',
                        'GUEST')">
                            <!-- Button trigger login form -->
                            <button type="button" class="btn btn-u pull-right" data-toggle="modal"
                                    data-target="#loginFormModal">Log in
                            </button>
                            <!-- Modal for login form -->
                            <div class="modal fade" id="loginFormModal" tabindex="-1" role="dialog">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-body">
                                        <form:form commandName="loginForm" modelAttribute="loginForm" action="/"
                                                   id="loginform" class="reg-page" method="POST">
                                            <div class="reg-header">
                                                <h3>Log into BugTrckr</h3>
                                            </div>
                                            <div class="alert-danger">
                                                <h3 class="text-center alert-danger" id="error">
                                                    Invalid email or password
                                                </h3>
                                            </div>
                                            <div class="input-group center-block" id="email">
                                                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                                <form:input path="username" name="username" type="username"
                                                            placeholder="Email" class="form-control"/>
                                                <form:errors path="username"/>
                                            </div>
                                            <div class="input-group" id="password">
                                                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                                <form:input path="password" name="password" type="password"
                                                            placeholder="Password" class="form-control"></form:input>
                                                <form:errors path="password"/>
                                            </div>
                                            <div class="margin-bottom-30">
                                                <button class="col-sm-offset-8 btn btn-default" data-dismiss="modal">
                                                    Cancel
                                                </button>
                                                <input class="col-sm-2 btn btn-u pull-right" type="submit"
                                                       value="Login"/>
                                            </div>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </sec:authorize>

                        <sec:authorize
                                access="hasAnyRole('ADMIN','PROJECT_MANAGER', 'DEVELOPER', 'QA', 'USER', 'GUEST')">
                            <li>
                                You are logged as &nbsp&nbsp&nbsp <sec:authentication property="principal.username"/>
                                &nbsp&nbsp&nbsp&nbsp
                                <a class="btn btn-secondary btn-sm btn-u"
                                   href="<spring:url value='/logout'/>">Logout</a>
                            </li>
                        </sec:authorize>
                    </div>
                </ul>
            </div>
        </div>
    </div>
</header>
