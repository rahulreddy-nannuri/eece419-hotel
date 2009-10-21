<%@include file="/WEB-INF/jsp/include.jsp" %>


<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h2>View Users</h2>
    <ul>
        <c:forEach items="${users}" var="user">
            <li><c:out value="${user.username}" /> <a href="/user/edit?id=<c:out value="${user.id}" />">Edit</a> <a href="/user/delete?id=<c:out value="${user.id}" />">Delete</a></li>
        </c:forEach>
    </ul>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <jsp:include page="/WEB-INF/jsp/user/sidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>

