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
                                                      data-toggle="tab">Users</a></li>
            <li role="presentation"><a href="#detailsUserTab" aria-controls="detailsUserTab" role="tab"
                                       data-toggle="tab">Staff Details</a></li>
        </ul>
    </div>
</div>


<!-- Tab panes -->
<div class="tab-content">
    <div role="tabpanel" class="tab-pane fade active" id="allUsersTab">
        <div class="margin-top-30 row">
            <div class="col-sm-12">
                <table class="table table-striped" id="AllUsersTable">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Project Title</th>
                        <th>Is deleted</th>
                        <th>Can login</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>


    <div role="tabpanel" class="tab-pane fade" id="detailsUserTab">
        <div class="margin-top-30 row">
            <div class="col-sm-8 col-sm-offset-1">
                <div class="row">
                    <div class="col-sm-3"><h5>ID</h5></div>
                    <div class="col-sm-9" id="detailsUserTab1"><h5></h5></div>
                </div>

                <div class="row">
                    <div class="col-sm-3"><h5>First name</h5></div>
                    <div class="col-sm-9" id="detailsUserTab2"><h5></h5></div>
                </div>

                <div class="row">
                    <div class="col-sm-3"><h5>Last name</h5></div>
                    <div class="col-sm-9" id="detailsUserTab3"><h5></h5></div>
                </div>

                <div class="row">
                    <div class="col-sm-3"><h5>Email</h5></div>
                    <div class="col-sm-9" id="detailsUserTab4"><h5></h5></div>
                </div>

                <div class="row">
                    <div class="col-sm-3"><h5>Role</h5></div>
                    <div class="col-sm-9" id="detailsUserTab5"><h5></h5></div>
                </div>

                <div class="row">
                    <div class="col-sm-3"><h5>Project Title</h5></div>
                    <div class="col-sm-9" id="detailsUserTab6"><h5></h5></div>
                </div>

                <div class="row">
                    <div class="col-sm-3"><h5>Description</h5></div>
                    <div class="col-sm-9" id="detailsUserTab7"><h5></h5></div>
                </div>

                <div class="row">
                    <div class="col-sm-3"><h5>Is marked deleted</h5></div>
                    <div class="col-sm-9" id="detailsUserTab9"><h5></h5></div>
                </div>

                <div class="row">
                    <div class="col-sm-3"><h5>Is marked enabled</h5></div>
                    <div class="col-sm-9" id="detailsUserTab10"><h5></h5></div>
                </div>

            </div>
            <div class="col-sm-3">
                <figure>
                    <img id="detailsUserTab8" src="" class="img-thumbnail" alt=""/>
                </figure>
            </div>
        </div>
    </div>


</div>
