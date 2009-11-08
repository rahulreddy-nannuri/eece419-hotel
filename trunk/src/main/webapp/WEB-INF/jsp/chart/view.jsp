<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h2>Chart</h2>
    <p>This chart is for you to see</p>
    <img src="${getURL}"
		alt="Sample chart" 
	/>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <jsp:include page="/WEB-INF/jsp/chart/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>