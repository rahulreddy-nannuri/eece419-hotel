<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h2>All Rooms</h2>
    <p><c:out value="${fn:length(rooms)}" /> rooms in found. </p>
    <ul class="room-list">
        <c:forEach items="${rooms}" varStatus="idx"><%-- TODO: use spring:bind in the loop? --%>
        	<c:choose>
	        	<c:when test="${idx.index % 2 == 0}"><%-- check to see if this is an odd item --%>
	            	<li class="odd">
	           	</c:when>
	           	<c:otherwise>
	           		<li class="even">
	           	</c:otherwise>
	       	</c:choose>
            	<h3>Room #<c:out value="${rooms[idx.index].number}" /></h3>
            	<p><c:out value="${rooms[idx.index].description}" /></p>
            	<ul class="nav">
            		<li><a href="/room/edit?id=<c:out value="${rooms[idx.index].id}" />">Edit</a></li>
            		<li><a href="/room/delete?id=<c:out value="${rooms[idx.index].id}" />">Delete</a></li>
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
