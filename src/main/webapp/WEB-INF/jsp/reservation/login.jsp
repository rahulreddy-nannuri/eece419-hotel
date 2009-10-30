<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>Login</h2>
    <form action="j_spring_security_check" class="std-form">
        <fieldset>
        <ul>
            <c:if test="${login_error == 1}">
                <li><p class="error">login failed</p></li>
            </c:if>
            <li>
	            <label for="j_username">Username:</label>
	            <input type="text" name="j_username" id="j_username"/>
          	</li>
          	<li>
	            <label for="j_password">Password:</label>
	            <input type="password" name="j_password" id="j_password"/>
	        </li>
            <li class="checkbox">
            	<input type='checkbox' name='_spring_security_remember_me'/>
            	<label for="_spring_security_remember_me">Remember me</label>
            </li>
        </ul>
        <input type="hidden" name="spring-security-redirect" value="/reserve" />
        <input type="submit" value="Login"/>
        </fieldset>
    </form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request"> 
	<jsp:include page="/WEB-INF/jsp/reservation/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
