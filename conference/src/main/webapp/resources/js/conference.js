function clickLoginSubmit() {
	$
			.ajax({
				url : contextPath + '/api/login',
				data : $('#loginForm').serialize(),
				type : 'post',
				contentType : 'application/x-www-form-urlencoded',
				processData : false,
				async : false,
				success : function(res) {
					if (res == "true")
						location.href = contextPath;
					else
						document.getElementById('loginWarning').innerHTML = 'Wrong user name or password';
				}
			});
}

function clickRegisterSubmit() {
	var spans = document.getElementById('registerForm').getElementsByTagName(
			'span');
	for (i = 0; i < spans.length; i++) {
		if (spans[i].className == 'error' || spans[i].className == 'fielderror')
			spans[i].innerHTML = '';
	}
	$
			.ajax({
				url : contextPath + '/api/register',
				data : $('#registerForm').serialize(),
				type : 'post',
				contentType : 'application/x-www-form-urlencoded',
				processData : false,
				async : false,
				success : function(res) {
					if (res.status == "SUCCESS")
						location.href = contextPath;
					else if (res.status == "SERVERFAIL")
						document.getElementById('registerWarning').innerHTML = 'Please try again';
					else {
						for (i = 0; i < res.result.length; i++) {
							document.getElementById(res.result[i].field
									+ 'error').innerHTML = res.result[i].defaultMessage;
						}
					}
				}
			});
}

function resetModalWindow(e, cl) {
	var spans = document.getElementById(cl).getElementsByTagName('span');
	for (i = 0; i < spans.length; i++) {
		if (spans[i].className == 'error' || spans[i].className == 'fielderror')
			spans[i].innerHTML = '';
	}
	if (e.className == 'closeModal') {
		location.href = new RegExp('.*#').exec(location.href) + 'close';
	}
}

function getCitiesList() {
	$('[name="town"]').prop('disabled', true);
	$.ajax({
		url : contextPath + '/api/citieslist',
		data : 'countryId=' + $('[name="country"]').val(),
		type : 'get',
		contentType : 'text/xml;charset=UTF-8',
		async : false,
		success : function(res) {
			$('[name="town"]').empty();
			for (var i = 0; i < res.length; i++) {
				$('[name="town"]').append(
						'<option value="' + res[i].cityId + '">'
								+ res[i].cityName + '</option>');
			}
			$('[name="town"]').prop('disabled', false);
		}
	});
}

function fillRows(id) {
	var table = document.getElementById(id);
	var rowsCount = table.rows.length;
	var selectItemElement = document.getElementById("itemsCount");
	var itemsCount = selectItemElement.options[selectItemElement.selectedIndex].value;
	if (rowsCount <= itemsCount) {
		for (i = rowsCount; i <= itemsCount; i++) {
			var row = table.insertRow(table.rows.length);
			for (j = 0; j < table.rows[0].cells.length; j++) {
				var cell = row.insertCell(j);
				cell.innerHTML = "&nbsp;";
			}
		}
	}
}

function changeItemsCount(selectItem) {
	location.href = contextPath + '/index.html?page=1&count='
			+ selectItem.options[selectItem.selectedIndex].value;
}

function bindDatePickerEvents(domSelector) {
	domSelector.find(".datePicker").datepicker({
		dateFormat : "yy-mm-dd",
		changeMonth : true,
		changeYear : true,
		showAnim : "show",
		minDate : 0
	});
}

function bindDateTimePickerEvents(domSelector) {
	domSelector.find(".datePicker").datetimepicker({
		dateFormat : "yy-mm-dd",
		changeMonth : true,
		changeYear : true,
		showAnim : "show",
		minDate : 0
	});
}

function filterChange() {
	var startDate = document.getElementById("filterStart").value;
	var endDate = document.getElementById("filterEnd").value;
	var categoryElem = document.getElementById("categories");
	var categoryId = categoryElem.options[categoryElem.selectedIndex].value;
	var itemsCountElem = document.getElementById("itemsCount");
	var itemsCount = itemsCountElem.options[itemsCountElem.selectedIndex].value;
	var newLocation = contextPath + '/index.html?page=1&count=' + itemsCount;
	if (startDate.match(/^\d{4}-(0[1-9]|1[1,2])-(0[1-9]|[1,2][0-9]|3[0,1])$/))
		newLocation = newLocation + '&startDate=' + startDate;
	if (endDate.match(/^\d{4}-(0[1-9]|1[1,2])-(0[1-9]|[1,2][0-9]|3[0,1])$/))
		newLocation = newLocation + '&endDate=' + endDate;
	if (parseInt(categoryId) > 0)
		newLocation = newLocation + '&categoryId=' + parseInt(categoryId);
	location.href = newLocation;
}

function expandToWindow(el) {
	var element = document.getElementById(el);
	var top = element.getBoundingClientRect().top;
	if (element.clientHeight < window.innerHeight) {
		element.style.height = (window.innerHeight - top - 60) + "px";
	}
}

function attendConference(conferenceId, e){
	e.getElementsByTagName("img")[0].src = contextPath + '/resources/images/loading.gif';
	var oldParentHref = e.getElementsByTagName("img")[0].parentNode.href;
	e.getElementsByTagName("img")[0].parentNode.href = "";
	$.ajax({
		url : contextPath + '/api/attendconference',
		data : 'conferenceId=' + conferenceId,
		type : 'get',
		contentType : 'text/xml;charset=UTF-8',
		async : false,
		error : function(jqXHR, textStatus, errorThrown){
			e.getElementsByTagName("img")[0].src = contextPath + '/resources/images/x.png';
			e.getElementsByTagName("img")[0].parentNode.href = oldParentHref;
		},
		success : function(res) {
			if (res == "true")
				e.getElementsByTagName("img")[0].src = contextPath + '/resources/images/v.png';
		}
	});
}

function addConference(){
	var spans = document.getElementById('addConferenceForm').getElementsByTagName('span');
	for (i = 0; i < spans.length; i++) {
		if (spans[i].className == 'error' || spans[i].className.indexOf('fielderror') != -1)
			spans[i].innerHTML = '';
	}
	$.ajax({
		url : contextPath + '/api/addConference',
		data : $('#addConferenceForm').serialize(),
		type : 'post',
		contentType : 'application/x-www-form-urlencoded',
		processData : false,
		async : false,
		success : function(res) {
			if (res.status == "SUCCESS")
				location.href = contextPath + '/participation.html?conferenceId=' + res.message;
			else if (res.status == "SERVERFAIL")
				document.getElementById('addConferenceWarning').innerHTML = 'Please try again';
			else {
				for (i = 0; i < res.result.length; i++) {
					document.getElementById(res.result[i].field
							+ 'Error').innerHTML = res.result[i].defaultMessage;
				}
			}
		}
	});
}

function showSummary(conferenceId){
	$.ajax({
		url : contextPath + '/api/getConferenceById',
		data : 'conferenceId=' + conferenceId,
		type : 'get',
		contentType : 'text/xml;charset=UTF-8',
		async : false,
		success : function(res) {
			$('#titleSummary').text(res.title);
			$('#creatorNameSummary').text(res.creatorName);
			$('#startDateSummary').text(res.startDate);
			$('#endDateSummary').text(res.endDate);
			$('#categoryNameSummary').text(res.categoryName);
			$('#locationSummary').text(res.location);
			$('#descriptionSummary').text(res.description);
			location.href = '#conferenceSummary';
		}
	});
}

function fillEditFormAndShow(conferenceId){
	var spans = document.getElementById('editConferenceForm').getElementsByTagName('span');
	for (i = 0; i < spans.length; i++) {
		if (spans[i].className == 'error' || spans[i].className.indexOf('fielderror') != -1)
			spans[i].innerHTML = '';
	}
	$.ajax({
		url : contextPath + '/api/getConferenceById',
		data : 'conferenceId=' + conferenceId,
		type : 'get',
		contentType : 'text/xml;charset=UTF-8',
		async : false,
		success : function(res) {
			document.getElementById('conferenceIdEdit').value = res.conferenceId;
			document.getElementById('titleEdit').value = res.title;
			document.getElementById('startDateEdit').value = res.startDate;
			document.getElementById('endDateEdit').value = res.endDate;
			$("#categoryIdEdit").find("option").each(function () {

			    if ($(this).val() == res.categoryName) {

			        $(this).prop("selected", true);
			    }
			});
			document.getElementById('locationEdit').value = res.location;
			document.getElementById('descriptionEdit').value  = res.description;
			location.href = '#editConferenceModal';
		}
	});
}

function editConference(){
	var spans = document.getElementById('editConferenceForm').getElementsByTagName('span');
	for (i = 0; i < spans.length; i++) {
		if (spans[i].className == 'error' || spans[i].className == 'fielderror')
			spans[i].innerHTML = '';
	}
	$.ajax({
		url : contextPath + '/api/editConference',
		data : $('#editConferenceForm').serialize(),
		type : 'post',
		contentType : 'application/x-www-form-urlencoded',
		processData : false,
		async : false,
		success : function(res) {
			if (res.status == "SUCCESS")
				location.href = contextPath + '/participation.html?conferenceId=' + res.message;
			else if (res.status == "SERVERFAIL")
				document.getElementById('editConferenceWarning').innerHTML = 'Please try again';
			else {
				for (i = 0; i < res.result.length; i++) {
					document.getElementById(res.result[i].field
							+ 'EditError').innerHTML = res.result[i].defaultMessage;
				}
			}
		}
	});
}

