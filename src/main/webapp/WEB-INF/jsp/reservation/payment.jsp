<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>Payment Details</h2>
	<form:form action="reserve" commandName="reservation" cssClass="std-form payment" method="post">
        <fieldset>
        <ul>
            <li>
            	<form:label path="paymentInfo.cardNumber">Card Number:</form:label>
            	<form:input path="paymentInfo.cardNumber"/>
				<form:errors cssClass="error" path="paymentInfo.cardNumber" />
          	</li>
          	<li>
          		<form:label path="paymentInfo.expiryMonth">Expiry Date:</form:label>
          		<form:select path="paymentInfo.expiryMonth">
	            	<form:option value="1">01 - January</form:option>
	            	<form:option value="2">02 - February</form:option>
	            	<form:option value="3">03 - March</form:option>
	            	<form:option value="4">04 - April</form:option>
	            	<form:option value="5">05 - May</form:option>
	            	<form:option value="6">06 - June</form:option>
	            	<form:option value="7">07 - July</form:option>
	            	<form:option value="8">08 - August</form:option>
	            	<form:option value="9">09 - September</form:option>
	            	<form:option value="10">10 - October</form:option>
	            	<form:option value="11">11 - November</form:option>
	            	<form:option value="12">12 - December</form:option>
          		</form:select>
	            <form:select path="paymentInfo.expiryYear">
	            	<c:forEach begin="2009" end="2020" var="year">
	            		<form:option value="${year}" />
	            	</c:forEach>
	            </form:select>
	            <form:errors cssClass="error" path="paymentInfo.expiry*" />    
	        </li>
	        <li>
	        	<form:label path="paymentInfo.securityCode">Security Code:</form:label>
	            <form:input path="paymentInfo.securityCode" />
	            <form:errors cssClass="error" path="paymentInfo.securityCode" />
	        </li>
	        <li>
	        	<form:label path="paymentInfo.cardType">Card Type:</form:label>
	        	<form:select path="paymentInfo.cardType">
	        		<form:option value="Visa">Visa</form:option>
	        		<form:option value="MasterCard">MasterCard</form:option>
	        	</form:select>
	        	<form:errors cssClass="error" path="paymentInfo.cardType" />
	        </li>
        </ul>
        <input type="submit" value="Reserve" name="_target1" />
        <input type="submit" value="Cancel" name="_cancel" />
        </fieldset>
    </form:form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request"> 
	<jsp:include page="/WEB-INF/jsp/reservation/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
