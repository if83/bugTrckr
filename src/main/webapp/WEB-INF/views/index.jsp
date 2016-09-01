<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<div class="row">
    <div class="col-sm-12"><h3>Welcome, you can manage here full project life-cycle...</h3></div>
</div>


<%--LoginForm Form--%>
<c:if test="${empty loginForm.username}">
    <div class="row">
        <div class="col-sm-5 col-md-3">
            <form:form commandName="loginForm" modelAttribute="loginForm" action="/" id="loginform" class="reg-page"
                       method="POST">
                <div class="reg-header">
                    <h4>Login to your account</h4>
                </div>

                <spring:hasBindErrors name="loginForm">
                    <c:if test="${errors.hasFieldErrors('username')}">
                        <c:set var="errorClass" value="has-error"/>
                    </c:if>
                    <c:if test="${errors.hasFieldErrors('password')}">
                        <c:set var="errorClass" value="has-error"/>
                    </c:if>
                </spring:hasBindErrors>

                <div class="input-group <c:out value='${errorClass}' />">
                    <span class="input-group-addon"><i class="fa fa-user"></i></span>
                    <form:input path="username" name="username" type="username" placeholder="Email" class="form-control"
                                required="" pattern=".{5,}"/>
                    <span class="help-block margin-left-5">
                        <form:errors path="username"/>
                    </span>
                </div>
                <div class="input-group <c:out value='${errorClass}' />">
                    <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                    <form:input path="password" name="password" type="password" placeholder="Password"
                                class="form-control"
                                required="" pattern=".{5,}"/>
                    <span class="help-block margin-left-5">
                        <form:errors path="password"/>
                    </span>
                </div>
                <div class="row">
                    <div class="col-sm-6 pull-right">
                        <input class="btn-u pull-right" type="submit" value="Login"/>
                    </div>
                </div>
            </form:form>
        </div>

        <div class="col-sm-7 col-md-9">
            <h4>For testing purposes:</h4>
            <ul>
                <li>As ADMIN, please login:
                    <ul>
                        <li>username: <b>admin@ss.com</b></li>
                        <li>password: <b>admin</b></li>
                    </ul>
                </li>
                <li>As PROJECT_MANAGER, please login:
                    <ul>
                        <li>username: <b>manager@ss.com</b></li>
                        <li>password: <b>manager</b></li>
                    </ul>
                </li>
                <li>As DEVELOPER, please login:
                    <ul>
                        <li>username: <b>developer@ss.com</b></li>
                        <li>password: <b>developer</b></li>
                    </ul>
                </li>
                <li>As QA, please login:
                    <ul>
                        <li>username: <b>quality_e@ss.com</b></li>
                        <li>password: <b>quality_e</b></li>
                    </ul>
                </li>
                <li>As USER, please login:
                    <ul>
                        <li>username: <b>user1@ss.com</b></li>
                        <li>password: <b>user1</b></li>
                    </ul>
                </li>

                <li>As GUEST, please login:
                    <ul>
                        <li>username: <b>guest1@ss.com</b></li>
                        <li>password: <b>guest1</b></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</c:if>
