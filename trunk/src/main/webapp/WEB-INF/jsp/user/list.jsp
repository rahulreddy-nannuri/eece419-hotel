<%@include file="/WEB-INF/jsp/include.jsp" %>


<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h3>View Users</h3>
    <ul>
        <c:forEach items="${users}" var="user">
            <li><c:out value="${user.username}" /> <a href="/user/edit?id=<c:out value="${user.id}" />">Edit</a> <a href="/user/delete?id=<c:out value="${user.id}" />">Delete</a></li>
        </c:forEach>
    </ul>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <ul>
        <li>View Users</li>
        <li><a href="/user/edit">New User</a></li>
    </ul>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>

