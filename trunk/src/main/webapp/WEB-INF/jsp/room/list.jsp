<%@ include file="/WEB-INF/jsp/navigation/header.jsp" %> 
<h2>Room :: List</h2>
<p><c:out value="${fn:length(rooms)}" /> rooms in database. <a href="/room/edit">Add Room</a></p>
<ul>
<c:forEach items="${rooms}" varStatus="idx"><%-- TODO: use spring:bind in the loop? --%>
<li><c:out value="${rooms[idx.index].number}" />: <c:out value="${rooms[idx.index].description}" /> <a href="/room/edit?id=<c:out value="${rooms[idx.index].id}" />">Edit</a> <a href="/room/delete?id=<c:out value="${rooms[idx.index].id}" />">Delete</a></li>
</c:forEach>
</ul>
<%@ include file="/WEB-INF/jsp/navigation/footer.jsp" %>