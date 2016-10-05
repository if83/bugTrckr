<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<c:choose>
    <c:when test="${action eq 'new'}">
        <c:set var="breadcrumsname" scope="session" value="Add project"/>
        <c:set var="buttonname" scope="session" value="Create"/>
    </c:when>
    <c:otherwise>
        <c:set var="breadcrumsname" scope="session" value="Update project"/>
        <c:set var="buttonname" scope="session" value="Update"/>
    </c:otherwise>
</c:choose>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left"> ${breadcrumsname} </h1>
        </div>
        <div class="col-sm-9">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li><a href="<spring:url value='/projects'/>">Projects</a></li>
                <li class="active"> ${breadcrumsname} </li>
            </ol>
        </div>
    </div>
</div>

<div class="margin-top-30">
    <form:form action="/projects/add" modelAttribute="project" method="POST" id="projectForm">
        <div class="col-sm-5 col-sm-offset-1">
            <spring:bind path="title">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label for="titleInput">Project title</label>
                    <form:input path="title" type="text" class="form-control" id="titleInput"
                                placeholder="Project Title"/>
                    <form:errors path="title" class="control-label"/>
                </div>
            </spring:bind>

            <label class="margin-top-30">
                <form:checkbox id="guest" path="guestView" value="true"/>&nbsp&nbspEnable project review by guests
            </label>
            <p class="small">(this option allows unauthorized users to visit project page)</p>

            <div id="enableView">
                <label class="margin-top-30"><form:checkbox id="issue" path="guestCreateIssues" value="true"/>
                    &nbsp&nbspEnable creation of issue by guests
                </label>
                <p class="small">(this option allows unauthorized users to create issues in the project)</p>
                <label class="margin-top-30"><form:checkbox id="comment" path="guestAddComment" value="true"/>
                    &nbsp&nbspEnable commenting of issues in project
                </label>
                <p class="small">(this option allows unauthorized users to comment issues in the project)</p>
            </div>
        </div>

        <form:hidden path="id"/>

        <spring:bind path="description">
            <div class="margin-top-30 col-sm-10 col-sm-offset-1 form-group ${status.error ? 'has-error' : ''}">
                <label for="editor1">Description</label><br/>
                <form:errors path="description" class="control-label"/>
                <form:textarea path="description" cols="100" id="editor1" rows="10"></form:textarea>
            </div>
            <div class="error" id="#descriptioEror"></div>
        </spring:bind>

        <div class="margin-top-30 col-sm-10 col-sm-offset-1">
            <div class="col-sm-1 col-sm-offset-9">
                <button id="cancelBtn" class="btn btn-default">Cancel</button>
            </div>
            <div>
                <button type="submit" class="btn btn-default pull-right" data-toggle="modal"
                        data-target="#projectCreatingModal">${buttonname}
                </button>
            </div>
        </div>
    </form:form>
</div>