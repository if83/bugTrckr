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
                <li><a href="<spring:url value='/users' />">Users</a></li>
                <li class="active"> User Detail</li>
            </ol>
        </div>
    </div>
</div>

<div class="margin-top-30 row">
    <div class="col-sm-11 col-sm-offset-1 col-md-6 col-md-offset-1">

        <div class="row">
            <label class="col-sm-3">ID</label>
            <div class="col-sm-9"><p>${user.id}</p></div>
        </div>


        <div class="row">
            <label class="col-sm-3">Email</label>
            <div class="col-sm-9"><p>${user.email}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-3">First name</label>
            <div class="col-sm-9"><p>${user.firstName}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-3">Last name</label>
            <div class="col-sm-9"><p>${user.lastName}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-3">Role</label>
            <div class="col-sm-9"><p>${user.role}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-3">Description</label>
            <div class="col-sm-9">${user.description}</div>
        </div>

        <c:if test="${not empty user.project}">
            <div class="row">
                <label class="col-sm-3">Active Project</label>
                <div class="col-sm-9">${user.project.title}</div>
            </div>
        </c:if>

        <div class="margin-top-30 row">
            <div class="col-sm-4">
                <spring:url value="/user/${user.id}/edit" var="useredit"/>
                <a class="btn btn-primary btn-u" href="${useredit}" role="button">Edit profile</a>
            </div>
            <div class="col-sm-8"></div>
        </div>

    </div>

    <div class="col-sm-11 col-sm-offset-1 col-md-3">
        <figure>
            <img src="data:image/jpg;base64,<c:out value='${user.encodedImage}'/>" class="img-thumbnail"
                 alt="${user.lastName}"/>
        </figure>
    </div>
</div>


<div class="margin-bottom-60">
    <div class="row"><p>&nbsp;</p></div>
</div>

<%--history table--%>
<div class="user-history col-sm-4 col-sm-offset-1">
    <H3>User`s activity</H3>
    <ul>
        <c:forEach var="history" items="${allHistory}">
            <c:choose>
                <c:when test="${history.action == 'CHANGE_STATUS'}">
                    <li value='${history.id}'>
                        <strong><a
                                href="<spring:url value='/user/${history.changedByUser.id}/view' />">${history.changedByUser.firstName}</a></strong>
                        changed status of <strong>${history.issue.title}</strong> to
                        <strong>${history.issueStatus}</strong>
                    </li>
                </c:when>
                <c:when test="${history.action == 'CHANGE_ASSIGNEE'}">
                    <li value='${history.id}'>
                        <strong><a
                                href="<spring:url value='/user/${history.changedByUser.id}/view' />">${history.changedByUser.firstName}</a></strong>
                        assigned <strong>${history.issue.title}</strong> to <strong><a
                            href="<spring:url value='/user/${history.assignedToUser.id}/view' />">${history.assignedToUser.firstName}</a></strong>
                    </li>
                </c:when>
            </c:choose>
        </c:forEach>
    </ul>
</div>

<%--worklog table--%>
<div class="col-sm-7">
    <h3 class="text-center">Worklog</h3>
    <table class="table table-hover">
        <thead>
        <tr>
            <th class="text-center">Project</th>
            <th class="text-center">Issue</th>
            <th class="text-center">Work date</th>
            <th class="text-center">Logged</th>
            <th><%--Edit--%></th>
            <th><%--Remove--%></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="workLogIterator" items="${workLogList}">
            <tr class="text-center">
                <td>
                    <a href="../../projects/project/${workLogIterator.issue.projectRelease.project.id}">
                    <c:out value="${workLogIterator.issue.projectRelease.project.title}"/></a>
                </td>
                <td>
                    <a href="../../issue/${workLogIterator.issue.id}">
                    <c:out value="${workLogIterator.issue.title}"/></a>
                </td>
                <td><c:out value="${workLogIterator.startTime}"/></td>
                <td><c:out value="${workLogIterator.amountOfTime}"/> hrs</td>
                <td>
                    <a href="<spring:url value='/issue/${workLogIterator.issue.id}/worklog/${workLogIterator.id}/edit' />"><i
                            class="fa fa-edit icon-table-u"></i></a>
                </td>
                <td>
                    <a href="<spring:url value='/issue/${workLogIterator.issue.id}/worklog/${workLogIterator.id}/remove' />"><i
                            class="fa fa-remove icon-table-u"></i></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>