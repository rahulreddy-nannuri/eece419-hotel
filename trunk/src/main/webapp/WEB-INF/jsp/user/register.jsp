<%@include file="/WEB-INF/jsp/include.jsp" %>


<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>User details</h2>
    <form:form commandName="user" action="/user/register" cssClass="std-form">
        <form:hidden path="id"/>
        <fieldset>
			<input type="hidden" name="view" value="${view}"/>
            <ol>
                <li>
                    <form:label path="username">Username:</form:label>
                    <form:input path="username" cssClass="text"/>
					<form:errors path="username" cssClass="error"/>
                </li>
                <li>
                    <form:label path="password">Password:</form:label>
                    <form:password path="password" cssClass="text"/>
					<form:errors path="password" cssClass="error"/>
                </li>
                <li>
                    <form:label path="email">Email:</form:label>
                    <form:input path="email" cssClass="text"/>
					<form:errors path="email" cssClass="error"/>
                </li>
                <li>
                    <form:label path="address">Address:</form:label>
                    <form:input path="address" cssClass="text"/>
					<form:errors path="address" cssClass="error"/>
                </li>
            </ol>
            <input type="submit" class="submit" value="Register"/>
        </fieldset>
    </form:form>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <jsp:include page="/WEB-INF/jsp/user/sidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>


