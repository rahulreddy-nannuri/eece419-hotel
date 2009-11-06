<%@ include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">

	<c:if test="${fn:length(roomTypes) == 0}">
	<h3 class="error">You need at least one Room Type before creating Rooms.</h3>
	</c:if>

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
					<form:label path="roomType">Room Type:</form:label>
					<form:select path="roomType" items="${roomTypes}" itemValue="id" itemLabel="name"/>
					<form:errors cssClass="error" path="roomType" />
				</li>
			</ol>

			<input type="submit" value="Save" />
		</fieldset>
	</form:form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/room/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
