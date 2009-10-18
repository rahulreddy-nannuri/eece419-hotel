<%@include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>X-Reserve</title>
        <link rel="stylesheet" type="text/css" href="/static/css/site.css" />
    </head>
    <body>
        <div id="wrap">
            <jsp:include flush="true" page="/WEB-INF/jsp/navigation/header.jsp"/>
            <div id="main">
                <h3>Edit User</h3>
                <form:form commandName="user" action="/user/save">
                    <form:hidden path="id"/>
                    <form:label path="username">Username:</form:label><form:input path="username"/>
                    <!-- password=<c:out value="${user.password}" /> -->
                    <form:label path="password">Password:</form:label><form:input path="password"/>
                    <form:label path="roles">Roles:</form:label><form:input path="roles"/>
                    <input type="submit" value="Save"/>
                </form:form>
            </div>
            <div id="sidebar">
                <ul>
                    <li><a href="/user/list">View Users</a></li>
                    <li>New User</li>
                </ul>
            </div>
            <jsp:include flush="true" page="/WEB-INF/jsp/navigation/footer.jsp"/>
        </div>
    </body>
</html>