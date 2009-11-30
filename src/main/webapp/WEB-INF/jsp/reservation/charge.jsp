<%@include file="/WEB-INF/jsp/include.jsp"%>

<c:set var="js" scope="request">
	$(function () {
		$("#chargeable-items li a").click(function () {
			$("#itemId").val(this.href.substr(this.href.lastIndexOf("#") + 1));
			$("#chargeableItem").submit();
		});
	});
</c:set>

<c:set var="main" scope="request">
	<h3>Add Standard Chargeable Item</h3>
	<ul id="chargeable-items" class="room-list">
		<c:forEach items="${itemTypes}" var="it" varStatus="idx">
			<li class="row${idx.index % 2}">
				<p><a href="#${it.id}">${it.name}</a> SKU: ${it.sku} Price: <fmt:formatNumber type="currency" value="${it.price}" /></p>
			</li>
		</c:forEach>
		<li><!-- total hack, I just needed a spacer --></li>
	</ul>
	<h3>Add Custom Chargeable Item</h3>
	<form:form commandName="chargeableItem" action="/reservation/charge" cssClass="std-form">
		<fieldset>
			<input name="itemId" id="itemId" type="hidden" />
			<input name="reservationId" id="reservationId" value="${reservationId}" type="hidden" />
			<ol>
				<li>
					<form:label path="name">Name:</form:label>
					<form:input path="name" />
					<form:errors cssClass="error" path="name" />
				</li>
				<li>
					<form:label path="description">Description:</form:label>
					<form:input path="description" />
					<form:errors cssClass="error" path="description" />
				</li>
				<li>
					<form:label path="price">Price:</form:label>
					<form:input path="price" />
					<form:errors cssClass="error" path="price" />
				</li>
				<li>
					<form:label path="sku">SKU:</form:label>
					<form:input path="sku" />
					<form:errors cssClass="error" path="sku" />
				</li>
			</ol>
			
			<input type="submit"  value="Save" />
		</fieldset>
	</form:form>
</c:set>


<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/room/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
