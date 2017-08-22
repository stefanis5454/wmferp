(function(app) {
	app.controller('mainCtrl', function($scope, $rootScope, $http, $location,
			cfpLoadingBar) {
		$scope.search = function() {
			var key = $rootScope.searchKey;
			$rootScope.searchKey = "";
			if (key == null || key.trim() == '') {
				swal('请输入搜索关键词', '', 'warning');
				return;
			}

			cfpLoadingBar.start();
			console.log('searchKey: ' + key);

			$location.url('/search/s' + key);

			cfpLoadingBar.complete()

		}

		$scope.showAll = function() {
			$rootScope.searchKey = "";
			cfpLoadingBar.start();
			$location.url('/search/all');
			cfpLoadingBar.complete()

		}

		$scope.init = function() {
			console.log("init main ctrl");

			if ($location.search().search != null) {
				$location.url('/search/' + $location.search().search);

			}
			if ($location.search().product != null) {
				$location.url('/product/' + $location.search().product);

			}
			if ($location.search().bundle != null) {
				$location.url('/bundle/' + $location.search().bundle);

			}
		}

		$scope.init();
	})
})(app);