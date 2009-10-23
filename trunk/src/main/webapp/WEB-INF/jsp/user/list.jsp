<%@include file="/WEB-INF/jsp/include.jsp"%>


<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>View Users</h2>
	<ul class="room-list">
		<c:forEach items="${users}" var="user" varStatus="idx">
			<li class="row${idx.index % 2}">
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
	<jsp:include page="/WEB-INF/jsp/user/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />

