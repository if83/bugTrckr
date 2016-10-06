<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="margin-top-30 row">
    <div class="col-sm-12">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs">
            <li role="presentation" class="active">
                <a href="#issuesFound" aria-controls="#issuesFound" role="tab" data-toggle="tab">Issues found</a>
            </li>
            <li role="presentation">
                <a href="#projectsFound" aria-controls="#projectsFound" role="tab" data-toggle="tab">Projects found</a>
            </li>
            <li role="presentation">
                <a href="#releaseFound" aria-controls="#releaseFound" role="tab" data-toggle="tab">
                    Releases found</a>
            </li>
        </ul>
    </div>
</div>
<div class="tab-content">
    <div role="tabpanel" class="tab-pane fade in active" id="issuesFound">
        <div class="margin-top-30 row">
            <div class="col-sm-12">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th class="text-center">Title</th>
                        <th class="text-center">Type</th>
                        <th class="text-center">Status</th>
                        <th class="text-center">Priority</th>
                        <th class="text-center">Description</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="issue" items="${issues}">
                        <tr>
                            <td class="text-center">
                                <a class="viewLink"
                                   href="<spring:url value='issue/${issue.id}'/>">
                                        ${issue.title}
                                </a>
                            </td>
                            <td class="text-center"><c:out value="${issue.type}"/></td>
                            <td class="text-center"><c:out value="${issue.status}"/></td>
                            <td class="text-center"><c:out value="${issue.priority}"/></td>
                            <td class="text-center"><c:out value="${issue.description}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div role="tabpanel" class="tab-pane fade" id="projectsFound">
        <div class="margin-top-30">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th class="text-center">Title</th>
                    <th class="text-center">Description</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="project" items="${projects}">
                    <tr>
                        <td class="text-center">
                            <a class="viewLink" href="<spring:url value='projects/project/${project.id}'/>">
                                <c:out value="${project.title}"/> </a></td>
                        <td class="text-center"><c:out value="${project.description}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div role="tabpanel" class="tab-pane fade" id="releaseFound">
        <div class="margin-top-30">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th class="text-center">Project</th>
                    <th class="text-center">Version</th>
                    <th class="text-center">Release Status</th>
                    <th class="text-center">Description</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="release" items="${releases}">
                    <tr>
                        <td class="text-center">
                            <a class="viewLink" href="<spring:url value='projects/project/${release.project.id}'/>">
                                <c:out value="${release.project.title}"/> </a></td>
                        <td class="text-center">
                            <a class="viewLink"
                               href="<spring:url value='project/${release.project.id}/release/${release.id}'/>">
                                <c:out value="${release.version}"/> </a></td>
                        <td class="text-center"><c:out value="${release.releaseStatus}"/></td>
                        <td class="text-center"><c:out value="${release.description}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
