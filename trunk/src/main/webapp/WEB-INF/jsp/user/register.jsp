<%@include file="/WEB-INF/jsp/include.jsp" %>


<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h3>Edit User</h3>
    <form action="j_spring_security_check">
        <label for="j_username">Username</label>
        <input type="text" name="j_username" id="j_username"/>
        <br/>
        <label for="j_password">Password</label>
        <input type="password" name="j_password" id="j_password"/>
        <br/>
        <input type='checkbox' name='_spring_security_remember_me'/> Remember me
        <br/>
        <input type="submit" value="Login"/>
    </form>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <li><a href="/user/login">Login</a></li>
    <li>Register</li>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>


