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
				<li>
					<form:label path="roomType">Room Type:</form:label>
					<form:input path="roomType" />
					<form:errors cssClass="error" path="roomType" />
				</li>
			</ol>

			<input type="submit" value="Save" />
		</fieldset>
	</form:form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request"> 
	<jsp:include page="/WEB-INF/jsp/room/sidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
