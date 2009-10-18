<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h3>Edit User</h3>
    <form:form commandName="user" action="/user/save">
        <form:hidden path="id"/>
        <form:label path="username">Username:</form:label><form:input path="username"/>
        <!-- password=<c:out value="${user.password}" /> -->
        <form:label path="password">Password:</form:label><form:input path="password"/>
        <form:label path="roles">Roles:</form:label><form:input path="roles"/>
        <input type="submit" value="Save"/>
    </form:form>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <ul>
        <li><a href="/user/list">View Users</a></li>
        <li>New User</li>
    </ul>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>
