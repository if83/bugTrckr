<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


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
            <div class="col-sm-9"><p>${user.role.toString()}</p></div>
        </div>

        <div class="row">
            <label class="col-sm-3">Description</label>
            <div class="col-sm-9">${user.description}</div>
        </div>

        <c:if test="${not empty user.project}">
            <div class="row">
                <label class="col-sm-3">Active Project</label>

                <div class="col-sm-9"><a class="viewLink" href="<spring:url value='/projects/project/${user.project.id}'/>">
                    ${user.project.title}</a></div>
            </div>
        </c:if>
        <sec:authorize access="hasRole('ADMIN')">
            <div class="margin-top-30 row">
                <div class="col-sm-4">
                    <spring:url value="/user/${user.id}/edit" var="useredit"/>
                    <a class="btn btn-default" href="${useredit}" role="button">Edit profile</a>
                </div>
                <div class="col-sm-8"></div>
            </div>
        </sec:authorize>
    </div>
</div>

<div class="row">
    <div class="user-history col-sm-10 col-sm-offset-1">

        <div class="margin-top-30 row">
            <div class="col-sm-12">
                <!-- tabs -->
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active">
                        <a href="#issueActivity" role="tab" data-toggle="tab">Activity</a>
                    </li>
                    <li role="presentation">
                        <a href="#commentActivity" role="tab" data-toggle="tab">Comments</a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="tab-content">
            <%--tab issue activity--%>
            <div role="tabpanel" class="tab-pane fade in active" id="issueActivity">
                <ul>
                    <%--parsing user's history--%>
                    <c:forEach var="history" items="${allHistory.content}">
                        <c:choose>
                            <c:when test="${(history.action == 'CREATE_ISSUE') or (history.action == 'CHANGE_ISSUE_ASSIGNEE')}">
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
                                    <c:when test="${history.action == 'CREATE_ISSUE'}">
                                        created issue <strong><a class="viewLink"
                                                                 href="<spring:url value='/issue/${history.issue.id}'/>">${history.issue.title}</a></strong>
                                        and assigned it to
                                    </c:when>
                                    <c:when test="${history.action == 'CHANGE_ISSUE_ASSIGNEE'}">
                                        reassigned <strong><a class="viewLink"
                                                              href="<spring:url value='/issue/${history.issue.id}'/>">${history.issue.title}</a></strong>
                                        to
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
                            <c:otherwise>
                                <c:if test="${(history.action ne 'ADD_ISSUE_COMMENT') and (history.action ne 'EDIT_ISSUE_COMMENT')}">
                                    <li>
                                        <strong>
                                            <c:choose>
                                                <c:when test="${history.changedByUser.id eq null}">
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
                                        changed ${fieldLowCase} of <strong><a class="viewLink"
                                                                              href="<spring:url value='/issue/${history.issue.id}'/>">${history.issue.title}</a></strong>
                                        to <strong>${fieldValue}</strong></br>
                                        <span class="createTime">at ${history.createTime}</span>
                                    </li>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
                <%--pagination of issue activity--%>
                <c:if test="${allHistory.getTotalPages()> 1}">
                    <nav aria-label="Page navigation" id="pagerID">
                        <div class="text-center">
                            <ul class="pagination">
                                <li>
                                    <a href="<spring:url value='/user/${user.id}/view?page=0'/>" aria-label="Start">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <c:forEach var="page" begin="0" end="${allHistory.getTotalPages() - 1}">
                                    <li>
                                        <a href="<spring:url value='/user/${user.id}/view?page=${page}'/>">${page + 1}</a>
                                    </li>
                                </c:forEach>
                                <li>
                                    <a href="<spring:url value='/user/${user.id}/view?page=${allHistory.getTotalPages() - 1}'/>"
                                       aria-label="End"><span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </nav>
                </c:if>
            </div>
            <%--comment tab--%>
            <div role="tabpanel" class="tab-pane fade" id="commentActivity">
                <ul>
                    <%--parsing user's history--%>
                    <c:forEach var="history" items="${commentHistory.content}">
                        <c:choose>
                            <c:when test="${history.action eq 'ADD_ISSUE_COMMENT' or history.action eq 'EDIT_ISSUE_COMMENT'}">
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
                                        <c:when test="${history.action eq 'ADD_ISSUE_COMMENT'}">
                                            add comment to issue <strong><a class="viewLink"
                                                                     href="<spring:url value='/issue/${history.issue.id}'/>">${history.issue.title}</a></strong>
                                        </c:when>
                                        <c:when test="${history.action eq 'EDIT_ISSUE_COMMENT'}">
                                            edited comment in issue <strong><a class="viewLink"
                                                                  href="<spring:url value='/issue/${history.issue.id}'/>">${history.issue.title}</a></strong>
                                        </c:when>
                                    </c:choose>
                                    <div class="user-comment">
                                            ${history.issueComment}
                                    </div>
                                    <span class="createTime">at ${history.createTime}</span>
                                </li>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>

