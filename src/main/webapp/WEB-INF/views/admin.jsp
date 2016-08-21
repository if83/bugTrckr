<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<sec:authorize access="hasRole('ADMIN')">
    <h1>Admin page, authenticated.</h1>
    <p>Must have role ADMIN</p>
</sec:authorize>
