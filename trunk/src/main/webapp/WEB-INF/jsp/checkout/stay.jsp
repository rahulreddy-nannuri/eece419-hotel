<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">


	<h3>Select reservation</h3>
	<form:form commandName="checkout" cssClass="std-form">
		<fieldset>
			<legend/>
			<form:errors cssClass="error" path="*" />
			<ol>
				<c:forEach var="stayRecords" items="${checkout.stayRecords}">
					<li>
						${stayRecords.description}<form:radiobutton path="selectedStayRecord" value="${stayRecords.id}" />
					</li>
				</c:forEach>
			</ol>
			<input type="submit" value="Back" name="_target0"/>
			<c:if test="${fn:length(checkout.stayRecords)>0}">
				<input type="submit" value="Check Out" name="_finish"/>
			</c:if>
		</fieldset>
	</form:form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/checkin/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
