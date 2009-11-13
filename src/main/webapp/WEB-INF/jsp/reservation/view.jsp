<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>All Reservations</h2>
	<p><c:out value="${fn:length(reservations)}" /> reservations found.</p>
	<ul class="room-list">
		<c:forEach items="${reservations}" var="reservation" varStatus="idx">
			<li class="row${idx.index % 2}">
				<p>Room Type: <c:out value="${reservation.roomType.name}" /></p>
				<%-- <p>Price: <c:out value="${reservation.price}" /></p> --%>
				<p>Date: <fmt:formatDate value="${reservation.checkIn}"/> - <fmt:formatDate value="${reservation.checkOut}"/>
				<%--
				<ul class="nav">
					<li><a href="/reservation/delete?id=<c:out value="${reservation.id}" />">Delete</a></li>
				</ul>
				--%>
			</li>
		</c:forEach>
	</ul>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/checkin/sidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
