<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">

	<h3>Select reservation</h3>
	<form:form commandName="checkin" cssClass="std-form">
		<fieldset>
			<form:errors cssClass="error" path="*" />
			<ol>
				<c:forEach var="reservation" items="${checkin.reservations}" >
					<li>
						${reservation.description}<form:radiobutton path="selectedReservation" value="${reservation.id}"/>
					</li>
				</c:forEach>
			</ol>
			<input type="submit" value="Back" name="_target0"/>
			<input type="submit" value="Check In" name="_finish"/>
		</fieldset>
	</form:form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
		<jsp:include page="/WEB-INF/jsp/checkin/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
