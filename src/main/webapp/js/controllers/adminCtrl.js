(function(app) {
    app
        .controller('adminCtrl', function($scope, $http, $location, cfpLoadingBar) {



                $scope.toUpload = function() {
                    //cfpLoadingBar.start();
                    $location.url('/upload');
                    //cfpLoadingBar.complete()

                }
                
                
               

            }
        )
})(app);