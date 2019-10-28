$(function () {
	var loginUrl = '/shopsystem/user/verifyuser';


	$('#submit').click(function () {
		var personinfo = {}
		personinfo.name = $('#user-name').val();
		personinfo.password = $('#password').val();
		var formdata = new FormData;
		formdata.append("user", JSON.stringify(personinfo));
		$.ajax({
			url : loginUrl,
			type: 'POST',
			data: formdata,
			contentType: false,
			processData: false,
			cache: false,
			success:function (data) {
				if (data.success){
					alert("thank you")
					window.location = '/shopsystem/frontend/index';
				} else {
					alert("fail!" + data.errMsg)

				}

				$('#kaptcha_img').click();
			}
		})


	})
})