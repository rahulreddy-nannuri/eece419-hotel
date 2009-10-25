<%@include file="/WEB-INF/jsp/include.jsp"%>
<c:set var="js" scope="request">
    $(function() {
	$("#checkindate").datepicker();
        $("#checkoutdate").datepicker();
        $("#price-slider").slider({
            range: true,
            min: 0,
            max: 1000,
            values: [0, 1000],
            slide: function(event, ui) {
                    $("#price-amount").val('$' + ui.values[0] + ' - $' + ui.values[1]);
            }
        });
        $("#price-amount").val('$' + $("#price-slider").slider("values", 0) + ' - $' + $("#price-slider").slider("values", 1));
    });

</c:set>
<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>Select a room type</h2>
	<p><c:out value="${fn:length(roomTypes)}" /> room types found.</p>
	<ul class="room-list">
		<c:forEach items="${roomTypes}" var="rmt" varStatus="idx">
			<li class="row${idx.index % 2}">
				<h3><c:out value="${rmt.name}" /></h3>
				<p>Occupancy: <c:out value="${rmt.maxOccupancy}" /></p>
				<p>Rate: $<c:out value="${rmt.dailyRate}" /></p>
				<p><c:out value="${rmt.description}" /></p>
				<ul class="nav">
					<li><a href="/roomtype/reserve/type/<c:out value="${rmt.id}" />">Reserve</a></li>
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
