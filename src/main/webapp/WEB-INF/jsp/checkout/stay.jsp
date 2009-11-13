<%@include file="/WEB-INF/jsp/include.jsp"%>
<c:set var="js" scope="request">
	<jsp:include page="/WEB-INF/jsp/ajax/form.js" />
</c:set>
<%-- main contains the main content --%>
<c:set var="main" scope="request">


	<h3>Select Stay Record</h3>
	<form:form id="stayRecord" commandName="checkout" cssClass="std-form">
		<fieldset>
			<form:errors cssClass="error" path="*" />
			<form:hidden path="selectedStayRecord" />
			 <ul class="room-list">
				 <c:forEach var="stayRecord" items="${checkout.stayRecords}" varStatus="idx">
					<li class="row${idx.index % 2}">
						<a onClick="submitForm('stayRecord','selectedStayRecord','${stayRecord.id}')">${stayRecord.description}</a>
		
					</li>
				</c:forEach>
			 </ul>
			<input type="submit" value="Back" name="_target0"/>
		</fieldset>
	</form:form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/checkin/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
