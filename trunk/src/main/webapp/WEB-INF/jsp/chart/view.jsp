<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h2>Chart</h2>
    <p>This chart is for you to see</p>
    <img src="http://chart.apis.google.com/chart?
		chs=500x300
		&amp;chtt=Reservation+Per+RoomTypes|For+Different+Seasons
		&amp;chg=9,12.5
		&amp;chd=s:blabhblhz,hghjgjhg7,jhgjgh9s
		&amp;cht=lc
		&amp;chdl=roomtype1|roomtype2|roomtype3
		&amp;chco=FF0000,00FF00,0000FF
		&amp;chxt=x,y,x
		&amp;chxl=0:|Jan|Feb|March|Apr|May|June|July|Aug|Sept|Oct|Nov|Dec|
		1:|0|25|50|75|100|
		2:|2009|2010"
		alt="Sample chart" 
		
	/>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <jsp:include page="/WEB-INF/jsp/roomtype/searchSidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>