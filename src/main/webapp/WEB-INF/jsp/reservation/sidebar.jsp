<%@include file="/WEB-INF/jsp/include.jsp" %>
<h3>Reserve a Room</h3>
<ol>
	<c:forEach items="Login,Payment Details,Confirmation,Finish" var="item" varStatus="status">
		<c:choose>
			<c:when test="${status.index == state}">
				<c:set var="liClass" value="current" />
			</c:when>
			<c:otherwise>
				<c:set var="liClass" value="" />
			</c:otherwise>
		</c:choose>
		<li class="<c:out value="${liClass}" />"><c:out value="${item}" /></li>
	</c:forEach>
</ol>