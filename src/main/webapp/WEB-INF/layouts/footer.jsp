<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="footer">
    <footer class="container-fluid">
        <div class="row">
            <div class="col-sm-6">2016 © All Rights Reserved <br/>BugTrckr</p>
            </div>
            <div class="col-sm-6">
                <sec:authorize access="isAuthenticated()">
                    <p class="pull-right">Your role is
                        <sec:authentication property="principal.username"/>
                    </p>
                </sec:authorize>
            </div>
        </div>
    </footer>
</div>
