<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h2>Chart</h2>
    <p>This chart is for you to see</p>
    <img src="http://chart.apis.google.com/chart?
		chs=250x100
		&amp;chd=t:60,40
		&amp;cht=p3
		&amp;chl=Hello|World"
		alt="Sample chart" 
	/>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <jsp:include page="/WEB-INF/jsp/roomtype/searchSidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>