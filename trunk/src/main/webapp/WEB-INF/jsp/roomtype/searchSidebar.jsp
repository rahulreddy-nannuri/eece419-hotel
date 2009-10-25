<h3>Search for a room</h3>
<form action="/search">
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
                </select>
            </td>
        </tr>

        <tr>
            <td colspan="2"  style="text-align:center"><input type="submit" value="search"/></td>
        </tr>
    </table>
</form>