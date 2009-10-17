<%@ include file="/WEB-INF/jsp/include.jsp" %> 
<html>
<head>
<link rel="stylesheet" href="/static/css/site.css" />
</head>
<body>
<ul class="header">
<li>user: 
<c:choose><c:when test="${currentuser != null}">
<c:out value="${currentuser.username}"/> <a href="/j_spring_security_logout">sign out</a>
</c:when><c:otherwise>
anonymous <%-- TODO: signup --%><a href="/spring_security_login">sign in</a>
</c:otherwise></c:choose>
</li>
<li>places: <a href="/user/">Users</a> <a href="/room/">Rooms</a></li>
</ul>