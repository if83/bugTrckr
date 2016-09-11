<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:choose>
    <c:when test="${formAction eq 'new'}">
        <c:set var="breadcrumsname" scope="session" value="Create Issue"/>
        <c:set var="buttonname" scope="session" value="Create"/>
    </c:when>
    <c:otherwise>
        <c:set var="breadcrumsname" scope="session" value="Update Issue"/>
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
                <li><a href="<spring:url value='/issue'/>">Issues</a></li>
                <li class="active"> ${breadcrumsname} </li>
            </ol>
        </div>
    </div>
</div>

<fmt:formatDate value="${sampleDate}" var="dateString" pattern="dd/MM/yyyy"/>

<div class="margin-top-30 row">
    <div class="col-sm-12 col-md-8 col-md-offset-1">
        <div class="row">
            <form:form commandName="issueCommand" action="/issue/add" modelAttribute="issue" method="POST">
                <div class="col-sm-6">

                    <spring:bind path="title">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="titleInput">Title</label>
                            <form:input path="title" type="text" cssClass="form-control" id="titleInput"
                                        placeholder="Title"/>
                            <form:errors path="title" cssClass="control-label"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="type">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="typeInput">Type</label>
                            <form:select path="type" type="text" cssClass="selectpicker form-control" id="typeInput"
                                         placeholder="Type" data-live-search="true">
                                <form:option value="" label="  Select a type"/>
                                <form:options items="${types}"/>
                            </form:select>
                            <form:errors path="type" cssClass="control-label"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="priority">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="priorityInput">Priority</label>
                            <form:select path="priority" type="text" cssClass="selectpicker form-control"
                                         id="priorityInput"
                                         placeholder="Priority" data-live-search="true">
                                <form:option value="" label="  Choose the piority"/>
                                <form:options items="${priority}"/>
                            </form:select>
                            <form:errors path="priority" cssClass="control-label"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="assignee">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="assigneeInput">Assignee Id</label>
                            <form:select path="assignee" type="text" cssClass="form-control selectpicker" id="assigneeInput"
                                         items="${users}" itemLabel="firstName" itemValue="id" data-live-search="true">
                            </form:select>
                            <form:errors path="assignee" cssClass="control-label"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="labels">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="labelsSelect2">Labels</label></br>
                            <form:select id="labelsSelect2" path="labels" items="${allLabels}" data-live-search="true"
                                         itemLabel="title" itemValue="id" multiple="multiple" style="width:380px;"
                                         cssClass="form-control selectpicker"/>
                            <form:errors path="labels" class="control-label"/>
                        </div>
                    </spring:bind>

                </div>

                <div class="col-sm-6">

                    <spring:bind path="estimateTime">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="estimateTimeInput">Estimate time in hours</label>
                            <form:input path="estimateTime" type="text" cssClass="form-control" id="estimateTimeInput"
                                        placeholder="estimateTime"/>
                            <form:errors path="estimateTime" cssClass="control-label"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="dueDate">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="datepicker">Date to finish an issue</label>
                            <form:input path="dueDate" type="text" cssClass="form-control" id="datepicker"
                                        value="${dateString}"/>
                            <form:errors path="dueDate" cssClass="control-label"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="project">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="projectInput">Project</label>
                            <form:select path="project" type="text" cssClass="selectpicker form-control"
                                         id="projectInput" data-live-search="true"
                                         items="${projects}" itemLabel="title" itemValue="id"/>
                            <form:errors path="project" cssClass="control-label"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="projectRelease">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="projectReleaseInput">Release</label>
                            <form:input path="projectRelease" type="text" cssClass="selectpicker form-control"
                                        id="projectReleaseInput" placeholder="ProjectRelease"/>
                            <form:errors path="projectRelease" cssClass="control-label"/>
                        </div>
                    </spring:bind>

                    <c:if test="${formAction eq 'edit'}">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="status">Status</label>
                            <form:select path="status" type="text" cssClass="form-control" id="status" placeholder="Status">
                                <form:option value="${issue.status}" label="${issue.status}"/>
                                <form:options items="${statuses}"/>
                            </form:select>
                            <form:errors path="status" cssClass="control-label"/>
                        </div>
                    </c:if>

                </div>

                <div class="col-sm-6">
                    <spring:bind path="editAbility">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label>Ability to edit an issue:</label>
                            <label class="pull-right"><form:radiobutton path="editAbility" value="false"/>False</label>
                            <label class="pull-right"><form:radiobutton path="editAbility" value="true"/>True
                                &nbsp&nbsp</label>
                            <form:errors path="editAbility" class="control-label"/>
                        </div>
                    </spring:bind>
                </div>
                <div class="col-sm-12">
                    <div>
                        <label for="editor1">Description</label>
                    </div>

                    <div>
                        <form:textarea path="description" cols="100" id="editor1" rows="10"></form:textarea>
                    </div>
                </div>

                <form:hidden path="lastUpdateDate"/>
                <form:hidden path="createTime"/>
                <form:hidden path="id"/>
                <div class="col-sm-12">
                    <input type="submit" value="${buttonname}" class="margin-top-30 btn-u pull-right"/>
                </div>
            </form:form>
        </div>
    </div>
</div>
