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
                <h3>All Rooms</h3>
                <p><c:out value="${fn:length(rooms)}" /> rooms in database. </p>
                <ul>
                    <c:forEach items="${rooms}" varStatus="idx"><%-- TODO: use spring:bind in the loop? --%>
                        <li><c:out value="${rooms[idx.index].number}" />: <c:out value="${rooms[idx.index].description}" /> <a href="/room/edit?id=<c:out value="${rooms[idx.index].id}" />">Edit</a> <a href="/room/delete?id=<c:out value="${rooms[idx.index].id}" />">Delete</a></li>
                    </c:forEach>
                </ul>
            </div>
            <div id="sidebar">
                <ul>
                    <li>View Rooms</li>
                    <li><a href="/room/edit">New Room</a></li>
                </ul>
            </div>
            <jsp:include flush="true" page="/WEB-INF/jsp/navigation/footer.jsp"/>
        </div>
    </body>
</html>