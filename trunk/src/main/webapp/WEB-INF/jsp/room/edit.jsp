<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">

	<h3>Edit Room Details</h3>
	<form:form commandName="room" action="/room/save" cssClass="std-form">
		<fieldset>
			<form:hidden path="id" />
			<ol>
				<li>
					<form:label path="number">Room Number:</form:label>
					<form:input path="number" />
					<form:errors cssClass="error" path="number" />
				</li>
				<li>
					<form:label path="description">Description:</form:label>
					<form:input path="description" />
					<form:errors cssClass="error" path="description" />
				</li>
			</ol>

			<input type="submit" value="Save" />
		</fieldset>
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
