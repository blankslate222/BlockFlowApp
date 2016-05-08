cmpe
.controller('feedCtrl', function($scope, $state, $rootScope, $http, $cookieStore) {
	
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

	$scope.getadminfeed = function() {
		$state.go('root.adminfeed');
	};
	
	
	
});

cmpe.controller('adminFeedCtrl', function($scope, $state, $rootScope, $http, $cookieStore) {
	
	$scope.feedList = [];
	var clientid = 21;
	$scope.getFeed = function() {
		
		console.log("inside get feed");
		$http.get("data/adminfeed").success(function(data) {
			console.log(data);
			var obj = data.controllerResponse;
		
			for (var key in obj) {
				console.log(key + " -- " + obj[key])
				var feedobj = {};
				var txnarr = obj[key];
				feedobj.client = key;
				feedobj.transactions = [];
				for (var i = 0; i < txnarr.length; i++) {
					feedobj.transactions.push(txnarr[i].transactionid);
				}
				//debugger;
				$scope.feedList.push(feedobj);
				
				//$scope.feedList.push(objs[i]);
			}
			console.log("Feed List");
			console.log($scope.feedList);

//			console.log($scope.feedList[0].transactionid);
		});
	};
	$scope.t={};
	$scope.load = function(t) {

		$http.get("/data/transactionData/"+t).success(function(data) {
				console.log(data.controllerResponse.responseObject);
				$scope.t=data.controllerResponse.responseObject;
		});
	};
	$scope.getFeed();
	
	
	$scope.getTransactionDetail = function() {
		console.log("going to get blockchain host!");
	};
	
	$scope.init = function (mutationHash) {
	  console.log(">>>"+mutationHash);
	};
	
	
});

cmpe.controller('deptFeedCtrl', function($scope, $state, $rootScope, $http, $cookieStore) {
	
	$scope.feedList = [];
	$scope.getFeed = function() {
		
		console.log("inside dept feed");
		$http.get("data/deptfeed").success(function(data) {
			console.log(data);
			var obj = data.controllerResponse.deptfeed;
			var dept = data.controllerResponse.department;
			$scope.department_name = dept.name;
			for (var feed in obj) {
				$scope.feedList.push(obj[feed]);
			}
		
			console.log("Feed List");
			console.log($scope.feedList);

//			console.log($scope.feedList[0].transactionid);
		});
	};
	$scope.t={};
	$scope.load = function(t) {

		$http.get("/data/transactionData/"+t).success(function(data) {
				console.log(data.controllerResponse.responseObject);
				$scope.t=data.controllerResponse.responseObject;
		});
	};
	$scope.getFeed();
	
	
	$scope.getTransactionDetail = function() {
		console.log("going to get blockchain host!");
	};
	
	$scope.init = function (mutationHash) {
	  console.log(">>>"+mutationHash);
	};
	
	
});