<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left"> ${issue.title} </h1>
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

<c:if test="${not empty msg}">
    <div class="row" id="redirectMessage">
        <div class="col-sm-4 col-sm-offset-8">
            <div class="alert alert-${alert} alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <strong>${msg}</strong>
            </div>
        </div>
    </div>
</c:if>

<div class="row">
    <div class="col-sm-10 col-sm-offset-1">
        <div class="margin-top-30 col-sm-5">
            <div>
                <div class="row">
                    <label class="col-sm-4">ID</label>
                    <div class="col-sm-8"><p>${issue.id}</p></div>
                </div>
                <div class="row">
                    <label class="col-sm-4">Title</label>
                    <div class="col-sm-8"><p>${issue.title}</p></div>
                </div>
                <div class="row">
                    <label class="col-sm-4">Type</label>
                    <div class="col-sm-8"><p>${issue.type}</p></div>
                </div>
                <div class="row">
                    <label class="col-sm-4">Status</label>
                    <div class="col-sm-8"><p>${issue.status}</p></div>
                </div>
                <div class="row">
                    <label class="col-sm-4">Priority</label>
                    <div class="col-sm-8">${issue.priority}</div>
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
                    <div class="col-sm-8"><p>
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
                    <div class="col-sm-8"><p>${issue.createTime}</p></div>
                </div>

                <div class="row">
                    <label class="col-sm-4">Time to finish the issue</label>
                    <div class="col-sm-8">${issue.dueDate}</div>
                </div>

                <div class="row">
                    <label class="col-sm-4">Last updated time</label>
                    <div class="col-sm-8">${issue.lastUpdateDate}</div>
                </div>
                <div class="row">
                    <label class="col-sm-4">Estimate time</label>
                    <div class="col-sm-8"><p>${issue.estimateTime}</p></div>
                </div>
                <div class="row">
                    <label class="col-sm-4">Ability to edit</label>
                    <div class="col-sm-8">${issue.editAbility}</div>
                </div>
                <div class="row">
                    <label class="col-sm-4">Labels</label>
                    <div class="col-sm-8">
                        <c:forEach var="label" items="${issue.labels}">
                            <c:out value="${label.title}"></c:out>
                        </c:forEach>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-4">Description</label>
                    <div class="col-sm-8">${issue.description}</div>
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
            </div>
        </div>

        <%--TODO: prokhorenkovkv add frontend validation--%>
        <div class="col-sm-7">
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

            <div class="tab-content">
                <%--comments--%>
                <div role="tabpanel" class="tab-pane fade in active" id="tabs-comments">
                    <%--comments list--%>
                    <div class="margin-top-30">
                        <%--<c:if test="${issueCommentsList.isEmpty()}">There is no comments yet. Be first.</c:if>
                        <c:forEach var="issueCommentsListIterator" items="${issueCommentsList}">
                            <a href="<spring:url value='/user/${issueCommentsListIterator.user.id}/view'/>">
                                    ${issueCommentsListIterator.user.firstName} ${issueCommentsListIterator.user.lastName}</a>
                            &nbsp;commented at <span class="trimMs"><c:out
                                value="${issueCommentsListIterator.timeStamp}"/></span>&nbsp;
                            <c:if test="${issueCommentsListIterator.isEdited == true}">
                                <span class="text-danger">[edited]</span>
                            </c:if>
                            <div class="pull-right">
                                <a href="<spring:url value='/issue/${issue.id}/comment/${issueCommentsListIterator.id}/edit'/>">
                                    <i class="fa fa-edit icon-table-u"></i></a>
                                &nbsp;
                                <a href="" data-toggle="modal"
                                   data-target="#removeCommentButton-${issueCommentsListIterator.id}"><i
                                        class="fa fa-remove icon-table-u"></i></a>
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
                                                   class="btn btn-u">Delete</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            ${issueCommentsListIterator.text}
                        </c:forEach>--%>
                    </div>

                    <%--comment form--%>
                    <%--<div class="margin-top-30">
                        <form:form action="${commentsAction}" modelAttribute="newIssueComment"
                                   method="POST">
                            <spring:bind path="text">
                                <div class="form-group ${status.error ? 'has-error' : ''} margin-bottom-30">
                                    <form:textarea path="text" cols="100" id="text" rows="5"
                                                   value="${newIssueComment.text}"
                                                   class="ckeditor"></form:textarea>
                                    <form:errors path="text" class="control-label"/>
                                </div>
                            </spring:bind>
                            <form:hidden path="user"/>
                            <form:hidden path="issue"/>
                            <form:hidden path="id"/>
                            <form:hidden path="isEdited"/>
                            <div class="col-sm-10 col-sm-offset-1">
                                <input type="submit" value="Comment" class="margin-top-30 btn-u pull-right"/>
                            </div>
                        </form:form>
                    </div>--%>
                </div>

                <%--worklog features--%>
                <div role="tabpanel" class="tab-pane fade" id="tabs-worklog">
                    <div class="margin-top-10 text-center">
                        <sec:authorize access="hasRole('${permissionToUseWorkLogForm}')">
                            <button class="workLogToggler btn-u row text-center">Add entry</button>
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
                                        <td>${workLogIterator.startDate} - ${workLogIterator.endDate}</td>
                                        <td>${workLogIterator.amountOfTime} hrs</td>
                                        <c:choose>
                                            <c:when test="${(workLogIterator.user == currentUser) or (currentUser.role == 'ROLE_ADMIN') }">
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
                                                                       class="btn btn-u">Delete</a>
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
                                        <div class="margin-top-10">
                                            <label for="startDate">Start date</label>
                                            <form:input path="startDate" type="text" class="form-control" id="startDate"
                                                        value="${startDate}" placeholder="yyyy-MM-dd"/>
                                            <form:errors path="startDate" class="control-label"/>
                                        </div>
                                    </spring:bind>

                                    <spring:bind path="endDate">
                                        <div class="margin-top-20">
                                            <label for="endDate">End date</label>
                                            <form:input path="endDate" type="text" class="form-control" id="endDate"
                                                        value="${endDate}" placeholder="yyyy-MM-dd"/>
                                            <form:errors path="endDate" class="control-label"/>
                                        </div>
                                    </spring:bind>

                                    <spring:bind path="amountOfTime">
                                        <div class="margin-top-20">
                                            <label for="amountOfTime">Amount (1-8 hrs per day range)</label>
                                                <%--may specify user or project workday duration instead--%>
                                            <form:input path="amountOfTime" type="text" class="form-control"
                                                        id="amountOfTime"
                                                        placeholder="Type your amount here"
                                                        value="${worklog.amountOfTime}"/>
                                            <form:errors path="amountOfTime" class="control-label"/>
                                        </div>
                                    </spring:bind>
                                </div>
                                <%--TODO: prokhorenkovkv fix same named paths--%>
                                <form:hidden path="user"/>
                                <form:hidden path="issue"/>
                                <form:hidden path="id"/>
                                <div class="col-sm-10 col-sm-offset-1" id="workLogButtons">
                                <span>
                                    <input type="submit" value="Submit" class="margin-top-30 btn-u col-sm-offset-5"
                                           id="workLogSubmitButton"/>
                                    <button id="workLogCancelButton" class="hidden">Cancel</button>
                                </span>
                                </div>
                            </form:form>
                        </sec:authorize>
                    </div>
                </div>

                <%--issue history--%>
                <div role="tabpanel" class="tab-pane fade issue-history" id="tabs-history">
                    <ul>
                        <c:forEach var="history" items="${allHistory.content}">
                            <c:choose>
                                <c:when test="${history.action == 'CREATE_ISSUE'}">
                                    <li>
                                        <strong>
                                            <c:choose>
                                                <c:when test="${history.changedByUser.id == null}">
                                                    <span class="removed-user">${history.assignedToUser.firstName} ${history.assignedToUser.lastName} [Removed user]</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="<spring:url value='/user/${history.changedByUser.id}/view' />">${history.changedByUser.firstName} ${history.changedByUser.lastName}</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </strong>
                                        created issue
                                        and assigned it to
                                        <strong>
                                            <c:choose>
                                                <c:when test="${history.assignedToUser.id == null}">
                                                    <span class="removed-user">${history.assignedToUser.firstName} ${history.assignedToUser.lastName} [Removed user]</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="<spring:url value='/user/${history.assignedToUser.id}/view' />">${history.assignedToUser.firstName} ${history.assignedToUser.lastName}</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </strong></br>
                                        <span class="createTime">at ${history.createTime}</span>
                                    </li>
                                </c:when>
                                <c:when test="${history.action == 'CHANGE_ISSUE_ASSIGNEE'}">
                                    <li>
                                        <strong>
                                            <c:choose>
                                                <c:when test="${history.changedByUser.id == null}">
                                                    <span class="removed-user">${history.assignedToUser.firstName} ${history.assignedToUser.lastName} [Removed user]</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="<spring:url value='/user/${history.changedByUser.id}/view' />">${history.changedByUser.firstName} ${history.changedByUser.lastName}</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </strong>
                                        reassigned issue
                                        to
                                        <strong>
                                            <c:choose>
                                                <c:when test="${history.assignedToUser.id == null}">
                                                    <span class="removed-user">${history.assignedToUser.firstName} ${history.assignedToUser.lastName} [Removed user]</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="<spring:url value='/user/${history.assignedToUser.id}/view' />">${history.assignedToUser.firstName} ${history.assignedToUser.lastName}</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </strong></br>
                                        <span class="createTime">at ${history.createTime}</span>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <strong>
                                            <c:choose>
                                                <c:when test="${history.changedByUser.id == null}">
                                                    <span class="removed-user">${history.assignedToUser.firstName} ${history.assignedToUser.lastName} [Removed user]</span>
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
<%--TODO: prokhorenkovkv add datepicker--%>