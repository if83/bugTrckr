<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>


<div class="row">
    <div>
        <table>
            <form:form>

            </form:form>
            <tr>
                <td>title</td>
                <td>${issue.title}</td>

                <td>description</td>
                <td>${issue.description}</td>

                <td>Status</td>
                <td>${issue.status}</td>
            </tr>
        </table>
    </div>
</div>

