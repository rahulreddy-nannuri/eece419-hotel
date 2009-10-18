<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h3>Welcome</h3>
    <p>Thank you for choosing X-Reserve and we hope you will enjoy your staying.</p>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <ul>
        <li><a href="/">Search for rooms</a></li>
    </ul>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>


