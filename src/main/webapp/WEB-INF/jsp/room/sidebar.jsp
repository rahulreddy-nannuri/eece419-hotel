<%@include file="/WEB-INF/jsp/include.jsp" %>
<c:if test="${isAdmin}">
<%-- sidebar contains the second navigation bar --%>
<h3>Manage Rooms</h3>
<ul>
	<li><a href="/room/list">View Rooms</a></li>
    <li><a href="/room/edit">Create Room</a></li>
    <li class="separator"></li>
    <li><a href="/roomtype/list">View Room Types</a></li>
    <li><a href="/roomtype/edit">Create Room Type</a></li>
    <li class="separator"></li>
    <li><a href="/itemtype/list">View Chargeable Items</a></li>
    <li><a href="/itemtype/edit">Create Chargeable Item</a></li>
    <li class="separator"></li>
    <li><a href="/image/list">View Room Images</a></li>
    <li><a href="/image/edit">Upload New Image</a></li>
</ul>
</c:if>