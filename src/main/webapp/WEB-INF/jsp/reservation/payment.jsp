<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>Payment Details</h2>
	<form action="reserve" class="std-form payment" method="post">
        <fieldset>
        <ul>
            <li>
	            <label for="cardNumber">Card Number:</label>
	            <input type="text" name="cardNumber" id="cardNumber"/>
          	</li>
          	<li>
	            <label for="expiryMonth">Expiry Date:</label>
	            <select name="expiryMonth" id="expiryMonth">
	            	<option value="1">01 - January</option>
	            	<option value="2">02 - February</option>
	            	<option value="3">03 - March</option>
	            	<option value="4">04 - April</option>
	            	<option value="5">05 - May</option>
	            	<option value="6">06 - June</option>
	            	<option value="7">07 - July</option>
	            	<option value="8">08 - August</option>
	            	<option value="9">09 - September</option>
	            	<option value="10">10 - October</option>
	            	<option value="11">11 - November</option>
	            	<option value="12">12 - December</option>
	            </select>
	            <select name="expiryYear" id="expiryYear">
	            	<c:forEach begin="2009" end="2020" var="year">
	            		<option><c:out value="${year}" /></option>
	            	</c:forEach>
	            </select>	            
	        </li>
	        <li>
	        	<label for="securityCode">Security Code:</label>
	            <input type="text" name="securityCode" id="securityCode"/>
	        </li>
	        <li>
	        	<label for="cardType">Card Type:</label>
	        	<select name="cardType" id="cardType">
	        		<option value="visa">Visa</option>
	        		<option value="mastercard">Mastercard</option>
	        		<option value="amex">American Express</option>
	        	</select>
	        </li>
        </ul>
        <input type="submit" value="Reserve" name="_target2" />
        <input type="submit" value="Cancel" name="_cancel" />
        </fieldset>
    </form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request"> 
	<jsp:include page="/WEB-INF/jsp/reservation/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
