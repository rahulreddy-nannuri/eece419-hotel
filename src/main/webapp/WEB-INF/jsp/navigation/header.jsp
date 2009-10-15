<%@ include file="/WEB-INF/jsp/include.jsp" %> 
<html>
<body>
<p>user: 
<c:choose><c:when test="${currentuser != null}">
<c:out value="${currentuser.username}"/> <a href="/j_spring_security_logout">sign out</a>
</c:when><c:otherwise>
anonymous <%-- TODO: signup --%><a href="/spring_security_login">sign in</a>
</c:otherwise></c:choose>
</p>
<p>places: <a href="/user/">Users</a> <a href="/room/">Rooms</a></p>