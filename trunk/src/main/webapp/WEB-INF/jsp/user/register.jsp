<%@include file="/WEB-INF/jsp/include.jsp" %>


<%-- main contains the main content --%>
<c:set var="main" scope="request">

	<h3>User details</h3>
    <form:form commandName="user" action="/user/save" cssClass="std-form">
        <form:hidden path="id"/>
        <fieldset>
			<input type="hidden" name="view" value="${view}"/>
            <ol>
                <li>
                    <form:label path="username">Username:</form:label>
                    <form:input path="username" cssClass="text"/>
                </li>
                <li>
                    <form:label path="password">Password:</form:label>
                    <form:password path="password" cssClass="text"/>
                </li>
                <li>
                    <form:label path="email">Email:</form:label>
                    <form:input path="email" cssClass="text"/>
                </li>
                <li>
                    <form:label path="address">Address:</form:label>
                    <form:input path="address" cssClass="text"/>
                </li>
            </ol>
            <input type="submit" class="submit" value="Register"/>
        </fieldset>

    </form:form>


</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <ul>
        <li><a href="/user/login">Login</a></li>
        <li>Register</li>
    </ul>

</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>


