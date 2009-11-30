<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<c:choose>
		<c:when test="${errors != null}">
			<h2>Reservation Failed</h2>
			<p>There are no available rooms matching your reservation criteria. 
			Please perform another search to make a new reservation.</p>
		</c:when>
		<c:otherwise>
			<h2>Reservation Complete</h2>
			<p>Your reservation has been submitted for processing.</p>
		</c:otherwise>
	</c:choose>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request"> 
	<jsp:include page="/WEB-INF/jsp/reservation/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
