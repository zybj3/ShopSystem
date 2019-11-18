$(function() {
	var shopId = getQueryString('shopId');
	var shopName = getQueryString('shopName');
	var shopInfoUrl = '/dianping/shopadmin/getshopmanagementinfo?shopId=' + shopId;
	alert(shopId)
	$.getJSON(shopInfoUrl, function(data) {
		if (data.redirect) {
			window.location.href = data.url;
		} else {
			if (data.shopId != undefined && data.shopId != null) {
				shopId = data.shopId;
			}
			$('#shopInfo')
			.attr('href', '/dianping/shopadmin/shopoperation?shopId=' + shopId);

			$('#shopName').text('Your current shops is  ' + shopName)

		}
	});
});