<%-- sidebar contains the second navigation bar --%>
<%@include file="/WEB-INF/jsp/include.jsp" %>

<h3>Reports</h3>
<script type="text/javascript">
/* <![CDATA[ */
$(function() {
	$("#reportyearpicker").change(function() {
		window.location.href = "/chart/view?year=" + $(this).val();
	});
});
/* ]]> */
</script>
<table>
	<tr>
		<td>Year:</td>
		<td><select id="reportyearpicker" onchange="OnChange(this.options[this.selectedIndex].value);">
			<option value="">Select a Year</option>
			<option value="2009">2009</option>
			<option value="2008">2008</option>
			<option value="2007">2007</option>
			<option value="2006">2006</option>
			<option value="2005">2005</option>
			<option value="2004">2004</option>
		</select></td>
	</tr>
</table>
