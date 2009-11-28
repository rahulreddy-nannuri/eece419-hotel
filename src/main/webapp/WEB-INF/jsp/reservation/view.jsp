<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>All Reservations</h2>
	<p><c:out value="${fn:length(reservations)}" /> reservations found.</p>
	<ul class="room-list">
		<c:forEach items="${reservations}" var="reservation" varStatus="idx">
			<li class="row${idx.index % 2}">
				<c:choose>
					<c:when test="${reservation.stayRecord != null}">
						<p>Room: #<c:out value="${reservation.stayRecord.room.number}"/></p>
						<p>Date: <fmt:formatDate value="${reservation.checkIn}"/> - <fmt:formatDate value="${reservation.checkOut}"/>
					</c:when>
					<c:otherwise>
						<p>Room Type: <c:out value="${reservation.roomType.name}" /></p>
						<p>Date: <fmt:formatDate value="${reservation.checkIn}"/> - <fmt:formatDate value="${reservation.checkOut}"/>
						<ul class="nav">
							<li><a href="/reservation/cancel?id=<c:out value="${reservation.id}" />">Cancel</a></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</li>
		</c:forEach>
	</ul>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<%--<jsp:include page="/WEB-INF/jsp/checkin/sidebar.jsp"/>--%>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
