cmpe.controller('feedCtrl', function($scope, $rootScope, $http, $cookieStore) {
	
	$scope.getFeed = function() {
		feeds(function(feeds) {
			$scope.feedlist = feeds; 
			console.log("in feedback")
		});
	}

	var feeds = function(callback) {
		console.log('Hello World');
		// fetch the hashes and display
		var clientid = 21;
		$http.get("data/chainfeed/" + clientid).success(function(data) {
			console.log(data);
			var feedlist = [];
			var objs = data.controllerResponse.responseObject;
			for (var i = 0; i < objs.length; i++) {
				feedlist.push(objs[i]);
			}
			callback(feedlist);
		}).error(function(err) {
			// $scope.status = data.error;
			console.log("Error" + err);
		});
	};
	// init()
	$scope.getFeed();
});