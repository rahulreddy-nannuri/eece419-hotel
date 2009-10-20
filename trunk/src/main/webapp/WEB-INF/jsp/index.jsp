<%@include file="/WEB-INF/jsp/include.jsp" %>

<c:set var="js" scope="request">
    $(function() {
		$("#checkindate").datepicker();
    $("#checkoutdate").datepicker();
	});

</c:set>
<%-- main contains the main content --%>
<c:set var="main" scope="request">
    <h3>Welcome</h3>
    <p>Thank you for choosing X-Reserve and we hope you will enjoy your staying.</p>
</c:set>

<%-- sidebar contains the second navigation bar --%>
<c:set var="sidebar" scope="request">
    <form action="/search">
        <table>
            <tr>
                <td><label for="checkindate">Check in:</label></td>
                <td><input type="text" name="checkindate" id="checkindate" /></td>
            </tr>
            <tr>
                <td><label for="checkindate">Check out:</label></td>
                <td><input type="text" name="checkoutdate" id="checkoutdate" /></td>
            </tr>
            <tr>
                <td><label for="adults">Adults</label></td>
                <td><select name="adults">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                    </select></td>
            </tr>
            <tr>
                <td rowspan="2"><input type="submit" value="search"/></td>
            </tr>
        </table>
       
        
    </form>
</c:set>

<jsp:include page="/WEB-INF/jsp/template/template.jsp"/>


