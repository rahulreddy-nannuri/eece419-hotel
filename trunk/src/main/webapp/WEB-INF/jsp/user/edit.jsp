<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">

    <form:form commandName="user" action="/user/save" cssClass="std-form">
		<fieldset>
			<legend>Edit Profile</legend>
			<form:hidden path="id"/>
			<ol>
				
					<li>
						<form:label path="username">Username:</form:label>
						<c:choose>
							<c:when test="${isAdmin}">
								<form:input path="username"/>
							</c:when>
							<c:otherwise>
								<form:input path="username" disabled="true"/>
							</c:otherwise>
						</c:choose>
						<form:errors cssClass="error" path="username" />
					</li>
				
				<li>
					<form:label path="password">Password:</form:label>
					<form:input path="password"/>
					<form:errors cssClass="error" path="password" />
				</li>
				<li>
					<form:label path="email">Eamil:</form:label>
					<form:input path="email"/>
					<form:errors cssClass="error" path="email" />
				</li>
				<li>
					<form:label path="address">Address:</form:label>
					<form:input path="address"/>
					<form:errors cssClass="error" path="address" />
				</li>

				<c:if test="${isAdmin}">
					<li>
						<form:label path="roles">Roles:</form:label>
						<form:input path="roles"/>
						<form:errors cssClass="error" path="roles" />
					</li>
				</c:if>

			</ol>

			<input type="submit" value="Save"/>
		</fieldset>
    </form:form>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <ul>
		<c:if test="${isAdmin}">
			<li><a href="/user/list">View all users</a></li>
		</c:if>
		<li>Edit profile</li>

    </ul>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>
