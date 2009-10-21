<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>Edit Profile</h2>
    <form:form commandName="user" action="/user/save" cssClass="std-form">
		<fieldset>
			<form:hidden path="id"/>
			<ol>
				
					<li>
						<form:label path="username">Username:</form:label>
						<c:choose>
							<%-- once you have a username, you can't change it --%>
							<c:when test="${user.id > 0}">
								<form:input path="username" disabled="true"/>
							</c:when>
							<c:otherwise>
								<form:input path="username"/>
							</c:otherwise>
						</c:choose>
						<form:errors cssClass="error" path="username" />
					</li>
				
				<li>
					<label for="password">Password:</label>
					<!-- don't bind password; we don't show it to the user -->
					<input id="password" name="password" />
					<form:errors cssClass="error" path="password" />
				</li>
				<li>
					<form:label path="email">Email:</form:label>
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
    <jsp:include page="/WEB-INF/jsp/user/sidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>
