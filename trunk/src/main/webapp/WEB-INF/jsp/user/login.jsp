<%@include file="/WEB-INF/jsp/include.jsp" %>

<%-- main contains the main content --%>
<c:set var="main" scope="request">

    <form action="j_spring_security_check" class="std-form">
        <fieldset>
            <legend>Login</legend>
            <c:if test="${login_error == 1}">
                <p class="error">login failed</p>
            </c:if>
            <label for="j_username">Username</label>
            <input type="text" name="j_username" id="j_username"/>
            <br/>
            <label for="j_password">Password</label>
            <input type="password" name="j_password" id="j_password"/>
            <br/>
            <input type='checkbox' name='_spring_security_remember_me'/> Remember me
            <br/>
            <input type="submit" value="Login"/>
        </fieldset>
    </form>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <ul>
        <li>Login</li>
        <li><a href="/user/edit">Register</a></li>
    </ul>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>

