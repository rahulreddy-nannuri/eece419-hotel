<%@include file="/WEB-INF/jsp/include.jsp"%>

<c:set var="main" scope="request">
	<h3>Edit Reservation Details</h3>
	<form:form commandName="reservation" action="/reservation/save" cssClass="std-form">
		<fieldset>
			<form:hidden path="id" />
			<ol>
				<li>
					<label>Room Type:</label>
					<c:out value="${reservation.roomType.name}"/>
				</li>
				<li>
					<label>User:</label>
					<c:out value="${reservation.user.username}"/>
				</li>
				<li>
					<form:label path="checkIn">Check In:</form:label>
					<form:input path="checkIn" />
					<form:errors cssClass="error" path="checkIn" />
				</li>
				<li>
					<form:label path="checkOut">Daily Rate:</form:label>
					<form:input path="checkOut" />
					<form:errors cssClass="error" path="checkOut" />
				</li>
				<li>
					<form:label path="quotedPrice">Quoted Price</form:label>
					<form:input path="quotedPrice" />
					<form:errors cssClass="error" path="quotedPrice" />
				</li>
			</ol>

			<input type="submit"  value="Save" />
		</fieldset>
	</form:form>

</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/room/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
