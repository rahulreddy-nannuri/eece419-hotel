<%@include file="/WEB-INF/jsp/include.jsp"%>
<c:set var="js" scope="request">
	<jsp:include page="/WEB-INF/jsp/ajax/form.js" />
</c:set>
<%-- main contains the main content --%>
<c:set var="main" scope="request">

	<h3>Select reservation</h3>
	<form:form id="reservation" commandName="checkin" cssClass="std-form">
		<fieldset>
			<form:errors cssClass="error" path="*" />
			<form:hidden path="selectedReservation" />
			 <ul class="room-list">
				<c:forEach var="reservation" items="${checkin.reservations}" varStatus="idx">
					<li class="row${idx.index % 2}">
						<a onClick="submitForm('reservation','selectedReservation','${reservation.id}')">${reservation.description}</a>
					</li>
				</c:forEach>
			</ul>
			<input type="submit" value="Back" name="_target0"/>
		</fieldset>
	</form:form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
		<jsp:include page="/WEB-INF/jsp/checkin/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
