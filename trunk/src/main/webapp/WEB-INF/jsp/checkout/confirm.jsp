<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">

	<h3>Checkout  successful</h3>

</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
		<jsp:include page="/WEB-INF/jsp/checkin/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
