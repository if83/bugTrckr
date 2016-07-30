<%--
  User: ihorlt
  Date: 21.07.16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<div class="row">
    <div class="col-sm-12"><h3>Welcome, you can manage here full project life-cycle...</h3></div>
</div>


<%--LoginForm Form--%>
<c:if test="${empty loginForm.email}">
<div class="row">
    <div class="col-sm-5 col-md-3">
        <form:form commandName="loginForm" modelAttribute="loginForm" action="/" class="reg-page" method="POST">
            <div class="reg-header">
                <h4>Login to your account</h4>
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                <form:input path="email" type="email" placeholder="Email" class="form-control" required=""/>
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                <form:input path="password" type="password" placeholder="Password" class="form-control" required=""/>
            </div>
            <div class="row">
                <div class="col-sm-6 pull-right">
                    <input class="btn-u pull-right" type="submit" value="Login"/>
                </div>
            </div>
        </form:form>
    </div>
</div>
    </c:if>
