cmpe.controller('clientCtrl', function($scope, $stateParams, $state, $log, $timeout,
		$rootScope, $http) {
	
	$scope.clients=[];
	
	$scope.doClientCreate = function() {
		var client={
				name: $scope.client.name,
				address: $scope.client.address
		}
		$http.post("/admin/client/create",client).success(function(data){
			if(data.controllerResponse.responseObject){
				$scope.clients.push(data.controllerResponse.responseObject);
			}				
			else{
			}
		}).error(function(err){
			//$scope.status = data.error;
			console.log("Error");
		});
	};
	
	$scope.getClients = function() {
		$http.get("/data/clients").success(function(data){
			var objs=data.controllerResponse.responseObject;
			for(var i=0;i<objs.length;i++){
				$scope.clients.push(objs[i]);
			}
			console.log($scope.clients);
		});
	};
	$scope.getClients();
});

cmpe.controller('clientDetailsCtrl', function($scope, $stateParams, $state, $log, $timeout,
		$rootScope, $http) {
	$scope.client={};
	$scope.getClient = function() {
		$http.get("/data/client/" + $stateParams.clientID).success(function(data){
			if(data.controllerResponse.responseObject){
				var objs=data.controllerResponse.responseObject;
				$scope.client=objs;
				console.log($scope.client);
			}
			//console.log(data.controllerResponse.responseObject);
		});
	};
	$scope.getClient();
	
});