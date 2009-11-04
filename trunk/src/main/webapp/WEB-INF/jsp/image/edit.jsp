<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">

	<h3>Edit Image</h3>
	<form:form commandName="image" action="/image/save" cssClass="std-form" enctype="multipart/form-data">
		<fieldset>
			<form:hidden path="id" />
			<ol>
				<li>
					<form:label path="name">Name:</form:label>
					<form:input path="name" />
					<form:errors cssClass="error" path="name" />
				</li>
				<li>
					<form:label path="data">File:</form:label>
					<input type="file" id="data" name="data"/>
					<form:errors cssClass="error" path="data" />
				</li>
			</ol>

			<input type="submit" value="Save" />
		</fieldset>
	</form:form>
</c:set>

<c:set var="sidebar" scope="request">
<jsp:include page="/WEB-INF/jsp/image/sidebar.jsp"/>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
