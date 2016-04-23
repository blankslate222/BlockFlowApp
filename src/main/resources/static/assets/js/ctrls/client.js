cmpe.controller('clientCtrl', function($scope, $stateParams, $state, $log, $timeout,
		$rootScope, $http) {
	
	$scope.client={};
	
	$scope.doClientCreate = function() {
		var client={
				name: $scope.client.name,
				address: $scope.client.address
		}
		$http.post("/admin/client/create",client).success(function(data){
			
			if(data.name){
				$state.go('root.dash');
				//console.log(data);	
			}				
			else{
				//$scope.status = data.error;
			}
		}).error(function(err){
			//$scope.status = data.error;
			console.log("Error");
		});
	};
});
