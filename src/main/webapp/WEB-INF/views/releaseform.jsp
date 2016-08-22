<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:choose>
    <c:when test="${formAction eq 'new'}">
        <c:set var="breadcrumsName" scope="session" value="Add release"/>
        <c:set var="buttonName" scope="session" value="Create"/>
    </c:when>
    <c:otherwise>
        <c:set var="breadcrumsName" scope="session" value="Update release"/>
        <c:set var="buttonName" scope="session" value="Update"/>
    </c:otherwise>
</c:choose>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left">${breadcrumsName}</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li><a href="<spring:url value='/projects'/>">Projects</a></li>
                <li><a href="<spring:url value='/project/${project.id}'/>">${project.title}</a></li>
                <li class="active">${breadcrumsName}</li>
            </ol>
        </div>
    </div>
</div>


<form:form action="/project/${project.id}/release/add" modelAttribute="release" method="POST">
    <div class="row">

        <div class="col-sm-12 col-md-5 col-md-offset-1">

            <spring:bind path="version">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label for="versionInput">Version</label>
                    <form:input path="version" type="text" class="form-control" id="versionInput"
                                placeholder="Version of release"/>
                    <form:errors path="version" class="control-label"/>
                </div>
            </spring:bind>

            <spring:bind path="releaseStatus">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label for="statusInput">Status</label>
                    <form:select path="releaseStatus" type="text" class="form-control" id="statusInput" placeholder="Status">
                        <form:option value="" label="Select a status"/>
                        <form:options items="${statuses}"/>
                    </form:select>
                    <form:errors path="releaseStatus" class="control-label"/>
                </div>
            </spring:bind>


        </div>

        <div class="col-sm-10 col-sm-offset-1">
            <label for="editor1">Description</label>
        </div>
        <div class="col-sm-10 col-sm-offset-1">
            <form:textarea path="description" cols="100" id="editor1" rows="10"/>
        </div>

        <form:hidden path="id"/>
        <div class="col-sm-10 col-sm-offset-1">
            <input type="submit" value="${buttonName}" class="margin-top-30 btn-u pull-right"/>
        </div>

    </div>
</form:form>


