function clickLoginSubmit(cp) {
	$.ajax({
		url : cp + '/api/login.html',
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
		url : cp + '/api/register.html',
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