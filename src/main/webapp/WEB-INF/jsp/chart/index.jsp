<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <jsp:include page="/WEB-INF/jsp/chart/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>