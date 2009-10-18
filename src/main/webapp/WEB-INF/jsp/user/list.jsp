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
                <h3>View Users</h3>
                <ul>
                    <c:forEach items="${users}" varStatus="idx"><%-- TODO: use spring:bind in the loop? --%>
                        <li><c:out value="${users[idx.index].username}" /> <a href="/user/edit?id=<c:out value="${users[idx.index].id}" />">Edit</a> <a href="/user/delete?id=<c:out value="${users[idx.index].id}" />">Delete</a></li>
                    </c:forEach>
                </ul>
            </div>
            <div id="sidebar">
                <ul>
                    <li>View Users</li>
                    <li><a href="/user/edit">New User</a></li>
                </ul>
            </div>
            <jsp:include flush="true" page="/WEB-INF/jsp/navigation/footer.jsp"/>
        </div>
    </body>
</html>