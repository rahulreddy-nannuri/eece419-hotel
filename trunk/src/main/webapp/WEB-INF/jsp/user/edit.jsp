<%@ include file="/WEB-INF/jsp/navigation/header.jsp" %>
<h2>User :: Edit</h2>
<form:form commandName="user" action="/user/save">
<form:hidden path="id"/>
<form:label path="username">Username:</form:label><form:input path="username"/>
<!-- password=<c:out value="${user.password}" /> -->
<form:label path="password">Password:</form:label><form:input path="password"/>
<form:label path="roles">Roles:</form:label><form:input path="roles"/>
<input type="submit" value="Save"/>
</form:form>
<%@ include file="/WEB-INF/jsp/navigation/footer.jsp" %>