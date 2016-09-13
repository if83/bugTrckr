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
            <label class="col-sm-3">Project</label>
            <div class="col-sm-9">
                <p>
                    <a class="viewLink"
                       href="<spring:url value='/projects/project/${issue.project.id}'/>">
                        ${issue.project.title}
                    </a>
                </p></div>
        </div>

        <div class="row">
            <label class="col-sm-3">Release</label>
            <div class="col-sm-9"><p>
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
            <label class="col-sm-3">Assignee</label>
            <div class="col-sm-9"><p>
                <a class="viewLink"
                   href="<spring:url
                       value='/user/${issue.assignee.id}/view'/>">
                    ${issue.assignee.firstName} ${issue.assignee.lastName}</a>
            </p>
            </div>
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
            <label class="col-sm-3">Labels</label>
            <div class="col-sm-6">
                <c:forEach var="label" items="${issue.labels}">
                    <c:out value="${label.title}"></c:out>
                </c:forEach>
            </div>
        </div>

        <div class="row">
            <label class="col-sm-3">Description</label>
            <div class="col-sm-9">${issue.description}</div>
        </div>
    </div>
</div>

<div class="col-sm-3">
    <%--TODO: fix layout--%>
</div>

<div class="col-sm-6 margin-top-30">
    <%--comments list--%>
    <%--TODO: comments pagination--%>
    <div>
        <c:forEach var="issueCommentsListIterator" items="${issueCommentsList}">
            <table class="table">
                <thead>
                <tr>
                    <th class="text-center">
                        <c:out value="${issueCommentsListIterator.user.firstName} ${issueCommentsListIterator.user.lastName}"/>
                        &nbsp;commented at <c:out value="${issueCommentsListIterator.timeStamp}"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                    <%--TODO: fix bold font--%>
                <tr>
                    <th><c:out value="${issueCommentsListIterator.text}"/></th>
                </tr>
                <tr>
                    <th class="pull-right">
                        <a href="<spring:url value='#'/>">
                            <i class="fa fa-edit icon-table-u"></i></a>
                        &nbsp;
                        <a href="<spring:url value='/issue/${issue.id}/comment/${issueCommentsListIterator.id}/remove'/>">
                            <i class="fa fa-remove icon-table-u"></i></a>
                    </th>
                </tr>
                </tbody>
            </table>
            <br>
        </c:forEach>
    </div>

    <%--comment form--%>
    <div>
        <form:form action="${newIssueComment.issue.id}/comment/save" modelAttribute="newIssueComment" method="POST">
            <spring:bind path="text">
                <div class="form-group ${status.error ? 'has-error' : ''} margin-bottom-30">
                    <form:textarea path="text" cols="103" id="text" rows="5"
                                   placeholder="Comment here..." value="${newIssueComment.text}"></form:textarea>
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

<div class="col-sm-3">

</div>