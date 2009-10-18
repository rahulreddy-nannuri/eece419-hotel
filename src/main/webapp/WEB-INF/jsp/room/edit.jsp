<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h3>Edit Room Details</h3>
    <form:form commandName="room" action="/room/save">
        <form:hidden path="id"/>
        <form:label path="number">Room Number:</form:label><form:input path="number"/>
        <form:label path="description">Description:</form:label><form:input path="description"/>
        <input type="submit" value="Save"/>
    </form:form>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <ul>
        <li><a href="/room/list">View Rooms</a></li>
        <li>New Room</li>
    </ul>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>
