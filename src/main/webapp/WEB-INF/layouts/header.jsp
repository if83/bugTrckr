<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<header class="topbar container-fluid">
    <div class="row">

        <div class="col-sm-5">
            <ul class="left-topbar">
                <li>Bug Trckr - An essential tool for a project developer</li>
            </ul>
        </div>
        <form action="/contentSearch" method="POST">
            <div class="col-sm-3">
                <il>
                    <input name="title" type="text" placeholder="Search"/>
                    <input type="submit" value="Search"/>
                </il>
            </div>
        </form>
        <div class="col-sm-4 col-md-4">
            <div class="row">
                <ul class="right-topbar">
                    <div class="col-sm-11 col-sm-offset-1">
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
