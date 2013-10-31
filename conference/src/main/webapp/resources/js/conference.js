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
	$.ajax({
		url : cp + '/api/register',
		data : $('#registerForm').serialize(),
		type : 'post',
		contentType : 'application/x-www-form-urlencoded',
		processData : false,
		async : false,
		success : function(res) {
			if(res == "true")
				location.href = cp;
			else
				document.getElementById('registerWarning').innerHTML = 'Please correct data';
		}
	});
}

function resetModalWindow(e, id){
	document.getElementById(id).innerHTML = '';
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