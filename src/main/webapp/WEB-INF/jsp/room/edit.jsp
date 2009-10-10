<%@ include file="/WEB-INF/jsp/include.jsp" %> 
<html>
<body>
<p>User: <c:out value="${user}"/></p>
<h2>Room :: Edit</h2>
<form:form commandName="room" action="/room/save">
<form:hidden path="id"/>
<form:label path="number">Room Number:</form:label><form:input path="number"/>
<form:label path="description">Description:</form:label><form:input path="description"/>
<input type="submit" value="Save"/>
</form:form>
</body>
</html>
