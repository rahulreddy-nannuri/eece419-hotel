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
					<label>Check In:</label>
					<c:out value="${reservation.checkIn}"/>
				</li>
				<li>
					<label>Check Out:</label>
					<c:out value="${reservation.checkOut}"/>
				</li>
				<li>
					<form:label path="quotedPrice">Quoted Price</form:label>
					<form:input path="quotedPrice" />
					<form:errors cssClass="error" path="quotedPrice" />
				</li>
				<li>
					<table class="chargeables">
					<tr><th>Chargeable Items:</th></tr>
					<c:forEach items="${reservation.chargeableItems}" var="citem">
					<tr>
						<td>${citem.name}</td><td><fmt:formatNumber type="currency" value="${citem.price}"/></td>
					</tr>
					</c:forEach>
					</table>
					<a href="/reservation/charge?reservationId=${reservation.id}">add chargeable item</a>
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
