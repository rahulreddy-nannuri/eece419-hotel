<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <p>This graph displays total reservations per month for different types of rooms for the current year</p>
    <img src="${getURL}"
	/>
	<p>&nbsp &nbsp</p>
    <p>This graph displays total reservations per month for the current year</p>
    <img src="${getURL2}"
	/>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <jsp:include page="/WEB-INF/jsp/chart/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>