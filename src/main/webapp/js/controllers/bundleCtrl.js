(function(app) {
	app.controller('bundleCtrl', function($scope, $http, $location,
			cfpLoadingBar) {
		$scope.tmLink = {
			"preffix" : "https://detail.tmall.com/item.htm?id=",
			"suffix" : ""
		}
		$scope.jdLink = {
			"preffix" : "https://item.jd.com/",
			"suffix" : ".html"
		}

		$scope.bundleColumnList = [ {
			'key' : 'bundle_tm_id',
			'label' : '天猫货号'
		}, {
			'key' : 'bundle_jd_id',
			'label' : '京东货号'
		},

		{
			'key' : 'total_rrp',
			'label' : '总价'
		}, {
			'key' : 'promotion_price',
			'label' : '活动价'
		}, {
			'key' : 'juhuasuan_price',
			'label' : '聚划算价'
		}, {
			'key' : 'daily_price',
			'label' : '平日价'
		}, {
			'key' : 'coo',
			'label' : '产地'
		},
		// { 'key': 'product_count', 'label': '单品数量' },
		// { 'key': 'links', 'label': '链接' }
		];

		$scope.relatedProductColumnList = [ {
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

		$scope.init = function() {
			var id = $location.path().replace('/bundle/', '');
			if (id == null) {
				swal('缺少参数：套组ID', '', 'warning');
				return;
			}

			$http.get("api/GetBundleInfo?id=" + id).then(function(response) {
				console.log("Response from GetBundleInfo?id=" + id)
				console.log(response.data);
				$scope.bundle = response.data.bundle;
				$scope.relatedProducts = response.data.relatedProducts;
				cfpLoadingBar.complete();
			});

		}

		$scope.init();
	})
})(app);