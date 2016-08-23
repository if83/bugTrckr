<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div class="breadcrumbs">
    <div class="row">
        <div class="col-sm-2 col-sm-offset-1">
            <h1 class="pull-left">Admin dashboard</h1>
        </div>
        <div class="col-sm-8">
            <ol class="pull-right breadcrumb">
                <li><a href="<spring:url value='/'/>">Home</a></li>
                <li class="active">Admin</li>
            </ol>
        </div>
    </div>
</div>


<div class="margin-top-30 row">
    <div class="col-sm-12">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist" id="'myUserTabs">
            <li role="presentation" class="active"><a href="#allUsersTab" aria-controls="allUsersTab" role="tab"
                                                      data-toggle="tab">All users</a></li>
            <li role="presentation"><a href="#admin_managerUsersTab" aria-controls="admin_managerUsersTab" role="tab"
                                       data-toggle="tab">Admin and managers</a></li>
            <li role="presentation"><a href="#otherStaffUsersTab" aria-controls="otherStaffUsersTab" role="tab"
                                       data-toggle="tab">Other staff members</a></li>
        </ul>
    </div>
</div>


    <!-- Tab panes -->
    <div class="tab-content">
            <div role="tabpanel" class="tab-pane fade active" id="allUsersTab">
                <div class="margin-top-30 row">
                <div class="col-sm-12">
                    <table id="AllUsersTable">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>First name</th>
                            <th>Last name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Project Title</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


            <div role="tabpanel" class="tab-pane fade" id="admin_managerUsersTab">
                <div class="margin-top-30  row">
                <div class="col-sm-12">
                    <table id="AdminManagerUsersTable">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>First name</th>
                            <th>Last name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Project Title</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


            <div role="tabpanel" class="tab-pane fade" id="otherStaffUsersTab">
                <div class="margin-top-30 row">
                <div class="col-sm-12">
                    <table id="otherStaffUsersTable">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>First name</th>
                            <th>Last name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Project Title</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
