function clickLoginSubmit(cp) {
	$.ajax({
		url : cp + '/api/login',
		data : $('#loginForm').serialize(),
		type : 'post',
		contentType : 'application/x-www-form-urlencoded',
		processData : false,
		async : false,
		success : function(res) {
			if(res == "true")
				location.href = cp;
			else
				document.getElementById('loginWarning').innerHTML = 'Wrong user name or password';
		}
	});
}

function clickRegisterSubmit(cp) {
	var spans = document.getElementById('registerForm').getElementsByTagName('span');
	for(i = 0; i < spans.length; i++){
		if(spans[i].className =='error' || spans[i].className =='fielderror')
			spans[i].innerHTML = '';
	}
	$.ajax({
		url : cp + '/api/register',
		data : $('#registerForm').serialize(),
		type : 'post',
		contentType : 'application/x-www-form-urlencoded',
		processData : false,
		async : false,
		success : function(res) {
			if(res.status == "SUCCESS")
				location.href = cp;
			else if (res.status == "SERVERFAIL")
				document.getElementById('registerWarning').innerHTML = 'Please try again';
			else{
				for(i = 0; i < res.result.length; i++){
					document.getElementById(res.result[i].field + 'error').innerHTML = res.result[i].defaultMessage;
				}
			}
		}
	});
}

function resetModalWindow(e, cl){
	var spans = document.getElementById(cl).getElementsByTagName('span');
	for(i = 0; i < spans.length; i++){
		if(spans[i].className =='error' || spans[i].className =='fielderror')
			spans[i].innerHTML = '';
	}
	if(e.className =='closeModal'){
		location.href = new RegExp('.*#').exec(location.href) + 'close';
	}
}

function getCitiesList(cp){
	$('[name="town"]').prop('disabled', true);
	$.ajax({
		url : cp + '/api/citieslist',
		data : 'countrycode=' + $('[name="country"]').val(),
		type : 'get',
		contentType : 'text/xml;charset=UTF-8',
		async : false,
		success : function(res) {
			$('[name="town"]').empty();
			for (var i = 0; i < res.length; i++){
				$('[name="town"]').append('<option value="' + res[i].cityname + '">' + res[i].cityname + '</option>');
			}
			$('[name="town"]').prop('disabled', false);
		}
	});
}