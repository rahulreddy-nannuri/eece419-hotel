<%@include file="/WEB-INF/jsp/include.jsp"%>

<%-- main contains the main content --%>
<c:set var="main" scope="request">
	<h2>Login</h2>
    <form action="reserve" class="std-form" method="post">
        <fieldset>
        <ul>
            <c:if test="${errors != null}">
                <li><p class="error">login failed</p></li>
            </c:if>
            <li>
	            <label for="username">Username:</label>
	            <input type="text" name="username" id="username"/>
          	</li>
          	<li>
	            <label for="password">Password:</label>
	            <input type="password" name="password" id="password"/>
	        </li>
        </ul>
        <input type="submit" value="Login" name="_target1" />
        <input type="submit" value="Cancel" name="_cancel" />
        </fieldset>
    </form>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request"> 
	<jsp:include page="/WEB-INF/jsp/reservation/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
