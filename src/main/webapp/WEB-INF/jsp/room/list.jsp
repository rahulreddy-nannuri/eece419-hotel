<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h2>All Rooms</h2>
    <p><c:out value="${fn:length(rooms)}" /> rooms in database. </p>
    <ul>
        <c:forEach items="${rooms}" varStatus="idx"><%-- TODO: use spring:bind in the loop? --%>
            <li><c:out value="${rooms[idx.index].number}" />: <c:out value="${rooms[idx.index].description}" /> <a href="/room/edit?id=<c:out value="${rooms[idx.index].id}" />">Edit</a> <a href="/room/delete?id=<c:out value="${rooms[idx.index].id}" />">Delete</a></li>
        </c:forEach>
    </ul>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/room/sidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>
