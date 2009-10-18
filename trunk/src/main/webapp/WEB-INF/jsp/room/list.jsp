<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h3>All Rooms</h3>
    <p><c:out value="${fn:length(rooms)}" /> rooms in database. </p>
    <ul>
        <c:forEach items="${rooms}" varStatus="idx"><%-- TODO: use spring:bind in the loop? --%>
            <li><c:out value="${rooms[idx.index].number}" />: <c:out value="${rooms[idx.index].description}" /> <a href="/room/edit?id=<c:out value="${rooms[idx.index].id}" />">Edit</a> <a href="/room/delete?id=<c:out value="${rooms[idx.index].id}" />">Delete</a></li>
        </c:forEach>
    </ul>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <ul>
        <li>View Rooms</li>
        <li><a href="/room/edit">New Room</a></li>
    </ul>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>
