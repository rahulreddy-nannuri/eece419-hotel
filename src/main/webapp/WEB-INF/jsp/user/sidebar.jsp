<%@include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
	<c:when test="${currentuser == null}">
		<ul>
	        <li><a href="/user/login">Login</a></li>
	        <li><a href="/user/registerform">Register</a></li>
	    </ul>
	</c:when>
	<c:otherwise>
		<h3>Manage Account</h3>
		<ul>
			<li><a href="/user/edit?id=${currentuser.id}">Edit Profile</a></li>
		</ul>
		<c:if test="${isAdmin}">
			<h3>Manage Users</h3>
			<ul>
				<li><a href="/user/list">View Users</a></li>
				<li><a href="/user/edit">Create User</a></li>
			</ul>
		</c:if>
	</c:otherwise>
</c:choose>