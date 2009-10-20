<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h3>Edit Room Details</h3>
	<form:form commandName="room" action="/room/save">
		<form:hidden path="id" />
		<table>
			<tr>
				<td><form:label path="number">Room Number:</form:label></td>
				<td><form:input path="number" /></td>
				<td><form:errors cssClass="error" path="number" /></td>
			</tr>
			<tr>
				<td><form:label path="description">Description:</form:label></td>
				<td><form:input path="description" /></td>
				<td><form:errors cssClass="error" path="description" /></td>
			</tr>
		</table>
		<input type="submit" value="Save" />
	</form:form>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
	<ul>
		<li><a href="/room/list">View Rooms</a></li>
		<li>New Room</li>
	</ul>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
