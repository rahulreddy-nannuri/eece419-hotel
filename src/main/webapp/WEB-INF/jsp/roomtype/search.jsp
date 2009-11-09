<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>Select a room type</h2>
	<p><c:out value="${fn:length(roomTypes)}" /> room types found.</p>
	<ul class="room-list">
		<c:forEach items="${roomTypes}" var="rmt" varStatus="idx">
			<li class="row${idx.index % 2}">
				<div class="image">
					<c:if test="${rmt.imageId > 0}">
						<img class="thumbnail" src="/image/view?id=${rmt.imageId}" alt="image of the room type" />
					</c:if>
				</div>
				<div class="description">
					<h3>Room Type: <c:out value="${rmt.name}" /></h3>
					<p>Occupancy: <c:out value="${rmt.maxOccupancy}" /></p>
					<p>Rate: <c:out value="${rmt.dailyRate}" /></p>
					<p><c:out value="${rmt.description}" /> (${available[rmt]} available)</p>
				</div>

				<ul class="nav">
				<c:url value="/reserve" var="reserveUrl">
					<c:param name="type" value="${rmt.id}"/>
					<c:param name="checkIn" value="${search.checkIn}"/>
					<c:param name="checkOut" value="${search.checkOut}"/>
				</c:url>
				<li><a href="${reserveUrl}">Reserve</a></li>
			</ul>
			</li>
		</c:forEach>
	</ul>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/roomtype/searchSidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
