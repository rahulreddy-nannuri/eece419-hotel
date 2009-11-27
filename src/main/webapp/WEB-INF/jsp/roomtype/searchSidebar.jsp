<%@include file="/WEB-INF/jsp/include.jsp" %>
<h3>Search for a Room</h3>
<script type="text/javascript">
/* <![CDATA[ */
$(function() {
	$("#checkIn").datepicker();
	$("#checkOut").datepicker();
	$("#price-slider").slider({
	    range: true,
	    min: 0,
	    max: 1000,
	    values: [${search.minPrice}, ${search.maxPrice}],
	    slide: function(event, ui) {
	            $("#price-amount").val('$' + ui.values[0] + ' - $' + ui.values[1]);
	            $("#minPrice").val(ui.values[0]);
	            $("#maxPrice").val(ui.values[1]);
	    }
	});
	$("#price-amount").val('$' + $("#price-slider").slider("values", 0) + ' - $' + $("#price-slider").slider("values", 1));

	function dateStr(date) {
		var ret = "";
		var m = date.getMonth() + 1;
		if (m < 10) ret += "0";
		ret += m + "/";
		var d = date.getDate();
		if (d < 10) ret += "0";
		ret += d + "/" + date.getFullYear();
		return ret;
	}

	function unDateStr(str) {
		var parts = str.split('/');
		return new Date(parts[2], parts[0] - 1, parts[1]);
	}
	
	if ($("#checkIn").val() == "") {
		$("#checkIn").val(dateStr(new Date()));
	}
	if ($("#checkOut").val() == "") {
		var nd = unDateStr($("#checkIn").val());
		$("#checkOut").val(dateStr(new Date(nd.getTime() + 1000*60*60*24*5)));
	}

	// attribute picker
	var allAttributes = [<c:forEach items="${allAttributes}" var="att">"${att}",</c:forEach>];
	
	$("#attributes").autocomplete(allAttributes, {
		autoFill: true,
		multiple: true
	});

	var attributeDialog = "<b>Useful Attributes:</b><br/><i>" + allAttributes.join(", ") + "</i>";
	$("label[for='attributes']").css("cursor", "help").qtip({
		content: attributeDialog
	});
});
/* ]]> */
</script>
<form:form action="/roomtype/search" method="get" commandName="search">
	<form:hidden path="minPrice"/>
	<form:hidden path="maxPrice"/>
    <table>
        <tr>
            <td width="100px">
                <label for="amount">Price range:</label>
            </td>
            <td>
                <div id="price-slider"></div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input type="text" id="price-amount" disabled="disabled" style="border:none;background-color:white"/></td>
        </tr>
        <tr>
            <td><form:label path="checkIn">Check in:</form:label></td>
            <td><form:input path="checkIn" /></td>
        </tr>
        <tr>
        	<td></td>
      		<td><form:errors cssClass="error" path="checkIn" /></td>
        </tr>
        <tr>
            <td><form:label path="checkOut">Check out:</form:label></td>
            <td><form:input path="checkOut" /></td>
        </tr>
        <tr>
        	<td></td>
            <td><form:errors cssClass="error" path="checkOut" /></td>
        </tr>
        <tr>
            <td><form:label path="occupancy">Guests:</form:label></td>
            <td><form:select path="occupancy">
                    <form:option value="1" />
                    <form:option value="2" />
                    <form:option value="3" />
                    <form:option value="4" />
                    <form:option value="5" />
                    <form:option value="6" />
                    <form:option value="7" />
                    <form:option value="8" />
                </form:select>
            </td>
        </tr>
        <tr>
            <td><form:label path="attributes">Attributes (comma-separated):</form:label></td>
            <td><form:textarea path="attributes" /></td>
        </tr>
        <tr>
            <td colspan="2"  style="text-align:center"><input type="submit" value="search"/></td>
        </tr>
    </table>
</form:form>