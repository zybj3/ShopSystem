$(function() {
	var loading = false;
	// 分页允许返回的最大条数，超过此数则禁止访问后台
	var maxItems = 999;
	// 一页返回的最大条数
	var pageSize = 3;
	// 获取店铺列表的URL
	var listUrl = '/dianping/frontend/listshops';
	// 获取店铺类别列表以及区域列表的URL
	var searchDivUrl = '/dianping/frontend/listshoppageinfo';
	// 页码
	var pageNum = 1;
	// 从地址栏URL里尝试获取parent shop category id.
	var parentId = getQueryString('parentId');
	// 是否选择了子类
	var selectedParent = false;
	if (parentId) {
		selectedParent = true;
	}
	var areaId = '';
	var shopCategoryId = '';
	var shopName = '';
	// 渲染出店铺类别列表以及区域列表以供搜索

	var maxpage = 0;
	getSearchDivData();
	// 预先加载10条店铺信息
	addItems(pageSize, pageNum);

	/**
	 * 获取店铺类别列表以及区域列表信息
	 *
	 * @returns
	 */
	function getSearchDivData() {
		// 如果传入了parentId，则取出此一级类别下面的所有二级类别
		var url = searchDivUrl + '?' + 'parentId=' + parentId;
		$
		.getJSON(
			url,
			function (data) {
				if (data.success) {
					// 获取后台返回过来的店铺类别列表
					var shopCategoryList = data.shopCategoryList;
					var html = '';
					html += '<a href="#" class="button" data-category-id=""> All Categories </a>';
					// 遍历店铺类别列表，拼接出a标签集
					shopCategoryList
					.map(function (item, index) {
						html += '<a href="#" class="button" data-category-id='
							+ item.shopCategoryId
							+ '>'
							+ item.shopCategoryName
							+ '</a>';
					});
					// 将拼接好的类别标签嵌入前台的html组件里
					$('#shoplist-search-div').html(html);
					var selectOptions = '<option value="">All Areas</option>';
					// 获取后台返回过来的区域信息列表
					var areaList = data.areaList;
					// 遍历区域信息列表，拼接出option标签集
					areaList.map(function (item, index) {
						selectOptions += '<option value="'
							+ item.areaId + '">'
							+ item.areaName + '</option>';
					});
					// 将标签集添加进area列表里
					$('#area-search').html(selectOptions);
				}
			});
	}

	/**
	 * 获取分页展示的店铺列表信息
	 *
	 * @param pageSize
	 * @param pageIndex
	 * @returns
	 */

	var pageCount = 0;

	function addItems(pageSize, pageIndex) {
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
			+ pageSize + '&parentId=' + parentId + '&areaId=' + areaId
			+ '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
		loading = true;
		$.getJSON(url, function (data) {
			if (data.success) {
				maxItems = data.count;
				pageCount = data.pageCount;
				var html = '';
				data.shopList.map(function (item, index) {
					html += '' + '<div class="card" data-shop-id="'
						+ item.shopId + '">' + '<div class="card-header">'
						+ item.shopName + '</div>'
						+ '<div class="card-content">'
						+ '<div class="list-block media-list">' + '<ul>'
						+ '<li class="item-content">'
						+ '<div class="item-media">' + '<img src="'
						+ item.shopImg + '" width="44">' + '</div>'
						+ '<div class="item-inner">'
						+ '<div class="item-subtitle">' + item.shopDesc
						+ '</div>' + '</div>' + '</li>' + '</ul>'
						+ '</div>' + '</div>' + '<div class="card-footer">'
						+ '<p class="color-gray">'
						+ 'Last Edit ' + new Date(item.lastEditTime) + '</p>' + '<span>Click to View</span>' + '</div>'
						+ '</div>';
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				loading = false;
				getButtonStatus()
			}
		});
	}

	function getButtonStatus() {
		if (pageNum === 1) {
			$('#previousbutton').hide();
			if (pageCount > 1) {
				$('#nextbutton').show();
			} else {
				$('#nextbutton').hide();
			}
		} else if (pageNum > 1 && pageNum < pageCount) {
			$('#nextbutton').show();
			$('#previousbutton').show();
		} else if (pageCount >= pageCount) {
			$('#nextbutton').hide();
			$('#previousbutton').show();
		}
	}


	$('#next').click(function () {
		pageNum += 1;
		$('.list-div').empty();
		addItems(pageSize, pageNum);
	});

	$('#previous').click(function () {
		pageNum -= 1;
		$('.list-div').empty();
		addItems(pageSize, pageNum);
	});


	$('.shop-list').on('click', '.card', function (e) {
		var shopId = e.currentTarget.dataset.shopId;
		window.location.href = '/dianping/frontend/shopdetail?shopId=' + shopId;
	});

	$('#shoplist-search-div').on(
		'click',
		'.button',
		function (e) {
			if (parentId && selectedParent) {
				shopCategoryId = e.target.dataset.categoryId;
				if ($(e.target).hasClass('button-fill')) {
					$(e.target).removeClass('button-fill');
					shopCategoryId = '';
				} else {
					$(e.target).addClass('button-fill').siblings()
					.removeClass('button-fill');
				}
				$('.list-div').empty();
				// 重置页码
				pageNum = 1;
				getButtonStatus();
				addItems(pageSize, pageNum);
			} else {
				parentId = e.target.dataset.categoryId;
				if ($(e.target).hasClass('button-fill')) {
					$(e.target).removeClass('button-fill');
					parentId = '';
				} else {
					$(e.target).addClass('button-fill').siblings()
					.removeClass('button-fill');
				}
				// alert(parentId)
				$('.list-div').empty();
				pageNum = 1;
				getButtonStatus()
				addItems(pageSize, pageNum);
			}

		});


	$('#search').on('change', function (e) {
		totalCount = 0;
		curCount = 0;
		shopName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		getButtonStatus();
		addItems(pageSize, pageNum);
	});


	var availableTutorials = {
		data:["Subway", "Mcdonald", "Taco Bell", "Jersey Mike's"]
	};
	$('#search').easyAutocomplete(availableTutorials);

	$('#area-search').on('change', function() {
		totalCount = 0;
		curCount = 0;
		areaId = $('#area-search').val();
		$('.list-div').empty();
		pageNum = 1;
		getButtonStatus()
		addItems(pageSize, pageNum);
	});

	// 点击后打开右侧栏
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});

	// 初始化页面
	$.init();
});