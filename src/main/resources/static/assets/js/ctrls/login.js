cmpe.controller('loginCtrl', function($scope, $stateParams, $state, $log, $timeout,
		$rootScope, $http) {
	
	$scope.user={};

	$scope.doLogin = function() {
		var user={
				email: $scope.user.email,
				password: $scope.user.password
		}
		$state.go('root.dash');
		//$scope.status = "Invalid Password";
		/*$http.post("/users/login",user).success(function(data){
			console.log(data);
			console.log()
			if(data.email){
				$state.go('root.dash');
			}				
			else
				$scope.status = data.error;
		}).error(function(err){
			console.log(err);
		})*/
	};
});
