cmpe.controller('feedCtrl', function($scope, $rootScope, $http, $cookieStore) {
	
	$scope.feedList = [];
	var clientid = 21;
	$scope.getFeed = function() {
		console.log("inside get feed");
		$http.get("data/chainfeed/" + clientid).success(function(data) {
			var objs = data.controllerResponse.responseObject;
			for (var i = 0; i < objs.length; i++) {
				$scope.feedList.push(objs[i]);
				//$scope.feedList.push(objs[i]);
			}
			console.log(objs);
			console.log("Feed List");
			console.log($scope.feedList);
			console.log($scope.feedList[0].transactionid);
		});
	};
	$scope.getFeed();

/*	$scope.getFeed = function() {
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
	$scope.getFeed();*/
});