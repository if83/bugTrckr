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

<%--release form--%>
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
            <c:if test="${formAction eq 'edit'}">
                <spring:bind path="releaseStatus">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label for="statusInput">Status</label>
                        <form:select path="releaseStatus" type="text" class="selectpicker form-control" id="statusInput"
                                     placeholder="Select status">
                            <c:forEach var="status" items="${releaseStatuses}">
                                <form:option value="${status}" label="${status.toString()}"/>
                            </c:forEach>
                        </form:select>
                        <form:errors path="releaseStatus" class="control-label"/>
                    </div>
                </spring:bind>
            </c:if>
        </div>
        <div class="col-sm-10 col-sm-offset-1">
            <label for="editor1">Description</label>
        </div>
        <div class="col-sm-10 col-sm-offset-1">
            <form:textarea path="description" cols="100" id="editor1" rows="10"/>
        </div>
        <form:hidden path="id"/>
        <div class="col-sm-10 col-sm-offset-1">
            <button type="button" class="margin-top-30 btn-u pull-right" data-toggle="modal"
                    data-target="#releaseConfirmModal">${buttonName}
            </button>
        </div>
        <!-- Modal confirmation for creating/editing of project -->
        <div class="modal fade" id="releaseConfirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">${buttonName} release</h4>
                    </div>
                    <div class="modal-body text-center">
                        Please confirm ${buttonName} of release
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <input type="submit" value="Confirm" class="btn btn-u"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>