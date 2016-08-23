<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left">Admin</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li class="active">Admin</li>
            </ol>
        </div>
    </div>
</div>

<sec:authorize access="hasRole('ADMIN')">
    <h1>Admin page, authenticated.</h1>
    <p>Must have role ADMIN</p>
</sec:authorize>
