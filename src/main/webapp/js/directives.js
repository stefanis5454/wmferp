app.directive('itemList', function($location) {
	return {
		restrict : 'E',
		// replace : 'true',
		scope : {
			itemType : '@',
			columnList : '=',
			items : '=',
			tmLink : '=',
			jdLink : '=',
			pageSize : '@'

		},
		templateUrl : 'templates/itemList.html',
		link : function(scope, element, attrs) {
			console.log("item-list init");

			scope.openItem = function(item) {
				
				$location.url('/' + scope.itemType + '/'
						+ item[scope.itemType + '_id']);
			}

		}
	};
});
app.directive('itemDetail', function($location) {
	return {
		restrict : 'E',
		// replace : 'true',
		scope : {
			itemType : '@',
			columnList : '=',
			item : '=',
			tmLink : '=',
			jdLink : '='

		},
		templateUrl : 'templates/itemDetail.html',
		link : function(scope, element, attrs) {
			console.log("item-detail init");


		}
	};
});

console.log("Directives intialized");