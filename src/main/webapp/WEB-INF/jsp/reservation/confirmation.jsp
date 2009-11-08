<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>Confirm Reservation</h2>
	<p>Thank you for reserving a room with X-Reserve.</p>
	<h3>Room Details</h3>
	<ul>
		<li>Type: <c:out value="${reservation.roomType.name}" /></li>
		<li>Price: <c:out value="${reservation.price}" /></li>
		<li>Description: <c:out value="${reservation.roomType.description}" /></li>
	</ul>
	<h3>Payment Details</h3>
	<ul>
		<li>Card Number: <c:out value="${reservation.cardNumber}" /></li>
		<li>Card Type: <c:out value="${reservation.cardType}" /></li>
	</ul>
	<form action="/reserve" class="std-form confirmation" method="post">
		<input type="submit" name="_finish" value="Reserve Room" />
		<input type="submit" value="Cancel" name="_cancel" />
	</form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request"> 
	<jsp:include page="/WEB-INF/jsp/reservation/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
