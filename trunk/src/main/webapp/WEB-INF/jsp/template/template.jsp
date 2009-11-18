<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title>X-Reserve</title>
<link rel="stylesheet" type="text/css" href="/static/css/redmond/jquery-ui-1.7.2.custom.css" />
<link rel="stylesheet" type="text/css" href="/static/css/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="/static/css/site.css" />
<script type="text/javascript" src="/static/js/jquery-1.3.2.js"></script>
<script type="text/javascript" src="/static/js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="/static/js/jquery.autocomplete.js"></script>
<c:if test="${js != null}"><script type="text/javascript">
${js}
</script></c:if>
</head>
<body>
<div id="wrap">
	<div id="header">
		<h1>X-Reserve</h1>
		<ul id="nav">
			<li><a href="/">Home</a></li>
			<c:choose>
				<c:when test="${currentuser == null}">
					<%-- anonymous customer --%>
				</c:when>
				<c:when test="${isAdmin}">
					<%-- admin --%>
					<li><a href="/room">Rooms</a></li>
					<li><a href="/user">Users</a></li>
					<li><a href="/chart/index">Reports</a></li>
					<li><a href="/reservation">Reservations</a></li>
				</c:when>
				<c:when test="${isStaff}">
					<%-- staff --%>
					<li><a href="/reservation">Reservations</a></li>
				</c:when>
				<c:otherwise>
					<%-- register customer --%>
					<li><a href="/reservation/view?userId=${currentuser.id}">Bookings</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
		<ul id="nav2">
			<c:choose>
				<c:when test="${currentuser != null}">
					<li>${currentuser.username}</li>
					<li><a href="/user/edit?id=${currentuser.id}">Edit Profile</a></li>
					<li><a href="/j_spring_security_logout">Logout</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="/user/login">Login</a></li>
					<li><a href="/user/registerform">Register</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
		<br style="clear: both;" />
	</div>
	<div id="sidebar">${sidebar}</div>
	<div id="main">${main}</div>
	<p id="footer">&copy;2009 EECE419 Pod1</p>
</div>
</body>
</html>
