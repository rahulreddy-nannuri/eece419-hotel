<%@include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>X-Reserve</title>
        <link rel="stylesheet" type="text/css" href="/static/css/site.css" />
    </head>
    <body>
        <div id="wrap">
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
            <div id="main">
                ${main}
            </div>
            <div id="sidebar">
                ${sidebar}
            </div>
            <div id="footer">
                <p>&copy;2009 EECE419 Pod1</p>
            </div>
        </div>
    </body>
</html>