<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>


<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left"> <tiles:insertAttribute name="breadcrumsname" flush="false"/>  </h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li><a href="<spring:url value='/user'/>">User</a></li>
                <li class="active"> <tiles:insertAttribute name="breadcrumsname"/> </li>
            </ol>
        </div>
    </div>
</div>


<div class="margin-top-30 row">
    <form:form action="/user/edit" commandName="user" modelAttribute="user">
    <div class="col-sm-12 col-md-5 col-md-offset-1">
        <div class="form-group">
            <label for="firstNameInput">First name</label>
            <input path="firstName" type="text" class="form-control" id="firstNameInput" placeholder="First name"/>
        </div>
        <div class="form-group">
            <label for="emailInput">E-mail</label>
            <input type="email" class="form-control" id="emailInput" placeholder="E-mail">
        </div>
        <div class="form-group">
            <label for="passwordInput">Password</label>
            <input type="password" class="form-control" id="passwordInput" placeholder="Password">
        </div>
    </div>
    <div class="col-sm-12 col-md-5">
        <div class="form-group">
            <label for="lastNameInput">Last name</label>
            <input type="text" class="form-control" id="lastNameInput" placeholder="Last name">
        </div>
        <div class="form-group">
            <label for="roleInput">Role</label>
            <input type="text" class="form-control" id="roleInput" placeholder="Role">
        </div>
    </div>
        <div class="col-sm-10 col-sm-offset-1">
            <label for="editor1">Description</label>
        </div>
        <div class="col-sm-10 col-sm-offset-1">
            <textarea cols="100" id="editor1" name="editor1" rows="10"></textarea>
        </div>

        <div class="col-sm-10 col-sm-offset-1">
            <input type="submit" value="Save" class="margin-top-30 btn-u pull-right" />
        </div>
    </form:form>

</div>
