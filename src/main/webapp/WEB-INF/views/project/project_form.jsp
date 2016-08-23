<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<c:choose>
    <c:when test="${formaction eq 'new'}">
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
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li><a href="<spring:url value='/projects'/>">Projects</a></li>
                <li class="active"> ${breadcrumsname} </li>
            </ol>
        </div>
    </div>
</div>

<c:if test="${not empty msg}">
    <div class="row">
        <div class="col-sm-4 col-sm-offset-8">
            <div class="alert alert-${css} alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <strong>${msg}</strong>
            </div>
        </div>
    </div>
</c:if>

<div class="margin-top-30">
    <form:form action="/projects/add" modelAttribute="project" method="POST">
        <div class="col-sm-4 col-sm-offset-1">
            <spring:bind path="title">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label for="titleInput">Project title</label>
                    <form:input path="title" type="text" class="form-control" id="titleInput"
                                placeholder="Project Title"/>
                    <form:errors path="title" class="control-label"/>
                </div>
            </spring:bind>

            <spring:bind path="guestView">
                <div class="form-group ${status.error ? 'has-error' : ''} margin-top-30">
                    <label>Ability to review project by guests:</label>
                    <label class="pull-right"><form:radiobutton path="guestView" value="false"/>False</label>
                    <label class="pull-right"><form:radiobutton path="guestView" value="true"/>True &nbsp&nbsp</label>
                    <form:errors path="guestView" class="control-label"/>
                </div>
            </spring:bind>

            <spring:bind path="guestCreateIssues">
                <div class="form-group ${status.error ? 'has-error' : ''} margin-top-30">
                    <label>Ability to create issue by guests:</label>
                    <label class="pull-right"><form:radiobutton path="guestCreateIssues" value="false"/>False</label>
                    <label class="pull-right"><form:radiobutton path="guestCreateIssues" value="true"/>True &nbsp&nbsp</label>
                    <form:errors path="guestCreateIssues" class="control-label"/>
                </div>
            </spring:bind>

            <spring:bind path="guestAddComment">
                <div class="form-group ${status.error ? 'has-error' : ''} margin-top-30">
                    <label>Ability to comment issues in project:</label>
                    <label class="pull-right"><form:radiobutton path="guestAddComment"  value="false"/>False</label>
                    <label class="pull-right"><form:radiobutton path="guestAddComment"  value="true"/>True &nbsp&nbsp</label>
                    <form:errors path="guestAddComment" class="control-label"/>
                </div>
            </spring:bind>
        </div>

        <div class="col-sm-6 pull-left">
            <div>
                <label for="editor">Description</label>
            </div>

            <div>
                <form:textarea path="description" cols="100" id="editor" rows="10"></form:textarea>
            </div>
        </div>

        <div class="col-sm-10 col-sm-offset-1">
            <input type="submit" value="${buttonname}" class="margin-top-30 btn-u pull-right"/>
        </div>
    </form:form>
</div>