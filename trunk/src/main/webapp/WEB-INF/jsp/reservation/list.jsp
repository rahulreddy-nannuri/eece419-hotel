
<%@include file="/WEB-INF/jsp/include.jsp"%>
<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<script type="text/javascript">
		/* <![CDATA[ */
		$(function() {
			$("#reservation-filter").change(function() {
				var options = $(this).serialize();
				var newURL = "/reservation/list?" + options;
				$(location).attr("href", newURL);
			});
		});
		/* ]]> */
	</script>
	<h2>All Reservations</h2>
	<div id="reservation-filter-container">
		<span id="reservation-filter-label">Show:</span>
		<select name="filter" id="reservation-filter">
			<option value="">All Reservations</option>
			<option value="past" ${(filter == "past") ? "selected" : ""} >Past Reservations</option>
			<option value="future" ${(filter == "future") ? "selected" : ""}>Future Reservations</option>
			<option value="current" ${(filter == "current") ? "selected" : ""}>Current Reservations</option>
		</select>
	</div>
	<p><c:out value="${fn:length(reservations)}" /> reservations found.</p>
	<ul class="room-list">
		<c:forEach items="${reservations}" var="reservation" varStatus="idx">
			<li class="row${idx.index % 2}">
				<h3>Username: <c:out value="${reservation.user.username}" /></h3>
				<c:choose>
					<c:when test="${reservation.checkedOut}">
						<p>Room: #<c:out value="${reservation.stayRecord.room.number}"/></p>
						<p>Date: <fmt:formatDate value="${reservation.checkIn}"/> - <fmt:formatDate value="${reservation.checkOut}"/>
					</c:when>
					<c:when test="${reservation.checkedIn}">
						<p>Room: #<c:out value="${reservation.stayRecord.room.number}"/></p>
						<p>Date: <fmt:formatDate value="${reservation.checkIn}"/> - <fmt:formatDate value="${reservation.checkOut}"/>
						<ul class="nav">
							<li><a href="/reservation/edit?id=<c:out value="${reservation.id}" />">Edit</a></li>						
							<li><a href="/reservation/checkout?id=<c:out value="${reservation.id}" />">Check Out</a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<p>Room Type: <c:out value="${reservation.roomType.name}" /></p>
						<p>Date: <fmt:formatDate value="${reservation.checkIn}"/> - <fmt:formatDate value="${reservation.checkOut}"/>
						<ul class="nav">
							<c:if test="${reservation.canCheckIn}">
								<li><a href="/reservation/checkin?id=<c:out value="${reservation.id}" />">Check In</a></li>
							</c:if>
							<li><a href="/reservation/edit?id=<c:out value="${reservation.id}" />">Edit</a></li>
							<li><a href="/reservation/cancel?id=<c:out value="${reservation.id}" />">Cancel</a></li>
							<li><a href="/reservation/delete?id=<c:out value="${reservation.id}" />">Delete</a></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</li>
		</c:forEach>
	</ul>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/checkin/sidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
