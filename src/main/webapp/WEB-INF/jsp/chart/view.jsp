<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<c:choose>
		<c:when test="${fn:length(getURL) eq 0}">
			<p>No Data Available</p>
		</c:when>
		<c:otherwise>
		    <img src="${getURL}"/>
			<p>&nbsp;</p>
		    <img src="${getURL2}"/>
	    </c:otherwise>
    </c:choose>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <jsp:include page="/WEB-INF/jsp/chart/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>