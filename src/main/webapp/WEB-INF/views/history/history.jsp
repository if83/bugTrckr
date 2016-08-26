<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left">History</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li class="active">History</li>
            </ol>
        </div>
    </div>
</div>


<div class="margin-top-30 row">
    <div class="col-sm-2 col-sm-offset-1">
        <a href="<spring:url value='' />" class="btn btn-primary btn-u"><i class="fa fa-plus icon-bg-u"></i>
            Add history</a>
    </div>
</div>

<div class="margin-top-30">
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Issue</th>
            <th>User</th>
            <th>ParentHistory</th>
            <th>LastChangeByUser</th>
        </tr>
        </thead>
    </table>
</div>