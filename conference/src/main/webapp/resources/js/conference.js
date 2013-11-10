function load(cp){
	contextPath = cp;
	rowsPerPage = Number(document.getElementById('itemsCount').value);
	fillConferenceTable();
}
function clickLoginSubmit() {
	$.ajax({
		url : contextPath + '/api/login',
		data : $('#loginForm').serialize(),
		type : 'post',
		contentType : 'application/x-www-form-urlencoded',
		processData : false,
		async : false,
		success : function(res) {
			if(res == "true")
				location.href = contextPath;
			else
				document.getElementById('loginWarning').innerHTML = 'Wrong user name or password';
		}
	});
}

function clickRegisterSubmit() {
	var spans = document.getElementById('registerForm').getElementsByTagName('span');
	for(i = 0; i < spans.length; i++){
		if(spans[i].className =='error' || spans[i].className =='fielderror')
			spans[i].innerHTML = '';
	}
	$.ajax({
		url : contextPath + '/api/register',
		data : $('#registerForm').serialize(),
		type : 'post',
		contentType : 'application/x-www-form-urlencoded',
		processData : false,
		async : false,
		success : function(res) {
			if(res.status == "SUCCESS")
				location.href = contextPath;
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

function getCitiesList(){
	$('[name="town"]').prop('disabled', true);
	$.ajax({
		url : contextPath + '/api/citieslist',
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

function fillConferenceTable(){
	var table = document.getElementById("conferenceList");
	for(i = 0; i < rowsPerPage; i++){
		var row = table.insertRow(table.rows.length);
		for(j = 0; j < 6; j++){
			var cell = row.insertCell(j);
			cell.innerHTML = "";
		}
	}
}