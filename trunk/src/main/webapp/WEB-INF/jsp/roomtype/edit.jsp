<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">

	<h3>Edit Room Type Details</h3>
	<form:form commandName="roomtype" action="/roomtype/save" cssClass="std-form">
		<fieldset>
			<form:hidden path="id" />
			<ol>
				<li>
					<form:label path="name">Name:</form:label>
					<form:input path="name" />
					<form:errors cssClass="error" path="name" />
				</li>
				<li>
					<form:label path="description">Description:</form:label>
					<form:input path="description" />
					<form:errors cssClass="error" path="description" />
				</li>
				<li>
					<form:label path="dailyRate">Daily Rate:</form:label>
					<form:input path="dailyRate" />
					<form:errors cssClass="error" path="dailyRate" />
				</li>
				<li>
					<form:label path="maxOccupancy">Max Occupancy</form:label>
					<form:input path="maxOccupancy" />
					<form:errors cssClass="error" path="maxOccupancy" />
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
