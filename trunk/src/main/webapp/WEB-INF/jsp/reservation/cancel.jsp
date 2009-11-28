<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>Reservation Cancelled</h2>
	<p>Your credit card has been billed <fmt:formatNumber type="currency" value="${bill.total}" />.</p>
	<ul class="room-list">
		<c:forEach items="${bill.allBillables}" var="item" varStatus="idx">
			<li class="row${idx.index % 2}">
				<h3>${item.name}</h3>
				<p>${item.description}</p>
				<c:if test="${item.price > 0}">
					<p>Cancellation Fee: <fmt:formatNumber type="currency" value="${item.price}" /></p>
				</c:if>
			</li>		
		</c:forEach>
	</ul>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request"> 
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
