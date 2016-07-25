<%--
  User: ihorlt
  Date: 18.07.16
--%>
<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
    <meta http-equiv="content-type" content="text/html" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome</title>
    <link href="<spring:url value='css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<spring:url value='css/bootstrap-theme.min.css' />" rel="stylesheet">
    <link href="<spring:url value='css/font-awesome.min.css' />" rel="stylesheet">
    <link href="<spring:url value='css/site.css' />" rel="stylesheet">
</head>
<body>
<div class="page">
    <tiles:insertAttribute name="header" />
    <div class="content">
        <tiles:insertAttribute name="menu" />
        <div class="container-fluid">
            <tiles:insertAttribute name="body" />
        </div>
    </div>
    <tiles:insertAttribute name="footer" />
</div>

<script src="<spring:url value='js/jquery-2.2.4.min.js' />"></script>
<script src="<spring:url value='js/bootstrap.min.js' />"></script>
</body>
</html>