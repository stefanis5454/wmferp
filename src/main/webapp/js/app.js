var app = angular.module('erpApp', [ 'ui.bootstrap', 'ngRoute',
		'angular-loading-bar' // , 'ngFileUpload'
]);
app.config([ '$routeProvider', '$locationProvider',
		function($routeProvider, $locationProvider) {
			$locationProvider.html5Mode(true);
			$routeProvider.when('/search/:keyword', {
				templateUrl : 'views/search.html',
				controller : 'searchCtrl'
			}).when('/product/:product_id', {
				templateUrl : 'views/product.html',
				controller : 'productCtrl'
			}).when('/bundle/:bundle_id', {
				templateUrl : 'views/bundle.html',
				controller : 'bundleCtrl'
			}).otherwise({
				redirectTo : ''
			});

		} ]);

console.log("App Init");



