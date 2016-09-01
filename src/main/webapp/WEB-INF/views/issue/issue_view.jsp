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
                <li><a href="<spring:url value='/issue' />">Issues</a></li>
                <li class="active"> Issue Information</li>
            </ol>
        </div>
    </div>
</div>

<div class="margin-top-30 row">
    <div class="col-sm-11 col-sm-offset-1 col-md-6 col-md-offset-1">

        <div class="row">
            <label class="col-sm-3">ID</label>
            <div class="col-sm-9"><p>${issue.id}</p></div>
        </div>


        <div class="row">
            <label class="col-sm-3">Title</label>
            <div class="col-sm-9"><p>${issue.title}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-3">Type</label>
            <div class="col-sm-9"><p>${issue.type}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-3">Status</label>
            <div class="col-sm-9"><p>${issue.status}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-3">Priority</label>
            <div class="col-sm-9">${issue.priority}</div>
        </div>

        <div class="row">
            <label class="col-sm-3">Release version</label>
            <div class="col-sm-9"><p>${issue.projectRelease.project.title} Release : ${issue.projectRelease.version}</p>
            </div>
        </div>

        <div class="row">
            <label class="col-sm-3">Assisgnee</label>
            <div class="col-sm-9"><p>${issue.assignee.firstName} ${issue.assignee.lastName} </p></div>
        </div>

        <div class="row">
            <label class="col-sm-3">Create Time</label>
            <div class="col-sm-9"><p>${issue.createTime}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-3">Time to finish the issue</label>
            <div class="col-sm-9">${issue.dueDate}</div>
        </div>

        <div class="row">
            <label class="col-sm-3">Last updated time</label>
            <div class="col-sm-9">${issue.lastUpdateDate}</div>
        </div>

        <div class="row">
            <label class="col-sm-3">Estimate time</label>
            <div class="col-sm-9"><p>${issue.estimateTime}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-3">Ability to edit</label>
            <div class="col-sm-9">${issue.editAbility}</div>
        </div>

        <div class="row">
            <label class="col-sm-3">Description</label>
            <div class="col-sm-9">${issue.description}</div>
        </div>
    </div>

</div>






