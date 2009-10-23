<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h2>All Rooms</h2>
    <p><c:out value="${fn:length(rooms)}" /> rooms found. </p>
    <ul class="room-list">
        <c:forEach items="${rooms}" var="room" varStatus="idx">
    		<li class="row${idx.index % 2}">
            	<h3>Room #<c:out value="${room.number}" /></h3>
            	<p><c:out value="${room.description}" /></p>
            	<ul class="nav">
            		<li><a href="/room/edit?id=<c:out value="${room.id}" />">Edit</a></li>
            		<li><a href="/room/delete?id=<c:out value="${room.id}" />">Delete</a></li>
            	</ul>
            </li>
        </c:forEach>
    </ul>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/room/sidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>
