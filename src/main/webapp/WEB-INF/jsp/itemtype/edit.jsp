<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">

	<h3>Edit Item Type Details</h3>
	<form:form commandName="itemType" action="/itemtype/save" cssClass="std-form">
		<fieldset>
			<form:hidden path="id" />
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
			<input type="submit" value="Save" />
		</fieldset>
	</form:form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/room/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
