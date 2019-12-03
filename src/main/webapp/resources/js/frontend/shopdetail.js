$(function () {
	var loading = false;

	var maxItems = 20;

	var pageSize = 3;

	var listUrl = '/dianping/frontend/listproductsbyshopid';

	var reviewUrl = '/dianping/frontend/getreview';

	var submitUrl = '/dianping/frontend/addreview';
	var pageNum = 1;

	var shopId = getQueryString('shopId');
	var productCategoryId = '';
	var productName = '';

	var searchDivUrl = '/dianping/frontend/listshopdetailinfo?shopId=' + shopId;

	getSearchDivData();
	addItems(pageSize, pageNum);


	function getSearchDivData() {
		var url = searchDivUrl;
		$
		.getJSON(
			url,
			function (data) {
				if (data.success) {
					var shop = data.shop;
					$('#shop-cover-pic').attr('src', shop.shopImg);
					$('#shop-update-time').html(new Date(shop.lastEditTime))
					$('#shop-name').html(shop.shopName);
					$('#shop-desc').html(shop.shopDesc);
					$('#shop-addr').html(shop.shopAddr);
					$('#shop-phone').html(shop.phone);

					var productCategoryList = data.productCategoryList;
					var html = '';

					productCategoryList
					.map(function (item, index) {
						html += '<a href="#" class="button" data-product-search-id='
							+ item.productCategoryId
							+ '>'
							+ item.productCategoryName
							+ '</a>';
					});

					$('#shopdetail-button-div').html(html);
				}
			});
	}

	function addItems(pageSize, pageIndex) {

		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
			+ pageSize + '&productCategoryId=' + productCategoryId
			+ '&productName=' + productName + '&shopId=' + shopId;

		loading = true;

		$.getJSON(url, function (data) {
			if (data.success) {

				maxItems = data.count;
				var html = '';

				data.productList.map(function (item, index) {
					html += '' + '<div class="card" data-product-id='
						+ item.productId + '>'
						+ '<div class="card-header">' + item.productName
						+ '</div>' + '<div class="card-content">'
						+ '<div class="list-block media-list">' + '<ul>'
						+ '<li class="item-content">'
						+ '<div class="item-media">' + '<img src="'
						+ item.imgAddr + '" width="44">' + '</div>'
						+ '<div class="item-inner">'
						+ '<div class="item-subtitle">' + item.productDesc
						+ '</div>' + '</div>' + '</li>' + '</ul>'
						+ '</div>' + '</div>' + '<div class="card-footer">'
						+ '<p class="color-gray">'
						+ new Date(item.lastEditTime)
						+ 'Update</p>' + '<span>Click to View</span>' + '</div>'
						+ '</div>';
				});

				$('.list-div').append(html);

				var total = $('.list-div .card').length;

				if (total >= maxItems) {
					// 隐藏提示符
					$('.infinite-scroll-preloader').hide();
				} else {
					$('.infinite-scroll-preloader').show();
				}

				pageNum += 1;

				loading = false;

				$.refreshScroller();
			}
		});
	}

	$('#review-button').click(function () {
		var url = reviewUrl + '?' + 'shopId=' + shopId;
		var html = '';
		$('.list-div').empty();
		$.getJSON(url, function (data) {
			if (data.success) {
				data.reviewList.map(function (item, index) {
					html += '' + '<div class="card">' + '<div class="card-content" style="height:121px;">' +'<h4>'+ '<font size="4" color="#4169e1">' +
						item.reviewText + '</font>' +'</h4>'+ '</div>' + '<div class="card-footer" style="height:50px;">'
						+  '<h5>' + 'Edit by ' + item.personInfo.name  +'</h5>'+ '<h5>' + 'Edit time:' + new Date(item.editTime) +'</h5>' + '</div>'
						+ '</div>';
				})
			}

			$('.list-div').html(html);

			$('.infinite-scroll-preloader').hide();

		})
	})
	
	$('#submit').click(function () {
		var review = {};
		review.reviewText = $('#review-text').val();
		review.shopId = shopId;

		var formData = new FormData;
		formData.append("reviewStr", JSON.stringify(review));

		$.ajax({
			url: submitUrl,
			type: 'POST',
			data:formData,
			contentType: false,
			processData: false,
			cache: false,
			success:function (data) {
				if (data.success){
					alert("Thank you for your submission")
				} else {
					alert("Submission Failed!  " + data.errMsg);
				}
			}
		})
	})

	$(document).on('infinite', '.infinite-scroll-bottom', function () {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

	$('#shopdetail-button-div').on(
		'click',
		'.button',
		function (e) {

			productCategoryId = e.target.dataset.productSearchId;
			if (productCategoryId) {

				if ($(e.target).hasClass('button-fill')) {
					$(e.target).removeClass('button-fill');
					productCategoryId = '';
				} else {
					$(e.target).addClass('button-fill').siblings()
					.removeClass('button-fill');
				}
				$('.list-div').empty();
				pageNum = 1;
				addItems(pageSize, pageNum);
			}
		});

	$('.list-div').on(
		'click',
		'.card',
		function (e) {
			var productId = e.currentTarget.dataset.productId;
			window.location.href = '/dianping/frontend/productdetail?productId='
				+ productId;
		});

	$('#search').on('change', function (e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	$(document).on('click', '.open-about', function () {
		$.popup('.popup-about');

	});

	$('#me').click(function () {
		$.openPanel('#panel-right-demo');
	});

	$.init();
});
