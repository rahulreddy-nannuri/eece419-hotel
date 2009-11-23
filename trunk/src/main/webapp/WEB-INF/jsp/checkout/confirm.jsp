<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">

	<h3>Checkout Successful</h3>
	
	<p>Your credit card has been billed <fmt:formatNumber type="currency" value="${bill.total}" />.</p>
	<ul class="room-list">
		<c:forEach items="${bill.allBillables}" var="item" varStatus="idx">
			<li class="row${idx.index % 2}">
				<h3>${item.name}</h3>
				<p>${item.description}</p>
				<c:if test="${item.price > 0}">
					<p>Price: <fmt:formatNumber type="currency" value="${item.price}" /></p>
				</c:if>
			</li>		
		</c:forEach>
	</ul>
	

</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
		<jsp:include page="/WEB-INF/jsp/checkin/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
