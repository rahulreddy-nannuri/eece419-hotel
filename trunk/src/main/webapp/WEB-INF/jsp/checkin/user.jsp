<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">


	<h3>Enter username</h3>
	<form:form commandName="checkin" cssClass="std-form">
		<fieldset>
			<ol>
				<li>
					<form:label path="username">Username:</form:label>
					<form:input path="username" />
					<form:errors cssClass="error" path="username" />
				</li>
				
			</ol>

			<input type="submit" value="Next" name="_target1"/>
		</fieldset>
	</form:form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request"> 
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
