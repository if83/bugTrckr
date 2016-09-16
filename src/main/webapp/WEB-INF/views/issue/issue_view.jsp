<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>


<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left"> Issue details </h1>
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


<div class="row col-sm-8 col-sm-offset-2">
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
                       value='/projects/project/
                       ${issue.projectRelease.project.id}/release/${issue.projectRelease.id}'/>">
                        ${issue.projectRelease.version}
                    </a>
                </p>
                </div>
            </div>
            <div class="row">
                <label class="col-sm-4">Assignee</label>
                <div class="col-sm-8"><p>
                    <a class="viewLink"
                       href="<spring:url
                       value='/user/${issue.assignee.id}/view'/>">
                        ${issue.assignee.firstName} ${issue.assignee.lastName}</a>
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
                <li role="presentation" class="active">
                    <a href="#tabs-comments" role="tab" data-toggle="tab">Comments</a>
                </li>
                <li role="presentation"><a href="#tabs-worklog" role="tab" data-toggle="tab">Work log</a></li>
                <li role="presentation"><a href="#tabs-history" role="tab" data-toggle="tab">History</a></li>
            </ul>
        </div>

        <div class="tab-content">
            <%--worklog table--%>
            <div role="tabpanel" class="tab-pane fade" id="tabs-worklog">
                <div class="margin-top-30">
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
                                    <c:when test="${currentUser == workLogIterator.user}">
                                        <td>
                                            <a href="<spring:url value='/issue/${workLogIterator.issue.id}/worklog/${workLogIterator.id}/edit' />"><i
                                                    class="fa fa-edit icon-table-u"></i></a>
                                        </td>
                                        <td>
                                            <a href="<spring:url value='/issue/${workLogIterator.issue.id}/worklog/${workLogIterator.id}/remove' />"><i
                                                    class="fa fa-remove icon-table-u"></i></a>
                                        </td>
                                        <c:set var="permissionToCreate" value="${currentUser.role}"/>
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
                <c:if test="${workLogsOfCurrentIssueByAllUsers.getTotalPages()> 1}">
                    <div class="row col-sm-offset-4 col-sm-4">
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
                                        <a href="<spring:url value='${paginationLink}?page=0'/>" aria-label="Start">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <c:forEach var="page" begin="0"
                                               end="${workLogsOfCurrentIssueByAllUsers.getTotalPages() - 1}">
                                        <li>
                                            <a href="<spring:url value='${paginationLink}?page=${page}'/>">${page + 1}</a>
                                        </li>
                                    </c:forEach>
                                    <li>
                                        <a href="<spring:url value='${paginationLink}?page=${workLogsOfCurrentIssueByAllUsers.getTotalPages() - 1}'/>"
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

            <%--comments--%>
            <div role="tabpanel" class="tab-pane fade in active" id="tabs-comments">
                <%--comments list--%>
                <div>
                    <c:forEach var="issueCommentsListIterator" items="${issueCommentsList}">
                        <div class="margin-top-30">
                            <a href="<spring:url value='/user/${issueCommentsListIterator.user.id}/view'/>">
                                    ${issueCommentsListIterator.user.firstName} ${issueCommentsListIterator.user.lastName}</a>
                            &nbsp;commented at <c:out value="${issueCommentsListIterator.timeStamp}"/>
                            <div class="pull-right">
                                <a href="<spring:url value='#'/>">
                                    <i class="fa fa-edit icon-table-u"></i></a>
                                &nbsp;
                                <a href="<spring:url value='/issue/${issue.id}/comment/${issueCommentsListIterator.id}/remove'/>">
                                    <i class="fa fa-remove icon-table-u"></i></a>
                            </div>
                                ${issueCommentsListIterator.text}
                        </div>
                    </c:forEach>
                </div>
                <%--comment form--%>
                <div class="margin-top-30">
                    <form:form action="${newIssueComment.issue.id}/comment/save" modelAttribute="newIssueComment"
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
                        <div class="col-sm-10 col-sm-offset-1">
                            <input type="submit" value="Comment" class="margin-top-30 btn-u pull-right"/>
                        </div>
                    </form:form>
                </div>
            </div>

            <%--history--%>
            <div role="tabpanel" class="tab-pane fade" id="tabs-history">
                history here
            </div>
        </div>
    </div>
</div>


<%--
&lt;%&ndash;worklog form&ndash;%&gt;
<div class="col-sm-6">
    &lt;%&ndash;TODO: prokhorenkovkv add datepicker&ndash;%&gt;
    <div class="margin-top-30">
        <sec:authorize access="hasRole('${permissionToUseWorkLogForm}')">
            <form:form action="${action}" modelAttribute="workLog" method="POST">
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
                                &lt;%&ndash;may specify user or project workday duration instead&ndash;%&gt;
                            <form:input path="amountOfTime" type="text" class="form-control" id="amountOfTime"
                                        placeholder="Type your amount here" value="${worklog.amountOfTime}"/>
                            <form:errors path="amountOfTime" class="control-label"/>
                        </div>
                    </spring:bind>
                </div>
                &lt;%&ndash;TODO: prokhorenkovkv fix same named paths&ndash;%&gt;
                <form:hidden path="user"/>
                <form:hidden path="issue"/>
                <form:hidden path="id"/>
                <div class="col-sm-10 col-sm-offset-1">
                    <input type="submit" value="Submit" class="margin-top-30 btn-u pull-right"/>
                </div>
            </form:form>
        </sec:authorize>
        <c:choose>
            <c:when test="${permissionToUseWorkLogForm == null}">
                <h4 class="margin-top-30 text-center ">You have no permissions to add worklog</h4>
            </c:when>
        </c:choose>
    </div>
</div>
</div>--%>
