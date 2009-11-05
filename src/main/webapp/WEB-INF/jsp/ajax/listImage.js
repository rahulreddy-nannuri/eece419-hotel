/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


$(function() {
	$("#image-select").dialog({
		bgiframe: true,
		autoOpen: false,
		height: 500,
		width: 500,
		modal: true,
		buttons: {
			'Add': function() {
				var imageId=$("input:radio[@name=imageId]:checked").attr("value");
				$("#imageId").attr("value", imageId);
				$("#roomTypeImage").attr("src", "/image/view?id="+imageId);
				$("#add-image").hide();
				$(this).dialog('close');
			},
			Cancel: function() {
				$(this).dialog('close');
			}
		},
		close: function() {	
		}
	});

	$('#add-image').click(function() {
		$('#image-select').dialog('open');
	});
});

function callback(data){
	var list=$('<ol></ol>').appendTo($("#image-select"));
	$(data).find("image").each(function(){
		var imageId=$(this).find("id").text();
		var name=$(this).find("name").text();
		var url=$(this).find("url").text();
		var radio=$("<input type='radio' name='imageId' />").attr('value', imageId);
		var img=$("<img height='50' width='50' />").attr('src', url);
		var listItem=$('<li></li>').append(radio).append(name).append(img);
		list.append(listItem);
	});
	
}

$.get("/ajax/listImage", null, callback , "xml");