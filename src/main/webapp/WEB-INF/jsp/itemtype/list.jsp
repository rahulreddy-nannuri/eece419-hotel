<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>All Item Types</h2>
	<p><c:out value="${fn:length(itemTypes)}" /> item types found.</p>
	<ul class="room-list">
		<c:forEach items="${itemTypes}" var="rmt" varStatus="idx">
			<li class="row${idx.index % 2}">
				<h3>Item Type: <c:out value="${rmt.name}" /></h3>
				<p>SKU: <c:out value="${rmt.sku}" /></p>
				<p>Price: <c:out value="${rmt.price}" /></p>
				<p>Description: <c:out value="${rmt.description}" /></p>
				<ul class="nav">
					<li><a href="/itemtype/edit?id=<c:out value="${rmt.id}" />">Edit</a></li>
					<li><a href="/itemtype/delete?id=<c:out value="${rmt.id}" />">Delete</a></li>
				</ul>
			</li>
		</c:forEach>
	</ul>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
