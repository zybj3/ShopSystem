$(function () {
	var registerUrl = '/shopsystem/user/adduser';

	var userType = 1;


	$('#shop-owner-selection2').click(function () {
		userType = 2;
	})

	$('#submit').click(function () {
		var personinfo = {}
		personinfo.name = $('#user-name').val();
		alert(personinfo.name)
		personinfo.email = $('#email').val();
		personinfo.gender = $('#gender').find('option').not(function () {
			return !this.selected;
		}).data('id');
		personinfo.password = $('#password').val();
		personinfo.userType = userType;
		var formdata = new FormData;
		formdata.append("verifyCodeActual", $('#code').val());
		formdata.append("user", JSON.stringify(personinfo));
		$.ajax({
			url : registerUrl,
			type: 'POST',
			data: formdata,
			contentType: false,
			processData: false,
			cache: false,
			success:function (data) {
				if (data.success){
					alert("thank you")
					window.location = '/shopsystem/user/login';
				} else {
					alert("fail!" + data.errMsg)
					$('#kaptcha_img').click();

				}
			}
		})


	})
})