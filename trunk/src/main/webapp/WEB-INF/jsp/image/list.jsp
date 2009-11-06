<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h2>All Images</h2>
    <p><c:out value="${fn:length(images)}" /> images found. </p>
    <ul class="room-list">
        <c:forEach items="${images}" var="image" varStatus="idx">
    		<li class="row${idx.index % 2}">
            	<h3><c:out value="${image.name}" /></h3>
            	<ul class="nav">
            		<li><a href="/image/view?id=<c:out value="${image.id}" />">View</a></li>
            		<li><a href="/image/edit?id=<c:out value="${image.id}" />">Edit</a></li>
            		<li><a href="/image/delete?id=<c:out value="${image.id}" />">Delete</a></li>
            	</ul>
            </li>
        </c:forEach>
    </ul>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/room/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>
