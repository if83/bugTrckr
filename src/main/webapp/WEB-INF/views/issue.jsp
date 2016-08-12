<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left">Issue</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li class="active">Issue</li>
            </ol>
        </div>
    </div>
</div>


<div class="row">
    <div class="col-sm-12">
        <h2 class=""><a href="<spring:url value='/worklog'/>">Worklog</a></h2>
    </div>
</div>

<div class="row">
    <div class="col-sm-12">
        <h2 class=""><a href="<spring:url value='/label'/>">Label</a></h2>
    </div>
</div>

