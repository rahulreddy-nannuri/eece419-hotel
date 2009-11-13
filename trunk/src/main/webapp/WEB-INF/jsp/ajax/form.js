function submitForm(form,name,value){
	$('#'+form).find('#'+name).attr("value", value);
	$('#'+form).append($("<input type='hidden' name='_finish' value='_finish' />"));
	$('#'+form).submit();
}

