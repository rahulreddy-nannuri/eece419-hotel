<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>All Room Types</h2>
	<p><c:out value="${fn:length(roomTypes)}" /> room types found.</p>
	<ul class="room-list">
		<c:forEach items="${roomTypes}" var="rmt" varStatus="idx">
			<li class="row${idx.index % 2}">
				<h3>Room Type: <c:out value="${rmt.name}" /></h3>
				<p>Occupancy: <c:out value="${rmt.maxOccupancy}" /></p>
				<p>Rate: <c:out value="${rmt.dailyRate}" /></p>
				<p><c:out value="${rmt.description}" /></p>
				<c:if test="${rmt.imageId > 0}">
					<img class="thumbnail" src="/image/view?id=${rmt.imageId}" alt="image of the room type" />
				</c:if>

				<ul class="nav">
					<li><a href="/roomtype/edit?id=<c:out value="${rmt.id}" />">Edit</a></li>
					<li><a href="/roomtype/delete?id=<c:out value="${rmt.id}" />">Delete</a></li>
				</ul>
			</li>
		</c:forEach>
	</ul>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/room/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
