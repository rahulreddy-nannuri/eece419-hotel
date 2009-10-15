<%@ include file="/WEB-INF/jsp/navigation/header.jsp" %> 
<h2>User :: List</h2>
<p><c:out value="${fn:length(users)}" /> users in database. <a href="/user/edit">Add User</a></p>
<ul>
<c:forEach items="${users}" varStatus="idx"><%-- TODO: use spring:bind in the loop? --%>
<li><c:out value="${users[idx.index].username}" /> <a href="/user/edit?id=<c:out value="${users[idx.index].id}" />">Edit</a> <a href="/user/delete?id=<c:out value="${users[idx.index].id}" />">Delete</a></li>
</c:forEach>
</ul>
<%@ include file="/WEB-INF/jsp/navigation/footer.jsp" %>