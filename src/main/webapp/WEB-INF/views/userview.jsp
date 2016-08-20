<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left"> User Detail </h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/' />">Home</a></li>
                <li><a href="<spring:url value='/users' />">Users</a></li>
                <li class="active"> User Detail</li>
            </ol>
        </div>
    </div>
</div>

<div class="margin-top-30 row">
    <div class="col-sm-11 col-sm-offset-1 col-md-6 col-md-offset-1">

        <div class="row">
            <label class="col-sm-2">ID</label>
            <div class="col-sm-10"><p>${user.id}</p></div>
        </div>


        <div class="row">
            <label class="col-sm-2">Email</label>
            <div class="col-sm-10"><p>${user.email}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-2">First name</label>
            <div class="col-sm-10"><p>${user.firstName}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-2">Last name</label>
            <div class="col-sm-10"><p>${user.lastName}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-2">Role</label>
            <div class="col-sm-10"><p>${user.role}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-2">Description</label>
            <div class="col-sm-10">${user.description}</div>
        </div>

    </div>

    <div class="col-sm-11 col-sm-offset-1 col-md-3">
        <figure>
            <img src="data:image/jpg;base64,<c:out value='${user.encodedImage}'/>" class="img-thumbnail"
                 alt="${user.lastName}"/>
        </figure>
    </div>
</div>


<div class="margin-bottom-60">
    <div class="row"><p>&nbsp;</p></div>
</div>

