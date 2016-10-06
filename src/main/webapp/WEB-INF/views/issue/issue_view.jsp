<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="head"></div>

<div class="breadcrumbs" id="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left"> ${issue.title}
                <sec:authentication var="principal" property="principal"/>
                <c:choose>
                    <c:when test="${principal != null}">
                        <sec:authorize access="@issueSecurityService.hasPermissionToEditIssue('${issue.id}')">
                            <sup>
                                <a href="<spring:url value='/issue/${issue.id}/edit/' />">
                                    <i class="fa fa-edit icon-table-u"></i>
                                </a>
                            </sup>
                        </sec:authorize>
                    </c:when>
                </c:choose>

            </h1>
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

<%--pop-up for notifications--%>
<p hidden id="message">${msg}</p>
<div class="modal fade" id="modalChanges" tabindex="-1" data-backdrop="false"
     role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-body text-center">
                ${msg}
            </div>
        </div>
    </div>
</div>

<%--content--%>
<div class="col-sm-10 col-sm-offset-1">
    <%--issue information--%>
    <div class="margin-top-30 col-sm-5">
        <div>
            <div class="row">
                <label class="col-sm-4">Title</label>
                <div class="col-sm-8"><p>${issue.title}</p></div>
            </div>
            <div class="row">
                <label class="col-sm-4">Type</label>
                <div class="col-sm-8"><p>${issue.type.toString()}</p></div>
            </div>
            <div class="row">
                <label class="col-sm-4">Status</label>
                <div class="col-sm-8"><p>${issue.status.toString()}</p></div>
            </div>
            <div class="row">
                <label class="col-sm-4">Priority</label>
                <div class="col-sm-8">${issue.priority.toString()}</div>
            </div>
            <div class="row">
                <label class="col-sm-4">Project</label>
                <div class="col-sm-8">
                    <p>
                        <a class="viewLink"
                           href="<spring:url value='/projects/project/${issue.project.id}'/>">
                            ${issue.project.title}
                        </a>
                    </p></div>
            </div>
            <div class="row">
                <label class="col-sm-4">Release</label>
                <div class="col-sm-8"><p>
                    <a class="viewLink"
                       href="<spring:url
                       value='/project/${issue.project.id}/release/${issue.projectRelease.id}'/>">
                        ${issue.projectRelease.version}
                    </a>
                </p>
                </div>
            </div>
            <div class="row">
                <label class="col-sm-4">Created By</label>
                <div class="col-sm-8"><p>
                    <c:choose>
                        <c:when test="${issue.createdBy != null}">
                            <sec:authorize access="isAuthenticated()">
                                <a class="viewLink"
                                href="<spring:url value='/user/${issue.createdBy.id}/view'/>">
                            </sec:authorize>
                            ${issue.createdBy.fullName}
                            </a>
                        </c:when>
                        <c:otherwise>
                            Anonymous User
                        </c:otherwise>

                    </c:choose>
                </p>
                </div>
            </div>
            <div class="row">
                <label class="col-sm-4">Assignee</label>
                <div class="col-sm-8">
                    <p>
                        <sec:authorize access="isAuthenticated()">
                        <a class="viewLink"
                           href="<spring:url value='/user/${issue.assignee.id}/view'/>">
                            </sec:authorize>
                            ${issue.assignee.fullName}</a>
                    </p>
                </div>
            </div>
            <div class="row">
                <label class="col-sm-4">Create Time</label>
                <div class="col-sm-8"><p>
                    <c:set var="createTime" value="${issue.createTime}"/>
                    <fmt:formatDate type="date" value="${createTime}"/>
                </p></div>
            </div>
            <div class="row">
                <label class="col-sm-4">Time to finish the issue</label>
                <div class="col-sm-8">
                    <c:set var="dueDate" value="${issue.dueDate}"/>
                    <fmt:formatDate type="date" value="${dueDate}"/>
                </div>
            </div>
            <div class="row">
                <label class="col-sm-4">Last updated time</label>
                <div class="col-sm-8">
                    <c:set var="lastUpdateDate" value="${issue.lastUpdateDate}"/>
                    <fmt:formatDate type="date" value="${lastUpdateDate}"/>
                </div>
            </div>
            <div class="row">
                <label class="col-sm-4">Estimate time</label>
                <div class="col-sm-8"><p>${issue.estimateTime} hrs</p></div>
            </div>
            <div class="row">
                <label class="col-sm-4">Labels</label>
                <div class="col-sm-8">
                    <c:forEach var="label" items="${issue.labels}">
                        <span class="label label-default ">
                            <c:out value="${label.title}"/>
                        </span>&nbsp;
                    </c:forEach>
                </div>
            </div>
            <div class="row">
                <c:set var="leftTime" value="${issue.estimateTime - totalSpentTimeByAllUsers}"/>
                <label class="col-sm-4">Time left</label>
                <div class="col-sm-8">${leftTime} hrs</div>
            </div>
            <div class="row">
                <label class="col-sm-4">Total spent time</label>
                <div class="col-sm-8">
                    <td>${totalSpentTimeByAllUsers} hrs
                </div>
            </div>
            <div class="row">
                <label class="col-sm-4">Description</label>
                <div class="col-sm-8">${issue.description}</div>
            </div>
        </div>
    </div>

    <%--TODO: prokhorenkovkv add frontend validation--%>
    <%--right panel--%>
    <div class="col-sm-7">
        <%--navigation tabs--%>
        <div id="tabs">
            <ul class="nav nav-tabs">
                <li role="presentation" class="active" id="tab-comments">
                    <a href="#tabs-comments" role="tab" data-toggle="tab">Comments</a>
                </li>
                <li role="presentation" id="tab-worklog"><a href="#tabs-worklog" role="tab" data-toggle="tab">Work
                    log</a></li>
                <li role="presentation" id="tab-history"><a href="#tabs-history" role="tab"
                                                            data-toggle="tab">History</a></li>
            </ul>
        </div>

        <%--tabs content--%>
        <div class="tab-content">
            <%--comments--%>
            <div role="tabpanel" class="tab-pane fade in active" id="tabs-comments">
                <div id="scrollButtons" class="hidden">
                    <a href=""><i class="fa fa-arrow-circle-up fa-2x icon-table-u" aria-hidden="true"></i></a><br>
                    <a href=""><i class="fa fa-arrow-circle-down fa-2x icon-table-u" aria-hidden="true"></i></a>
                </div>
                <%--comments list--%>
                <div class="margin-top-30">
                    <c:if test="${issueCommentsList.isEmpty()}">There is no comments yet. Be first.</c:if>
                    <c:forEach var="issueCommentsListIterator" items="${issueCommentsList}">
                        <div class="margin-top-20">
                            <c:choose>
                                <c:when test="${issueCommentsListIterator.user != null}">
                                    <strong>
                                        <a href="<spring:url value='/user/${issueCommentsListIterator.user.id}/view'/>">
                                                ${issueCommentsListIterator.user.firstName}
                                                ${issueCommentsListIterator.user.lastName}
                                        </a>
                                    </strong>
                                </c:when>
                                <c:otherwise>
                                    <strong>${issueCommentsListIterator.anonymousName}</strong>
                                </c:otherwise>
                            </c:choose>
                            &nbsp;commented
                            <c:set var="timeStampDate" value="${issueCommentsListIterator.timeStamp}"/>
                            <fmt:formatDate type="date" value="${timeStampDate}"/> <%--pattern="dd.MM.yyyy"--%>
                            at
                            <c:set var="timeStampTime" value="${issueCommentsListIterator.timeStamp}"/>
                            <fmt:formatDate type="time" value="${timeStampTime}" pattern="HH:mm:ss"/>
                            &nbsp;
                            <c:if test="${issueCommentsListIterator.isEdited == true}">
                                <span class="text-danger">[edited]</span>
                            </c:if>
                            <div class="pull-right">
                                <c:if test="${(currentUser == issueCommentsListIterator.user and currentUser.role != null) or
                                                currentUser.role == 'ROLE_ADMIN' or
                                                permissionToUseWorkLogForm == 'PROJECT_MANAGER'}">
                                    <a href="<spring:url value='/issue/${issue.id}/comment/${issueCommentsListIterator.id}/edit'/>">
                                        <i class="fa fa-edit icon-table-u"></i></a>
                                    &nbsp;
                                    <a href="" data-toggle="modal"
                                       data-target="#removeCommentButton-${issueCommentsListIterator.id}">
                                        <i class="fa fa-remove icon-table-u"></i>
                                    </a>
                                    <div class="modal fade" id="removeCommentButton-${issueCommentsListIterator.id}"
                                         tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button class="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                    <h4 class="modal-title pull-left">Remove comment</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <p class="text-center">Please confirm removal</p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button class="btn btn-default" data-dismiss="modal">
                                                        Cancel
                                                    </button>
                                                    <a href="<spring:url value='/issue/${issue.id}/comment/${issueCommentsListIterator.id}/remove' />"
                                                       class="btn btn-default">Delete</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="issue-comment prevent-inline-displaying">
                            <div>${issueCommentsListIterator.text}</div>
                        </div>
                    </c:forEach>
                </div>

                <%--comment form--%>
                <div class="margin-top-30" id="issueCommentForm">
                    <sec:authorize
                            access="@issueCommentSecurityService.hasPermissionToCreateIssueComment('${issue.id}')">
                        <form:form action="${commentsAction}" modelAttribute="issueComment"
                                   method="POST">
                            <c:choose>
                                <c:when test="${currentUser == null}">
                                    <spring:bind path="anonymousName">
                                        <div class="margin-top-10" id="anonymousNameDiv">
                                            <label for="anonymousName">Introduce yourself to comment</label>
                                            <form:input path="anonymousName" type="text" class="form-control"
                                                        id="anonymousName"
                                                        placeholder="Type your name here (8-32 characters)"
                                                        value="${issueComment.anonymousName}"/>
                                            <div class="text-danger hidden">&nbsp;8-32 characters required</div>
                                        </div>
                                    </spring:bind>
                                </c:when>
                                <c:otherwise>
                                    <form:hidden path="anonymousName"/>
                                </c:otherwise>
                            </c:choose>
                            <spring:bind path="text">
                                <div class="margin-bottom-30" id="textDiv">
                                    <form:textarea path="text" cols="100" id="text" rows="5"
                                                   value="${issueComment.text}"
                                                   class="ckeditor"></form:textarea>
                                    <form:errors path="text" class="control-label"/>
				    <div class="text-danger hidden">&nbsp;At least one character required</div>
                                </div>
                            </spring:bind>
                            <form:hidden path="user"/>
                            <form:hidden path="issue"/>
                            <form:hidden path="id"/>
                            <form:hidden path="isEdited"/>
                            <div class="col-sm-12" id="issueCommentButtons">
                                <span class="pull-right">
                                    <button id="issueCommentCancelButton" class="hidden btn btn-default">Cancel</button>
                                    <input type="submit" value="Submit" class="margin-top-30 btn btn-default"
                                           id="issueCommentSubmitButton"/>
                                </span>
                            </div>
                        </form:form>
                    </sec:authorize>
                </div>
            </div>

            <%--worklog features--%>
            <div role="tabpanel" class="tab-pane fade" id="tabs-worklog">
                <div class="margin-top-10 text-center">
                    <sec:authorize access="hasRole('${permissionToUseWorkLogForm}')">
                        <button class="workLogToggler btn btn-default row text-center">Add entry</button>
                    </sec:authorize>
                </div>
                <div class="margin-top-10" id="workLogTable">
                    <div>
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th class="text-center">User</th>
                                <th class="text-center">Work period</th>
                                <th class="text-center">Logged</th>
                                <th><%--Edit--%></th>
                                <th><%--Remove--%></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="workLogIterator" items="${workLogsOfCurrentIssueByAllUsers.content}">
                                <tr class="text-center">
                                    <td>
                                        <a href="<spring:url value='/user/${workLogIterator.user.id}/view'/>">
                                                ${workLogIterator.user.firstName} ${workLogIterator.user.lastName}</a>
                                    </td>
                                    <td>
                                        <c:set var="startDateFormatted" value="${workLogIterator.startDate}"/>
                                        <fmt:formatDate type="date" value="${startDateFormatted}"/><%-- pattern="dd/MM/yyyy"--%>
                                        &ndash;
                                        <c:set var="endDateFormatted" value="${workLogIterator.endDate}"/>
                                        <fmt:formatDate type="date" value="${endDateFormatted}"/><%-- pattern="dd/MM/yyyy"--%>
                                    </td>
                                    <td>${workLogIterator.amountOfTime} hrs</td>
                                    <c:choose>
                                        <c:when test="${(workLogIterator.user == currentUser) or
                                                (currentUser.role == 'ROLE_ADMIN') or
                                                permissionToUseWorkLogForm == 'PROJECT_MANAGER'}">
                                            <td>
                                                <a href="<spring:url value='/issue/${workLogIterator.issue.id}/worklog/${workLogIterator.id}/edit' />"><i
                                                        class="fa fa-edit icon-table-u"></i></a>
                                            </td>
                                            <td>
                                                <a href="" data-toggle="modal"
                                                   data-target="#removeWorkLogButton-${workLogIterator.id}"><i
                                                        class="fa fa-remove icon-table-u"></i></a>
                                                <div class="modal fade"
                                                     id="removeWorkLogButton-${workLogIterator.id}"
                                                     tabindex="-1"
                                                     role="dialog" aria-labelledby="myModalLabel">
                                                    <div class="modal-dialog" role="document">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button class="close" data-dismiss="modal"
                                                                        aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                                <h4 class="modal-title pull-left">Remove work
                                                                    log</h4>
                                                            </div>
                                                            <div class="modal-body">
                                                                <p class="text-center">Please confirm removal</p>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button class="btn btn-default"
                                                                        data-dismiss="modal">
                                                                    Cancel
                                                                </button>
                                                                <a href="<spring:url value='/issue/${workLogIterator.issue.id}/worklog/${workLogIterator.id}/remove' />"
                                                                   class="btn btn-default">Delete</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <i class="fa fa-lock icon-table-u"></i>
                                            </td>
                                            <td></td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                    </div>

                    <%--worklog pagination--%>
                    <div id="workLogPagination">
                        <c:if test="${workLogsOfCurrentIssueByAllUsers.getTotalPages()> 1}">
                            <div class="col-sm-offset-4 col-sm-4">
                                <nav aria-label="Page navigation" id="pagerID">
                                    <div class="text-center">
                                        <c:choose>
                                            <c:when test="${stage == 'new'}">
                                                <c:set var="paginationLink" value="${issue.id}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="paginationLink" value="edit"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <ul class="pagination">
                                            <li>
                                                <a href="<spring:url value='${paginationLink}?worklog_page=0'/>"
                                                   aria-label="Start">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                            <c:forEach var="page" begin="0"
                                                       end="${workLogsOfCurrentIssueByAllUsers.getTotalPages() - 1}">
                                                <li>
                                                    <a href="<spring:url value='${paginationLink}?worklog_page=${page}'/>">${page + 1}</a>
                                                </li>
                                            </c:forEach>
                                            <li>
                                                <a href="<spring:url value='${paginationLink}?worklog_page=${workLogsOfCurrentIssueByAllUsers.getTotalPages() - 1}'/>"
                                                   aria-label="End">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </nav>
                            </div>
                        </c:if>
                    </div>
                </div>

                <%--worklog form--%>
                <div id="workLogForm" class="hidden col-sm-offset-1">
                    <sec:authorize access="hasRole('${permissionToUseWorkLogForm}')">
                        <form:form action="${workLogAction}" modelAttribute="workLog" method="POST">
                            <div class="col-sm-8">
                                <spring:bind path="startDate">
                                    <div class="margin-top-10" id="startDateDiv">
                                        <label for="startDate">Start date</label>
                                        <form:input path="startDate" type="text" class="form-control datepicker"
                                                    id="startDate"
                                                    value="${startDate}"/>
                                        <div class="text-danger hidden">&nbsp;Required</div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="endDate">
                                    <div class="margin-top-20" id="endDateDiv">
                                        <label for="endDate">End date</label>
                                        <form:input path="endDate" type="text" class="form-control datepicker"
                                                    id="endDate"
                                                    value="${endDate}"/>
                                        <div class="text-danger hidden">&nbsp;Required</div>
                                    </div>
                                </spring:bind>

                                <spring:bind path="amountOfTime">
                                    <div class="margin-top-20" id="amountOfTimeDiv">
                                        <label for="amountOfTime">Amount (1-8 hrs per day range)</label>
                                            <%--may specify user or project workday duration instead--%>
                                        <form:input path="amountOfTime" type="text" class="form-control"
                                                    id="amountOfTime"
                                                    placeholder="Type your amount here"
                                                    value="${worklog.amountOfTime}"/>
                                        <div class="text-danger hidden">&nbsp;Required</div>
                                    </div>
                                </spring:bind>
                            </div>
                                                        
			    <form:hidden path="user"/>
                            <form:hidden path="issue"/>
                            <form:hidden path="id"/>
                            <div class="col-sm-8" id="workLogButtons">
                                <span class="pull-right">
                                    <button id="workLogCancelButton" class="hidden btn btn-default">Cancel</button>
                                    <input type="submit" value="Submit" class="margin-top-30 btn btn-default"
                                           id="workLogSubmitButton"/>
                                </span>
                            </div>
                        </form:form>
                    </sec:authorize>
                </div>
            </div>

            <%--issue history--%>
            <div role="tabpanel" class="tab-pane fade issue-history prevent-inline-displaying" id="tabs-history">
                <ul>
                    <c:forEach var="history" items="${allHistory.content}">
                        <c:choose>
                            <c:when test="${(history.action eq 'CREATE_ISSUE') or (history.action eq 'CHANGE_ISSUE_ASSIGNEE')}">
                                <li>
                                    <strong>
                                        <c:choose>
                                            <c:when test="${history.changedByUser.id eq null}">
                                                <c:choose>
                                                    <c:when test="${empty history.changedByUser.lastName}">
                                                        <span class="removed-user">${history.anonymName} [anonym]</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="removed-user">${history.changedByUser.firstName} ${history.changedByUser.lastName} [removed]</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="<spring:url value='/user/${history.changedByUser.id}/view' />">${history.changedByUser.firstName} ${history.changedByUser.lastName}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </strong>
                                    <c:choose>
                                        <c:when test="${history.action eq 'CREATE_ISSUE'}">
                                            created issue and assigned it to
                                        </c:when>
                                        <c:when test="${history.action eq 'CHANGE_ISSUE_ASSIGNEE'}">
                                            reassigned issue to
                                        </c:when>
                                    </c:choose>
                                    <strong>
                                        <c:choose>
                                            <c:when test="${history.assignedToUser.id eq null}">
                                                <span class="removed-user">${history.assignedToUser.firstName} ${history.assignedToUser.lastName} [removed]</span>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="<spring:url value='/user/${history.assignedToUser.id}/view' />">${history.assignedToUser.firstName} ${history.assignedToUser.lastName}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </strong></br>
                                    <span class="createTime">at ${history.createTime}</span>
                                </li>
                            </c:when>
                            <c:when test="${history.action eq 'ADD_ISSUE_COMMENT' or history.action eq 'EDIT_ISSUE_COMMENT'}">
                                <li>
                                    <strong>
                                        <c:choose>
                                            <c:when test="${history.changedByUser.id eq null}">
                                                <c:choose>
                                                    <c:when test="${history.anonymName eq null}">
                                                        <span class="removed-user">${history.changedByUser.firstName} ${history.changedByUser.lastName} [removed]</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="removed-user">${history.anonymName} [anonym]</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="<spring:url value='/user/${history.changedByUser.id}/view' />">${history.changedByUser.firstName} ${history.changedByUser.lastName}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </strong>
                                    <c:choose>
                                        <c:when test="${history.action eq 'ADD_ISSUE_COMMENT'}">
                                            add comment to issue
                                        </c:when>
                                        <c:when test="${history.action eq 'EDIT_ISSUE_COMMENT'}">
                                            edited comment
                                        </c:when>
                                    </c:choose>
                                    <div class="history-issue-comment">
                                            ${history.issueComment}
                                    </div>
                                    <span class="createTime">at ${history.createTime}</span>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${(history.action ne 'ADD_ISSUE_COMMENT') and (history.action ne 'EDIT_ISSUE_COMMENT')}">
                                    <li>
                                        <strong>
                                            <c:choose>
                                                <c:when test="${history.changedByUser.id == null}">
                                                    <span class="removed-user">${history.changedByUser.firstName} ${history.changedByUser.lastName} [removed]</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="<spring:url value='/user/${history.changedByUser.id}/view' />">${history.changedByUser.firstName} ${history.changedByUser.lastName}</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </strong>
                                        <c:set var="inputField" value="${fn:split(history.action, '_')[2]}"/>
                                        <c:set var="fieldLowCase" value="${fn:toLowerCase(inputField)}"/>
                                        <c:set var="fieldValue" value="${history[fieldLowCase]}"/>
                                        changed ${fieldLowCase} of issue
                                        to <strong>${fieldValue}</strong></br>
                                        <span class="createTime">at ${history.createTime}</span>
                                    </li>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
                <c:if test="${allHistory.getTotalPages() gt 1}">
                    <nav aria-label="Page navigation" id="pagerID">
                        <div class="text-center">
                            <ul class="pagination">
                                <li>
                                    <a href="<spring:url value='/issue/${issue.id}?page=0'/>" aria-label="Start">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <c:forEach var="page" begin="0" end="${allHistory.getTotalPages() - 1}">
                                    <li>
                                        <a href="<spring:url value='/issue/${issue.id}?page=${page}'/>">${page + 1}</a>
                                    </li>
                                </c:forEach>
                                <li>
                                    <a href="<spring:url value='/issue/${issue.id}?page=${allHistory.getTotalPages() - 1}'/>"
                                       aria-label="End"><span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>
</div>