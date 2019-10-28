$(function () {
	var userinfoUrl ='/shopsystem/userinfo/getuserinfo';
	getUserInfo();
	function getUserInfo() {
		$.getJSON(userinfoUrl, function (data) {
			if (data.success){
				var userType = data.currentUser.userType;

				var html = userType==1
					?
					'<div class="content-block">' +
					'<p>' +'Hello ' +  data.currentUser.name + '</p>' +	'<p>' +
						'<a href="/shopsystem/user/login" usertype="1" class="close-panel" id="log-out">Log out</a> ' +
					'</p>' +
					'</div>'
					:
					'<div class="content-block">' +
					'<p>' +'Hello ' +  data.currentUser.name + '</p>' +	'<p>' +
					'<a href="/shopsystem/user/login"  class="close-panel" id="log-out">Log out</a> ' +
					'</p>' + '<p>' +
				'<a href="/shopsystem/shopadmin/shoplist" class="close-panel" >ShopManagement</a> ' +
				'</p>' +
					'</div>';
				$('#panel-right-demo').html(html)
			} else {
				var html = '<div class="content-block"><p><a href="/shopsystem/user/login" usertype="1" class="close-panel" id="log-out">Log in</a> \'</p> /div>';
				$('#panel-right-demo').html(html)
			}
		})
	}
})