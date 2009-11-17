<%-- sidebar contains the second navigation bar --%>
<%@include file="/WEB-INF/jsp/include.jsp" %>

<h3>Reports</h3>
<script type="text/javascript">
/* <![CDATA[ */
function OnChange(value){
    window.location.href = "/chart/view?year=" + value;
    
    return true;

	}
/* ]]> */
</script>

	<h4>Year : 
		<select name="year" onchange="OnChange(this.options[this.selectedIndex].value);">
			<option value=""></option>
			<option value="2009">2009</option>
			<option value="2008">2008</option>
			<option value="2007">2007</option>
			<option value="2006">2006</option>
			<option value="2005">2005</option>
			<option value="2004">2004</option>
		</select>
	</h4>
