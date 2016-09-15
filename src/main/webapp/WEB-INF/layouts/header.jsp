<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<nav class="navbar navbar-default">
    <div class="container-fluid">


        <div class="nav navbar navbar-left">
                <a href="<spring:url value='/'/>">
                    <img src="<spring:url value='/images/AS.png'/>" style="width: 70px;"></a>
        </div>

        <div class="navbar-header">
            <div class="navbar-header">
                <a class="navbar-brand" href="/">BugTrckr</a>
            </div>
        </div>

        <ul class="nav navbar-nav">
            <li class="mainMenuHomeItem active"><a href="<spring:url value='/'/>">Home</a></li>
            <li class="mainMenuProjectItem"><a href="<spring:url value='/projects'/>">Projects</a></li>
            <li class="mainMenuIssueItem"><a href="<spring:url value='/issue'/>">Issues</a></li>

            <sec:authorize access="hasAnyRole('ADMIN', 'PROJECT_MANAGER')">
                <li class="mainMenuUserItem"><a href="<spring:url value='/users'/>">Users</a></li>
            </sec:authorize>

            <sec:authorize access="hasAnyRole('DEVELOPER', 'QA', 'USER', 'GUEST')">
                <li class="mainMenuUserItem"><a href="<spring:url value='/user/details'/>">Profile</a></li>
            </sec:authorize>

            <sec:authorize access="hasRole('ADMIN')">
                <li class="mainMenuAdminItem"><a href="<spring:url value='/admin'/>">Admin</a></li>
            </sec:authorize>

            <li class="mainMenuAboutItem"><a href="<spring:url value='/about'/>">About</a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
            <li>
                <a>
                    <form action="/contentSearch" method="POST" class="form-inline">
                        <input class="form-control" type="text" placeholder="Search">
                        <button class="btn btn-outline-success" type="submit">Search</button>
                    </form>
                </a>
            </li>

            <li>
                <a>
                    <sec:authorize access="!hasAnyRole('ADMIN','PROJECT_MANAGER', 'DEVELOPER', 'QA', 'USER',
                        'GUEST')">
                        <!-- Button trigger login form -->
                        <button type="button" class="btn btn-u pull-right" data-toggle="modal"
                                data-target="#loginFormModal">Log in
                        </button>

                        <!-- Modal for login form -->
                        <div class="modal fade" id="loginFormModal" tabindex="-1"
                             role="dialog">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">Log into BugTrckr</h4>
                                    </div>
                                    <form:form commandName="loginForm" modelAttribute="loginForm" action="/"
                                               id="loginform" class="reg-page" method="POST">
                                        <div class="alert-danger">
                                            <h5 class="text-center alert alert-danger" id="error">
                                                Invalid email or password
                                            </h5>
                                        </div>

                                        <div id="msg" class="alert alert-danger margin-bottom-20" hidden></div>

                                        <div class="input-group" id="email">
                                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                            <form:input path="username" name="username" type="username"
                                                        placeholder="Email" class="form-control"/>
                                            <form:errors path="username"/>
                                        </div>
                                        <div class="input-group" id="password">
                                            <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                            <form:input path="password" name="password" type="password"
                                                        placeholder="Password" class="form-control"/>
                                            <form:errors path="password"/>
                                        </div>

                                        <div class="form-group margin-bottom-40">
                                            <div class="controls pull-right">
                                                <button type="submit" class="btn-u pull-right"><i
                                                        class="glyphicon glyphicon-log-in"></i> Log in</button>
                                            </div>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </sec:authorize>
                    <sec:authorize
                            access="hasAnyRole('ADMIN','PROJECT_MANAGER', 'DEVELOPER', 'QA', 'USER', 'GUEST')">
                        <div>
                            <sec:authentication property="principal.username"/>
                            <a class="btn btn-u"
                               href="<spring:url value='/logout'/>">Logout</a>
                        </div>
                    </sec:authorize>
                </a>

            </li>
        </ul>
    </div>
</nav>
