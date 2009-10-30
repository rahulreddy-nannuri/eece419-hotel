<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>Confirm Reservation</h2>
	<p>Thank you for reserving a room with X-Reserve.</p>
	<h3>Room Details</h3>
	<ul>
		<li>Type: <c:out value="${roomTypeName}" /></li>
		<li>Price: <c:out value="${roomTypePrice}" /></li>
		<li>Description: <c:out value="${roomTypeDescription}" /></li>
	</ul>
	<h3>Payment Details</h3>
	<ul>
		<li>Card Number: <c:out value="${maskedCardNumber}" /></li>
		<li>Card Type: <c:out value="${cardType}" /></li>
	</ul>
	<form action="/reserve" class="std-form confirmation">
		<input type="hidden" name="cardNumber" value="<c:out value="${cardNumber}" />" />
		<input type="hidden" name="expiryMonth" value="<c:out value="${expiryMonth}" />" />
		<input type="hidden" name="expiryYear" value="<c:out value="${expiryYear}" />" />
		<input type="hidden" name="securityCode" value="<c:out value="${securityCode}" />" />
		<input type="hidden" name="cardType" value="<c:out value="${cardType}" />" />
		<input type="hidden" name="state" value="<c:out value="${state + 1}" />" />
        <input type="hidden" name="type" value="<c:out value="${type}" />" />
		<input type="submit" name="confirmation" value="Reserve Room" />
	</form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request"> 
	<jsp:include page="/WEB-INF/jsp/reservation/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
