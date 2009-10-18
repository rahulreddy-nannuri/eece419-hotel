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
            <jsp:include flush="true" page="/WEB-INF/jsp/navigation/header.jsp"/>
            <div id="main">
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
            </div>
            <div id="sidebar">
                <ul>
                    <li>Login</li>
                    <li><a href="/user/register">Register</a></li>
                </ul>
            </div>
            <jsp:include flush="true" page="/WEB-INF/jsp/navigation/footer.jsp"/>
        </div>
    </body>
</html>
