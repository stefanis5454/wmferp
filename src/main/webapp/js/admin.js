var app = angular.module('adminApp', ['ui.bootstrap', 'ngRoute', 'angular-loading-bar' , 'bootstrap.fileField'
]);
app.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
    $locationProvider.html5Mode(true);
    console.log("admin config");
    $routeProvider
        .when('/upload', {
            templateUrl: 'views/upload.html',
            controller: 'uploadCtrl'
        }).otherwise({
            redirectTo: '/upload'
        });
}]);