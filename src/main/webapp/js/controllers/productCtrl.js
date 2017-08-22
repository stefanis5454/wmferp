(function(app) {
	app.controller('productCtrl',

	function($scope, $http, $location, cfpLoadingBar) {
		$scope.tmLink = {
			"preffix" : "https://detail.tmall.com/item.htm?id=",
			"suffix" : ""
		}
		$scope.jdLink = {
			"preffix" : "https://item.jd.com/",
			"suffix" : ".html"
		}

		$scope.productColumnList = [ {
			'key' : 'product_id',
			'label' : '单品货号'
		},
		// { 'key': 'product_name', 'label': '名称' },
		{
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
		// { 'key': 'links', 'label': '链接' }
		];


		$scope.relatedBundleColumnList = [ {
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
		// { 'key': 'links', 'label': '链接' }
		];

		$scope.init = function() {
			var id = $location.path().replace('/product/', '');
			if (id == null) {
				swal('缺少参数：产品ID', '', 'warning');
				return;
			}

			$http.get("api/GetProductInfo?id=" + id).then(function(response) {
				console.log("Response from GetProductInfo?id=" + id)
				console.log(response.data);
				$scope.product = response.data.product;
				$scope.relatedBundles = response.data.relatedBundles;
				cfpLoadingBar.complete();
			});

		}

		$scope.init();

	})
})(app);