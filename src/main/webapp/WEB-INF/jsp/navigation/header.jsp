<%@include file="/WEB-INF/jsp/include.jsp" %>
<div id="header">
    <h1>X-Reserver</h1>
</div>
<div id="nav">
    <ul>
        <li><a href="/">Home</a></li>
        <li><a href="/room">Manage Room</a></li>
        <li><a href="#">My Bookings</a></li>
    </ul>
</div>
<div id="user">
    <ul>
        <c:choose>
            <c:when test="${currentuser != null}">
                <li><a href="/user">Edit Profile</a></li>
                <li><a href="/j_spring_security_logout">Logout</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="/user/login">Login</a></li>
                <li><a href="/user/edit">Register</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>

