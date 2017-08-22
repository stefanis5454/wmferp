(function(app) {
	app.controller('searchCtrl', function($scope, $http, $location,
			cfpLoadingBar) {
		$scope.tmLink = {
			"preffix" : "https://detail.tmall.com/item.htm?id=",
			"suffix" : ""
		}
		$scope.jdLink = {
			"preffix" : "https://item.jd.com/",
			"suffix" : ".html"
		}

		$scope.searchedProductColumnList = [ {
			'key' : 'product_id',
			'label' : '单品货号'
		}, {
			'key' : 'product_name',
			'label' : '名称'
		}, {
			'key' : 'category',
			'label' : '类别'
		}, {
			'key' : 'product_line',
			'label' : '产品线'
		}, {
			'key' : 'status',
			'label' : '状态'
		}, {
			'key' : 'product_rrp',
			'label' : '原价'
		}, {
			'key' : 'coo',
			'label' : '产地'
		},
		// { 'key': 'links', 'label': '链接' }
		];

		$scope.searchedBundleColumnList = [ {
			'key' : 'bundle_tm_id',
			'label' : '天猫货号'
		}, {
			'key' : 'bundle_jd_id',
			'label' : '京东货号'
		}, {
			'key' : 'bundle_name',
			'label' : '名称'
		}, {
			'key' : 'total_rrp',
			'label' : '总价'
		}, {
			'key' : 'coo',
			'label' : '产地'
		},
		// { 'key': 'product_count', 'label': '单品数量' },
		// { 'key': 'links', 'label': '链接' }
		];

		$scope.init = function() {
			var path = $location.path();
			var params = {};
			if (path == '/search/all') {
				$scope.titleProduct = "所有单品";
				$scope.titleBundle = "所有套组";
				// var url = "api/SearchProducts";
			} else {
				var keyword = path.replace('/search/s', '');
				$scope.titleProduct = "相关单品";
				$scope.titleBundle = "相关套组";
				params.keyword = keyword;
				// var url = "api/SearchProducts?keyword=" + keyword
			}

			var req = {
				"method" : 'POST',
				"url" : 'api/SearchProducts',
				"params" : params
			};

			$http(req).then(function(response) {
				console.log("Response from api/SearchProducts: " + keyword)
				console.log(response.data);
				$scope.products = response.data.products;
				$scope.bundles = response.data.bundles;
				cfpLoadingBar.complete();
			});

		}
		$scope.init();

	})
})(app);