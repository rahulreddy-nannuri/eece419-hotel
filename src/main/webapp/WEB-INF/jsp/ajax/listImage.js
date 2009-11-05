/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


$(function() {
	$("#image-select").dialog({
		bgiframe: true,
		autoOpen: false,
		height: 500,
		width: 640,
		modal: true,
		buttons: {
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
	$(data).find("image").each(function(){
		var imageId=$(this).find("id").text();
		var name=$("<p/>").append($(this).find("name").text());
		var url=$(this).find("url").text();
		var img=$("<img class='thumbnail' />").attr('src', url);
		var imgDiv=$('<div class="image"></>').append(img).append("<br/>").append(name).attr('image-id',imageId);
		$("#image-list").append(imgDiv);

		imgDiv.click(function(){
			//alert("you clicked me!");
			var imageId=$(this).attr("image-id");
			if(imageId == null){
				$("#error-msg").html("Select an image")
				return;
			}
			$("#imageId").attr("value", imageId);
			$("#room-type-image").attr("src", "/image/view?id="+imageId);
			$("#add-image").html("Change image");
			$('#image-select').dialog('close');
		});
		
	});
}

$.get("/ajax/listImage", null, callback , "xml");