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
                <h3>Edit Room Details</h3>
                <form:form commandName="room" action="/room/save">
                    <form:hidden path="id"/>
                    <form:label path="number">Room Number:</form:label><form:input path="number"/>
                    <form:label path="description">Description:</form:label><form:input path="description"/>
                    <input type="submit" value="Save"/>
                </form:form>
            </div>
            <div id="sidebar">
                <ul>
                    <li><a href="/room/list">View Rooms</a></li>
                    <li>New Room</li>
                </ul>
            </div>
            <jsp:include flush="true" page="/WEB-INF/jsp/navigation/footer.jsp"/>
        </div>
    </body>
</html>