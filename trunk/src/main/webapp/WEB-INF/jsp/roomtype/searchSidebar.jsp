<%@include file="/WEB-INF/jsp/include.jsp" %>
<h3>Search for a room</h3>
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
});
/* ]]> */
</script>
<form:form action="/reserve" commandName="search">
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
            <td><form:label path="checkOut">Check out:</form:label></td>
            <td><form:input path="checkOut" /></td>
        </tr>
        <tr>
            <td><form:label path="occupancy">Adults</form:label></td>
            <td><form:select path="occupancy">
                    <form:option value="1" />
                    <form:option value="2" />
                    <form:option value="3" />
                    <form:option value="4" />
                </form:select>
            </td>
        </tr>
        <tr>
            <td colspan="2"  style="text-align:center"><input type="submit" value="search"/></td>
        </tr>
    </table>
</form:form>