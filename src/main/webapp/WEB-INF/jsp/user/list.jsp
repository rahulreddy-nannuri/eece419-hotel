<%@include file="/WEB-INF/jsp/include.jsp" %>


<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h2>View Users</h2>
    <ul class="room-list">
        <c:forEach items="${users}" var="user" varStatus="idx">
        	<c:choose>
	        	<c:when test="${idx.index % 2 == 0}"><%-- check to see if this is an odd item --%>
	            	<li class="odd">
	           	</c:when>
	           	<c:otherwise>
	           		<li class="even">
	           	</c:otherwise>
	       	</c:choose>
            	<h3><c:out value="${user.username}" /></h3>
            	<ul class="nav">
            		<li><a href="/user/edit?id=<c:out value="${user.id}" />">Edit</a></li>
            		<li><a href="/user/delete?id=<c:out value="${user.id}" />">Delete</a></li>
            	</ul>
            </li>
        </c:forEach>
    </ul>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <jsp:include page="/WEB-INF/jsp/user/sidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>

