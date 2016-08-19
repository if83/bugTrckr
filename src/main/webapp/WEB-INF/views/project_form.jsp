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

<div class="margin-top-30 row">
    <form:form action="/projects/add" modelAttribute="project" method="POST">
        <div class="col-sm-12 col-md-5 col-md-offset-1">

            <spring:bind path="title">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label for="titleInput">Title</label>
                    <form:input path="title" type="text" class="form-control" id="titleInput"
                                placeholder="Title"/>
                    <form:errors path="title" class="control-label"/>
                </div>
            </spring:bind>

            <spring:bind path="guestView">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label>Ability to review project by gests</label><br>
                    <form path="guestView" type="boolean" class="form-control" id="abilityToViewInput">
                        <label class="radio-inline">
                            <input type="radio" name="guestViewRadios" id="guestViewChooseTrue" value="true" checked>
                            True
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="guestViewRadios" id="guestViewChooseFalse" value="false">
                            False
                        </label>
                    </form>
                    <form:errors path="guestView" class="control-label"/>
                </div>
            </spring:bind>
        </div>

        <div class="col-sm-10 col-sm-offset-1">
            <label for="editor1">Description</label>
        </div>
        <div class="col-sm-10 col-sm-offset-1">
            <form:textarea path="description" cols="100" id="editor1" rows="10"></form:textarea>
        </div>
        <form:hidden path="id"/>
        <div class="col-sm-10 col-sm-offset-1">
            <input type="submit" value="${buttonname}" class="margin-top-30 btn-u pull-right"/>
        </div>
    </form:form>
</div>
