<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h2>Chart</h2>
    <p>This chart is for you to see</p>
    <img src="http://chart.apis.google.com/chart?
		chs=500x300
		&amp;chd=s:blabhblhz
		&amp;cht=lc
		&amp;chdl=reservation
		&amp;chxt=x,y,x
		&amp;chxl=0:|Jan|Feb|March|Apr|May|June|July|Aug|Sept|Oct|Nov|Dec|
		1:|0|25|50|75|100|
		2:|2006|2007|2008"
		alt="Sample chart" 
		
	/>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <jsp:include page="/WEB-INF/jsp/roomtype/searchSidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>