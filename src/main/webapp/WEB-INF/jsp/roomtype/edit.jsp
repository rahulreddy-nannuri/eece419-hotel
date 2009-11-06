<%@include file="/WEB-INF/jsp/include.jsp"%>

<c:set var="js" scope="request">
	<jsp:include page="/WEB-INF/jsp/ajax/listImage.js" />
</c:set>

<%-- main contains the main content --%>
<c:set var="main" scope="request">

	<h3>Edit Room Type Details</h3>
	<form:form commandName="roomType" action="/roomtype/save" cssClass="std-form">
		<fieldset>
			<form:hidden path="id" />
			<form:hidden path="imageId"/>
			<ol>
				<li>
					<form:label path="name">Name:</form:label>
					<form:input path="name" />
					<form:errors cssClass="error" path="name" />
				</li>
				<li>
					<form:label path="description">Description:</form:label>
					<form:input path="description" />
					<form:errors cssClass="error" path="description" />
				</li>
				<li>
					<form:label path="dailyRate">Daily Rate:</form:label>
					<form:input path="dailyRate" />
					<form:errors cssClass="error" path="dailyRate" />
				</li>
				<li>
					<form:label path="maxOccupancy">Max Occupancy</form:label>
					<form:input path="maxOccupancy" />
					<form:errors cssClass="error" path="maxOccupancy" />
				</li>
				<li>
					<form:label path="attributesText">Attributes (one per line)</form:label>
					<form:textarea path="attributesText" />
					<form:errors cssClass="error" path="attributesText" />
				</li>
				<li>
					<c:choose>
						<c:when test="${roomType.imageId > 0}">
							<img id="room-type-image" class="thumbnail" src="/image/view?id=${roomType.imageId}" alt="roomtype image"/>
							<a id="add-image" href="#">Change image</a>
						</c:when>
						<c:otherwise>
							<img id="room-type-image" class="thumbnail" />
							<a id="add-image" href="#">Add Image</a>
						</c:otherwise>
					</c:choose>

				</li>
			</ol>

			<input type="submit"  value="Save" />
		</fieldset>
	</form:form>


	<div id="image-select">
		<p>Select an image below</p>
		<p id="error-msg" class="error" />
		<div id="image-list"></div>
	</div>
</c:set>

<%-- use the default room sidebar --%>
<c:set var="sidebar" scope="request">
	<jsp:include page="/WEB-INF/jsp/room/sidebar.jsp" />
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp" />
